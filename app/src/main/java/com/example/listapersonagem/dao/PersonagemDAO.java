package com.example.listapersonagem.dao;

import com.example.listapersonagem.model.Personagem;

import java.util.ArrayList;
import java.util.List;

public class PersonagemDAO {

    private final static List<Personagem> personagens = new ArrayList<>();

    public void salva(Personagem personagemSalvo) { //método para salvar(persistencia)

        personagens.add(personagemSalvo);//adicionando o obejto personagemSalvo
    }

    public List<Personagem> todos() {
        return new ArrayList<>(personagens);
    }
}
