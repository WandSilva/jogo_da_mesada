package view.tabuleiro;

import javafx.scene.shape.Circle;

/**
 * O peão contém um 'Circle' (objeto que fica visível no tabuleiro) e sua coordenadas (coluna e linha).
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

    /**
     * instancia o peão e define o seu tamanho
     */
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
