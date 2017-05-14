package model;

import java.util.ArrayList;

/**
 *
 * @author vinicius
 */

public class Sala {

    private ArrayList<Jogador> jogadores;
    private int numeroJogadores;
    private boolean jogando = false;

    public Sala(Jogador novoJogador) {
        jogadores.add(novoJogador);
        numeroJogadores = 1;
    }

    public void addJogador(Jogador novoJogador) {
        jogadores.add(novoJogador);
        numeroJogadores = numeroJogadores + 1;
    }

    public int tamanhoSala() {
        return numeroJogadores;
    }

    public boolean salaOcupada() {
        if (!this.jogando) {
            return this.jogando;
        } else {
            return !this.jogando;
        }

    }
}
