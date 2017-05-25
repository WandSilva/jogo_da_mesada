package view.tabuleiro;/**
 * Created by wanderson on 08/05/17.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Tabuleiro extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Jogo da mesada");
        stage.show();
    }

}
///home/vinicius/Jogo da Mesada/cliente_jogoMesada/src/Tabuleiro/FXMLView.fxml