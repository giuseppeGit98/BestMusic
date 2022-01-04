package com.example.demo.repositories;

import com.example.demo.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtenteRepository extends JpaRepository<Utente,Integer> {
    Utente findById(int id);
    List<Utente> findByNome(String nome);
    Utente findByEmail(String email);
    Utente findByTelefono(String telefono);
    boolean existsById(int id);
    boolean existsByEmail(String email);
    boolean existsByTelefono(String telefono);


}
