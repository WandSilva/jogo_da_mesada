package model;

import exception.SaldoInsuficienteException;

/**
 * Gere as operações monetárias de um jogador.
 */
public class ContaBancaria {

    private double saldo;
    private double dividaTotal;
    private double dividaMensal;

    public ContaBancaria() {
        this.saldo = 0;
        this.dividaTotal = 0;
    }

    /**
     * deposita um determinado valor na conta
     * @param valor
     */
    public void depositar(double valor) {
        this.saldo += valor;
    }

    /**
     * debita um determinado valor na conta
     * @param valor
     * @throws SaldoInsuficienteException
     */
    public void debitar(double valor) throws SaldoInsuficienteException {
        if (valor > this.saldo)
            throw new SaldoInsuficienteException();
        else
            this.saldo -= valor;
    }

    /**
     * retorna o saldo atual do jogador
     * @return
     */
    public double getSaldo() {
        return saldo;
    }


    /**
     * método utilizado para cobrar juros caso o jogador não pague alguma parte dívida.
     */
    public void cobrarApenasJuros() throws SaldoInsuficienteException {
        double juros = (dividaTotal * 0.1);
        debitar(juros);
    }


    public void aumentarDivida(double valor) {
        this.dividaTotal += valor;
    }

    public void diminuirDivida(double valor){
        this.dividaTotal -= valor;
    }

    public void setDivida(double divida) {
        this.dividaTotal = divida;
    }

    /**
     * retorna o valor da dívida do jogador
     * @return
     */
    public double getDivida() {
        return dividaTotal;
    }

    public double getDividaMensal() {
        return dividaMensal;
    }

    public void setDividaMensal(double dividaMensal) {
        this.dividaMensal = dividaMensal;
    }
}
