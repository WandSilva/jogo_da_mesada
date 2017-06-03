package controller;

import comunicacao.ClienteJogoMesada;
import exception.SaldoInsuficienteException;
import model.Jogador;

/**
 * Created by wanderson on 06/05/17.
 */
public class ControllerAcaoCasas {

    private Jogador jogador;
    private int numeroDeJogadores;
    private ClienteJogoMesada cliente;

    public ControllerAcaoCasas() {
        this.jogador = Jogador.getInstance();
    }


/*---------------------------------- OPERACOES DAS CASAS ESPECIAIS ----------------------------*/

    /**
     * recebe o dinheiro acumulado no sorte grande se cair na casa 'Sorte Grande'.
     */
    public void casaSorteGrande() {
        Double valor = cliente.getSorteGrande();
        jogador.depositar(valor);
        cliente.setSorteGrande(0);
    }

    /**
     * paga 100 multiplicado pelo valor sorteado no dado caso
     * um outro jogador caia na casa 'Manatona Beneficente'
     *
     * @param valorRolarDado
     * @throws SaldoInsuficienteException
     */
    public void casaMaratonaBeneficente(int valorRolarDado) throws SaldoInsuficienteException {
        int valor = valorRolarDado * 100;
        try {
            jogador.debitar(valor);
        } catch (SaldoInsuficienteException e) {
            jogador.fazerEmprestimo(valor - jogador.getSaldoJogador());
            jogador.debitar(valor);
        }
        double aux = cliente.getSorteGrande();
        cliente.setSorteGrande(aux+valor);
    }

    /**
     * Rola um valor no dado, se o valor sorteado for 3, o jogador ganha 1000.
     *
     * @param valorRolarDado
     * @return TRUE se ganhou o concurso, caso não, FALSE
     */
    public boolean casaConcursoBandaArrocha(int valorRolarDado) {
        if (valorRolarDado == 3) {
            jogador.depositar(1000);
            return true;
        } else return false;
    }

    /**
     * evento da casa Bolão de esportes
     *
     * @param valorDado
     * @param numeroEscolhido
     * @param numeroParticipantes
     * @return
     * @throws SaldoInsuficienteException
     */
    public boolean casaBolaoEsportes(int valorDado, int numeroEscolhido, int numeroParticipantes) throws SaldoInsuficienteException {
        jogador.debitar(100);
        double premio = 1000 + numeroParticipantes * 100;

        if (numeroEscolhido == valorDado) {
            jogador.depositar(premio);
            return true;
        } else return false;

    }

    /**
     * debita o 100 x valor do dado sorteado na casa Negocio de Ocasião
     *
     * @param valorDado
     * @throws SaldoInsuficienteException
     */
    public void pagarNeogocioOcasiao(int valorDado) throws SaldoInsuficienteException {
        try {
            jogador.debitar(valorDado * 100);
        } catch (SaldoInsuficienteException e) {
            jogador.fazerEmprestimo((valorDado * 100) - jogador.getSaldoJogador());
            jogador.debitar(valorDado * 100);
        }
    }

    public void casaPremio() {
        jogador.depositar(5000);
    }

    public void casaPraiaNodomingo() throws SaldoInsuficienteException {
        try {
            jogador.debitar(100);
        } catch (SaldoInsuficienteException e) {
            jogador.fazerEmprestimo(100 - jogador.getSaldoJogador());
            jogador.debitar(100);
        }
        double aux = cliente.getSorteGrande();
        cliente.setSorteGrande(aux+100);
    }

    public void casaAjudeaFloresta() throws SaldoInsuficienteException {
        try {
            jogador.debitar(100);
        } catch (SaldoInsuficienteException e) {
            jogador.fazerEmprestimo(100 - jogador.getSaldoJogador());
            jogador.debitar(100);
        }
        double aux = cliente.getSorteGrande();
        cliente.setSorteGrande(aux+100);
    }

    public void casaLanchonete() throws SaldoInsuficienteException {
        try {
            jogador.debitar(100);
        } catch (SaldoInsuficienteException e) {
            jogador.fazerEmprestimo(100 - jogador.getSaldoJogador());
            jogador.debitar(100);
        }
        double aux = cliente.getSorteGrande();
        cliente.setSorteGrande(aux+100);    }

    public void casaShopping() throws SaldoInsuficienteException {
        try {
            jogador.debitar(100);
        } catch (SaldoInsuficienteException e) {
            jogador.fazerEmprestimo(100 - jogador.getSaldoJogador());
            jogador.debitar(100);
        }
        double aux = cliente.getSorteGrande();
        cliente.setSorteGrande(aux+100);
    }

    public void casaFelizAniversario(boolean caiuNaCasa) throws SaldoInsuficienteException {
        if (caiuNaCasa)
            jogador.depositar(numeroDeJogadores * 100);
        else
            try {
                jogador.debitar(100);
            } catch (SaldoInsuficienteException e) {
                jogador.fazerEmprestimo(100 - jogador.getSaldoJogador());
                jogador.debitar(100);
            }
    }

    /**
     * paga a mesada e faz o cálculo dos juros
     *
     * @throws SaldoInsuficienteException
     */
    public void casaDiaDaMesada() {
        jogador.depositar(3500); //recebe a mesada
        jogador.setDividaMensal(jogador.getDividaJogador()); //pega a dívida do mês
        jogador.receberJuros(jogador.getDividaMensal() * 0.1);
    }


    public void setCliente(ClienteJogoMesada cliente) {
        this.cliente = cliente;
    }

    public double DELETARISSO(){
        return cliente.getSorteGrande();
    }

}
