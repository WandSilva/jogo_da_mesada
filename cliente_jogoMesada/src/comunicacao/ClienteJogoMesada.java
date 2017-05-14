package comunicacao;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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

    private static String host = new String();
    private static int portaClienteServidor;
    private static int portaClienteCliente;
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
    public ClienteJogoMesada() {

        try {
            this.conexaoClienteServidor = new Socket(host, portaClienteServidor);
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
                    ClienteJogoMesada.usuario = usuario;
                    return "HOME_PAGE";

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

    public synchronized String iniciarPartida() {

        if (conexaoClienteServidor.isConnected()) {
            try {
                saidaDados.writeBytes("003" + '\n');

                String pacoteDados = entradaDados.readLine();

                if (pacoteDados.startsWith("100")) {
                    return "HOME_PAGE";

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

    public String solicitarEmprestimo(Sala sala) {
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

    private static class ThreadCliente extends Thread {

        private MulticastSocket socketMulticast;

        public ThreadCliente(MulticastSocket socketMulticast) {

        }

        public void run() {
            try {
                while (true) {

                    byte dados[] = new byte[256];
                    DatagramPacket datagrama = new DatagramPacket(dados, dados.length);
                    socketMulticast.receive(datagrama);
                    Thread.sleep(100);
                }
            } catch (Exception e) {
            }
        }

    }
}
