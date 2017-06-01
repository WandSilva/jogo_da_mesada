package view.telaInicial;

import controller.Facade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.OrdemJogada;
import view.tabuleiro.Tabuleiro;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by wanderson on 25/05/17.
 */
public class FXMLTelaInicialController implements Initializable {

    @FXML
    TextArea txtJogadoresConect;

    private Facade facade;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String ipServidor = JOptionPane.showInputDialog("Informe o IP do Servidor:");
        this.facade = Facade.getInstance();
        this.facade.setIP(ipServidor);
        String nomeUsuario = JOptionPane.showInputDialog("Informe seu nome:");
        if (facade.conectarServidor(nomeUsuario).equals("OK")) {
            facade.iniciarJogador(nomeUsuario);
            facade.enviarNotificacao(nomeUsuario);
            mostraJogadorConectado();

        } else {
            boolean nomeExiste = true;

            while (nomeExiste) {
                JOptionPane.showMessageDialog(null, "Usuário já logado. Por favor, escolha outro nome.");
                nomeUsuario = JOptionPane.showInputDialog("Informe seu nome:");
                if (facade.conectarServidor(nomeUsuario).equals("OK")) {
                    facade.iniciarJogador(nomeUsuario);
                    facade.enviarNotificacao(nomeUsuario);
                    nomeExiste = false;
                    mostraJogadorConectado();
                }
            }
        }

    }

    @FXML
    public void iniciarPartida() {
        Tabuleiro x = new Tabuleiro();

        try {
            x.start(new Stage());
            ((Stage) txtJogadoresConect.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mostraJogadorConectado() {
        ArrayList<OrdemJogada> lista = new ArrayList();
        lista = facade.getUsuariosConectados();
        String usuarios = new String();
        for (int i = 0; i < lista.size(); i++) {
            usuarios = usuarios + lista.get(i).getNome()+"\n";
        }
        txtJogadoresConect.setText(usuarios);
    }

}
