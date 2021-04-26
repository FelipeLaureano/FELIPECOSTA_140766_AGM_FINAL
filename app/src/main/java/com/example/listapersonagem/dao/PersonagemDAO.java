package com.example.listapersonagem.dao;

import com.example.listapersonagem.model.Personagem;

import java.util.ArrayList;
import java.util.List;

public class PersonagemDAO { //classe para salvar dentro da aplicação - base de dados

    private final static List<Personagem> personagens = new ArrayList<>();
    private static int contadorDeId = 1;

    public void salva(Personagem personagemSalvo) { //método para salvar(persistencia)
        personagemSalvo.setId(contadorDeId);
        personagens.add(personagemSalvo);//adicionando o obejto personagemSalvo
        contadorDeId++;

    }

    public void edita(Personagem personagem){ //diferente de salva pois preciso achar o ID existente
        Personagem personagemEscolhido = null;
        for (Personagem p:
             personagens) {
            if(p.getId() == personagem.getId()){
                personagemEscolhido = p;
            }

        }
        if(personagemEscolhido != null){
            int posicaoDoPersonagem = personagens.indexOf(personagemEscolhido);
            personagens.set(posicaoDoPersonagem, personagem);
        }
    }

    public List<Personagem> todos() { //método para buscar todas as listagens
        return new ArrayList<>(personagens);
    }
}
