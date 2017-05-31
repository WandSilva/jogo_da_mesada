package view.tabuleiro;

import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by wanderson on 30/05/17.
 */
public class AparenciaPeao {

    ArrayList<Pane> peoes;

    public AparenciaPeao(){
        this.peoes = new ArrayList<>();
        init();
    }

    public void init(){
        Pane peao1 = new Pane();
        BackgroundImage bi1 = new BackgroundImage(new Image("amarelo.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
        peao1.setBackground(new Background(bi1));

        Pane peao2 = new Pane();
        BackgroundImage bi2 = new BackgroundImage(new Image("vermelho.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
        peao2.setBackground(new Background(bi2));

        Pane peao3 = new Pane();
        BackgroundImage bi3 = new BackgroundImage(new Image("azul.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
        peao3.setBackground(new Background(bi3));

        Pane peao4 = new Pane();
        BackgroundImage bi4 = new BackgroundImage(new Image("rosa.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
        peao4.setBackground(new Background(bi4));

        Pane peao5 = new Pane();
        BackgroundImage bi5 = new BackgroundImage(new Image("lilas.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
        peao5.setBackground(new Background(bi5));

        Pane peao6 = new Pane();
        BackgroundImage bi6 = new BackgroundImage(new Image("verde.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
        peao6.setBackground(new Background(bi6));

        peoes.add(peao1);
        peoes.add(peao2);
        peoes.add(peao3);
        peoes.add(peao4);
        peoes.add(peao5);
        peoes.add(peao6);
    }

    public ArrayList<Pane> getAparenciaPeoes(){
        return this.peoes;
    }
}
