package controller;

import exception.SaldoInsuficienteException;
import model.CartaCompra;
import model.CartaCorreio;
import model.Jogador;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by wanderson on 07/05/17.
 */
public class ControllerJogador {

    private Jogador jogador;

    public Jogador getJogador() {
        return jogador;
    }

    public ControllerJogador() {
    }

    /**
     * cria um jogador para a partida.
     *
     * @param nome
     */
    public void iniciarJogador(String nome) {
        this.jogador = Jogador.getInstance();
        this.jogador.setNome(nome);
        this.jogador.depositar(3000);
    }


    /**
     * rola um dado de 6 faces.
     *
     * @return um valor aleatório entre 1 e 6.
     */
    public int rolarDado() {
        Random dado = new Random();
        int dado6Faces = 1 + dado.nextInt(6);
        return dado6Faces;
    }

    /**
     * paga 10% de juros sobre a dívida
     * @throws SaldoInsuficienteException
     */
    public void pagarJuros() throws SaldoInsuficienteException {
        jogador.pagarJuros(jogador.getDividaMensal() * 0.1);
    }

    /**
     * toda toda a dívida do jogador
     * @throws SaldoInsuficienteException
     */
    public void pagarDividaTotal() throws SaldoInsuficienteException {
        jogador.pagarDividaCompleta();
    }

    /**
     * paga parte da dívida do jogador
     * @param valor
     * @throws SaldoInsuficienteException
     */
    public void pagarDividaParcial(double valor) throws SaldoInsuficienteException {
        jogador.pagarDividaParcial(valor);
    }

    /**
     * o jogador recebe uma carta correio
     * @param cartaCorreio
     */
    public void receberCartaCorreio(CartaCorreio cartaCorreio) {
        jogador.receberCartaCorreio(cartaCorreio);
    }

    /**
     * o jogador recebe uma carta Compras e Entretenimento e
     * debita o valor informado na carta
     * @param cartaCompra
     * @throws SaldoInsuficienteException
     */
    public void comprarCartaCompraEntretenimento(CartaCompra cartaCompra) throws SaldoInsuficienteException {
        jogador.debitar(cartaCompra.getValorInicial());
        jogador.addCartaCompraEntretenimento(cartaCompra);
    }

    /**
     *  o jogador recebe uma carta Compras e Entretenimento e
     *  debita informado por parametro
     * @param valor
     * @param cartaCompra
     * @throws SaldoInsuficienteException
     */
    public void comprarCartaCompraEntretenimento(double valor, CartaCompra cartaCompra) throws SaldoInsuficienteException {
        jogador.debitar(valor);
        jogador.addCartaCompraEntretenimento(cartaCompra);
    }

    /**
     * o jogador vende uma de suas cartas Compra e Entretenimento e
     * recebe a quantia informada na carta
     * @param nomeCartaCompra
     */
    public void venderCartaCompraEntretenimento(String nomeCartaCompra) {

        CartaCompra cartaCompra = null;
        for (CartaCompra aux : jogador.getCartasCompra()) {
            if (aux.getNome().equals(nomeCartaCompra)) {
                cartaCompra = aux;
            }
        }
        jogador.removerCartaCompraEntretemimento(cartaCompra);
        jogador.depositar(cartaCompra.getValorRevenda());
    }

    /**
     * aumento o saldo e a dívida de acordo com o valor informado
     * @param valor
     */
    public void fazerEmprestimo(double valor) {
        jogador.fazerEmprestimo(valor);
    }

    /**
     * retorna a dívida do jogadir
     * @return
     */
    public double verDividaJogador() {
        return jogador.getDividaJogador();
    }

    /**
     * retorna a dívida do jogador
     * @return
     */
    public double verSaldoJogador() {
        return jogador.getSaldoJogador();
    }

    /**
     * retorna a lista de cartas correio de um jogador
     * @return
     */
    public ArrayList<CartaCorreio> getCartasCorreioJogador() {
        return jogador.getCartasCorreio();
    }

    /**
     * retorna a lista de cartas compra de um jogador
     * @return
     */
    public ArrayList<CartaCompra> getCartaCompraJogador() {
        return this.jogador.getCartasCompra();
    }
    

}
