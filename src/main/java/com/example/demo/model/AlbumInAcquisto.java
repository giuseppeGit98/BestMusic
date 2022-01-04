package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "album_in_acquisto", schema = "public", catalog = "music")
public class AlbumInAcquisto implements Serializable {
    private int id;
    private int quantita;
    private double prezzo;


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
    @Column(name = "quantita")
    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    @Basic
    @Column(name = "prezzo")
    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AlbumInAcquisto that = (AlbumInAcquisto) o;

        if (id != that.id) return false;
        if (quantita != that.quantita) return false;
        if (Double.compare(that.prezzo, prezzo) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + quantita;
        temp = Double.doubleToLongBits(prezzo);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
    private Acquisto acquisto;
    private Album album;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="acquisto_id",nullable = false)
    public Acquisto getAcquisto() {
        return acquisto;
    }

    public void setAcquisto(Acquisto acquisto) {
        this.acquisto = acquisto;
    }
    @ManyToOne
    @JoinColumn(name="album_id",nullable = false)




    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
}
