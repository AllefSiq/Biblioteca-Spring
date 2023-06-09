package br.com.allef.biblioteca.models;


import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.io.Serializable;
import java.util.List;

@Entity
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Long id;

    @Column(nullable = false)
    private String nome;
    @Column(unique = true, nullable = false)
    private String email;


    private boolean ativo = true;


    public Usuario() {
    }

    public Usuario(String nome, String email) {
        this.nome = nome;
        this.email = email;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
