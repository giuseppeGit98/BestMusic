package com.example.demo.repositories;

import com.example.demo.model.Carrello;
import com.example.demo.model.Utente;
import com.example.demo.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarrelloRepository extends JpaRepository<Carrello,Integer> {
    Carrello findById(int id);
    Carrello findByUtenteAndAlbum(Utente utente,Album album);
    List<Carrello> findByUtente(Utente utente);
    boolean existsByUtenteAndAlbum(Utente utente,Album album);
    boolean existsById(int id);
}
