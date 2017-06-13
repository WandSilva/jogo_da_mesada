package comunicacao;

import model.Jogador;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
 * @author Wanderson e Santana
 * @version 1.0
 *
 */
public class ServidorJogoMesada {

    private static final int portaClienteServidor = 22222;
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
            jogadoresOnline = new ArrayList<>();
            salasDePartidas = new ArrayList<>();

            while (true) {
                Socket clienteServidor = socketClienteServidor.accept();
                System.out.println("Conexão Estabelecida com:" + " " + clienteServidor.getInetAddress().getHostAddress());
                new ThreadServidor(clienteServidor).start();

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Esta interna classe implementa a Thread responsavel por tratar a comunicação entre Cliente-Servidor.
     * @author Wanderson e Santana
     * @version 1.0
     */
    
    private static class ThreadServidor extends Thread {

        private final Socket conexaoClienteServidor;
        //private final MulticastSocket servidorGrupo;
        private final BufferedReader entradaDadosClienteServidor;
        private final DataOutputStream saidaDadosClienteServidor;
        private ArrayList<Jogador> jogadores = new ArrayList<>();

        /**
        * Construtor da(s) Thread(s).
        * @param conexaoClienteServidor - Socket entre o Cliente e o Servidor.
        * @author Wanderson e Santana
        * @version 1.0
        */
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
             * 001 - Login
             * 002 - Remover Jogador
             * 003 - 
             * 004 - Iniciar Partida
             * 005 - Lista de Jogadores
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
                                String enderecoMulticast = entrarSala(novoJogadorOnline);
                                saidaDadosClienteServidor.writeBytes("100" + ";" + enderecoMulticast + "\n");
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
                            sala.ocuparSala();
                            ArrayList<String> ordemJogada = new ArrayList<>();
                            ordemJogada = listaJogadores(sala);

                            saidaDadosClienteServidor.writeBytes("400" + ";" + ordemJogada + "\n");

                        } else if (pacoteDados.startsWith("005")) {
                            String[] dados = new String[2];
                            dados = pacoteDados.split(";");
                            Jogador jogador = new Jogador();
                            jogador.setNome(dados[1]);
                            Sala sala = buscarSala(jogador);
                            ArrayList<String> ordemJogada = new ArrayList<>();
                            ordemJogada = listaJogadores(sala);                          
                            saidaDadosClienteServidor.writeBytes("500" + ";" + ordemJogada + "\n");
                        } else if (pacoteDados.startsWith("006")){
                            String[] dados = new String[3];
                            dados = pacoteDados.split(";");
                            Jogador jogador = new Jogador();
                            jogador.setNome(dados[1]);
                            Sala salaBuscada = new Sala();
                            for (Sala sala:salasDePartidas){
                                for (Jogador jogador2:sala.getJogadores()){
                                    if(jogador2.equals(jogador.getNome())){
                                        jogador2.setSaldo(Double.parseDouble(dados[2]));
                                        salaBuscada = sala;
                                    }
                                }
                            }
                            
                            Thread.sleep(2000);
                            ArrayList<String> ordemResultado = new ArrayList<>();
                            ordemResultado = ordenaJogadores(salaBuscada);
                            saidaDadosClienteServidor.writeBytes("600"+";"+ordemResultado+'\n');
                        }                             
                    } catch (Exception ex) {
                        //System.out.println("Conexão Finalizada!");

                        ex.printStackTrace();
                    }
                }
            }

        }

        /**
        * Metodo que adiciona um jogador na sala.
        * @param novoJogadorOnline
        * @return IP Multicast
        * @author Wanderson e Santana
        * 
        */
        private synchronized String entrarSala(Jogador novoJogadorOnline) {

            if (salasDePartidas.isEmpty()) {
                Sala novaSala = new Sala(novoJogadorOnline);
                salasDePartidas.add(novaSala);
                return "235.0.0.1";
            } else {
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
                Sala novaSala = new Sala(novoJogadorOnline);
                salasDePartidas.add(novaSala);
                int numero = salasDePartidas.size();
                return "235.0.0." + numero;
            }
        }

        /**
        * Metodo que remove um jogador da sala.
        * @param jogador
        * @author Wanderson e Santana
        */
        
        private synchronized void sairSala(Jogador jogador) {

            for (Sala sala : salasDePartidas) {
                if (sala != null) {
                    if (sala.removerJogador(jogador)) {
                        break;
                    }
                }

            }

        }
        /**
        * Método que retorna a lista de jogadores de uma sala.
        * @param sala - Sala. 
        * @return Lista de Jogadores.
        * @author Wanderson e Santana
        */
        private synchronized ArrayList<String> listaJogadores(Sala sala) {
            ArrayList<String> jogadores = new ArrayList<>();
            for (Jogador jogador1 : sala.getJogadores()) {
                jogadores.add(jogador1.getNome());
            }
            return jogadores;
        }
        
        private synchronized ArrayList<String> ordenaJogadores(Sala sala) {
            
            ArrayList<String> jogadores = new ArrayList<>();
                
            boolean changed = false;
            do {
                changed = false;
                for (int a = 0; a < sala.getJogadores().size() - 1; a++) {
                    if (sala.getJogadores().get(a).getSaldoJogador() < sala.getJogadores().get(a+1).getSaldoJogador()) {

                        Jogador auxMenor = new Jogador();
                        Jogador auxMaior = new Jogador();
                        auxMenor = sala.getJogadores().get(a);
                        auxMaior = sala.getJogadores().get(a+1);
                        sala.getJogadores().remove(a);
                        sala.getJogadores().remove(a+1);
                        sala.getJogadores().add(a, auxMaior);
                        sala.getJogadores().add(a+1, auxMenor);
                        changed = true;
                        }
                    }
               } while (changed);

            for (Jogador jogador1 : sala.getJogadores()) {
                jogadores.add(jogador1.getNome() + "#" +jogador1.getSaldoJogador());
            }
            return jogadores;
        }

        /**
        * Método que retorna a sala de um jogador.
        * @param jogador - O Jogador. 
        * @return Sala procurada.
        * @author Wanderson e Santana
        */
        private synchronized Sala buscarSala(Jogador jogador) {
            for (Sala sala : salasDePartidas) {
                if (sala != null) {
                    jogadores = sala.getJogadores();
                    for (Jogador jogadorProcurado : jogadores) {
                        if (jogadorProcurado.equals(jogador)) {
                            return sala;
                        }
                    }
                }
            }
            return null;
        }
    }
}
