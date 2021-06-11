package com.example.listapersonagem.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listapersonagem.R;
import com.example.listapersonagem.dao.PersonagemDAO;
import com.example.listapersonagem.model.Personagem;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import static com.example.listapersonagem.ui.activities.ConstantesActivities.CHAVE_PERSONAGEM;

public class FormularioPersonagemActivity extends AppCompatActivity {


    private static final String TITULO_APPBAR_EDITA_PERSONAGEM = "Editar Personagem";
    private static final String TITULO_APPBAR_NOVO_PERSONAGEM = "Novo Personagem";
    private EditText campoNome;
    private EditText campoAltura;
    private EditText campoNascimento;
    private EditText campoTelefone;
    private EditText campoEndereco;
    private EditText campoCep;
    private EditText campoRg;
    private EditText campoGenero;
    private final PersonagemDAO dao = new PersonagemDAO(); //criaçao da variavel com classe PersonagemDAO
    private Personagem personagem;

    @Override //criado para icone aparecer
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_formulario_personagem_menu_salvar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override //criado para ação ser executada
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.activity_formulario_personagem_menu_salvar){
            finalizaFormulario();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_personagem);
        inicializacaoCampos();//refatoração - criando métodos (ctrl + alt + m)
        configuraBotaoSalvar();//refatoração - criando métodos (ctrl + alt + m)
        carregaPersonagem();//refatoração
    }

    private void carregaPersonagem() {
        Intent dados = getIntent();
        if (dados.hasExtra(CHAVE_PERSONAGEM)) {
            setTitle(TITULO_APPBAR_EDITA_PERSONAGEM);//cabeçalho
            personagem = (Personagem) dados.getSerializableExtra(CHAVE_PERSONAGEM);
            preencheCampos();//refatoração
        } else {
            setTitle(TITULO_APPBAR_NOVO_PERSONAGEM);//cabeçalho
            personagem = new Personagem();
        }
    }

    private void preencheCampos() {
        campoNome.setText(personagem.getNome());
        campoAltura.setText(personagem.getAltura());
        campoNascimento.setText(personagem.getNascimento());
        campoTelefone.setText(personagem.getTelefone());
        campoEndereco.setText(personagem.getEndereco());
        campoCep.setText(personagem.getCep());
        campoRg.setText(personagem.getRg());
        campoGenero.setText(personagem.getGenero());
    }

    private void configuraBotaoSalvar() {
        Button botaoSalvar = findViewById(R.id.button_salvar); //vincular botão
        botaoSalvar.setOnClickListener(new View.OnClickListener() { //botao SALVAR
            @Override
            public void onClick(View view) {
                finalizaFormulario();//refatoração

                //startActivity(new Intent(FormularioPersonagemActivity.this, ListaPersonagemActivity.class));//navega entre as activities

                /*Toast.makeText(FormularioPersonagemActivity.this, //mensagem rapida para teste
                        personagemSalvo.getNome() + " - " +
                                personagemSalvo.getAltura() + " - " +
                                personagemSalvo.getNascimento(),Toast.LENGTH_SHORT).show();*/

                //new Personagem(nome, altura, nascimento);

                //Toast.makeText(FormularioPersonagemActivity.this,"Estou funcionando!",Toast.LENGTH_SHORT).show(); //mensagem rapida para teste
            }
        });
    }

    private void finalizaFormulario() {
        preenchePersonagem();
        if (personagem.IdValido()) {
            dao.edita(personagem);
        } else {
            dao.salva(personagem);//chamando funçao dentro da classe para salvar
        }
        finish();
    }

    private void inicializacaoCampos() {
        campoNome = findViewById(R.id.edittext_nome); //vincular ao campo nome
        campoAltura = findViewById(R.id.edittext_altura); //vincular ao campo altura
        campoNascimento = findViewById(R.id.edittext_nascimento); //vincular ao campo nascimento
        campoTelefone = findViewById(R.id.edittext_telefone); //vincular ao campo telefone
        campoEndereco = findViewById(R.id.edittext_endereco); //vincular ao campo endereco
        campoCep = findViewById(R.id.edittext_cep); //vincular ao campo cep
        campoRg = findViewById(R.id.edittext_rg); //vincular ao campo rg
        campoGenero = findViewById(R.id.edittext_genero); //vincular ao campo genero

        SimpleMaskFormatter smfAltura = new SimpleMaskFormatter("N,NN");//variaveis de mascara para altura
        MaskTextWatcher mtwAltura = new MaskTextWatcher(campoAltura, smfAltura);
        campoAltura.addTextChangedListener(mtwAltura);

        SimpleMaskFormatter smfNascimento = new SimpleMaskFormatter("NN/NN/NNNN");//variaveis de mascara para nascimento
        MaskTextWatcher mtwNascimento = new MaskTextWatcher(campoNascimento, smfNascimento);
        campoNascimento.addTextChangedListener(mtwNascimento);

        SimpleMaskFormatter smfTelefone = new SimpleMaskFormatter("(NN)NNNNN-NNNN");//variaveis de mascara para telefone
        MaskTextWatcher mtwTelefone = new MaskTextWatcher(campoTelefone, smfTelefone);
        campoTelefone.addTextChangedListener(mtwTelefone);

        SimpleMaskFormatter smfCep = new SimpleMaskFormatter("NNNNN-NNN");//variaveis de mascara para cep
        MaskTextWatcher mtwCep = new MaskTextWatcher(campoCep, smfCep);
        campoCep.addTextChangedListener(mtwCep);

        SimpleMaskFormatter smfRg = new SimpleMaskFormatter("NN.NNN.NNN-N");//variaveis de mascara para rg
        MaskTextWatcher mtwRg = new MaskTextWatcher(campoRg, smfRg);
        campoRg.addTextChangedListener(mtwRg);

    }

    private void preenchePersonagem() {

        String nome = campoNome.getText().toString(); // salvando dentro da variavel criada
        String altura = campoAltura.getText().toString(); // salvando dentro da variavel criada
        String nascimento = campoNascimento.getText().toString(); // salvando dentro da variavel criada
        String telefone = campoTelefone.getText().toString(); // salvando dentro da variavel criada
        String endereco = campoEndereco.getText().toString(); // salvando dentro da variavel criada
        String cep = campoCep.getText().toString(); // salvando dentro da variavel criada
        String rg = campoRg.getText().toString(); // salvando dentro da variavel criada
        String genero = campoGenero.getText().toString(); // salvando dentro da variavel criada

        personagem.setNome(nome);
        personagem.setAltura(altura);
        personagem.setNascimento(nascimento);
        personagem.setTelefone(telefone);
        personagem.setEndereco(endereco);
        personagem.setCep(cep);
        personagem.setRg(rg);
        personagem.setGenero(genero);
    }
}