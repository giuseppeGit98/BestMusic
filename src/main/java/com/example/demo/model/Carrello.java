package com.example.demo.model;

import javax.persistence.*;

@Entity
public class Carrello {
    private int id;
    private Integer quantitaAlbum;
    private Double prezzo;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "quantita_album")
    public Integer getQuantitaAlbum() {
        return quantitaAlbum;
    }

    public void setQuantitaAlbum(Integer quantitaAlbum) {
        this.quantitaAlbum = quantitaAlbum;
    }

    @Basic
    @Column(name = "prezzo")
    public Double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Double prezzo) {
        this.prezzo = prezzo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Carrello carrello = (Carrello) o;

        if (id != carrello.id) return false;
        if (quantitaAlbum != null ? !quantitaAlbum.equals(carrello.quantitaAlbum) : carrello.quantitaAlbum != null)
            return false;
        if (prezzo != null ? !prezzo.equals(carrello.prezzo) : carrello.prezzo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (quantitaAlbum != null ? quantitaAlbum.hashCode() : 0);
        result = 31 * result + (prezzo != null ? prezzo.hashCode() : 0);
        return result;
    }
    private Album album;
    private Utente utente;
    @ManyToOne
    @JoinColumn(name="album_id",nullable = false)
    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
    @ManyToOne
    @JoinColumn(name="utente_id",nullable = false)




    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }
}
