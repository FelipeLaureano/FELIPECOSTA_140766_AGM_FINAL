package com.example.listapersonagem.ui.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listapersonagem.R;
import com.example.listapersonagem.dao.PersonagemDAO;
import com.example.listapersonagem.model.Personagem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.example.listapersonagem.ui.activities.ConstantesActivities.CHAVE_PERSONAGEM;

public class ListaPersonagemActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Lista de Personagens";
    private final PersonagemDAO dao = new PersonagemDAO();//variável global
    private ArrayAdapter<Personagem> adapter;

    @Override //sobrescreve
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_personagem);
        setTitle(TITULO_APPBAR);//(ctrl + alt + c) - levar alteraçãp do nome escrito para inicio do codigo
        configuraFabNovoPersonagem();//refatoração - criando novo método (ctrl + alt + m)
        configuraLlista(); //refatoração (ctrl + alt + m)

        //dao.salva(new Personagem("Ken","1,80","02041979"));//texto fixo para teste
        //dao.salva(new Personagem("Hyu","1,80","02041979"));//texto fixo para teste

        //List<String> personagem = new ArrayList<>(Arrays.asList("Alex", "Ken", "Ryu"));

        //FAZER MANUALMENTE:
        /*TextView primeiroPersonagem = findViewById(R.id.textView);
        TextView segundoPersonagem = findViewById(R.id.textView2);
        TextView terceiroPersonagem = findViewById(R.id.textView3);

        primeiroPersonagem.setText(personagem.get(0));
        segundoPersonagem.setText(personagem.get(1));
        terceiroPersonagem.setText(personagem.get(2));*/
    }

    private void configuraFabNovoPersonagem() {
        FloatingActionButton botaoNovoPersonagem = findViewById(R.id.fab_novo_personagem);//criação de vinculo com o botão
        botaoNovoPersonagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreFormularioSalva();//refatoração
            }
        });
    }

    private void abreFormularioSalva() {
        startActivity(new Intent(ListaPersonagemActivity.this, FormularioPersonagemActivity.class));//chamar a nova tela
    }

    //método para que dados nao sejam apagados quando dar voltar(back)
    @Override
    protected void onResume() {
        super.onResume();
        atualizaPersonagem();
    }

    private void atualizaPersonagem() {
        adapter.clear();//limpa lista
        adapter.addAll(dao.todos());//carrega todas informações guardadas
    }

    private void remove(Personagem personagem){
        dao.remove(personagem);
        adapter.remove(personagem);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //menu.add("Remover");//fazer manualmente
        //menu.add("Teste");//fazer manualmente
        getMenuInflater().inflate(R.menu.activity_lista_personagens_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        //CharSequence tituloDoMenu = item.getTitle();//pegar por titulo
        configuraMenu(item);
        return super.onContextItemSelected(item);
    }

    private void configuraMenu(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.activity_lista_personagem_menu_remover) {

            new AlertDialog.Builder(this)//alerta para saber se quer mesmo apagar
                    .setTitle("Removendo Personagem")
                    .setMessage("Tem certeza que deseja remover?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();//pegar o menu
                            Personagem personagemEscolhido = adapter.getItem(menuInfo.position);
                            remove(personagemEscolhido);
                        }
                    })
                    .setNegativeButton("Não", null)
                    .show();
        }
    }

    private void configuraLlista() {
        ListView listaDePersonagens = findViewById(R.id.lista_personagem);
        configuraAdapter(listaDePersonagens); //refatoração
        configuraItemPorClique(listaDePersonagens); //refatoração
        registerForContextMenu(listaDePersonagens);
    }

    private void configuraItemPorClique(ListView listaDePersonagens) {
        listaDePersonagens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                Personagem personagemEscolhido = (Personagem) adapterView.getItemAtPosition(posicao);
                //Log.i( tag: "personagem", msg: "" + personagemEscolhido); //Vizualizar no LogCat
                abreFormularioModoEditar(personagemEscolhido);//refatoração
            }
        });
    }

    private void abreFormularioModoEditar(Personagem personagem) {
        Intent vaiParaFormulario = new Intent(this, FormularioPersonagemActivity.class);
        vaiParaFormulario.putExtra(CHAVE_PERSONAGEM, personagem);
        startActivity(vaiParaFormulario);
    }

    private void configuraAdapter(ListView listaDePersonagens) {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);//ArrayAdapter - acumula varios itens
        listaDePersonagens.setAdapter(adapter);
    }
}
