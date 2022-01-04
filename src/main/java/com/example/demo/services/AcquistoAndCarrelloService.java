package com.example.demo.services;

import com.example.demo.model.*;
import com.example.demo.repositories.*;
import com.example.demo.support.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Service
public class AcquistoAndCarrelloService {
    @Autowired
    CarrelloRepository carrelloRepository;
    @Autowired
    UtenteRepository utenteRepository;
    @Autowired
    AlbumRepository albumRepository;
    @Autowired
    AcquistoRepository acquistoRepository;
    @Autowired
    AlbumInAcquistoRepository albumInAcquistoRepository;



    @Transactional(readOnly = false)
    public Carrello  addCarrello (Carrello linea) throws RuntimeException {
        if(!utenteRepository.existsByEmail(linea.getUtente().getEmail()))
            throw new RuntimeException("Utente Non Trovato");
        if(!albumRepository.existsById(linea.getAlbum().getId()))
            throw new RuntimeException("Album non trovato");
        if(linea.getQuantitaAlbum()== null) linea.setQuantitaAlbum(1);
        if(linea.getQuantitaAlbum()<0){
            throw new RuntimeException("La quantità deve essere > 0");
        }

        if(linea.getQuantitaAlbum()==0) {
            linea.setQuantitaAlbum(1);
        }


        Utente utente= utenteRepository.findByEmail((linea.getUtente().getEmail()));
        Album album= albumRepository.findById(linea.getAlbum().getId());
        if(linea.getQuantitaAlbum()>album.getQuantita())
            throw new RuntimeException("Quantità non disponibile");

        Carrello lineaCarrello= new Carrello();

        if(carrelloRepository.existsByUtenteAndAlbum(utente, album)){
            lineaCarrello = carrelloRepository.findByUtenteAndAlbum(utente,album);
            lineaCarrello.setQuantitaAlbum(lineaCarrello.getQuantitaAlbum()+linea.getQuantitaAlbum());
            lineaCarrello.setPrezzo(lineaCarrello.getPrezzo()+linea.getQuantitaAlbum()*album.getPrezzo());
            if(lineaCarrello.getQuantitaAlbum()>album.getQuantita())
                throw new RuntimeException("Quantità non disponibile");
        }

       else  {
                lineaCarrello.setAlbum(album);
                lineaCarrello.setUtente(utente);

                List<Carrello> line= utente.getCarrelli();
                if(line==null) line= new LinkedList<>();
                line.add(lineaCarrello);
                utente.setCarrelli(line);
                List<Carrello> line2 = album.getCarrelli();
                if( line2== null) line2= new LinkedList<>();
                line2.add(lineaCarrello);
                album.setCarrelli(line2);


                lineaCarrello.setQuantitaAlbum(linea.getQuantitaAlbum());
                lineaCarrello.setPrezzo(album.getPrezzo()*lineaCarrello.getQuantitaAlbum());
       }

            carrelloRepository.save(lineaCarrello);
            return lineaCarrello;
    }

    @Transactional(readOnly = false)
    public void removeCarrello(Carrello c) throws RuntimeException{
        if(!(carrelloRepository.existsById(c.getId())))
            throw new RuntimeException("Carrello inesistente");
        Utente utente = utenteRepository.findById(c.getUtente().getId());
        Album prodotto = albumRepository.findById(c.getAlbum().getId());
        if(!(carrelloRepository.existsByUtenteAndAlbum(utente,prodotto)))
            throw new RuntimeException("Carrello inesistente");
        Carrello carrello = carrelloRepository.findByUtenteAndAlbum(utente,prodotto);
        utente.getCarrelli().remove(carrello);
        prodotto.getCarrelli().remove(carrello);
        carrelloRepository.delete(carrello);
    }
    @Transactional(readOnly = true)
    public List<Carrello>mostraCarrelloPerUtente(String email) throws UtenteNonTrovatoException {
        if(! utenteRepository.existsByEmail(email)) throw new UtenteNonTrovatoException();
        Utente u = utenteRepository.findByEmail(email);
        return carrelloRepository.findByUtente(u);
    }
    @Transactional(readOnly = true)
    public Carrello mostraCarrelloPerIdCarrello(int id) throws CarrelloNonEsistenteException{
        if(! carrelloRepository.existsById(id)){
            throw new CarrelloNonEsistenteException();
        }
        return carrelloRepository.findById(id);
    }




    public Acquisto addAcquisto(String email) throws RuntimeException{
        if(!utenteRepository.existsByEmail(email))
            throw new RuntimeException("Utente non valido");
        Utente utente = utenteRepository.findByEmail(email);
        Collection<Carrello> lineeCarrello = carrelloRepository.findByUtente(utente);
        if(lineeCarrello.size()<= 0) throw new RuntimeException("Inserisci elementi nel carrello");
        Acquisto ordine = new Acquisto();
        ordine.setUtente(utente);
        acquistoRepository.save(ordine);
        ordine = acquistoRepository.findById(ordine.getId());
        for(Carrello c : lineeCarrello){
            AlbumInAcquisto p = new AlbumInAcquisto();
            p.setAcquisto(ordine);
            p.setQuantita(c.getQuantitaAlbum());
            p.setPrezzo(c.getPrezzo());
            addAlbumInAcquisto(p, ordine, c.getAlbum());
            removeCarrello(c);
        }
        double totale=0;
        for(AlbumInAcquisto po : ordine.getOrdine()){
            totale += po.getPrezzo();
        }
        ordine.setSommaTotale(totale);
        acquistoRepository.save(ordine);
        return ordine;
    }




    public void addAlbumInAcquisto(AlbumInAcquisto p, Acquisto o, Album prodotto) throws RuntimeException{
        if(albumInAcquistoRepository.existsByAcquistoAndAlbum(o,prodotto))
            throw new RuntimeException("Già presente");
        if(!albumRepository.existsById(prodotto.getId()))
            throw new RuntimeException("Album non presente");
        Album pro=albumRepository.findById(prodotto.getId());
        if(p.getQuantita()<0||(p.getQuantita()!=0 && p.getQuantita()>pro.getQuantita()))
            throw new RuntimeException("Quantità non disponibile");
        if(p.getPrezzo()==0 || p.getPrezzo()!=((pro.getPrezzo())*p.getQuantita()))
            throw new RuntimeException("Prezzo errato");
        AlbumInAcquisto pio=new AlbumInAcquisto();
        pio.setAcquisto(o);
        pio.setQuantita(p.getQuantita());
        List<AlbumInAcquisto> listaOrdine =o.getOrdine();
        if(listaOrdine==null){
            listaOrdine=new LinkedList<>();
        }
        listaOrdine.add(pio);
        o.setOrdine(listaOrdine);
        pio.setAlbum(pro);
        Collection<AlbumInAcquisto> listaProdotto=prodotto.getAlbum_in_acquisti();
        if(listaProdotto==null)
            listaProdotto=new LinkedList<>();
        listaProdotto.add(pio);
        prodotto.setAlbum_in_acquisti(listaProdotto);
        pro.setQuantita(pro.getQuantita()-pio.getQuantita());
        pio.setPrezzo(pro.getPrezzo()*pio.getQuantita());
        albumInAcquistoRepository.save(pio);
    }


    @Transactional(readOnly = true)
    public List<AlbumInAcquisto>mostraAlbumInAcquistoByAcquisto(int id) throws AcquistoNonEsistenteException {
        if(!acquistoRepository.existsById(id)) throw new AcquistoNonEsistenteException();
        Acquisto a = acquistoRepository.findById(id);
        List<AlbumInAcquisto> lista = albumInAcquistoRepository.findByAcquisto(a);
        return lista;
    }
    @Transactional(readOnly = true)
    public Acquisto mostraAcquistoById(int id) throws AcquistoNonEsistenteException {
        if(!acquistoRepository.existsById(id)){
            throw new AcquistoNonEsistenteException();
        }
        return acquistoRepository.findById(id);
    }
    @Transactional(readOnly = true)
    public List<Acquisto>mostraAcquistoPerUtente(String email){
        return acquistoRepository.findByUtente(utenteRepository.findByEmail(email));
    }

    @Transactional(readOnly = true)
    public List<Acquisto> getAllAcquisti(){
        return acquistoRepository.findAll();
    }

}
