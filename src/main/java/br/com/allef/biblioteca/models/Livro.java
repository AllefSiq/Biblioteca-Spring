package br.com.allef.biblioteca.models;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    public class Livro {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String nome;

        private Date lancamento;

        @ManyToMany(mappedBy = "livros")
        @JsonIdentityReference(alwaysAsId = true)
        @JsonProperty("autores")
        private List<Autor> autores = new ArrayList<Autor>();
        private String Categoria;
        private Integer numEstoque;


        private boolean Ativo = true;



        public Livro(){}

        public boolean retirarDoEstoque(){
            if (numEstoque> 1){
                numEstoque = numEstoque-1;
                return true;
            }else{
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
            numEstoque = numEstoque+1;
    }
}
