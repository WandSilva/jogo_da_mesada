package view;

import controller.ControllerJogador;
import controller.Facade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.scene.shape.Circle;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLViewController implements Initializable {

    @FXML
    private Pane paneTeste;
    @FXML
    private Pane paneTeste2;
    @FXML
    private GridPane grid;

    private Peao peao;


    ControllerJogador controllerJogador = new ControllerJogador("nome");

    Image image = new Image("img.jpg");
    ImageView imageView = new ImageView(image);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.peao = new Peao();
        grid.add(peao.getPeao(),0,0);

        imageView.setScaleX(0.09);
        imageView.setScaleY(0.09);
        BackgroundImage myBI = new BackgroundImage(new Image("pokeball.png"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
        paneTeste.setBackground(new Background(myBI));


        BackgroundFill myBF = new BackgroundFill(Color.BLUEVIOLET, new CornerRadii(1),
                new Insets(0.0, 0.0, 0.0, 0.0));// or null for the padding
        paneTeste2.setBackground(new Background(myBF));
    }

    @FXML
    public void testeGrid(ActionEvent event) {

        int dado = controllerJogador.rolarDado();
        JOptionPane.showMessageDialog(null, "Valor sorteado: " + dado);

        peao.setColuna(peao.getColuna() + dado);

        if (peao.getColuna() > 6) {
            peao.setColuna(dado - (7 - ((peao.getColuna()-dado))));
            peao.setLinha(peao.getLinha() + 1);
        }
        if (peao.getLinha() == 5) {
            peao.setLinha(0);
            peao.setColuna(0);
        }

        grid.getChildren().remove(peao.getPeao());
        grid.add(peao.getPeao(), peao.getColuna(), peao.getLinha());


    }

    @FXML
    public void testeGrid2(ActionEvent event) {


    }

}
