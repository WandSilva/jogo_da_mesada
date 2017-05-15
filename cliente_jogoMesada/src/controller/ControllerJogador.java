package controller;

import model.CartaCorreio;
import model.Jogador;

import java.util.Random;

/**
 * Created by wanderson on 07/05/17.
 */
public class ControllerJogador {

    private Jogador jogador;

    public ControllerJogador() {
    }

    /**
     * cria um jogador para a partida.
     * @param nome
     */
    public void iniciarJogador(String nome){
        this.jogador = Jogador.getInstance();
        this.jogador.setNome(nome);
        this.jogador.depositar(3000);
    }

    /**
     * rola um dado de 6 faces.
     * @return um valor aleatório entre 1 e 6.
     */
    public int rolarDado(){
        Random dado = new Random();
        int dado6Faces = 1 + dado.nextInt( 6 );
        return dado6Faces;
    }

    public void receberCartaCorreio(CartaCorreio cartaCorreio){
        jogador.receberCartaCorreio(cartaCorreio);
    }

    public void fazerEmprestimo(double valor){
        jogador.fazerEmprestimo(valor);
    }
}
