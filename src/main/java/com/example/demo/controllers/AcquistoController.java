package com.example.demo.controllers;

import com.example.demo.model.Acquisto;
import com.example.demo.model.AlbumInAcquisto;
import com.example.demo.model.Utente;
import com.example.demo.services.AcquistoAndCarrelloService;
import com.example.demo.support.ResponseMessage;
import com.example.demo.support.exceptions.AcquistoNonEsistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/purchases")
@CrossOrigin("http://localhost:4200")
public class AcquistoController {

    @Autowired
    private AcquistoAndCarrelloService acquistoService;

    @PostMapping()
    public ResponseEntity acquista(@RequestBody Utente utente) {
        try {
            Acquisto ac = acquistoService.addAcquisto(utente.getEmail());
            
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseMessage("Acquisto fallito"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseMessage("Acquisto effettuato"), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getByUtente(@RequestParam(value = "email",defaultValue = "") String email) {
        List<Acquisto> a = acquistoService.mostraAcquistoPerUtente(email);
        if (a.size() <= 0)
            return new ResponseEntity<>(new ResponseMessage("Non ha fatto nessun acquisto"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(a, HttpStatus.OK);
    }

    @GetMapping("/acquisto/{id}")
    public ResponseEntity getAlbumInAcquistoByAcquisto(@PathVariable("id") int id) {
        List<AlbumInAcquisto> ret = null;
        try {
            ret = acquistoService.mostraAlbumInAcquistoByAcquisto(id);
        } catch (AcquistoNonEsistenteException e) {
            return new ResponseEntity<>(new ResponseMessage("Errore"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }
}

