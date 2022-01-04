package com.example.demo.controllers;

import com.example.demo.model.Utente;
import com.example.demo.services.UtenteService;
import com.example.demo.support.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.support.exceptions.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin("http://localhost:4200")
public class UtenteController {
    @Autowired
    UtenteService utenteService;

    @PostMapping
    public ResponseEntity create(@RequestBody Utente utente){
        try{
            utenteService.registraUtente(utente);
            return new ResponseEntity(new ResponseMessage("Operazione effettuata con successo"),HttpStatus.OK);
        }catch (MailGiaEsistenteException m){
            return new ResponseEntity<>(new ResponseMessage("EMail già esistente"),HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/email")
    public ResponseEntity getByEmail(@RequestParam(value="email",defaultValue = "") String email){
        Utente ret;
        try{
            ret = utenteService.getUtentePerEmail(email);
            return new ResponseEntity<>(ret,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseMessage("Email non esistente"),HttpStatus.BAD_REQUEST);
        }

    }

}
