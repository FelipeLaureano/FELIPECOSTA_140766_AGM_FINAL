package com.example.listapersonagem.model;

public class Personagem { //classe para persistencia de dados

    //SETS
    private final String nome;
    private final String altura;
    private final String nascimento;

    public Personagem(String nome, String altura, String nascimento) {


        this.nome = nome;
        this.altura = altura;
        this.nascimento = nascimento;
    }

    //GETS
    public String getNome() {
        return nome;
    }

    public String getAltura() {
        return altura;
    }

    public String getNascimento() {
        return nascimento;
    }
}
