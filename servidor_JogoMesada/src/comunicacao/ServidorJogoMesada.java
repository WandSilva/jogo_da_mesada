package comunicacao;

import model.Jogador;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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

    private static final int portaClienteServidor = 22222;
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

            ServerSocket socketClienteServidor = new ServerSocket(portaClienteServidor);
            System.out.println("Servidor executando na porta" + " " + portaClienteServidor);
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
        private ArrayList<Jogador> array = new ArrayList<>();

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
                                jogadoresOnline.add(dados[1]);
                                String enderecoMulticast = novaSala(novoJogadorOnline);
                                saidaDadosClienteServidor.writeBytes("100"+";"+enderecoMulticast+"\n");
                            } else {
                                saidaDadosClienteServidor.writeBytes("UsuarioExistente\n");
                            }
                        } else if (pacoteDados.startsWith("002")) {
                            String[] dados = new String[2];
                            dados = pacoteDados.split(";");
                            Jogador jogador = new Jogador();
                            jogador.setNome(dados[1]);
                            jogadoresOnline.remove(dados[1]);
                            sairSala(jogador);
                            saidaDadosClienteServidor.writeBytes("200\n");

                        } else if (pacoteDados.startsWith("003")) {
                            String[] dados = new String[2];

                        } else if (pacoteDados.startsWith("004")) {
                            String[] dados = new String[2];
                            dados = pacoteDados.split(";");
                            Jogador jogador = new Jogador();
                            jogador.setNome(dados[1]);
                            Sala sala = buscarSala(jogador);
                            if (!sala.isJogando()) {
                                sala.ocuparSala();
                                iniciarPartida(sala);
                                saidaDadosClienteServidor.writeBytes("400\n");
                            }
                        }

                    } catch (IOException ex) {
                        //System.out.println("Conexão Finalizada!");

                        System.out.println("Conexão Finalizada!");
                    }

                }
            }

        }

        private synchronized String novaSala(Jogador novoJogadorOnline) {

            if (salasDePartidas.isEmpty()) {
                Sala novaSala = new Sala(novoJogadorOnline);
                salasDePartidas.add(novaSala);
                return "235.0.0.1";  
            }

            for (Sala sala : salasDePartidas) {
                if (sala == null) {
                    Sala novaSala = new Sala(novoJogadorOnline);
                    salasDePartidas.add(novaSala);
                    int numero = salasDePartidas.size();
                    return "235.0.0." + numero;

                } else if (sala.tamanhoSala() < 6 && !sala.salaOcupada()) {
                    sala.addJogador(novoJogadorOnline);
                    int numero = salasDePartidas.size();
                    return "235.0.0." + numero;
                }
            }
            return "";
        }

        private synchronized void sairSala(Jogador jogador) {

            for (Sala sala : salasDePartidas) {
                if (sala != null) {
                    if (sala.removerJogador(jogador)) {
                        break;
                    }
                }

            }

        }

        private synchronized void iniciarPartida(Sala sala) {
            
            
            
        }

        private synchronized Sala buscarSala(Jogador jogador) {
            for (Sala sala : salasDePartidas) {
                if (sala != null) {
                    array = sala.getJogadores();
                    for (Jogador array1 : array) {
                        if (array1.equals(jogador)) {
                            return sala;
                        }
                    }
                }
            }
            return null;
        }
    }
}
