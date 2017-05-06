package model;

import exception.SaldoInsuficienteException;

/**
 * Gere as operações monetárias de um jogador.
 */
public class ContaBancaria {

    private double saldo;
    private double divida;

    public ContaBancaria() {
        this.saldo = 0;
        this.divida = 0;
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
     * paga toda a dívida do jogador
     * @throws SaldoInsuficienteException
     */
    public void pagarDividaCompleta() throws SaldoInsuficienteException {
        this.debitar(divida);
        this.divida = 0;
    }

    /**
     * paga apenas uma parte da dívida e o banco aplica juros.
     * Se o jogador tentar pagar um valor maior que sua dívida, será debitado o total valor da
     * dívida
     * @param valor
     * @throws SaldoInsuficienteException
     */
    public void pagarDividaParcial(double valor) throws SaldoInsuficienteException {
        if (valor > divida)
            this.pagarDividaCompleta();
        else {
            this.debitar(valor);
            this.divida -= valor;
            this.cobrarJuros();
        }
    }

    /**
     * faz um emprestimo, aumentando o saldo do jogador e a sua dívida.
     * @param valor
     */
    public void fazerEmprestimo(double valor){
        this.divida += valor;
        this.saldo =+ valor;
    }

    /**
     * método utilizado para cobrar juros caso o jogador não pague alguma parte dívida.
     */
    public void cobrarJuros(){
        this.divida += (this.divida * 0.1);
    }

    /**
     * retorna o valor da dívida do jogador
     * @return
     */
    public double getDivida() {
        return divida;
    }

}
