package controller;

import exception.SaldoInsuficienteException;
import model.Jogador;
import model.SorteGrande;

import java.util.Random;

/**
 * Created by wanderson on 06/05/17.
 */
public class ControllerAcaoCasas {

    private SorteGrande sorteGrande;
    private Jogador jogador;
    private int numeroDeJogadores;

    public ControllerAcaoCasas() {
        this.sorteGrande = new SorteGrande();
        this.jogador = Jogador.getInstance();
    }


/*---------------------------------- OPERACOES DAS CASAS ESPECIAIS ----------------------------*/
    /**
     * recebe o dinheiro acumulado no sorte grande se cair na casa 'Sorte Grande'.
     */
    public void casaSorteGrande(Boolean caiuNaCasa){
        if (caiuNaCasa)
            jogador.depositar(sorteGrande.doarTodoDinheiro());
        else
            sorteGrande.doarTodoDinheiro();
    }

    /**
     * paga 100 multiplicado pelo valor sorteado no dado caso
     * um outro jogador caia na casa 'Manatona Beneficente'
     * @param valorRolarDado
     * @throws SaldoInsuficienteException
     */
    public void casaMaratonaBeneficente(int valorRolarDado) throws SaldoInsuficienteException {
        jogador.debitar(valorRolarDado*100);
        this.sorteGrande.arrecadarDinheiro(valorRolarDado*100);
    }

    /**
     * Rola um valor no dado, se o valor sorteado for 3, o jogador ganha 1000.
     * @param valorRolarDado
     * @return TRUE se ganhou o concurso, caso não, FALSE
     */
    public Boolean casaConcursoBandaArrocha(int valorRolarDado){
        if (valorRolarDado == 3){
            jogador.depositar(1000);
            return true;
        }
        else return false;
    }

    public void casaPremio(){
        jogador.depositar(5000);
    }

    public void casaPraiaNodomingo(double valor) throws SaldoInsuficienteException {
        jogador.debitar(valor);
        sorteGrande.arrecadarDinheiro(valor);
    }

    public void casaAjudeaFloresta(double valor) throws SaldoInsuficienteException {
        jogador.debitar(valor);
        sorteGrande.arrecadarDinheiro(valor);
    }
    public void casaLanchonete(double valor) throws SaldoInsuficienteException {
        jogador.debitar(valor);
        sorteGrande.arrecadarDinheiro(valor);
    }
    public void casaShopping(double valor) throws SaldoInsuficienteException {
        jogador.debitar(valor);
        sorteGrande.arrecadarDinheiro(valor);
    }
    public void casaFelizAniversario(boolean caiuNaCasa) throws SaldoInsuficienteException {
        if (caiuNaCasa)
            jogador.depositar(numeroDeJogadores*100);
        else
            jogador.debitar(100);
    }

    /**
     * @param opcaoEscolhida
     * 1 - pagar toda a dívida
     * 2 - pagar parte da dívida
     * 3 - pagar apenas os juros
     * @throws SaldoInsuficienteException
     */
    public void casaDiaDaMesada(int opcaoEscolhida, double valorPagamentoDivida) throws SaldoInsuficienteException {

        jogador.depositar(3500); //recebe a mesada

        switch (opcaoEscolhida){
            case 1:
                jogador.pagarDividaCompleta();
                break;
            case 2:
                jogador.pagarDividaParcial(valorPagamentoDivida);
                break;
            case 3:
                jogador.pagarApenasJuros();
        }
    }

}
