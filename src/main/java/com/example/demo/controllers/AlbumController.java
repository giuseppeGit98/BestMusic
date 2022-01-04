package com.example.demo.controllers;

import com.example.demo.model.Album;
import com.example.demo.services.AlbumService;
import com.example.demo.services.UtenteService;
import com.example.demo.support.ResponseMessage;
import com.example.demo.support.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/albums")
@CrossOrigin("http://localhost:4200")
public class AlbumController {
    @Autowired
    UtenteService utenteService;
    @Autowired
    AlbumService albumService;


    @PostMapping
    public ResponseEntity aggiungiAlbum(@RequestBody Album album){
        try{
            albumService.inserisciAlbum(album);
        }catch (AlbumGiaEsistenteException a){
            return new ResponseEntity<>(new ResponseMessage("Album gia esistente"), HttpStatus.BAD_REQUEST);
        }catch (GenereNonEsistenteException e){
            return new ResponseEntity(new ResponseMessage("Genere non esistente"),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseMessage("Aggiunta avvenuta con successo"),HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity getTuttiAlbum(){
        List<Album>ret = albumService.showAllAlbums();
        if(ret.size()<=0) {
            return new ResponseEntity<>(new ResponseMessage("No Risultati!"),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ret,HttpStatus.OK);
    }

    @GetMapping("/tipoAlbum/{tipo}")
   public ResponseEntity getAlbumPerGenere(@PathVariable("tipo") String tipo){
        List<Album>ret=null;
        try{
            ret = albumService.getAlbumPerGenere(tipo);
        }catch (GenereNonEsistenteException e){
            return new ResponseEntity<>(new ResponseMessage("Genere non esistente"),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ret,HttpStatus.OK);
    }

    @GetMapping("/by_name/{nome}")
    public ResponseEntity getAlbumByNome(@PathVariable(value="nome_album") String nome_album){
        List<Album>ret=null;
        try{
            ret = albumService.getAlbumByNome(nome_album);
        }catch (AlbumNonEsistenteException ex){
            return new ResponseEntity<>(new ResponseMessage("Album non esistente"),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ret,HttpStatus.OK);

    }




}
