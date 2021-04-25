package com.example.listapersonagem.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.listapersonagem.R;
import com.example.listapersonagem.dao.PersonagemDAO;
import com.example.listapersonagem.model.Personagem;

public class FormularioPersonagemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_personagem);
        setTitle("Formulário de Personagens");//cabeçalho

        PersonagemDAO dao = new PersonagemDAO(); //criaçao da variavel com classe PersonagemDAO

        EditText campoNome = findViewById(R.id.edittext_nome); //vincular ao campo
        EditText campoAtura = findViewById(R.id.edittext_altura); //vincular ao campo
        EditText campoNascimento = findViewById(R.id.edittext_nascimento); //vincular ao campo

        Button botaoSalvar = findViewById(R.id.button_salvar); //vincular botão

        botaoSalvar.setOnClickListener(new View.OnClickListener() { //botao SALVAR
            @Override
            public void onClick(View v) {



                String nome = campoNome.getText().toString(); // salvando dentro da variavel criada
                String altura = campoAtura.getText().toString(); // salvando dentro da variavel criada
                String nascimento = campoNascimento.getText().toString(); // salvando dentro da variavel criada

                Personagem personagemSalvo = new Personagem(nome, altura, nascimento);
                dao.salva(personagemSalvo);//chamando funçao dentro da classe para salvar

                startActivity(new Intent(FormularioPersonagemActivity.this, ListaPersonagemActivity.class));//navega entre as activities

                /*Toast.makeText(FormularioPersonagemActivity.this, //mensagem rapida para teste
                        personagemSalvo.getNome() + " - " +
                                personagemSalvo.getAltura() + " - " +
                                personagemSalvo.getNascimento(),Toast.LENGTH_SHORT).show();*/

                new Personagem(nome, altura, nascimento);

                //Toast.makeText(FormularioPersonagemActivity.this,"Estou funcionando!",Toast.LENGTH_SHORT).show(); //mensagem rapida para teste
            }
        });

    }
}