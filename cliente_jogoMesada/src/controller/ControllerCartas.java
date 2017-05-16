package controller;

import exception.SaldoInsuficienteException;
import model.CartaCompra;
import model.CartaCorreio;
import model.Jogador;
import model.SorteGrande;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by wanderson on 07/05/17.
 */
public class ControllerCartas {
    private ArrayList<CartaCorreio> listaCartasCorreio;
    private ArrayList<CartaCompra> listaCartasCompra;
    private Jogador jogador;
    private SorteGrande sorteGrande;

    public ControllerCartas() {
        this.listaCartasCorreio = new ArrayList<>();
        this.listaCartasCompra = new ArrayList<>();
        this.jogador = Jogador.getInstance();
        this.sorteGrande = SorteGrande.getInstance();
        this.iniciarCartasCorreio();
        this.criarCartasCompra();
    }


    /*--------------------------------- METODOS DAS CARTAS CORREIO --------------------------*/

    /**
     * recebe uma carta e verifica seu tipo para executar sua devida ação
     *
     * @param pegouCarta
     * @param cartaCorreio
     * @throws SaldoInsuficienteException
     */
    public void acaoCartas(boolean pegouCarta, CartaCorreio cartaCorreio) throws SaldoInsuficienteException {
        switch (cartaCorreio.getTipo()) {
            case "contas":
                this.acaoCartaConta(cartaCorreio);
                break;
            case "pague um vizinho agora":
                this.acaoCartaPagueVizinhoAgora(pegouCarta, cartaCorreio);
                break;
            case "dinheiro extra":
                this.acaoDinheiroExtra(pegouCarta, cartaCorreio);
                break;
            case "doacoes":
                this.acaoDoacoes(cartaCorreio);
                break;
            case "cobranca monstro":
                this.acaoCobrancaMonstro(cartaCorreio);
                break;
            case "va para frente agora":
                this.criarCartaVaParaFrenteAgora();
        }
    }

    private void acaoCartaConta(CartaCorreio cartaCorreio) throws SaldoInsuficienteException {
        jogador.debitar(cartaCorreio.getValor());
    }

    private void acaoCartaPagueVizinhoAgora(boolean pegouCarta, CartaCorreio cartaCorreio) throws SaldoInsuficienteException {
        if (pegouCarta)
            jogador.debitar(cartaCorreio.getValor());
        else
            jogador.depositar(cartaCorreio.getValor());
    }

    private void acaoDinheiroExtra(boolean pegouCarta, CartaCorreio cartaCorreio) throws SaldoInsuficienteException {
        if (pegouCarta)
            jogador.depositar(cartaCorreio.getValor());
        else
            jogador.debitar(cartaCorreio.getValor());
    }

    private void acaoDoacoes(CartaCorreio cartaCorreio) throws SaldoInsuficienteException {
        jogador.debitar(cartaCorreio.getValor());
        sorteGrande.arrecadarDinheiro(cartaCorreio.getValor());
    }

    public void acaoCobrancaMonstro(CartaCorreio cartaCorreio) throws SaldoInsuficienteException {
        jogador.debitar(cartaCorreio.getValor());
    }

    public void acaoVaParaFrenteAgora(CartaCorreio cartaCorreio) {

    }


    /*---------------------------------- criando as cartas correio ---------------------------*/

    /**
     * intancia aa cartaa correio do tipo 'conta'
     */
    private void criarCartasConta() {
        CartaCorreio cartaConta1 = new CartaCorreio("contas", "mensalidade da Netflix");
        cartaConta1.setValor(50);

        CartaCorreio cartaConta2 = new CartaCorreio("contas", "Dívida na cantina");
        cartaConta1.setValor(100);

        listaCartasCorreio.add(cartaConta1);
        listaCartasCorreio.add(cartaConta2);
    }

    /**
     * instancia as cartas correio do tipo 'Pague um vizinho agora'
     */
    private void criarCartaPagueVizinhoAgora() {
        CartaCorreio cartaPagueVizinho1 = new CartaCorreio("pague um vizinho agora",
                "Reembolso por janela quebrada");
        cartaPagueVizinho1.setValor(500);

        CartaCorreio cartaPagueVizinho2 = new CartaCorreio("pague um vizinho agora",
                "Contribuição para a festa da vizinhança");
        cartaPagueVizinho1.setValor(200);

        listaCartasCorreio.add(cartaPagueVizinho1);
        listaCartasCorreio.add(cartaPagueVizinho2);
    }

    /**
     * instancia as cartas correio do tipo 'Dinheiro extra'
     */
    private void criarCartaDinheiroExtra() {
        CartaCorreio cartaDinheiroExtra1 = new CartaCorreio("dinheiro extra",
                "Contribuição por formatar computadores");
        cartaDinheiroExtra1.setValor(300);

        CartaCorreio cartaDinheiroExtra2 = new CartaCorreio("dinheiro extra",
                "Pagamento por ajudar a construir casa na arvore");
        cartaDinheiroExtra1.setValor(200);

        listaCartasCorreio.add(cartaDinheiroExtra1);
        listaCartasCorreio.add(cartaDinheiroExtra2);
    }

    /**
     * instancia as cartas correio do tipo 'Doacoes'
     */
    private void criarCartaDoacoes() {
        CartaCorreio cartaDoacoes1 = new CartaCorreio("doacoes",
                "ajudar os moradores de rua");
        cartaDoacoes1.setValor(300);

        CartaCorreio cartaDoacoes2 = new CartaCorreio("doacoes",
                "ajuda para contruir o campo de futebol");
        cartaDoacoes1.setValor(100);

        listaCartasCorreio.add(cartaDoacoes1);
        listaCartasCorreio.add(cartaDoacoes2);
    }

    /**
     * instancia as cartas correio do tipo 'Cobranca monstro'
     */
    private void criarCartaCobrancaMosntro() {
        CartaCorreio cartaDoacoes1 = new CartaCorreio("cobranca monstro",
                "Computador novo");
        cartaDoacoes1.setValor(2200);

        CartaCorreio cartaDoacoes2 = new CartaCorreio("cobranca monstro",
                "Viagem nas ferias");
        cartaDoacoes1.setValor(3300);

        listaCartasCorreio.add(cartaDoacoes1);
        listaCartasCorreio.add(cartaDoacoes2);
    }

    /**
     * instancia as cartas correio do tipo 'Va para frente agora'
     */
    private void criarCartaVaParaFrenteAgora() {
        CartaCorreio cartaVaParaFrente1 = new CartaCorreio("va para frente agora",
                "pode ir para a proxima casa ''Compras'' ou ''achou um comprador''");
        cartaVaParaFrente1.setValor(0);

        CartaCorreio cartaVaParaFrente2 = new CartaCorreio("va para frente agora",
                "pode ir para a proxima casa ''Compras'' ou ''achou um comprador''");
        cartaVaParaFrente1.setValor(0);

        listaCartasCorreio.add(cartaVaParaFrente1);
        listaCartasCorreio.add(cartaVaParaFrente2);
    }

    /*----------------------------metodos das cartas compra----------------------------*/

    public void criarCartasCompra() {
        CartaCompra cartaCompra1  = new CartaCompra("Smart phone novo", 1500, 2000);
        CartaCompra cartaCompra2  = new CartaCompra("Smart phone usado", 1000, 1200);
        CartaCompra cartaCompra3  = new CartaCompra("PS4", 1500, 2000);
        CartaCompra cartaCompra4  = new CartaCompra("Item raro de colecionador", 5000, 10000);
        CartaCompra cartaCompra5  = new CartaCompra("Jogo novo", 200, 250);
        CartaCompra cartaCompra6  = new CartaCompra("Aeromodelo", 2000, 3000);
        CartaCompra cartaCompra7  = new CartaCompra("Bola de futebol", 200, 250);
        CartaCompra cartaCompra8  = new CartaCompra("Pacote de Jogos na steam", 500, 750);
        CartaCompra cartaCompra9  = new CartaCompra("Ingresso para jogo da seleção", 500, 1000);
        CartaCompra cartaCompra10 = new CartaCompra("Guitarra", 1500, 2000);
        CartaCompra cartaCompra11 = new CartaCompra("ingresso pro show do Safadão", 300, 600);
        CartaCompra cartaCompra12 = new CartaCompra("Notebook", 2500, 3500);


        listaCartasCompra.add(cartaCompra1);
        listaCartasCompra.add(cartaCompra2);
        listaCartasCompra.add(cartaCompra3);
        listaCartasCompra.add(cartaCompra4);
        listaCartasCompra.add(cartaCompra5);
        listaCartasCompra.add(cartaCompra6);
        listaCartasCompra.add(cartaCompra7);
        listaCartasCompra.add(cartaCompra8);
        listaCartasCompra.add(cartaCompra9);
        listaCartasCompra.add(cartaCompra10);
        listaCartasCompra.add(cartaCompra11);
        listaCartasCompra.add(cartaCompra12);


    }


    /*----------------------------outros metodos--------------------------------------*/

    /**
     * instancia as cartas correio
     */
    private void iniciarCartasCorreio() {
        this.criarCartaCobrancaMosntro();
        this.criarCartaDinheiroExtra();
        this.criarCartaDoacoes();
        this.criarCartaPagueVizinhoAgora();
        this.criarCartasConta();
        this.criarCartaVaParaFrenteAgora();
    }

    /**
     * retorna uma carta correio aleatoria
     *
     * @return CartaCorreio
     */
    public CartaCorreio pegarCartaCorreio() {
        Random random = new Random();
        int sorteio = random.nextInt(12);
        return listaCartasCorreio.get(sorteio);
    }

    /**
     * retorna uma carta de compra aleatória
     * @return CartaCompra
     */
    public CartaCompra pegarCartaCompra(){
        Random random = new Random();
        int sorteio = random.nextInt(12);
        return listaCartasCompra.get(sorteio);
    }

}
