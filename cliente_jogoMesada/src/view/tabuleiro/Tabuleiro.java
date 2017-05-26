package view.tabuleiro;/**
 * Created by wanderson on 08/05/17.
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class Tabuleiro extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });
        Parent root = FXMLLoader.load(getClass().getResource("/view/tabuleiro/FXMLView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Jogo da mesada");
        stage.show();
    }

}
///home/vinicius/Jogo da Mesada/cliente_jogoMesada/src/Tabuleiro/FXMLView.fxml