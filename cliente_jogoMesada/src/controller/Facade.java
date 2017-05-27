package controller;

import exception.SaldoInsuficienteException;
import model.CartaCompra;
import model.CartaCorreio;

import java.util.ArrayList;

/**
 * Created by wanderson on 06/05/17.
 */
public class Facade {

    private static Facade INSTANCE = null;
    private ControllerAcaoCasas controllerCasas;
    private ControllerJogador controllerJogador;
    private ControllerCartas controllerCartas;
    private ControllerComunicacao controllerComunicacao;

    public Facade() {
        this.controllerCasas = new ControllerAcaoCasas();
        this.controllerJogador = new ControllerJogador();
        this.controllerCartas = new ControllerCartas();
        this.controllerComunicacao = ControllerComunicacao.getInstance();
    }

    public static Facade getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Facade();
        }
        return INSTANCE;
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
    
    public String getNome()
    {
        return this.controllerJogador.getJogador().getNome();
    }

    public int getIdJogador() {
        return this.controllerJogador.getIdJogador();
    }

    public void comprarCartaCompraEntretenimento(CartaCompra cartaCompra) throws SaldoInsuficienteException {
        this.controllerJogador.comprarCartaCompraEntretenimento(cartaCompra);
    }

    public void comprarCartaCompraEntretenimento(double valor, CartaCompra cartaCompra) throws SaldoInsuficienteException {
        this.controllerJogador.comprarCartaCompraEntretenimento(valor, cartaCompra);
    }

    public void venderCartaCompraEntretenimento(String carta) {
        this.controllerJogador.venderCartaCompraEntretenimento(carta);
    }

    public double verDividaJogador() {
        return controllerJogador.verDividaJogador();
    }

    public double verSaldoJogador() {
        return controllerJogador.verSaldoJogador();
    }

    public ArrayList<CartaCorreio> verCartasCorreioJogador() {
        return this.controllerJogador.getCartasCorreioJogador();
    }

    public ArrayList<CartaCompra> verCartasCompraJogador() {
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

    public void pagarNeogocioOcasiao(int valorDado) throws SaldoInsuficienteException {
        this.controllerCasas.pagarNeogocioOcasiao(valorDado);
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

    public double getValorSorteGrande() {
        return this.controllerCasas.getValorSorteGrande();
    }

    //******************************METODOS DO CONTROLLER CARTA***********************//
    public void acaoCartas(boolean pegouCarta, String tipoCarta) throws SaldoInsuficienteException {
        this.controllerCartas.acaoCartas(pegouCarta, tipoCarta);
    }

    public CartaCorreio pegarCartaCorreio() {
        return this.controllerCartas.pegarCartaCorreio();
    }

    public CartaCompra pegarCartaCompra() {
        return this.controllerCartas.pegarCartaCompra();
    }

    //******************************METODOS DO CONTROLLER COMUNICACAO***********************//
    public String conectarServidor(String nomeUsuario) {
        return this.controllerComunicacao.conectarCliente(nomeUsuario);
    }

    public void enviarNotificacao(String nomeUsuario) {
        this.controllerComunicacao.enviarNotificacao(nomeUsuario);
    }

    public void setIP(String ip) {
        this.controllerComunicacao.setIP(ip);
    }

    public boolean getControle() {
        return this.controllerComunicacao.getControle();
    }

    public void setControle(boolean controle) {
        this.controllerComunicacao.setControle(controle);
    }

    public ArrayList<String> getUsuariosConectados() {

        return controllerComunicacao.usuariosConectados();
    }
    
    public void enviarOrdemJogada(ArrayList<String> ordemJogada)
    {
        controllerComunicacao.enviarOrdemJogada(ordemJogada);
    }
    
    public ArrayList<String> iniciarPartida()
    {
        return controllerComunicacao.iniciarPartida();
    }
    
    public void removerJogadorServidor() {
        controllerComunicacao.sairServidor();
    }
    
    public void enviarJogada(String nomeUsuario, int numDado)
    {
        controllerComunicacao.enviarJogada(nomeUsuario, numDado);
    }
}
