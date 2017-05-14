package view;

import javafx.scene.shape.Circle;

/**
 * Created by wanderson on 14/05/17.
 */
public class Peao {

    private Circle peao;
    private int linha;
    private int coluna;

    public Peao(){
        initPeao();
        this.coluna = 0;
        this.linha = 0;
    }
    public void initPeao(){
        this.peao = new Circle();
        peao.setRadius(20);
    }

    public Circle getPeao() {
        return peao;
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
