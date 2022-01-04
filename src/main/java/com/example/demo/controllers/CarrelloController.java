package com.example.demo.controllers;

import com.example.demo.model.Carrello;
import com.example.demo.services.AcquistoAndCarrelloService;
import com.example.demo.support.ResponseMessage;
import com.example.demo.support.exceptions.AlbumNonEsistenteException;
import com.example.demo.support.exceptions.CarrelloNonEsistenteException;
import com.example.demo.support.exceptions.QuantitaNonDisponibileException;
import com.example.demo.support.exceptions.UtenteNonTrovatoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/carrelli")
public class CarrelloController {
    @Autowired
    private AcquistoAndCarrelloService carrelloService;

    @PostMapping
    public ResponseEntity addCarrello(@RequestBody  Carrello carrello){
        try{
            carrelloService.addCarrello(carrello);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseMessage("Aggiunta non riuscita"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new ResponseMessage("Aggiunta effettuata"),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeCarrello(@PathVariable("id") Integer id){
        try{
            Carrello c = carrelloService.mostraCarrelloPerIdCarrello(id);
            carrelloService.removeCarrello(c);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseMessage("Rimozione non riuscita"),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseMessage("Rimozione riuscita"),HttpStatus.OK);
    }
    @GetMapping("/email")
    public ResponseEntity mostraCarrelloPerUtente(@RequestParam("email") String email){
        List<Carrello> ret;
        try{
            ret = carrelloService.mostraCarrelloPerUtente(email);
        }catch (UtenteNonTrovatoException e){
            return new ResponseEntity<>(new ResponseMessage("Utente non trovato"),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ret,HttpStatus.OK);
    }
    @PostMapping("/quantita")
    public ResponseEntity cambiaQuantita(@RequestBody Carrello carrello){
        Carrello linea= null;
        try{
            linea = carrelloService.mostraCarrelloPerIdCarrello(carrello.getId());
            carrelloService.removeCarrello(linea);
        } catch (CarrelloNonEsistenteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }


        try{
            Carrello risultato= carrelloService.addCarrello(carrello);
            return new ResponseEntity<>(risultato, HttpStatus.OK);
        }catch(Exception ex){ //se non va a buon fine per via delle quantità
            Carrello risultato2= carrelloService.addCarrello(linea);
            return new ResponseEntity<>(risultato2, HttpStatus.BAD_REQUEST);

        }
    }



}
