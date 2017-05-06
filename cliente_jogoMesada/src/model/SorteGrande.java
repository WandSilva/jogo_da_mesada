package model;

/**
 * Created by wanderson on 06/05/17.
 */
public class SorteGrande {

    private double valorAcumulado;

    public SorteGrande(){
        this.valorAcumulado = 0;
    }

    /**
     * arrecada dinheiro do(s) jogador(es)
     * @param valor
     */
    public void arrecadarDinheiro(double valor){
        this.valorAcumulado += valor;
    }

    /**
     * doa o dinheiro para o jogador que caiu na casa sorte Grande
     * @return
     */
    public double doarTodoDinheiro(){
        double valor = valorAcumulado;
        this.valorAcumulado = 0;
        return valor;
    }
}
