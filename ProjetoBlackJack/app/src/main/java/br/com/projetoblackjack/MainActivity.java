package br.com.projetoblackjack;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText nome_usuario;
    EditText senha_usuario;
    CheckBox mostrar_senha;
    private static String nome_usuario_jogo = "";
    private static String senha_usuario_jogo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nome_usuario = findViewById(R.id.txt_usuario);
        senha_usuario = findViewById(R.id.txt_senha);
        mostrar_senha = findViewById( R.id.bt_mostrar_senha );

    }

    public void cadastrar(View view) {
        Intent intent = new Intent(this, Tela_Cadastro.class);
        startActivity(intent);
    }


    public void entrar_jogo(View view) {
        String nome = nome_usuario.getText().toString();
        String senha = senha_usuario.getText().toString();
        if (verificar_usuario(nome, senha) == true){

            Log.i("TesteEntrar", "Encontrou o usuario " + nome + " com a senha " + senha);

            nome_usuario_jogo = nome;
            senha_usuario_jogo = senha;
            Intent intent = new Intent(this, Tela_jogo.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Nome de usuário ou senha inválidos", Toast.LENGTH_LONG).show();
            Log.i("TesteEntrar", nome + " e " + senha + "invalidos");
        }

    }

    public void mostrar_senha(View view) {

        if (mostrar_senha.isChecked() == true){
            senha_usuario.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
        else{
            senha_usuario.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    public boolean verificar_usuario(String nome, String senha){
        List<Usuario> usuarios = Tela_Cadastro.getUsuarios_cadastrados();

        for (int i=0; i < usuarios.size(); i++){
            if (usuarios.get( i ).getNome_usuario().contentEquals(nome)){
                if (usuarios.get( i ).getSenha().contentEquals( senha ))
                    return true;
            }
        }
        return false;
    }

    public static String getNome_usuario_jogo() {
        return nome_usuario_jogo;
    }

    public static String getSenha_usuario_jogo() {
        return senha_usuario_jogo;
    }
}
