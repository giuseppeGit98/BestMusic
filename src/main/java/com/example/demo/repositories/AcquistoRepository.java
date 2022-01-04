package com.example.demo.repositories;

import com.example.demo.model.Acquisto;
import com.example.demo.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcquistoRepository extends JpaRepository<Acquisto,Integer> {
    Acquisto findById(int id);
    List<Acquisto> findByUtente(Utente utente);
    boolean existsById(int id);
}
