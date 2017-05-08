package comunicacao;

import model.Jogador;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta classe implementa a abstração do servidor do Jogo da Mesada. A execução
 * dele independe da execução do cliente.
 *
 * @see model.Jogador
 * @author Wanderson e Santana
 * @version 1.0
 *
 */
public class ServidorJogoMesada {

    private static int porta = 22222;
    private static ArrayList<Jogador> jogadoresCadastrados;
    private static ArrayList<Jogador> jogadoresOnline;

    /**
     * Método responsável por iniciar a execução do servidor e disponibilizá-lo
     * para uso.
     *
     * @param args Parâmetros não utilizados.
     *
     */
    public static void main(String[] args) {
        try {

            ServerSocket socketClienteServidor = new ServerSocket(porta);
            System.out.println("Servidor executando na porta" + " " + porta);
            jogadoresCadastrados = new ArrayList<>();
            jogadoresOnline = new ArrayList<>();

            while (true) {
                Socket clienteServidor = socketClienteServidor.accept();
                System.out.println("Conexão Estabelecida com:" + " " + clienteServidor.getInetAddress().getHostAddress());
                new ThreadServidor(clienteServidor).start();

            }
        } catch (IOException ex) {
            System.out.println("Conexão Finalizada!");
        }
    }

    private static class ThreadServidor extends Thread {

        private final Socket clienteServidor;
       //private final MulticastSocket servidorGrupo;
        private final BufferedReader entradaDados;
        private final DataOutputStream saidaDados;

        public ThreadServidor(Socket clienteServidor) throws IOException {
            this.clienteServidor = clienteServidor;
            this.entradaDados = new BufferedReader(new InputStreamReader(this.clienteServidor.getInputStream()));
            this.saidaDados = new DataOutputStream(this.clienteServidor.getOutputStream());
        }

        /**
         * Método para execução da(s) Thread(s).
         *
         * @see Thread
         * @author Wanderson e Santana
         *
         */
        @Override
        public synchronized void run() {

            //jogadoresCadastrados = (ArrayList<Jogador>) abrirArquivoCadastro(jogadoresCadastrados);
            
            /**
             * Informações sobre o Protocolo
             *
             */
            while (true) {
                if (clienteServidor.isConnected()) {

                    try {
                        String pacoteDados = entradaDados.readLine();

                        if (pacoteDados.startsWith("")) {

                        }

                    } catch (IOException ex) {
                        Logger.getLogger(ServidorJogoMesada.class.getName()).log(Level.SEVERE, null, ex);
                        //System.out.println("Conexão Finalizada!");
                    }

                }
            }

        }

        private synchronized Object abrirArquivoCadastro(Object o) {

            String arquivo = "data" + File.separator + "data_bcli.dat";

            try {
                FileInputStream fi = new FileInputStream(arquivo);
                ObjectInputStream oi = new ObjectInputStream(fi);

                o = oi.readObject();
                oi.close();
                return o;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }

        private synchronized void salvarArquivoCadastro(Object o) {
            String arquivo = "data" + File.separator + "data_bcli.dat";
            try {
                FileOutputStream fo = new FileOutputStream(arquivo);
                ObjectOutputStream oo = new ObjectOutputStream(fo);
                oo.writeObject(o);
                oo.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}