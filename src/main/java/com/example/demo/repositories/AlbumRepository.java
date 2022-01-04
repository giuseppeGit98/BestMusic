package com.example.demo.repositories;

import com.example.demo.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AlbumRepository extends JpaRepository<Album,Integer> {
    Album findById(int id);
    List<Album> findByGenere(String genere);
    List<Album> findByAutore(String autore);
    List<Album> findByNome(String nome);
    List<Album>findByAnno(int anno);
    List<Album>findByGenereAndPrezzo(String genere, double prezzo);
    boolean existsById(int id);
    boolean existsByGenereAndPrezzo(String genere,double prezzo);
    boolean existsByAutore(String nomeA);
    boolean existsByAnno(int anno);
    boolean existsByGenere(String genere);
    boolean existsByNomeAndAutore(String nome,String Autore);
    boolean existsByNome(String nome);
}
