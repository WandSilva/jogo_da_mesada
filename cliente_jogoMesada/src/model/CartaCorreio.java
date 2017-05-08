package model;

/**
 * Created by wanderson on 07/05/17.
 */
public class CartaCorreio {
    private String tipo;
    private String nome;
    private double valor;

    public CartaCorreio(String tipo, String nome) {
        this.tipo = tipo;
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }


    public double getValor() {
        return valor;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }
}
