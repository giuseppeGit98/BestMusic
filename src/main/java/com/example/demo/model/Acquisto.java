package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
public class Acquisto {
    private int id;
    private Double sommaTotale;

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
    @Column(name = "somma_totale", nullable = true)
    public Double getSommaTotale() {
        return sommaTotale;
    }

    public void setSommaTotale(Double sommaTotale) {
        this.sommaTotale = sommaTotale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Acquisto acquisto = (Acquisto) o;

        if (id != acquisto.id) return false;
        if (sommaTotale != null ? !sommaTotale.equals(acquisto.sommaTotale) : acquisto.sommaTotale != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (sommaTotale != null ? sommaTotale.hashCode() : 0);
        return result;
    }
    private List<AlbumInAcquisto> ordine;
    private Utente utente;
    @ManyToOne
    @JoinColumn(name="utente_id",nullable = false)
    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    @OneToMany(mappedBy = "acquisto",cascade = CascadeType.ALL)




    public List<AlbumInAcquisto> getOrdine() {
        return ordine;
    }

    public void setOrdine(List<AlbumInAcquisto> ordine) {
        this.ordine = ordine;
    }

}
