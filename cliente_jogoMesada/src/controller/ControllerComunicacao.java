/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import comunicacao.ClienteJogoMesada;
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

    public ArrayList<String> iniciarPartida() {
        return cliente.iniciarPartida();
    }

    public void setControle(boolean controle) {
        this.controle = controle;
    }

    public void sairServidor() {
        cliente.sair();
    }   
}
