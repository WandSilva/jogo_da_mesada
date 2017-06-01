/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import comunicacao.ClienteJogoMesada;
import model.Jogador;
import model.OrdemJogada;

import java.util.ArrayList;

/**
 *
 * @author Santana
 */
public class ControllerComunicacao {

    private static ControllerComunicacao INSTANCE = null;
    private ClienteJogoMesada cliente;
    ArrayList<OrdemJogada> usuarios;
    private String endIP;

    private ControllerComunicacao() {
        this.usuarios = new ArrayList<>();
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
        ArrayList<OrdemJogada> lista = usuariosConectados();
        for (int i=0;i<lista.size();i++){
            if(lista.get(i).getNome().equals(nome)){
                id = lista.get(i).getId();
            }
        }
        return id;
    }

    public void enviarNotificacao(String nomeUsuario) {
        cliente.entrouNaSala(nomeUsuario);
    }

    public void enviarJogada(int id, int numDado) {
        cliente.jogar(id, numDado);
    }

    public int getProximoJogador() {
        return Integer.parseInt(cliente.getProximoJogador());
    }

    public int getUltimoDado() {
        return cliente.getUltimoDado();
    }

    public boolean getControle() {
        return cliente.getControleMsgJogada();
    }

    public ArrayList<OrdemJogada> usuariosConectados() {
        ArrayList<String> nomeUsuarios = cliente.usuariosConectados();

        usuarios.clear();
        for (int i=0; i<nomeUsuarios.size();i++){
            usuarios.add(new OrdemJogada(nomeUsuarios.get(i), i));
        }
        return usuarios;
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
        this.cliente.setControleMsgJogada(controle);
    }

    public void sairServidor() {
        cliente.sair();
    }

    public void finalizarJogada(int dado) {
        int meuId = this.getIdJogador();
        int proximo = meuId+1;
        
        cliente.jogar(proximo, dado);
        //agora é só mandar o ID do proximo e o Dado que foi recebido por parametro;
    }
}
