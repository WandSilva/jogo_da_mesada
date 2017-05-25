package view.telaInicial;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by wanderson on 25/05/17.
 */
public class Application extends javafx.application.Application {
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLTelaInicial.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Jogo da mesada");
        stage.show();
    }
}
