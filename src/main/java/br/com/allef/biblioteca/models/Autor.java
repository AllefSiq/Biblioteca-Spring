package br.com.allef.biblioteca.models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;


import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "autor")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Autor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "autor_id")
    private Long id;
    private String nome;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "autores_livros", joinColumns = @JoinColumn(name = "autores_fk"), inverseJoinColumns = @JoinColumn(name = "biblioteca_fk"))
    @JsonIdentityReference(alwaysAsId = true)
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    private List<Livro> livros = new ArrayList<Livro>();
    private Date dataDeNascimento;

    private boolean ativo = true;

    public Autor() {
    }

    public Autor(Autor autor) {
        this.id = autor.id;
        this.dataDeNascimento = autor.dataDeNascimento;
        this.nome = autor.nome;
        this.livros = autor.livros;

    }

    public Autor(Long id, String nome, Date dataDeNascimento) {
        this.id = id;
        this.nome = nome;
        this.dataDeNascimento = dataDeNascimento;

    }


    public Autor(String nome, Date dataDeNascimento) {
        this.nome = nome;
        this.dataDeNascimento = dataDeNascimento;
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


    public Date getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(Date dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }


    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public boolean isAtivo() {
        return ativo;
    }
}
