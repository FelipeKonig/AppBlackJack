package br.com.projetoblackjack;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Tela_jogo extends BaseActivity {

    Button bt_inicar_jogo;
    Button bt_comprar;
    Button bt_parar;

    TextView valor_cpu;
    TextView valor_usuario;
    TextView vitoria_jogador;
    TextView derrota_jogador;

    ImageView carta,carta1,carta2,carta3,carta4,carta5,carta6,carta7;

    ImageView carta_reserva,carta1_computador,carta2_computador,carta3_computador,carta4_computador,carta5_computador,
            carta6_computador, carta7_computador;

    private Usuario jogador = new Usuario().verifica_usuario();
    private int valor_mao_jogador = 0;
    private int valor_mao_cpu = 0;
    private int indice_carta_jogador = 1;
    private int indice_carta_npc = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_tela_jogo );

        bt_inicar_jogo = findViewById( R.id.bt_inicar_jogo );
        bt_comprar = findViewById( R.id.bt_comprar );
        bt_parar = findViewById( R.id.bt_parar );

        valor_cpu = findViewById( R.id.valor_computador);
        valor_usuario = findViewById( R.id.valor_usuario );

        vitoria_jogador = findViewById( R.id.pontuacao_vitoria );
        derrota_jogador = findViewById( R.id.potuacao_derrota );

        carta1 = findViewById( R.id.carta7_jogador );
        carta2 = findViewById( R.id.carta6_jogador );
        carta3 = findViewById( R.id.carta1_jogador );
        carta4 = findViewById( R.id.carta2_jogador );
        carta5 = findViewById( R.id.carta3_jogador );
        carta6 = findViewById( R.id.carta4_jogador );
        carta7 = findViewById( R.id.carta5_jogador );

        carta1_computador = findViewById( R.id.carta4_computador );
        carta2_computador = findViewById( R.id.carta7_computador );
        carta3_computador = findViewById( R.id.carta3_computador );
        carta4_computador = findViewById( R.id.carta5_computador );
        carta5_computador = findViewById( R.id.carta2_computador );
        carta6_computador = findViewById( R.id.carta6_computador );
        carta7_computador = findViewById( R.id.carta1_computador );

        carta_reserva = findViewById( R.id.carta_reserva );

        vitoria_jogador.setText( String.valueOf( jogador.getVitorias() ) );
        derrota_jogador.setText( String.valueOf( jogador.getDerrotas() ) );

        getSupportActionBar().setTitle(jogador.getNome());
    }

    public void comecar_jogo(View view) {

        limpar_cartas_npc();

        valor_mao_jogador = 0;
        valor_mao_cpu = 0;

        indice_carta_jogador = 1;
        indice_carta_npc = 1;

        Log.i( "COMEÇANDO NOVO TURNO", String.valueOf( indice_carta_jogador ) );

        visibilidade_comecar();

        valor_mao_cpu = npc_valor_mao();

        valor_cpu.setText(String.valueOf( valor_mao_cpu ));

        primeiras_cartas_jogador();

    }

    public void terminar_turno(View view) {
        visibilidade_terminar();

        int pontuacao_vitoria = 0;
        int pontuacao_derrota = 0;

        if (verifica_ganhador().equals( "jogador" )){
            pontuacao_vitoria = 1;

        }else if (verifica_ganhador().equals( "computador" )){
            pontuacao_derrota = 1;

        }

        jogador.salvar_placar( pontuacao_vitoria, pontuacao_derrota);

        vitoria_jogador.setText( String.valueOf( jogador.getVitorias() ) );
        derrota_jogador.setText( String.valueOf( jogador.getDerrotas() ) );

    }

    public int npc_valor_mao() {
        Jogo jogo = new Jogo();

        int valor = jogo.entrega_carta();
        int carta1 = valor;

        coloca_carta_mesa( valor, "npc" );

        valor = jogo.entrega_carta();
        int carta2 = valor;

        coloca_carta_mesa( valor, "npc" );

        int valor_mao = carta1 + carta2;

       Log.i( "VALOR INICIAL NPC", String.valueOf( valor_mao ) );

        while (true) {

            Random comprar_parar = new Random();

            if (valor_mao <= 15) {
                valor = jogo.entrega_carta();

                int carta = valor;

                coloca_carta_mesa( valor, "npc" );

                valor_mao += carta;
            }
            else if (valor_mao < 21) {
                if (comprar_parar.nextInt( 3 ) == 1) {
                    valor = jogo.entrega_carta();

                    int carta = valor;

                    coloca_carta_mesa( valor, "npc" );

                    valor_mao += carta;
                } else {
                    break;
                }
            } else {
                break;
            }
        }

       Log.i( "VALOR FINAL NPC", String.valueOf( valor_mao ) );
        return valor_mao;
    }

    public void comprar_carta(View view) {

        int valor = jogador.entrega_carta();
        valor_mao_jogador += valor;

        coloca_carta_mesa( valor, "jogador" );

        valor_usuario.setText(String.valueOf( valor_mao_jogador ));

        if(valor_mao_jogador > 21)
            bt_comprar.setVisibility( View.INVISIBLE );

    }

    public void coloca_carta_mesa(int valor, String usuario){

        if(usuario.contentEquals( "jogador" )){

            Log.i( "CARTA JOGADOR:", String.valueOf( valor ) );

            verifica_carta_valor(valor, indice_carta_jogador, usuario );

            Log.i( "INDICE CARTA JOGADOR:", String.valueOf( indice_carta_jogador ) );

            indice_carta_jogador += 1;

            if (indice_carta_jogador == 8)
                indice_carta_jogador = 1;
        }else{

            Log.i( "CARTA NPC", String.valueOf( valor ) );
            verifica_carta_valor(valor, indice_carta_npc, usuario );

            Log.i( "INDICE CARTA NPC:", String.valueOf( indice_carta_npc ) );

            indice_carta_npc += 1;

            if (indice_carta_npc == 8)
                indice_carta_npc = 1;
        }

    }

    public void primeiras_cartas_jogador(){

        indice_carta_jogador = 1;

        int valor1 = jogador.entrega_carta();

        valor_mao_jogador += valor1;

        coloca_carta_mesa( valor1, "jogador"  );

        int valor2 = jogador.entrega_carta();

        valor_mao_jogador += valor2;

        coloca_carta_mesa( valor2,"jogador"  );

        valor_usuario.setText(String.valueOf( valor_mao_jogador ));
    }

    public void limpar_cartas_npc(){
        carta1_computador.setImageDrawable( null );
        carta2_computador.setImageDrawable( null );
        carta3_computador.setImageDrawable( null );
        carta4_computador.setImageDrawable( null );
        carta5_computador.setImageDrawable( null );
        carta6_computador.setImageDrawable( null );
        carta7_computador.setImageDrawable( null );
    }

    public void visibilidade_comecar(){

        esconder_cartas();

        carta2_computador.setVisibility( View.VISIBLE );
        carta_reserva.setVisibility( View.VISIBLE );

        bt_inicar_jogo.setVisibility( View.INVISIBLE );
        bt_parar.setVisibility( View.VISIBLE );
        bt_comprar.setVisibility( View.VISIBLE );

        valor_cpu.setVisibility( View.INVISIBLE );
        valor_usuario.setVisibility( View.VISIBLE );
    }

    public void visibilidade_terminar(){
        bt_parar.setVisibility( View.INVISIBLE );
        bt_comprar.setVisibility( View.INVISIBLE );
        bt_inicar_jogo.setVisibility( View.VISIBLE );

        valor_cpu.setVisibility( View.VISIBLE );
        valor_usuario.setVisibility( View.VISIBLE );

        carta_reserva.setVisibility( View.INVISIBLE );

        mostrar_cartas_computador();

    }

    public String verifica_ganhador(){
        if (valor_mao_jogador > 21 && valor_mao_cpu > 21){
            Toast.makeText(this, "Empatou!", Toast.LENGTH_LONG).show();
            return "empate";
        }
        else if(valor_mao_jogador > 21){
            Toast.makeText(this, "Você estourou, o computador ganhou!", Toast.LENGTH_LONG).show();
            return "computador";
        }
        else if(valor_mao_cpu > 21){
            Toast.makeText(this, "O computador estourou, você ganhou!", Toast.LENGTH_LONG).show();
            return "jogador";
        }
        else if(valor_mao_jogador == 21 && valor_mao_cpu == 21){
            Toast.makeText(this, "Empatou!", Toast.LENGTH_LONG).show();
            return "empate";
        }
        else {
            if (valor_mao_jogador > valor_mao_cpu){
                Toast.makeText(this, "Você ganhou!", Toast.LENGTH_LONG).show();
                return "jogador";
            }
            else if (valor_mao_jogador < valor_mao_cpu){
                Toast.makeText(this, "Você perdeu!", Toast.LENGTH_LONG).show();
                return "computador";
            }
            else{
                Toast.makeText(this, "Empatou!", Toast.LENGTH_LONG).show();
                return "empate";
            }
        }
    }

    public void mostrar_cartas_computador(){
        carta1_computador.setVisibility( View.VISIBLE );
        carta2_computador.setVisibility( View.VISIBLE );
        carta3_computador.setVisibility( View.VISIBLE );
        carta4_computador.setVisibility( View.VISIBLE );
        carta5_computador.setVisibility( View.VISIBLE );
        carta6_computador.setVisibility( View.VISIBLE );
        carta7_computador.setVisibility( View.VISIBLE );

    }

    public void esconder_cartas(){

        carta1.setVisibility( View.INVISIBLE );
        carta2.setVisibility( View.INVISIBLE );
        carta3.setVisibility( View.INVISIBLE );
        carta4.setVisibility( View.INVISIBLE );
        carta5.setVisibility( View.INVISIBLE );
        carta6.setVisibility( View.INVISIBLE );
        carta7.setVisibility( View.INVISIBLE );

        carta1_computador.setVisibility( View.INVISIBLE );
        carta3_computador.setVisibility( View.INVISIBLE );
        carta4_computador.setVisibility( View.INVISIBLE );
        carta5_computador.setVisibility( View.INVISIBLE );
        carta6_computador.setVisibility( View.INVISIBLE );
        carta7_computador.setVisibility( View.INVISIBLE );
    }

    public void verifica_carta_valor(int valor, int indice, String usuario){

        if (usuario.contentEquals( "jogador" )){
            if (indice == 1)
                carta = carta1;
            if (indice == 2)
                carta = carta2;
            if (indice == 3)
                carta = carta3;
            if (indice == 4)
                carta = carta4;
            if (indice == 5)
                carta = carta5;
            if (indice == 6)
                carta = carta6;
            if (indice == 7)
                carta = carta7;
        }else{
            if (indice == 1)
                carta = carta1_computador;
            if (indice == 2)
                carta = carta2_computador;
            if (indice == 3)
                carta = carta3_computador;
            if (indice == 4)
                carta = carta4_computador;
            if (indice == 5)
                carta = carta5_computador;
            if (indice == 6)
                carta = carta6_computador;
            if (indice == 7)
                carta = carta7_computador;
        }

        if (usuario.contentEquals( "npc" ) && valor == 10){
            Log.i( "VERIFICANDO CARTA NPC", String.valueOf( valor ) );
        }
            if (valor == 1)
                this.carta.setImageResource( R.drawable.card1 );
            if (valor == 2)
                this.carta.setImageResource( R.drawable.card2 );
            if (valor == 3)
                this.carta.setImageResource( R.drawable.card3 );
            if (valor == 4)
                this.carta.setImageResource( R.drawable.card4 );
            if (valor == 5)
                this.carta.setImageResource( R.drawable.card5 );
            if (valor == 6)
                this.carta.setImageResource( R.drawable.card6 );
            if (valor == 7)
                this.carta.setImageResource( R.drawable.card7 );
            if (valor == 8)
                this.carta.setImageResource( R.drawable.card8 );
            if (valor == 9)
                this.carta.setImageResource( R.drawable.card9 );
            if (valor == 10){
                Random sorteia_carta = new Random();

                int escolhe = sorteia_carta.nextInt(3);

                if (escolhe == 1){
                    this.carta.setImageResource( R.drawable.card10 );

                    Log.i( "CAIU NO ", "10" );
                }
                if (escolhe == 2){
                    this.carta.setImageResource( R.drawable.card11 );
                    Log.i( "CAIU NO ", "REI" );
                }
                if (escolhe == 3){
                    this.carta.setImageResource( R.drawable.card12 );
                    Log.i( "CAIU NA ", "DAMA" );
                }
                if (escolhe == 0){
                    this.carta.setImageResource( R.drawable.card13 );
                    Log.i( "CAIU NO ", "VALETE" );
                }
            }
        if (usuario.contentEquals( "jogador" ))   {
            carta.setVisibility( View.VISIBLE );
        }
    }
}
