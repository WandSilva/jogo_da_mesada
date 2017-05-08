package controller;

import model.CartaCompras;
import model.CartaCorreio;

import java.util.ArrayList;

/**
 * Created by wanderson on 07/05/17.
 */
public class ControllerCartas {
    private ArrayList<CartaCorreio> listaCartasCorreio;
    private ArrayList<CartaCompras> listaCartasCompra;

    public ControllerCartas() {
        this.listaCartasCorreio = new ArrayList<>();
        this.listaCartasCompra = new ArrayList<>();
    }



    /*---------------- criado as cartas correio -------------------*/

    private void criarCartasConta(){
        CartaCorreio cartaConta1 = new CartaCorreio("contas", "mensalidade da Netflix");
        cartaConta1.setValor(50);

        CartaCorreio cartaConta2 = new CartaCorreio("contas", "Dívida na cantina");
        cartaConta1.setValor(100);

        listaCartasCorreio.add(cartaConta1);
        listaCartasCorreio.add(cartaConta2);
    }

    private void criarCartaPagueVizinhoAgora(){
        CartaCorreio cartaPagueVizinho1 = new CartaCorreio("pague um vizinho agora",
                "Reembolso por janela quebrada");
        cartaPagueVizinho1.setValor(500);

        CartaCorreio cartaPagueVizinho2 = new CartaCorreio("pague um vizinho agora",
                "Contribuição para a festa da vizinhança");
        cartaPagueVizinho1.setValor(200);

        listaCartasCorreio.add(cartaPagueVizinho1);
        listaCartasCorreio.add(cartaPagueVizinho2);
    }
    private void criarCartaDinheiroExtra(){
        CartaCorreio cartaDinheiroExtra1 = new CartaCorreio("dinheiro extra",
                "Contribuição por formatar computadores");
        cartaDinheiroExtra1.setValor(300);

        CartaCorreio cartaDinheiroExtra2 = new CartaCorreio("dinheiro extra",
                "Pagamento por ajudar a construir casa na arvore");
        cartaDinheiroExtra1.setValor(200);

        listaCartasCorreio.add(cartaDinheiroExtra1);
        listaCartasCorreio.add(cartaDinheiroExtra2);
    }
    private void criarCartaDoacoes(){
        CartaCorreio cartaDoacoes1 = new CartaCorreio("doacoes",
                "ajudar os moradores de rua");
        cartaDoacoes1.setValor(300);

        CartaCorreio cartaDoacoes2 = new CartaCorreio("doacoes",
                "ajuda para contruir o campo de futebol");
        cartaDoacoes1.setValor(100);

        listaCartasCorreio.add(cartaDoacoes1);
        listaCartasCorreio.add(cartaDoacoes2);
    }
    private void criarCartaCobrancaMosntro(){
        CartaCorreio cartaDoacoes1 = new CartaCorreio("cobranca monstro",
                "Computador novo");
        cartaDoacoes1.setValor(2200);

        CartaCorreio cartaDoacoes2 = new CartaCorreio("cobranca monstro",
                "Viagem nas ferias");
        cartaDoacoes1.setValor(3300);

        listaCartasCorreio.add(cartaDoacoes1);
        listaCartasCorreio.add(cartaDoacoes2);
    }

    private void criarCartaVaParaFrenteAgora(){
        CartaCorreio cartaVaParaFrente1 = new CartaCorreio("va para frente agora",
                "pode ir para a proxima casa ''Compras'' ou ''achou um comprador''");
        cartaVaParaFrente1.setValor(0);

        CartaCorreio cartaVaParaFrente2 = new CartaCorreio("va para frente agora",
                "pode ir para a proxima casa ''Compras'' ou ''achou um comprador''");
        cartaVaParaFrente1.setValor(0);

        listaCartasCorreio.add(cartaVaParaFrente1);
        listaCartasCorreio.add(cartaVaParaFrente2);
    }
}
