package model;

import exception.SaldoInsuficienteException;

import java.util.ArrayList;

/**
 * Created by wanderson on 06/05/17.
 */
public class Jogador {

    private static Jogador jogador = new Jogador();
    private ContaBancaria contaBancaria;
    private String nome;
    private ArrayList<CartaCorreio> cartasCorreio;
    private ArrayList<CartaCompra> cartasCompra;


    public Jogador() {
        this.contaBancaria = new ContaBancaria();
        this.cartasCorreio = new ArrayList<>();
    }

    /**
     * deposita um determinado valor na conta
     *
     * @param valor
     */
    public void depositar(double valor) {
        this.contaBancaria.depositar(valor);
    }

    /**
     * debita um determinado valor na conta
     *
     * @param valor
     */
    public void debitar(double valor) throws SaldoInsuficienteException {
        this.contaBancaria.debitar(valor);
    }

    /**
     * retorna o saldo do jogador
     * @return
     */
    public double getSaldoJogador() {
        return this.contaBancaria.getSaldo();
    }

    /**
     * retorna a dívida do jogador
     * @return
     */
    public double getDividaJogador() {
        return this.contaBancaria.getDivida();
    }


    /**
     * o jogador paga toda sua dívida ao banco
     *
     * @throws SaldoInsuficienteException
     */
    public void pagarDividaCompleta() throws SaldoInsuficienteException {
        this.debitar(getDividaJogador());
        contaBancaria.setDivida(0);
    }

    /**
     * paga apenas uma parte da dívida e o banco aplica juros.
     * Se o jogador tentar pagar um valor maior que sua dívida, será debitado o total valor da
     * dívida
     * @param valor
     * @throws SaldoInsuficienteException
     */
    public void pagarDividaParcial(double valor) throws SaldoInsuficienteException {
        if (valor > contaBancaria.getDivida())
            this.pagarDividaCompleta();
        else {
            this.debitar(valor);
            contaBancaria.diminuirDivida(valor);
        }
    }

    /**
     * pega um valor emprestado do banco, aumentando seu saldo e a sua dívida.
     *
     * @param valor
     */
    public void fazerEmprestimo(double valor) {
        this.depositar(valor);
        this.contaBancaria.aumentarDivida(valor);
    }

    /**
     * método utilizado para cobrar juros ao jogador
     */
    public void receberJuros(double valor) throws SaldoInsuficienteException {
        contaBancaria.aumentarDivida(valor);
    }

    /**
     * paga apenas os juros
     */
    public void pagarJuros(double valor) throws SaldoInsuficienteException {
        this.debitar(valor);
        contaBancaria.diminuirDivida(valor);
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public static Jogador getInstance() {
        return jogador;
    }

    public ArrayList<CartaCorreio> getCartasCorreio() {
        return cartasCorreio;
    }

    public ArrayList<CartaCompra> getCartasCompra() {
        return cartasCompra;
    }


}
