package com.example.listapersonagem.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.listapersonagem.R;
import com.example.listapersonagem.dao.PersonagemDAO;
import com.example.listapersonagem.model.Personagem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ListaPersonagemActivity extends AppCompatActivity {

    @Override //sobrescreve
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_personagem);
        setTitle("Lista de Personagens");

        FloatingActionButton botaoNovoPersonagem = findViewById(R.id.fab_novo_personagem);//criação de vinculo com o botão
        botaoNovoPersonagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListaPersonagemActivity.this, FormularioPersonagemActivity.class));//chamar a nova tela
            }
        });

        //List<String> personagem = new ArrayList<>(Arrays.asList("Alex", "Ken", "Ryu"));


        //FAZER MANUALMENTE:
        /*TextView primeiroPersonagem = findViewById(R.id.textView);
        TextView segundoPersonagem = findViewById(R.id.textView2);
        TextView terceiroPersonagem = findViewById(R.id.textView3);

        primeiroPersonagem.setText(personagem.get(0));
        segundoPersonagem.setText(personagem.get(1));
        terceiroPersonagem.setText(personagem.get(2));*/

    }

    @Override
    protected void onResume() {
        super.onResume();

        PersonagemDAO dao = new PersonagemDAO();
        ListView listaDePersonagens = findViewById(R.id.lista_personagem);
        List<Personagem> personagens = dao.todos();
        listaDePersonagens.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, personagens));
        listaDePersonagens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                Personagem personagemEscolhido = personagens.get(posicao);
                //Log.i( tag: "personagem", msg: "" + personagemEscolhido); //Vizualizar no LogCat
                Intent vaiParaFormulario = new Intent(ListaPersonagemActivity.this, FormularioPersonagemActivity.class);
                startActivity(vaiParaFormulario);
            }
        });
    }
}
