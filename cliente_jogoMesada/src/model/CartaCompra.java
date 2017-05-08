package model;

/**
 * Created by wanderson on 07/05/17.
 */
public class CartaCompra {
    private String nome;
    private double valorInicial;
    private double valorRevenda;

    public CartaCompra(String nome, double valorInicial, double valorRevenda) {
        this.nome = nome;
        this.valorInicial = valorInicial;
        this.valorRevenda = valorRevenda;
    }

    public String getNome() {
        return nome;
    }

    public double getValorInicial() {
        return valorInicial;
    }

    public double getValorRevenda() {
        return valorRevenda;
    }
}
