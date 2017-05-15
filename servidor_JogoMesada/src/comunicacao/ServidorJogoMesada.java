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
import model.Sala;

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
//    private static ArrayList<Jogador> jogadoresCadastrados;
    private static ArrayList<String> jogadoresOnline;
    private static ArrayList<Sala> salasDePartidas;

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
//            jogadoresCadastrados = new ArrayList<>();
            jogadoresOnline = new ArrayList<>();
            salasDePartidas = new ArrayList<>();

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

        private final Socket conexaoClienteServidor;
        //private final MulticastSocket servidorGrupo;
        private final BufferedReader entradaDadosClienteServidor;
        private final DataOutputStream saidaDadosClienteServidor;

        public ThreadServidor(Socket conexaoClienteServidor) throws IOException {
            this.conexaoClienteServidor = conexaoClienteServidor;
            this.entradaDadosClienteServidor = new BufferedReader(new InputStreamReader(this.conexaoClienteServidor.getInputStream()));
            this.saidaDadosClienteServidor = new DataOutputStream(this.conexaoClienteServidor.getOutputStream());
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

            /**
             * Informações sobre o Protocolo
             *
             */
            while (true) {
                if (conexaoClienteServidor.isConnected()) {

                    try {
                        String pacoteDados = entradaDadosClienteServidor.readLine();

                        if (pacoteDados.startsWith("001")) {

                            String[] dados = new String[2];
                            dados = pacoteDados.split(";");

                            if (!jogadoresOnline.contains(dados[1])) {
                                Jogador novoJogadorOnline = new Jogador();
                                novoJogadorOnline.setNome(dados[1]);
                                novaSala(novoJogadorOnline);
                                saidaDadosClienteServidor.writeBytes("100\n");
                            } else {
                                saidaDadosClienteServidor.writeBytes("UsuarioExistente");
                            }
                        } else if (pacoteDados.startsWith("002")) {
                            String[] dados = new String[2];
                            dados = pacoteDados.split(";");
                            Jogador removerJogador = new Jogador();
                            removerJogador.setNome(dados[1]);
                            jogadoresOnline.remove(dados[2]);
                            sairSala(removerJogador);
                            saidaDadosClienteServidor.writeBytes("200\n");

                        } else if (pacoteDados.startsWith("003")){
                            
                        } else if (pacoteDados.startsWith("004")){
                            
                        }

                    } catch (IOException ex) {
                        //System.out.println("Conexão Finalizada!");

                        System.out.println("Conexão Finalizada!");
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

        private synchronized void novaSala(Jogador novoJogadorOnline) {

            if (salasDePartidas.isEmpty()) {

                Sala novaSala = new Sala(novoJogadorOnline);
                salasDePartidas.add(novaSala);
            }

            for (Sala sala : salasDePartidas) {
                if (sala == null) {
                    Sala novaSala = new Sala(novoJogadorOnline);
                    salasDePartidas.add(novaSala);
                    break;

                } else if (sala.tamanhoSala() < 6 && !sala.salaOcupada()) {
                    sala.addJogador(novoJogadorOnline);
                    break;
                }
            }

        }

        private synchronized void sairSala(Jogador removerJogador) {

            for (Sala sala : salasDePartidas) {
                if (sala != null) {
                    if (sala.removerJogador(removerJogador)) {
                        break;
                    }
                }

            }

        }
    }
}
