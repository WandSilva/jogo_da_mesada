package model;

/**
 * Created by wanderson on 31/05/17.
 */
public class OrdemJogada {
    private String nome;
    private int id;

    public OrdemJogada(String nome, int id) {
        this.nome = nome;
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
