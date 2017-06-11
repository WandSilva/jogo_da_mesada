package view.telaInicial;

import controller.Facade;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import view.tabuleiro.Tabuleiro;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
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
            atualizarStatus();

        } else {
            boolean nomeExiste = true;

            while (nomeExiste) {
                JOptionPane.showMessageDialog(null, "Usuário já logado. Por favor, escolha outro nome.");
                nomeUsuario = JOptionPane.showInputDialog("Informe seu nome:");
                if (facade.conectarServidor(nomeUsuario).equals("OK")) {
                    facade.iniciarJogador(nomeUsuario);
                    facade.enviarNotificacao(nomeUsuario);
                    nomeExiste = false;
                    atualizarStatus();
                }
            }
        }

    }

    @FXML
    public void iniciarPartida() {
        int aux = facade.getUsuariosConectados().get(0).getId();

        if(aux == facade.getIdJogador()){
            facade.iniciarPartida();
            facade.iniciarTabuleiro();
        }
        else{
            JOptionPane.showMessageDialog(null,"Você não é o moderador da sala.", "Calma amigão", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void atualizarStatus() {
        Task t = new Task() {
            @Override
            protected Object call() throws Exception {
                while (true) {
                    Platform.runLater(() -> {
                        String usuarios = new String();
                        for (int i = 0; i < facade.getUsuariosConectados().size(); i++) {
                            usuarios = usuarios + facade.getUsuariosConectados().get(i).getNome()+"\n";
                        }
                        txtJogadoresConect.setText(usuarios);

                        if (facade.getGatilhoInicioPartida()){
                            facade.setGatilhoInicioPartida(false);
                            Tabuleiro x = new Tabuleiro();
                            try {
                                x.start(new Stage());
                                ((Stage) txtJogadoresConect.getScene().getWindow()).close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Thread.currentThread().interrupt();
                        }
                    });

                    Thread.sleep(2000);
                }
            }
        };
        new Thread(t).start();


    }

}
