package model;

import java.util.ArrayList;

/**
 *
 * @author vinicius
 */
public class Sala {

    private ArrayList<Jogador> jogadores;

    public Sala(Jogador novoJogador) {
        jogadores.add(novoJogador);
    }
}
