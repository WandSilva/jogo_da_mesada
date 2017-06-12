package controller;

import exception.SaldoInsuficienteException;
import model.CartaCompra;
import model.CartaCorreio;
import model.OrdemJogada;

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
        this.controllerComunicacao = ControllerComunicacao.getInstance();
        this.controllerJogador = new ControllerJogador();
        this.controllerCasas = new ControllerAcaoCasas();
        this.controllerCartas = new ControllerCartas();
    }

    public static Facade getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Facade();
        }
        return INSTANCE;
    }

    public void sincronizarComunicacao() {
        this.controllerCasas.setCliente(this.controllerComunicacao.getCliente());
        this.controllerCartas.setCliente(this.controllerComunicacao.getCliente());

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

    public void receberDinheiro(double valor) {
        this.controllerJogador.receberDinheiro(valor);
    }

    public void darDinheiro(double valor) {
        try {
            controllerJogador.darDineiro(valor);
        } catch (SaldoInsuficienteException e) {
            e.printStackTrace();
        }
    }

    public void receberCartaCorreio(CartaCorreio cartaCorreio) {
        this.controllerJogador.receberCartaCorreio(cartaCorreio);
    }

    public String getNome() {
        return this.controllerJogador.getJogador().getNome();
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

    public void acaoCasaSorteGrande() {
        this.controllerCasas.casaSorteGrande();
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
        //return this.controllerComunicacao.getSorteGrande();
        return this.controllerCasas.getSorteGrande();
    }

    //******************************METODOS DO CONTROLLER CARTA***********************//
    public void acaoCartas(int idDestino, String tipoCarta) throws SaldoInsuficienteException {
        this.controllerCartas.acaoCartas(idDestino, tipoCarta);
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

    public ArrayList<OrdemJogada> getUsuariosConectados() {

        return controllerComunicacao.usuariosConectados();
    }

    public void enviarOrdemJogada(ArrayList<String> ordemJogada) {
        controllerComunicacao.enviarOrdemJogada(ordemJogada);
    }

    public ArrayList<String> iniciarPartida() {
        return controllerComunicacao.iniciarPartida();
    }

    public void removerJogadorServidor() {
        controllerComunicacao.sairServidor();
    }

    public void enviarJogada(int id, int numDado) {
        controllerComunicacao.enviarJogada(id, numDado);
    }

    public int getIdJogador() {
        return this.controllerComunicacao.getIdJogador();
    }

    public int getProximoJogador() {
        return this.controllerComunicacao.getProximoJogador();
    }

    public int getUltimoDado() {
        return this.controllerComunicacao.getUltimoDado();
    }

    public double getSorteGrande() {
        return this.controllerComunicacao.getSorteGrande();
    }

    public boolean getControleSorte() {
        return this.controllerComunicacao.getControle();
    }

    public void setControleSorteGrande(boolean valor) {
        this.controllerComunicacao.setControleSorteGrande(valor);
    }

    public void informarJogada(int dado) {
        this.controllerComunicacao.informarJogada(dado);
    }

    public void setControleTranferenciaIn(boolean controle) {
        this.controllerComunicacao.setControleTranferenciaIn(controle);
    }

    public void setControleTranferenciaOut(boolean controle) {
        this.controllerComunicacao.setControleTranferenciaOut(controle);
    }

    public boolean getControleTranferenciaIn() {
        return controllerComunicacao.getControleTranferenciaIn();
    }

    public boolean getControleTranferenciaOut() {
        return this.controllerComunicacao.getControleTranferenciaOut();
    }

    public double getValorTranferencia() {
        return this.controllerComunicacao.getValorTranferencia();
    }

    public int getIdJogadorTranferencia() {
        return controllerComunicacao.getIdJogadorTranferencia();
    }

    public boolean getGatilhoInicioPartida() {
        return this.controllerComunicacao.getGatilhoInicioPartida();
    }

    public void setGatilhoInicioPartida(boolean gatilho) {
        this.controllerComunicacao.setGatilhoInicioPartida(gatilho);
    }

    public void iniciarTabuleiro() {
        this.controllerComunicacao.iniciarTabuleiro();
    }

    public void finalizarJogada(int atualJogador) {
        this.controllerComunicacao.finalizarJogada(atualJogador);
    }

    public int getAtualJogador() {
        return this.controllerComunicacao.getAtualJogador();
    }


    public void concursoBandaRock(int idProximo) {
        this.controllerComunicacao.concursoBandaRock(idProximo);
    }

    public void setControleConcursoBanda(boolean controle) {
        this.controllerComunicacao.setControleConcursoBanda(controle);
    }

    public boolean getControleConcursoBanda() {
        return this.controllerComunicacao.getControleConcursoBanda();
    }

    public int getIdProxJogadorEvento() {
        return this.controllerComunicacao.getIdProxJogadorEvento();
    }

    public void participarBolao(int id) {
        this.controllerComunicacao.participarBolao(id);
    }

    public String getNomeUsuarioPorId(int id) {
        return this.controllerComunicacao.getNomeUsiarioPorId(id);
    }

    public int getReultadoBolao() {
        return this.controllerComunicacao.getReultadoBolao();
    }

    public boolean getControleBolao() {
        return this.controllerComunicacao.getControleBolao();
    }

    public void setControleBolao(boolean controleBolao) {
        this.controllerComunicacao.setControleBolao(controleBolao);
    }

    public int getNumeroParticipantesBolao() {
        return this.controllerComunicacao.getNumeroParticipantesBolao();
    }

    public void organizadorBolao(int id) {
        this.controllerComunicacao.organizadorBolao(id);
    }

    public int getOrganizadorBolao() {
        return this.controllerComunicacao.getOrganizadorBolao();
    }

    public void resultadoBolao(int resultado) {
        this.controllerComunicacao.resultadoBolao(resultado);
    }
    
    public void enviarSaldoFinal(double saldoFinal)
    {
        this.controllerComunicacao.enviarSaldoFinal(saldoFinal);
    }

    public void setVencedorBolao(int vencedorBolao){
        this.controllerComunicacao.setVencedorBolao(vencedorBolao);
    }

    public void limparListaBolao(){
        this.controllerComunicacao.limparListaBolao();
    }
}
