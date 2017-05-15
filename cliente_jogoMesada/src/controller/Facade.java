package controller;

import model.CartaCorreio;

/**
 * Created by wanderson on 06/05/17.
 */
public class Facade {

    private ControllerAcaoCasas controllerCasas;
    private ControllerJogador controllerJogador;
    private ControllerCartas controllerCartas;

    public Facade() {
        this.controllerCasas = new ControllerAcaoCasas();
        this.controllerJogador = new ControllerJogador();
        this.controllerCartas = new ControllerCartas();
    }

    //******************************METODOS DO CONTROLLER JOGADOR***********************//

    public void iniciarJogador(String nome){
        controllerJogador.iniciarJogador(nome);
    }

    public int rolarDado(){
        return this.controllerJogador.rolarDado();
    }

    public void fazerEmprestimo(double valor){
        this.controllerJogador.fazerEmprestimo(valor);
    }

    public void receberCartaCorreio(CartaCorreio cartaCorreio){
        this.controllerJogador.receberCartaCorreio(cartaCorreio);
    }

    public double verDividaJogador(){
        return controllerJogador.verDividaJogador();
    }
    public double verSaldoJogador(){
        return controllerJogador.verSaldoJogador();
    }


    //******************************METODOS DO CONTROLLER CASAS***********************//

    //******************************METODOS DO CONTROLLER CARTA***********************//
}
