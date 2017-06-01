package comunicacao;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.*;
import java.util.ArrayList;
import model.Sala;

/**
 * Esta classe implementa a parte da aplicação responśavel por comunicar-se com
 * o servidor e com outros clientes por meio de requisições. Modelo
 * Peer-to-Peer.
 *
 * @version 1.0
 * @author Wanderson e Santana
 */
public class ClienteJogoMesada {

    private final static String host = new String();
    private final static int portaClienteServidor = 22222;
    private final static int portaClienteCliente = 44444;
    private static String usuario;
    private static int id;
    private static boolean controleMsgJogada;
    private BufferedReader entradaDados;
    private DataOutputStream saidaDados;
    private Socket conexaoClienteServidor;
    private InetAddress enderecoMulticast;
    private MulticastSocket conexaoGrupo;
    private static String proximoJogador = "0";
    private static int ultimoDado = 0;
    private static ArrayList<String> ordemJogadas = new ArrayList();

    /**
     *
     *
     *
     * @author Wanderson e Santana
     */
    public ClienteJogoMesada(String ipServidor) {
        try {
            this.conexaoClienteServidor = new Socket(ipServidor, portaClienteServidor);
            this.saidaDados = new DataOutputStream(this.conexaoClienteServidor.getOutputStream());
            this.entradaDados = new BufferedReader(new InputStreamReader(this.conexaoClienteServidor.getInputStream()));

        } catch (IOException ex) {
            Logger.getLogger(ClienteJogoMesada.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * Método responsável por efetuar o login no servidor do jogo
     *
     * @param usuario nome para identificar o jogador
     * @author Wanderson e Santana
     */
    public synchronized String entrar(String usuario) {

        if (conexaoClienteServidor.isConnected()) {
            try {
                saidaDados.writeBytes("001" + ";" + usuario + '\n');

                String pacoteDados = entradaDados.readLine();

                if (pacoteDados.startsWith("100")) {
                    String[] dados = new String[2];
                    dados = pacoteDados.split(";");
                    ClienteJogoMesada.usuario = usuario;
                    enderecoMulticast = InetAddress.getByName(dados[1]);
//                    System.out.println(enderecoMulticast);
                    conexaoGrupo = new MulticastSocket(portaClienteCliente);
                    conexaoGrupo.joinGroup(enderecoMulticast);
                    new ThreadCliente(conexaoGrupo).start();
                    return "OK";

                } else {
                    return pacoteDados;
                }

            } catch (IOException ex) {
                //System.out.println(ex.toString());
                return "Falha na conexão com o servidor!";
            }
        } else {
            return "ERRO! Tente novamente...";
        }
    }

    public synchronized String sair() {
        if (conexaoClienteServidor.isConnected()) {
            try {
                saidaDados.writeBytes("002" + ";" + ClienteJogoMesada.usuario + '\n');

                String pacoteDados = entradaDados.readLine();

                if (pacoteDados.startsWith("200")) {
                    return "";

                } else {
                    return pacoteDados;
                }

            } catch (IOException ex) {
                //System.out.println(ex.toString());
                return "Falha na conexão com o servidor!";
            }
        } else {
            return "ERRO! Tente novamente...";
        }
    }

    public synchronized String trocarSala() {
        if (conexaoClienteServidor.isConnected()) {
            try {
                saidaDados.writeBytes("003" + ";" + ClienteJogoMesada.usuario + '\n');

                String pacoteDados = entradaDados.readLine();

                if (pacoteDados.startsWith("300")) {
                    return "";

                } else {
                    return pacoteDados;
                }

            } catch (IOException ex) {
                //System.out.println(ex.toString());
                return "Falha na conexão com o servidor!";
            }
        } else {
            return "ERRO! Tente novamente...";
        }
    }

    public synchronized ArrayList<String> iniciarPartida() {
        if (conexaoClienteServidor.isConnected()) {
            try {
                saidaDados.writeBytes("004" + ";" + ClienteJogoMesada.usuario + '\n');

                String pacoteDados = entradaDados.readLine();

                if (pacoteDados.startsWith("400")) {
                    String[] dados = new String[2];
                    dados = pacoteDados.split(";");

                    String[] dados2 = new String[6];
                    dados2 = dados[1].split(",");

                    ArrayList<String> ordem = new ArrayList();

                    for (String string : dados2) {
                        ordem.add(string.replace("[", "").replace("]", "").replace(" ", ""));
                    }

                    ClienteJogoMesada.ordemJogadas = ordem;
                    return ordem;

                } else {
                    return new ArrayList<>();
                }

            } catch (IOException ex) {
                //System.out.println(ex.toString());
                return new ArrayList<>();
            }
        } else {
            return new ArrayList<>();
        }
    }

    public synchronized ArrayList<String> usuariosConectados() {

        if (conexaoClienteServidor.isConnected()) {
            try {
                saidaDados.writeBytes("005" + ";" + ClienteJogoMesada.usuario + '\n');

                String pacoteDados = entradaDados.readLine();

                if (pacoteDados.startsWith("500")) {

                    String[] dados = new String[2];
                    dados = pacoteDados.split(";");

                    String[] dados2 = new String[6];
                    dados2 = dados[1].split(",");

                    ArrayList<String> lista = new ArrayList();

                    for (String string : dados2) {
                        lista.add(string.replace("[", "").replace("]", "").replace(" ", ""));
                    }

                    return lista;

                } else {
                    return new ArrayList<>();
                }

            } catch (IOException ex) {
                //System.out.println(ex.toString());
                return new ArrayList<>();
            }
        } else {
            return new ArrayList<>();
        }
    }

    public String getProximoJogador() {
        return proximoJogador;
    }

    public int getUltimoDado() {
        return ultimoDado;
    }

    public synchronized void jogar(int id, int numDado) {
        byte dados[] = ("1001" + ";" + id + ";" + numDado).getBytes();
        DatagramPacket msgPacket = new DatagramPacket(dados, dados.length, enderecoMulticast, portaClienteCliente);
        try {
            conexaoGrupo.send(msgPacket);
        } catch (IOException ex) {
            Logger.getLogger(ClienteJogoMesada.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void entrouNaSala(String nomeUsuario) {
        byte dados[] = ("1002" + ";" + nomeUsuario + " entrou na sala!").getBytes();
        DatagramPacket msgPacket = new DatagramPacket(dados, dados.length, enderecoMulticast, portaClienteCliente);
        try {
            conexaoGrupo.send(msgPacket);
        } catch (IOException ex) {
            Logger.getLogger(ClienteJogoMesada.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void enviarOrdemJogada(ArrayList<String> ordem) {
        byte dados[] = ("1004" + ";" + ordem).getBytes();
        DatagramPacket msgPacket = new DatagramPacket(dados, dados.length, enderecoMulticast, portaClienteCliente);
        try {
            conexaoGrupo.send(msgPacket);
        } catch (IOException ex) {
            Logger.getLogger(ClienteJogoMesada.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void setControleMsgJogada(boolean valor) {
        ClienteJogoMesada.controleMsgJogada = valor;
    }
    
    public synchronized boolean getControleMsgJogada()
    {
        return controleMsgJogada;
    }

    public synchronized void aceitar() {

    }

    private static class ThreadCliente extends Thread {

        private final MulticastSocket socketMulticast;

        public ThreadCliente(MulticastSocket socketMulticast) {
            this.socketMulticast = socketMulticast;
        }

        public void run() {
            try {
                while (true) {
                    byte dados[] = new byte[1024];
                    DatagramPacket datagrama = new DatagramPacket(dados, dados.length);
                    socketMulticast.receive(datagrama);
                    String msg = new String(datagrama.getData());

                    if (msg.startsWith("1001")) {
                        String[] dadosRecebidos = new String[3];
                        dadosRecebidos = msg.split(";");
                        proximoJogador = dadosRecebidos[1];
                        ultimoDado = Integer.parseInt(dadosRecebidos[2]);
                        ClienteJogoMesada.controleMsgJogada = true;

                        System.out.println("Dados Recebidos");

                    } else if (msg.startsWith("1002")) {
                        String[] dadosRecebidos = new String[2];
                        dadosRecebidos = msg.split(";");
                        System.out.println(dadosRecebidos[1]);
                    } else if (msg.startsWith("1003")) {

                    } else if (msg.startsWith("1004")) {
                        String[] dadosRecebidos = new String[2];
                        dadosRecebidos = msg.split(";");

                        String[] dados2 = new String[6];
                        dados2 = dadosRecebidos[1].split(",");

                        ArrayList<String> ordem = new ArrayList();

                        for (String string : dados2) {
                            ordem.add(string.replace("[", "").replace("]", "").replace(" ", ""));

                        }
                        ClienteJogoMesada.ordemJogadas = ordem;
                    }
                    Thread.sleep(500);
                }
            } catch (Exception e) {
            }
        }

    }
}
