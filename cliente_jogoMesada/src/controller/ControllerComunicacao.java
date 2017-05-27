/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import comunicacao.ClienteJogoMesada;
import model.Jogador;

import java.util.ArrayList;

/**
 *
 * @author Santana
 */
public class ControllerComunicacao {

    private static ControllerComunicacao INSTANCE = null;
    private ClienteJogoMesada cliente;
    private String endIP;
    private boolean controle;

    private ControllerComunicacao() {
    }

    public void setIP(String ip) {
        this.endIP = ip;
        cliente = new ClienteJogoMesada(ip);
    }

    public synchronized static ControllerComunicacao getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ControllerComunicacao();
        }

        return INSTANCE;
    }

    public String conectarCliente(String nomeUsuario) {

        return cliente.entrar(nomeUsuario);
    }

    public int getIdJogador(){
        int id=0;
        String nome = Jogador.getInstance().getNome();
        ArrayList<String> lista = usuariosConectados();
        for (int i=0;i<lista.size();i++){
            if(lista.get(i).equals(nome)){
                id =i;
            }
        }
        return id;
    }

    public void enviarNotificacao(String nomeUsuario) {
        cliente.entrouNaSala(nomeUsuario);
    }

    public void enviarJogada(String nomeUsuario, int numDado) {
        cliente.jogar(nomeUsuario, numDado);
    }

    public String getUltimoJogador() {
        return cliente.getUltimoJogador();
    }

    public int getUltimoDado() {
        return cliente.getUltimoDado();
    }

    public boolean getControle() {
        return controle;
    }

    public ArrayList<String> usuariosConectados() {
        return cliente.usuariosConectados();
    }

    public ArrayList<String> iniciarPartida()
    {
       return cliente.iniciarPartida();
    }
    
    public void enviarOrdemJogada(ArrayList<String> ordemJogada)
    {
        cliente.enviarOrdemJogada(ordemJogada);
    }
    
    public void setControle(boolean controle) {
        this.controle = controle;
    }

    public void sairServidor() {
        cliente.sair();
    }
}
