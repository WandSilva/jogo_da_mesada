package controller;

import exception.SaldoInsuficienteException;
import model.CartaCompra;
import model.CartaCorreio;

import java.util.ArrayList;

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

    public void iniciarJogador(String nome) {
        controllerJogador.iniciarJogador(nome);
    }

    public int rolarDado() {
        return this.controllerJogador.rolarDado();
    }

    public void pagarJuros() throws SaldoInsuficienteException {
        this.controllerJogador.pagarJuros();
    }
    public void pagarDividaTotal() throws SaldoInsuficienteException {
        this.controllerJogador.pagarDividaTotal();
    }
    public void pagarDividaParcial(double valor) throws SaldoInsuficienteException {
        this.controllerJogador.pagarDividaParcial(valor);
    }

    public void fazerEmprestimo(double valor) {
        this.controllerJogador.fazerEmprestimo(valor);
    }

    public void receberCartaCorreio(CartaCorreio cartaCorreio) {
        this.controllerJogador.receberCartaCorreio(cartaCorreio);
    }
    public int getIdJogador(){
        return this.controllerJogador.getIdJogador();
    }

    public void comprarCartaCompraEntretenimento(CartaCompra cartaCompra) throws SaldoInsuficienteException {
        this.controllerJogador.comprarCartaCompraEntretenimento(cartaCompra);
    }
    public void venderCartaCompraEntretenimento(String carta){
        this.controllerJogador.venderCartaCompraEntretenimento(carta);
    }

    public double verDividaJogador() {
        return controllerJogador.verDividaJogador();
    }

    public double verSaldoJogador() {
        return controllerJogador.verSaldoJogador();
    }

    public ArrayList<CartaCorreio> verCartasCorreioJogador(){
        return this.controllerJogador.getCartasCorreioJogador();
    }
    public ArrayList<CartaCompra> verCartasCompraJogador(){
        return controllerJogador.getCartaCompraJogador();
    }


    //******************************METODOS DO CONTROLLER CASAS***********************//

    public void acaCasaPremio() {
        this.controllerCasas.casaPremio();
    }

    public void acaoCasaSorteGrande(Boolean caiuNaCasa) {
        this.controllerCasas.casaSorteGrande(caiuNaCasa);
    }

    public void acaoCasaMaratonaBeneficente(int valorRolarDado) throws SaldoInsuficienteException {
        this.controllerCasas.casaMaratonaBeneficente(valorRolarDado);
    }

    public boolean casaConcursoArrocha(int valorRolarDado) {
        return this.controllerCasas.casaConcursoBandaArrocha(valorRolarDado);
    }

    public void acaoCasaPraiaNodomingo() throws SaldoInsuficienteException {
        this.controllerCasas.casaPraiaNodomingo();
    }

    public void acaoCasaAjudeaFloresta() throws SaldoInsuficienteException {
        this.controllerCasas.casaAjudeaFloresta();
    }

    public void acaoCasaLanchonete() throws SaldoInsuficienteException {
        this.controllerCasas.casaLanchonete();
    }

    public void acaoCasaShopping() throws SaldoInsuficienteException {
        this.controllerCasas.casaShopping();
    }

    public void acaoCasaFelizAniversario(boolean caiuNaCasa) throws SaldoInsuficienteException {
        this.controllerCasas.casaFelizAniversario(caiuNaCasa);
    }

    public void acaoCasaDiaDaMesada() {
        this.controllerCasas.casaDiaDaMesada();
    }

    public double getValorSorteGrande(){
        return this.controllerCasas.getValorSorteGrande();
    }

    //******************************METODOS DO CONTROLLER CARTA***********************//
    public void acaoCartas(boolean pegouCarta, String tipoCarta) throws SaldoInsuficienteException {
        this.controllerCartas.acaoCartas(pegouCarta,tipoCarta);
    }
    public CartaCorreio pegarCartaCorreio() {
        return this.controllerCartas.pegarCartaCorreio();
    }
    public CartaCompra pegarCartaCompra(){
        return this.controllerCartas.pegarCartaCompra();
    }
}
