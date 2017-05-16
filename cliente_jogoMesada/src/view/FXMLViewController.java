package view;

import controller.Facade;
import exception.SaldoInsuficienteException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FXMLViewController implements Initializable {

    //********DECLARAÇÃO DOS PANES PRESENTES NO GRID**********/
    @FXML
    private Pane casa23;
    @FXML
    private Pane casa01;
    @FXML
    private Pane casa00;
    @FXML
    private Pane casa22;
    @FXML
    private Pane casa44;
    @FXML
    private Pane casa03;
    @FXML
    private Pane casa02;
    @FXML
    private Pane casa24;
    @FXML
    private Pane casa04;
    @FXML
    private Pane casa61;
    @FXML
    private Pane casa60;
    @FXML
    private Pane casa63;
    @FXML
    private Pane casa41;
    @FXML
    private Pane casa62;
    @FXML
    private Pane casa40;
    @FXML
    private Pane casa43;
    @FXML
    private Pane casa21;
    @FXML
    private Pane casa42;
    @FXML
    private Pane casa20;
    @FXML
    private Pane casa64;
    @FXML
    private Pane casa12;
    @FXML
    private Pane casa34;
    @FXML
    private Pane casa33;
    @FXML
    private Pane casa11;
    @FXML
    private Pane casa14;
    @FXML
    private Pane casa13;
    @FXML
    private Pane casa50;
    @FXML
    private Pane casa52;
    @FXML
    private Pane casa30;
    @FXML
    private Pane casa51;
    @FXML
    private Pane casa32;
    @FXML
    private Pane casa10;
    @FXML
    private Pane casa54;
    @FXML
    private Pane casa53;
    @FXML
    private Pane casa31;
    //////////////////

    @FXML
    private GridPane grid;
    @FXML
    private Label labelSaldo;
    @FXML
    private Label labelDivida;
    @FXML
    private ComboBox<String> comboCorreio;
    @FXML
    private TextArea textCorreio;

    private Peao peao;

    Facade facade;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.facade = new Facade();
        String nome = JOptionPane.showInputDialog("Informe seu nome");
        facade.iniciarJogador("nome");
        this.atualizarValoresTela();
        this.peao = new Peao();
        peao.getPeao().setStroke(Color.AQUA);
        peao.getPeao().setFill(Color.GOLD);
        this.grid.add(peao.getPeao(), 0, 0);
        this.adicionarImagensTabuleiro();
        this.mostrarCartasCorreio();
    }


    @FXML
    public void moverPeao(ActionEvent event) {

        int dado = facade.rolarDado();
        JOptionPane.showMessageDialog(null, "Valor sorteado: " + dado);

        peao.setColuna(peao.getColuna() + dado);

        if (peao.getColuna() > 6) {
            peao.setColuna(dado - (7 - ((peao.getColuna() - dado))));
            peao.setLinha(peao.getLinha() + 1);
        }
        if (peao.getLinha() >= 5 && peao.getColuna()>=3) {
            peao.setLinha(0);
            peao.setColuna(0);
        }

        grid.getChildren().remove(peao.getPeao());
        grid.add(peao.getPeao(), peao.getColuna(), peao.getLinha());

        this.realizarAcaoCasa(peao.getColuna(), peao.getLinha());
    }

    public void realizarAcaoCasa(int coluna, int linha) {

        //correio de 1 carta
        if ((coluna == 1 && linha == 0) || (coluna == 4 && linha == 1) || (coluna == 5 && linha == 2) || (coluna == 1 && linha == 3)) {
            facade.receberCartaCorreio(facade.pegarCartaCorreio());
            this.mostrarCartasCorreio();
        }
        //casa premio
        else if (coluna == 2 && linha == 0) {
            facade.acaCasaPremio();
            atualizarValoresTela();
        }
        //correio de 3 cartas
        else if ((coluna == 3 && linha == 0) || (coluna == 2 && linha == 2)) {
            facade.receberCartaCorreio(facade.pegarCartaCorreio());
            facade.receberCartaCorreio(facade.pegarCartaCorreio());
            facade.receberCartaCorreio(facade.pegarCartaCorreio());
            this.mostrarCartasCorreio();
        }
        //correio de 2 cartas
        else if ((coluna == 5 && linha == 0) || (coluna == 3 && linha == 3)) {
            facade.receberCartaCorreio(facade.pegarCartaCorreio());
            facade.receberCartaCorreio(facade.pegarCartaCorreio());
            this.mostrarCartasCorreio();
        }
        //compras e entretenimento
        else if ((coluna == 4 && linha == 0) || (coluna == 5 && linha == 1) || (coluna == 1 && linha == 2) || (coluna == 4 && linha == 3)) {
            // facade.acaCasaPremio();
            atualizarValoresTela();
        }
        //bolao de esportes
        else if ((coluna == 6 && linha == 0) || (coluna == 6 && linha == 1) || (coluna == 6 && linha == 2) || (coluna == 6 && linha == 3)) {
            //  facade.acaCasaPremio();
            atualizarValoresTela();
        }
        //achou um comprador
        else if ((coluna == 2 && linha == 1) || (coluna == 3 && linha == 3) || (coluna == 2 && linha == 2) || (coluna == 5 && linha == 3) || (coluna == 1 && linha == 4)) {
            // facade.acaCasaPremio();
            atualizarValoresTela();
        }
        //praia no domingo
        else if (coluna == 0 && linha == 1) {
            try {
                facade.acaoCasaPraiaNodomingo();
            } catch (SaldoInsuficienteException e) {
                JOptionPane.showMessageDialog(null, "Saldo insuficiente, faça um deposito", "Saldo insuficiente", JOptionPane.ERROR_MESSAGE);
            }
            atualizarValoresTela();
        }
        //concurso banda de rock
        else if (coluna == 1 && linha == 1) {
            facade.casaConcursoArrocha(0);
            atualizarValoresTela();
        }
        //feliz aniversário
        else if (coluna == 3 && linha == 1) {
            try {
                facade.acaoCasaFelizAniversario(true);
            } catch (SaldoInsuficienteException e) {
                e.printStackTrace();
            }
            atualizarValoresTela();
        }
        //ajude a floresta
        else if (coluna == 0 && linha == 2) {
            try {
                facade.acaoCasaAjudeaFloresta();
            } catch (SaldoInsuficienteException e) {
                JOptionPane.showMessageDialog(null, "Saldo insuficiente, faça um deposito", "Saldo insuficiente", JOptionPane.ERROR_MESSAGE);
            }
            atualizarValoresTela();
        }
        //lanchonete
        else if (coluna == 4 && linha == 2) {
            try {
                facade.acaoCasaLanchonete();
            } catch (SaldoInsuficienteException e) {
                JOptionPane.showMessageDialog(null, "Saldo insuficiente, faça um deposito", "Saldo insuficiente", JOptionPane.ERROR_MESSAGE);
            }
            atualizarValoresTela();
        }
        //negocio de ocasião
        else if (coluna == 0 && linha == 3) {
            //facade.acaCasaPremio();
            atualizarValoresTela();
        }
        //compras no shopping
        else if (coluna == 0 && linha == 4) {
            try {
                facade.acaoCasaShopping();
            } catch (SaldoInsuficienteException e) {
                JOptionPane.showMessageDialog(null, "Saldo insuficiente, faça um deposito", "Saldo insuficiente", JOptionPane.ERROR_MESSAGE);
            }
            atualizarValoresTela();
        }
        //maratona beneficente
        else if (coluna == 2 && linha == 4) {
            try {
                facade.acaoCasaMaratonaBeneficente(0);
            } catch (SaldoInsuficienteException e) {
                e.printStackTrace();
            }
            atualizarValoresTela();
        }

    }

    @FXML
    public void fazerEmprestimo() {
        String valor = JOptionPane.showInputDialog("Valor do emprestimo:");
        facade.fazerEmprestimo(Double.parseDouble(valor));
        labelDivida.setText("Dívida: " + facade.verDividaJogador());
        labelSaldo.setText("Saldo: " + facade.verSaldoJogador());
    }

    private void adicionarImagensTabuleiro() {
        ArrayList<Pane> casas = organizarCasas();

        for (Pane casa : casas) {
            BackgroundImage bi = new BackgroundImage(new Image(casa.getId() + ".png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
            casa.setBackground(new Background(bi));
        }
    }

    public void atualizarValoresTela() {
        labelDivida.setText("Dívida: " + facade.verDividaJogador());
        labelSaldo.setText("Saldo: " + facade.verSaldoJogador());
    }

    public void mostrarCartasCorreio() {
        comboCorreio.getItems().clear();
        for (int i=0; i<facade.verCartasJogador().size(); i++)
            comboCorreio.getItems().addAll(facade.verCartasJogador().get(i).getTipo());
    }

    @FXML
    private void teste(ActionEvent event) {

        System.out.println(comboCorreio.getValue());
    }


    private ArrayList<Pane> organizarCasas() {
        ArrayList<Pane> casas = new ArrayList<>();
        casas.add(casa00);
        casas.add(casa01);
        casas.add(casa02);
        casas.add(casa03);
        casas.add(casa04);
        casas.add(casa10);
        casas.add(casa11);
        casas.add(casa12);
        casas.add(casa13);
        casas.add(casa14);
        casas.add(casa20);
        casas.add(casa21);
        casas.add(casa22);
        casas.add(casa23);
        casas.add(casa24);
        casas.add(casa30);
        casas.add(casa31);
        casas.add(casa32);
        casas.add(casa33);
        casas.add(casa34);
        casas.add(casa40);
        casas.add(casa41);
        casas.add(casa42);
        casas.add(casa43);
        casas.add(casa50);
        casas.add(casa51);
        casas.add(casa52);
        casas.add(casa53);
        casas.add(casa60);
        casas.add(casa61);
        casas.add(casa62);
        casas.add(casa63);
        casas.add(casa64);

        return casas;
    }

}
