package com.example.listapersonagem.ui.activities;

import android.content.Intent;
import android.content.PeriodicSync;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.listapersonagem.R;
import com.example.listapersonagem.dao.PersonagemDAO;
import com.example.listapersonagem.model.Personagem;

public class FormularioPersonagemActivity extends AppCompatActivity {

    private EditText campoNome;
    private EditText campoAltura;
    private EditText campoNascimento;
    private final PersonagemDAO dao = new PersonagemDAO(); //criaçao da variavel com classe PersonagemDAO
    private Personagem Personagem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_personagem);
        setTitle("Formulário de Personagens");//cabeçalho

        inicializacaoCampos();
        configuraBotao();

        Intent dados = getIntent();
        if(dados.hasExtra("personagem")) {
            Personagem personagem = (Personagem) dados.getSerializableExtra("personagem");
            campoNome.setText(personagem.getNome());
            campoAltura.setText(personagem.getAltura());
            campoNascimento.setText(personagem.getNascimento());
        }else{
            Personagem = new Personagem();
        }

    }

    private void configuraBotao() {
        Button botaoSalvar = findViewById(R.id.button_salvar); //vincular botão
        botaoSalvar.setOnClickListener(new View.OnClickListener() { //botao SALVAR
            @Override
            public void onClick(View view) {

                String nome = campoNome.getText().toString(); // salvando dentro da variavel criada
                String altura = campoAltura.getText().toString(); // salvando dentro da variavel criada
                String nascimento = campoNascimento.getText().toString(); // salvando dentro da variavel criada

                Personagem personagemSalvo = new Personagem(nome, altura, nascimento);
                dao.salva(personagemSalvo);//chamando funçao dentro da classe para salvar
                finish();

                //startActivity(new Intent(FormularioPersonagemActivity.this, ListaPersonagemActivity.class));//navega entre as activities

                /*Toast.makeText(FormularioPersonagemActivity.this, //mensagem rapida para teste
                        personagemSalvo.getNome() + " - " +
                                personagemSalvo.getAltura() + " - " +
                                personagemSalvo.getNascimento(),Toast.LENGTH_SHORT).show();*/

                //new Personagem(nome, altura, nascimento);

                personagemSalvo.setNome(nome);
                personagemSalvo.setAltura(altura);
                personagemSalvo.setNascimento(nascimento);
                dao.edita(personagemSalvo);

                //Toast.makeText(FormularioPersonagemActivity.this,"Estou funcionando!",Toast.LENGTH_SHORT).show(); //mensagem rapida para teste
            }
        });
    }

    private void inicializacaoCampos() {
        campoNome = findViewById(R.id.edittext_nome); //vincular ao campo
        campoAltura = findViewById(R.id.edittext_altura); //vincular ao campo
        campoNascimento = findViewById(R.id.edittext_nascimento); //vincular ao campo
    }
}