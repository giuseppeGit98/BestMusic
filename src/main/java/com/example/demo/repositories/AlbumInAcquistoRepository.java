package com.example.demo.repositories;

import com.example.demo.model.Acquisto;
import com.example.demo.model.Album;
import com.example.demo.model.AlbumInAcquisto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumInAcquistoRepository extends JpaRepository<AlbumInAcquisto,Integer> {
    boolean existsByAcquistoAndAlbum(Acquisto a , Album ab);
    AlbumInAcquisto findByAcquistoAndAlbum(Acquisto a,Album ab);
    List<AlbumInAcquisto> findByAcquisto(Acquisto a);
}