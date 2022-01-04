package com.example.demo.services;

import com.example.demo.model.Utente;
import com.example.demo.repositories.AlbumRepository;
import com.example.demo.repositories.UtenteRepository;
import com.example.demo.support.exceptions.MailGiaEsistenteException;
import com.example.demo.support.exceptions.UtenteNonTrovatoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import java.util.List;


@Service
public class UtenteService {
    @Autowired
    private UtenteRepository utenteRepository;


    @Transactional(readOnly=false)
    public void registraUtente(Utente utente) throws MailGiaEsistenteException {
        if(utenteRepository.existsByEmail(utente.getEmail())){
            throw new MailGiaEsistenteException();
        }
        utenteRepository.save(utente);

    }
    @Transactional(readOnly = true)
    public Utente getUtentePerEmail(String email) throws RuntimeException{
        if(!utenteRepository.existsByEmail(email)){
            throw new RuntimeException("Email non esistente");
        }
        return utenteRepository.findByEmail(email);
    }
    @Transactional(readOnly = true)
    public List<Utente>getUtentePerNome(String nome){
         return utenteRepository.findByNome(nome);
    }
    @Transactional(readOnly = true)
    public Utente getUtentePerId(int id){
        return utenteRepository.findById(id);
    }
    public Utente getUtentePerTelefono(String telefono){
        return utenteRepository.findByTelefono(telefono);
    }
    @Transactional(readOnly = false)
    public void modificaUtente(Utente utente) throws UtenteNonTrovatoException{
        if(! utenteRepository.existsById(utente.getId())){
            throw new UtenteNonTrovatoException();
        }
        Utente u = utenteRepository.findById(utente.getId());
        u.setNome(utente.getNome());
        u.setCognome(utente.getCognome());
        u.setTelefono(utente.getTelefono());
        u.setEmail(utente.getEmail());
        u.setIndirizzo(utente.getIndirizzo());
        utenteRepository.save(u);

    }
    //Vedere per i prodotti

}
