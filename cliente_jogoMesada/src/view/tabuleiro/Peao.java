package view.tabuleiro;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;


/**
 * O peão contém um 'Circle' (objeto que fica visível no tabuleiro) e sua coordenadas (coluna e linha).
 */
public class Peao {

    private Pane peao;
    private int linha;
    private int coluna;

    public Peao(){
        this.coluna = 0;
        this.linha = 0;
    }

    public Pane getPeao() {
        return peao;
    }

    public void setPeao(Pane peao) {
        this.peao = peao;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }

}
