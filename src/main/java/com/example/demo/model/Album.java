package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
public class Album implements Serializable {
    @Version
    protected long version;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",nullable = false)
    private int id;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "autore")
    private String autore;
    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    @Basic
    @Column(name = "nome")
    private String nome;
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Basic
    @Column(name = "anno")
    private Integer anno;
    public Integer getAnno() {
        return anno;
    }

    public void setAnno(Integer anno) {
        this.anno = anno;
    }

    @Basic
    @Column(name = "quantita")
    private Integer quantita;
    public Integer getQuantita() {
        return quantita;
    }

    public void setQuantita(Integer quantita) {
        this.quantita = quantita;
    }

    @Basic
    @Column(name = "prezzo")
    private Double prezzo;
    public Double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Double prezzo) {
        this.prezzo = prezzo;
    }

    @Basic
    @Column(name = "genere")
    private String genere;
    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Album album = (Album) o;

        if (id != album.id) return false;
        if (autore != null ? !autore.equals(album.autore) : album.autore != null) return false;
        if (nome != null ? !nome.equals(album.nome) : album.nome != null) return false;
        if (anno != null ? !anno.equals(album.anno) : album.anno != null) return false;
        if (quantita != null ? !quantita.equals(album.quantita) : album.quantita != null) return false;
        if (prezzo != null ? !prezzo.equals(album.prezzo) : album.prezzo != null) return false;
        if (genere != null ? !genere.equals(album.genere) : album.genere != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (autore != null ? autore.hashCode() : 0);
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + (anno != null ? anno.hashCode() : 0);
        result = 31 * result + (quantita != null ? quantita.hashCode() : 0);
        result = 31 * result + (prezzo != null ? prezzo.hashCode() : 0);
        result = 31 * result + (genere != null ? genere.hashCode() : 0);
        return result;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "album")
    private Collection<AlbumInAcquisto> album_in_acquisti;
    public Collection<AlbumInAcquisto> getAlbum_in_acquisti() {
        return album_in_acquisti;
    }

    public void setAlbum_in_acquisti(Collection<AlbumInAcquisto> album_in_acquisti) {
        this.album_in_acquisti = album_in_acquisti;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "album",cascade = CascadeType.ALL)
    private List<Carrello> carrelli;
    public List<Carrello> getCarrelli() {
        return carrelli;
    }

    public void setCarrelli(List<Carrello> carrelli) {
        this.carrelli = carrelli;
    }


}
