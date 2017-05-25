/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import comunicacao.ClienteJogoMesada;

/**
 *
 * @author Santana
 */
public class ControllerComunicacao {

    private static ControllerComunicacao INSTANCE = null;
    private  ClienteJogoMesada cliente;
    private String endIP;
    private boolean controle;

    private ControllerComunicacao() {
    }

    public void setIP(String ip){
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
     
     public void enviarNotificacao(String nomeUsuario)
     {
         cliente.entrouNaSala(nomeUsuario);
     }

    public boolean getControle() {
        return controle;
    }

    public void setControle(boolean controle) {
        this.controle = controle;
    }
}
