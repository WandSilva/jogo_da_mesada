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
    private int idJogador;

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

    public void setIdJogador(int idJogador) {
        this.idJogador = idJogador;
    }

    public int getIdJogador() {
        return idJogador;
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

    public void pagarJuros() throws SaldoInsuficienteException {
        jogador.pagarJuros(jogador.getDividaMensal() * 0.1);
    }

    public void pagarDividaTotal() throws SaldoInsuficienteException {
        jogador.pagarDividaCompleta();
    }

    public void pagarDividaParcial(double valor) throws SaldoInsuficienteException {
        jogador.pagarDividaParcial(valor);
    }

    public void receberCartaCorreio(CartaCorreio cartaCorreio) {
        jogador.receberCartaCorreio(cartaCorreio);
    }

    public void comprarCartaCompraEntretenimento(CartaCompra cartaCompra) throws SaldoInsuficienteException {
        jogador.debitar(cartaCompra.getValorInicial());
        jogador.addCartaCompraEntretenimento(cartaCompra);
    }

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

    public void fazerEmprestimo(double valor) {
        jogador.fazerEmprestimo(valor);
    }

    public double verDividaJogador() {
        return jogador.getDividaJogador();
    }

    public double verSaldoJogador() {
        return jogador.getSaldoJogador();
    }

    public ArrayList<CartaCorreio> getCartasCorreioJogador() {
        return jogador.getCartasCorreio();
    }

    public ArrayList<CartaCompra> getCartaCompraJogador() {
        return this.jogador.getCartasCompra();
    }
}
