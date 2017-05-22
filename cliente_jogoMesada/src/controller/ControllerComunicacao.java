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
    private final ClienteJogoMesada cliente;
    private String endIP;

    private ControllerComunicacao(String ip) {
        this.endIP = ip;
        cliente = new ClienteJogoMesada(ip);
    }
    
    private ControllerComunicacao() {
        cliente = new ClienteJogoMesada();
    }

    public synchronized static ControllerComunicacao getInstance(String ipServidor) {
        if (INSTANCE == null) {
            INSTANCE = new ControllerComunicacao(ipServidor);
        }

        return INSTANCE;
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
}
