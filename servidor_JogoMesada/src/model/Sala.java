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
        jogadores = new ArrayList<>();
        jogadores.add(novoJogador);
        numeroJogadores = 1;
    }

    public Sala() {

    }

    public void addJogador(Jogador novoJogador) {
        jogadores.add(novoJogador);
        numeroJogadores = numeroJogadores + 1;
    }

    public int tamanhoSala() {
        return numeroJogadores;
    }

    public boolean salaOcupada() {
        if (jogando == true)
            return true;
        else
            return false;
    }

    public void ocuparSala() {
        this.jogando = true;
    }

    public void desocuparSala() {
        this.jogando = false;
    }

    public boolean removerJogador(Jogador removerJogador) {
        for (Jogador jogador : jogadores) {
            if (jogador != null && jogador.equals(removerJogador)) {
                jogadores.remove(jogador);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Jogador> getJogadores() {
        return jogadores;
    }

    public int getNumeroJogadores() {
        return numeroJogadores;
    }

    public boolean isJogando() {
        return jogando;
    }

}
