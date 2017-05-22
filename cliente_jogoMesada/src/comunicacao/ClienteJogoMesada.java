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
    private BufferedReader entradaDados;
    private DataOutputStream saidaDados;
    private Socket conexaoClienteServidor;
    private InetAddress enderecoMulticast;
    private MulticastSocket conexaoGrupo;

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
    
    public ClienteJogoMesada()
    {
        
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

    public synchronized String iniciarPartida() {

        if (conexaoClienteServidor.isConnected()) {
            try {
                saidaDados.writeBytes("004" + ";" + ClienteJogoMesada.usuario + '\n');

                String pacoteDados = entradaDados.readLine();

                if (pacoteDados.startsWith("400")) {

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

    public synchronized void minhaJogada(int numDado) {
        byte dados[] = ("1001" + ";" + ClienteJogoMesada.usuario + ";" + numDado + "\n").getBytes();
        DatagramPacket msgPacket = new DatagramPacket(dados, dados.length, enderecoMulticast, portaClienteCliente);
        try {
            conexaoGrupo.send(msgPacket);
        } catch (IOException ex) {
            Logger.getLogger(ClienteJogoMesada.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static class ThreadCliente extends Thread {

        private final MulticastSocket socketMulticast;

        public ThreadCliente(MulticastSocket socketMulticast) {
            this.socketMulticast = socketMulticast;
        }

        public void run() {
            try {
                while (true) {
                    Thread.sleep(500);
                    byte dados[] = new byte[1024];
                    DatagramPacket datagrama = new DatagramPacket(dados, dados.length);
                    socketMulticast.receive(datagrama);
                    String msg = new String(datagrama.getData());
                    
                    if (msg.startsWith("1001"))
                    {
                        String[] dadosRecebidos = new String[3];
                        dadosRecebidos = msg.split(";");
                        
                        //Chamar para atualizar dados;
                        
                    }
                    
                }
            } catch (Exception e) {
            }
        }

    }
}
