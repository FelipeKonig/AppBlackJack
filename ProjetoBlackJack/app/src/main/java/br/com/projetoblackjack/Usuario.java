package br.com.projetoblackjack;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.lang.Integer.parseInt;

public class Usuario extends Jogo {
    private String nome, email, senha, nome_usuario;
    private int vitorias = 0;
    private int derrotas = 0;

    public Usuario() {
    }

    public Usuario(String nome, String nome_usuario , String senha, String email) {
        this.nome = nome;
        this.nome_usuario = nome_usuario;
        this.senha = senha;
        this.email = email;
    }

    public Usuario(String nome_usuario, String senha) {
        this.nome_usuario = nome_usuario;
        this.senha = senha;
    }

    public void salvar_placar(int vitorias, int derrotas){

        Usuario jogador = verifica_usuario();

        jogador.setVitorias( jogador.getVitorias() + vitorias );
        jogador.setDerrotas( jogador.getDerrotas() + derrotas );
    }

    public Usuario verifica_usuario(){

        String nome = MainActivity.getNome_usuario_jogo();
        String senha = MainActivity.getSenha_usuario_jogo();

        List<Usuario> usuarios = Tela_Cadastro.getUsuarios_cadastrados();

        for (int indice=0; indice < usuarios.size(); indice++) {

            if (usuarios.get( indice ).getNome_usuario().contentEquals(nome)) {
                if (usuarios.get(indice).getSenha().contentEquals(senha)){
                    return usuarios.get( indice );
                }
            }
        }
        return null;
    }

    public String getNome() {
        return nome;
    }

    public int getVitorias() { return vitorias; }

    public void setVitorias(int vitorias) { this.vitorias = vitorias; }

    public int getDerrotas() { return derrotas; }

    public void setDerrotas(int derrotas) { this.derrotas = derrotas; }

    public String getSenha() {
        return senha;
    }

    public String getNome_usuario() {
        return nome_usuario;
    }
}
