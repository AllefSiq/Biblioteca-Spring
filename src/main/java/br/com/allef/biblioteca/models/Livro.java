package br.com.allef.biblioteca.models;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;


import java.util.Date;


    @Entity
    public class Livro {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        private String nome;

        private Date lancamento;

        @ManyToOne
        @JoinColumn(name = "autor_id")
        private Autor autor;
        private String Categoria;
        private Integer numEstoque;


        public Livro(){}

        public Livro(String nome, Date lancamento, Autor autor, String categoria, Integer numEstoque) {
            this.nome = nome;
            this.lancamento = lancamento;
            this.autor = autor;
            Categoria = categoria;
            this.numEstoque = numEstoque;
        }

        public boolean retirarDoEstoque(Integer numDeLivros){
            if (this.numEstoque>=numDeLivros) {
                this.numEstoque = numEstoque - numDeLivros;

                return true;
            }else
                return false;
        }


    }
