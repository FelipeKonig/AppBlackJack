package br.com.projetoblackjack;

import android.util.Log;

import java.util.Random;


public class Jogo {

    public Jogo() {
    }

    public int entrega_carta(){

        int carta_pega;
        int carta=0;

        Random carta_sorteada = new Random();

        while (true){
            carta_pega = carta_sorteada.nextInt(11);

            if (carta_pega > 0){
                carta = carta_pega;
                break;
            }

        }
        return carta;
    }

}
