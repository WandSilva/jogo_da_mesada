package model;

import exception.SaldoInsuficienteException;

/**
 * Created by wanderson on 06/05/17.
 */
public class Jogador {

    private ContaBancaria conta;
    private String nome;

    public Jogador(String nome) {
        this.conta = new ContaBancaria();
        this.nome = nome;
    }

    public void depositar(double valor){
        this.conta.depositar(valor);
    }
    public void debitar(double valor) throws SaldoInsuficienteException {
        this.conta.debitar(valor);
    }

    public double getSaldoJogador(){
        return this.conta.getSaldo();
    }
    public double getDividaJogador(){
        return this.conta.getDivida();
    }
    public void pagarDividaCompleta() throws SaldoInsuficienteException {
        conta.pagarDividaCompleta();
    }
    public void pagarDividaParcial(double valor) throws SaldoInsuficienteException {
        conta.pagarDividaParcial(valor);
    }




}
