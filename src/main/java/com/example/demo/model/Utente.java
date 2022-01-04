package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
public class Utente implements Serializable {
    private int id;
    private String cognome;
    private String nome;
    private String indirizzo;
    private String email;
    private String telefono;
    private String password;

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
    @Column(name = "cognome")
    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    @Basic
    @Column(name = "nome")
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Basic
    @Column(name = "indirizzo")
    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    @Basic
    @Column(name = "email", nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "telefono")
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Utente utente = (Utente) o;

        if (id != utente.id) return false;
        if (cognome != null ? !cognome.equals(utente.cognome) : utente.cognome != null) return false;
        if (nome != null ? !nome.equals(utente.nome) : utente.nome != null) return false;
        if (indirizzo != null ? !indirizzo.equals(utente.indirizzo) : utente.indirizzo != null) return false;
        if (email != null ? !email.equals(utente.email) : utente.email != null) return false;
        if (telefono != null ? !telefono.equals(utente.telefono) : utente.telefono != null) return false;
        if (password != null ? !password.equals(utente.password) : utente.password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (cognome != null ? cognome.hashCode() : 0);
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + (indirizzo != null ? indirizzo.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (telefono != null ? telefono.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
    private Collection<Acquisto> acquisti;
    private List<Carrello> carrelli;
    @JsonIgnore
    @OneToMany( mappedBy = "utente",cascade = CascadeType.ALL)
    public Collection<Acquisto> getAcquisti() {
        return acquisti;
    }

    public void setAcquisti(Collection<Acquisto> acquisti) {
        this.acquisti = acquisti;
    }
    @JsonIgnore
    @OneToMany(mappedBy = "utente",cascade = CascadeType.ALL)




    public List<Carrello> getCarrelli() {
        return carrelli;
    }

    public void setCarrelli(List<Carrello> carrelli) {
        this.carrelli = carrelli;
    }
}
