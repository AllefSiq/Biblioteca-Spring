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
        private Integer id;
        private String nome;

        private Date lancamento;

        @ManyToMany(mappedBy = "livros")
        @JsonIdentityReference(alwaysAsId = true)
        @JsonProperty("autores")
        private List<Autor> autores = new ArrayList<Autor>();
        private String Categoria;
        private Integer numEstoque;


        public Livro(){}

    /*
        public Livro(String nome, Date lancamento, String categoria, Integer numEstoque) {
            this.nome = nome;
            this.lancamento = lancamento;
            Categoria = categoria;
            this.numEstoque = numEstoque;

        }
*/
        public boolean retirarDoEstoque(Integer numDeLivros){
            if (this.numEstoque>=numDeLivros) {
                this.numEstoque = numEstoque - numDeLivros;

                return true;
            }else
                return false;
        }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }


    public Integer getId() {
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
}
