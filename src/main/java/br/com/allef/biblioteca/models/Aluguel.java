package br.com.allef.biblioteca.models;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Aluguel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dataAluguel;
    private Date dataDevolucao;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "livro_id")
    private Livro livro;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    private boolean devolvido;


    private boolean ativo = true;

    //construtor
    public Aluguel(Date dataAluguel, Date dataDevolucao, Livro livro, Usuario usuario, boolean devolvido) {
        this.dataAluguel = dataAluguel;
        this.dataDevolucao = dataDevolucao;
        this.livro = livro;
        this.usuario = usuario;
        this.devolvido = devolvido;
    }

    public Aluguel() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataAluguel() {
        return dataAluguel;
    }

    public void setDataAluguel(Date dataAluguel) {
        this.dataAluguel = dataAluguel;
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isDevolvido() {
        return devolvido;
    }

    public void setDevolvido(boolean devolvido) {
        this.devolvido = devolvido;
    }
}
