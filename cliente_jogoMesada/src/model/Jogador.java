package model;

import exception.SaldoInsuficienteException;

/**
 * Created by wanderson on 06/05/17.
 */
public class Jogador {

    private static Jogador jogador = new Jogador();
    private ContaBancaria contaBancaria;
    private String nome;

    public Jogador() {
        this.contaBancaria = new ContaBancaria();

    }

    /**
     * deposita um determinado valor na conta
     * @param valor
     */
    public void depositar(double valor) {
        this.contaBancaria.depositar(valor);
    }

    /**
     * debita um determinado valor na conta
     * @param valor
     */
    public void debitar(double valor) throws SaldoInsuficienteException {
        this.contaBancaria.debitar(valor);
    }

    public double getSaldoJogador() {
        return this.contaBancaria.getSaldo();
    }

    public double getDividaJogador() {
        return this.contaBancaria.getDivida();
    }

    /**
     * o jogador paga toda sua dívida ao banco
     * @throws SaldoInsuficienteException
     */
    public void pagarDividaCompleta() throws SaldoInsuficienteException {
        contaBancaria.pagarDividaCompleta();
    }

    /**
     * o jogador paga parte de dua dívida. O banco aplica juros sobre a parte restante.
     * @param valor
     * @throws SaldoInsuficienteException
     */
    public void pagarDividaParcial(double valor) throws SaldoInsuficienteException {
        contaBancaria.pagarDividaParcial(valor);
    }

    /**
     * pega um valor emprestado do banco, aumentando seu saldo e a sua dívida.
     * @param valor
     */
    public void PegarEmprestimo(double valor) {
        this.contaBancaria.fazerEmprestimo(valor);
    }

    /**
     * método utilizado para cobrar juros caso o jogador escolha por não pagar
     * alguma parte da dívida.
     */
    public void pagarJuros() {
        this.contaBancaria.cobrarJuros();
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public static Jogador getInstance() {
        return jogador;
    }


}
