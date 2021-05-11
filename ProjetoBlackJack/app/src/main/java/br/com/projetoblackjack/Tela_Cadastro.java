package br.com.projetoblackjack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Tela_Cadastro extends AppCompatActivity {

    EditText nome_pessoa;
    EditText nome_usuario;
    EditText email_usuario;
    EditText senha_usuario;
    final static List<Usuario> usuarios_cadastrados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela__cadastro);

        nome_pessoa = findViewById(R.id.txt_nome_cadastro);
        nome_usuario = findViewById(R.id.txt_nome_usuario_cadastro);
        email_usuario = findViewById(R.id.txt_email_cadastro);
        senha_usuario = findViewById(R.id.txt_senha_cadastro);
    }

    public void cadastrar(View view) {
        String nome = nome_pessoa.getText().toString();
        String nomeUsuario = nome_usuario.getText().toString();
        String senha = senha_usuario.getText().toString();
        String email = email_usuario.getText().toString();

        if(verificar_cadastro(nome,nomeUsuario,senha,email) == true){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

    }

    private void adicionar_cadastro(Usuario novo_usuario){
        Log.i( "teste","Entrou" );
        if(usuarios_cadastrados.isEmpty()){
            usuarios_cadastrados.add( new Usuario( "admin", "admin" ) );
        }

        usuarios_cadastrados.add( novo_usuario ) ;

        for (int i=0;i < usuarios_cadastrados.size(); i++)
            Log.i( "teste",usuarios_cadastrados.get( i ).getNome_usuario() );
    }

    private boolean verificar_cadastro(String nome, String nomeUsuario, String senha, String email){
        if (!(nome.contentEquals("") || nomeUsuario.contentEquals("") || senha.contentEquals("") || email.contentEquals(""))){
            Log.i("teste1cadastro", "TODOS OS ITENS FORAM PREENCHIDOS");

            Usuario novo_usuario = new Usuario(nome, nomeUsuario, senha, email);
            adicionar_cadastro( novo_usuario );

            return true;
        }else {
            Log.i("teste1cadastro", "COLOQUE TODOS OS ITENS");

            verifica_edit_text( nome,nomeUsuario,senha,email );
        }
        return false;
    }

    private void verifica_edit_text(String nome,String nomeUsuario,String senha, String email){
        if(nome.contentEquals("")) {
            nome_pessoa.setBackgroundTintList( ColorStateList.valueOf(-65536));
            Toast.makeText(this, "Coloque seu nome completo", Toast.LENGTH_LONG).show();
        }
        if(nomeUsuario.contentEquals("")) {
            nome_usuario.setBackgroundTintList( ColorStateList.valueOf(-65536));
            Toast.makeText(this, "Coloque um nome de usuário válido", Toast.LENGTH_LONG).show();
        }
        if(senha.contentEquals("")) {
            senha_usuario.setBackgroundTintList( ColorStateList.valueOf(-65536));
            Toast.makeText(this, "Coloque uma senha", Toast.LENGTH_LONG).show();
        }
        if(email.contentEquals("")) {
            email_usuario.setBackgroundTintList( ColorStateList.valueOf(-65536));
            Toast.makeText(this, "Coloque um email válido", Toast.LENGTH_LONG).show();
        }
    }

    public static List<Usuario> getUsuarios_cadastrados() {
        return usuarios_cadastrados;
    }
}
