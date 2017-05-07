package model;

/**
 * Created by wanderson on 07/05/17.
 */
public class CartaCompras {
    private String nome;
    private String descricao;
    private double valorInicial;
    private double valorRevenda;

    public CartaCompras(String nome, double valorInicial, double valorRevenda) {
        this.nome = nome;
        this.valorInicial = valorInicial;
        this.valorRevenda = valorRevenda;
    }
}
