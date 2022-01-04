package com.example.demo.services;

import com.example.demo.model.Album;
import com.example.demo.repositories.AlbumRepository;
import com.example.demo.repositories.UtenteRepository;
import com.example.demo.support.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlbumService {
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private UtenteRepository utenteRepository;


    @Transactional(readOnly = false)
    public void inserisciAlbum(Album album) throws AlbumGiaEsistenteException, GenereNonEsistenteException {
        if(albumRepository.existsById(album.getId()) || albumRepository.existsByNomeAndAutore(album.getNome(),album.getAutore())){
            throw new AlbumGiaEsistenteException();
        }
        Album a = albumRepository.save(album);

    }
    @Transactional(readOnly = true)
    public List<Album> getAlbumPerGenere(String nomeGenere) throws GenereNonEsistenteException{
        if(!albumRepository.existsByGenere(nomeGenere)){
            throw new GenereNonEsistenteException();
        }

        return albumRepository.findByGenere(nomeGenere);
    }
    @Transactional(readOnly = true)
    public List<Album>getAlbumPerArtista(String nomeArtista)throws ArtistaNonEsistenteException {
        if(!albumRepository.existsByAutore(nomeArtista)){
            throw new ArtistaNonEsistenteException();
        }
        else{
            return albumRepository.findByAutore(nomeArtista);
        }

    }
    @Transactional(readOnly = true)
    public List<Album> getAlbumPerAnno(int anno) throws DataNonEsistenteException {
        if(!albumRepository.existsByAnno(anno)){
            throw new DataNonEsistenteException();
        }else{
            return albumRepository.findByAnno(anno);
        }
    }
    @Transactional(readOnly = true)
    public List<Album>showAllAlbums(){
        return albumRepository.findAll();
    }
    @Transactional(readOnly = false)
    public Album rimuoviAlbum(Album a) throws AlbumNonEsistenteException {
        if(!albumRepository.existsById(a.getId())){
            throw new AlbumNonEsistenteException();
        }
        else{
            Album del = albumRepository.findById(a.getId());
            albumRepository.delete(del);
            return del;
        }
    }
    @Transactional(readOnly = true)
    public List<Album> getAlbumByGenereAndPrezzo(String genere,double prezzo) throws AlbumNonEsistenteException {
        if(!albumRepository.existsByGenereAndPrezzo(genere,prezzo)){
            throw new AlbumNonEsistenteException();
        }else{
            return albumRepository.findByGenereAndPrezzo(genere,prezzo);
        }
    }
    @Transactional(readOnly = true)
    public List<Album> getAlbumByNome(String nome) throws AlbumNonEsistenteException{
        if(!albumRepository.existsByNome(nome)){
            throw new AlbumNonEsistenteException();
        }
        return albumRepository.findByNome(nome);
    }


}
