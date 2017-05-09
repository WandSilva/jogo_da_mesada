package view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLViewController implements Initializable {

    @FXML
    private Pane paneTeste;
    @FXML
    private Pane paneTeste2;
    @FXML
    private GridPane grid;

    @FXML
    private Circle peao;
    @FXML
    private Circle peao2;


    private int rowP1= 0;
    private int columP1 = 0;
    private int rowP2= 0;
    private int columP2 = 0;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        BackgroundImage myBI= new BackgroundImage(new Image("img.jpg"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
        paneTeste.setBackground(new Background(myBI));


        BackgroundFill myBF = new BackgroundFill(Color.BLUEVIOLET, new CornerRadii(1),
                new Insets(0.0,0.0,0.0,0.0));// or null for the padding
        paneTeste2.setBackground(new Background(myBF));
    }

    @FXML
    public void testeGrid(ActionEvent event){
        grid.getChildren().remove(peao);
        grid.add(peao, columP1,rowP1);
        columP1++;
        if (columP1==7){
            columP1=0;
            rowP1++;
        }
        if (rowP1==5){
            columP1=0;
            rowP1=0;
        }

    }

    @FXML
    public void testeGrid2(ActionEvent event){
        grid.getChildren().remove(peao2);
        grid.add(peao2, columP2,rowP2);
        columP2++;
        if (columP2==7){
            columP2=0;
            rowP2++;
        }
        if (rowP2==5){
            columP2=0;
            rowP2=0;
        }

    }

}
