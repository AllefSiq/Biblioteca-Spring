package br.com.allef.biblioteca.models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Livro implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    private Date lancamento;

    @ManyToMany(mappedBy = "livros", fetch = FetchType.EAGER)
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("autores")
    private List<Autor> autores = new ArrayList<Autor>();
    private String Categoria;
    private Integer numEstoque;


    private boolean ativo = true;


    public Livro(Livro livroDoAutor) {
        this.id = livroDoAutor.id;
        this.nome = livroDoAutor.nome;
        this.lancamento = livroDoAutor.lancamento;
        this.Categoria = livroDoAutor.Categoria;
        this.numEstoque = livroDoAutor.numEstoque;
        this.autores = livroDoAutor.autores;


    }

    public Livro() {

    }

    public boolean retirarDoEstoque() {
        if (numEstoque > 1) {
            numEstoque = numEstoque - 1;
            return true;
        } else {
            return false;
        }

    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }


    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getLancamento() {
        return lancamento;
    }

    public void setLancamento(Date lancamento) {
        this.lancamento = lancamento;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    public Integer getNumEstoque() {
        return numEstoque;
    }

    public void setNumEstoque(Integer numEstoque) {
        this.numEstoque = numEstoque;
    }

    public void devolverAoEstoque() {
        numEstoque = numEstoque + 1;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

}
