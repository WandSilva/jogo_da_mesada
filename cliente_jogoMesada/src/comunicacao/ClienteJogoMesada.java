package comunicacao;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.*;
import java.util.ArrayList;

/**
 * Esta classe implementa a parte da aplicação responśavel por comunicar-se com
 * o servidor e com outros clientes por meio de requisições. Modelo
 * Peer-to-Peer.
 *
 * @author Wanderson e Santana
 * @version 1.0
 */
public class ClienteJogoMesada {

    private final static String host = new String();
    private final static int portaClienteServidor = 22222;
    private final static int portaClienteCliente = 44444;
    private static String usuario;
    private static int idDestino;
    private static double valorTransferencia;
    private static boolean controleTransferenciaIn;
    private static boolean controleTransferenciaOut;
    private static boolean controleMsgJogada;
    private static boolean controleConcursoBanda;
    private static int idProximoJogadorEvento;
    private static double sorteGrande;
    private static boolean controleSorteGrande;
    private static boolean gatilhoInicioPartida;
    private BufferedReader entradaDados;
    private DataOutputStream saidaDados;
    private Socket conexaoClienteServidor;
    private InetAddress enderecoMulticast;
    private MulticastSocket conexaoGrupo;
    private static String proximoJogador;
    private static String atualJogador;
    private static int ultimoDado;
    private static ArrayList<String> ordemJogadas = new ArrayList();
    private static ArrayList<String> jogadoresBolao = new ArrayList<>();
    private static boolean controleBolao;
    private static int vencedorBolao;
    private static int numeroParticipantesBolao;
    private static int idOrganizadorBolao;

    /**
     * Construtor da classe que recebe o IP do servidor do jogo.
     *
     * @param ipServidor IP do Servidor
     * @author Wanderson e Santana
     */
    public ClienteJogoMesada(String ipServidor) {

        sorteGrande = 0;
        proximoJogador = "0";
        atualJogador = "0";
        vencedorBolao=-1;
        try {
            this.conexaoClienteServidor = new Socket(ipServidor, portaClienteServidor);
            this.saidaDados = new DataOutputStream(this.conexaoClienteServidor.getOutputStream());
            this.entradaDados = new BufferedReader(new InputStreamReader(this.conexaoClienteServidor.getInputStream()));

        } catch (IOException ex) {
            Logger.getLogger(ClienteJogoMesada.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método responsável por efetuar o login no servidor do jogo
     *
     * @param usuario - nome para identificar o jogador
     * @return Resposta do Servidor.
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

    /**
     * Método responsável por efetuar a retirada de um jogador do servidor do jogo
     *
     * @return Resposta do Servidor.
     * @author Wanderson e Santana
     */

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

    /**
     * Método responsável por efetuar a troca de uma sala
     *
     * @return Resposta do Servidor.
     * @author Wanderson e Santana
     */

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

    /**
     * Método responsável por iniciar uma partida.
     *
     * @return Ordem das jogadas.
     * @author Wanderson e Santana
     */

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

    /**
     * Método responsável por consultar quem são os usuários conectados em uma determinada sala.
     *
     * @return Lista dos jogadores
     * @author Wanderson e Santana
     */

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

    /**
     * Método responsável por enviar uma jogada para todos os outros jogadores.
     *
     * @param id      - ID do jogador que efetuou a jogada.
     * @param numDado - Número do dado.
     * @author Wanderson e Santana
     */

    public synchronized void jogar(int id, int numDado) {
        byte dados[] = ("1001" + ";" + id + ";" + numDado).getBytes();
        DatagramPacket msgPacket = new DatagramPacket(dados, dados.length, enderecoMulticast, portaClienteCliente);
        try {
            conexaoGrupo.send(msgPacket);
        } catch (IOException ex) {
            Logger.getLogger(ClienteJogoMesada.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método responsável por notificar que alguém entrou na sala.
     *
     * @param nomeUsuario - Nome do Jogador que entrou na sala.
     * @author Wanderson e Santana
     */

    public synchronized void entrouNaSala(String nomeUsuario) {
        byte dados[] = ("1002" + ";" + nomeUsuario + " entrou na sala!").getBytes();
        DatagramPacket msgPacket = new DatagramPacket(dados, dados.length, enderecoMulticast, portaClienteCliente);
        try {
            conexaoGrupo.send(msgPacket);
        } catch (IOException ex) {
            Logger.getLogger(ClienteJogoMesada.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método responsável por enviar a ordem das jogadas.
     *
     * @param ordem - ID do jogador que efetuou a jogada.
     * @author Wanderson e Santana
     */

    public synchronized void enviarOrdemJogada(ArrayList<String> ordem) {
        byte dados[] = ("1004" + ";" + ordem).getBytes();
        DatagramPacket msgPacket = new DatagramPacket(dados, dados.length, enderecoMulticast, portaClienteCliente);
        try {
            conexaoGrupo.send(msgPacket);
        } catch (IOException ex) {
            Logger.getLogger(ClienteJogoMesada.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método responsável por atualizar o valor do sorte grande.
     *
     * @param valor - Valor atual do sorte grande.
     * @author Wanderson e Santana
     */

    public synchronized void setSorteGrande(double valor) {
        byte dados[] = ("1005" + ";" + valor).getBytes();
        DatagramPacket msgPacket = new DatagramPacket(dados, dados.length, enderecoMulticast, portaClienteCliente);
        try {
            conexaoGrupo.send(msgPacket);
        } catch (IOException ex) {
            Logger.getLogger(ClienteJogoMesada.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método responsável por transferir valores entre jogadores.
     *
     * @param idDestino - ID do jogador destino.
     * @param valor     - Valor a ser transferido.
     * @author Wanderson e Santana
     */

    public synchronized void transferirValores(int idDestino, double valor) {
        byte dados[] = ("1006" + ";" + idDestino + ";" + valor).getBytes();
        DatagramPacket msgPacket = new DatagramPacket(dados, dados.length, enderecoMulticast, portaClienteCliente);
        try {
            conexaoGrupo.send(msgPacket);
        } catch (IOException ex) {
            Logger.getLogger(ClienteJogoMesada.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void receberValores(int idPagador, double valor) {
        byte dados[] = ("1007" + ";" + idPagador + ";" + valor).getBytes();
        DatagramPacket msgPacket = new DatagramPacket(dados, dados.length, enderecoMulticast, portaClienteCliente);
        try {
            conexaoGrupo.send(msgPacket);
        } catch (IOException ex) {
            Logger.getLogger(ClienteJogoMesada.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void iniciarTabuleiro() {
        byte dados[] = ("1008").getBytes();
        DatagramPacket msgPacket = new DatagramPacket(dados, dados.length, enderecoMulticast, portaClienteCliente);
        try {
            conexaoGrupo.send(msgPacket);
        } catch (IOException ex) {
            Logger.getLogger(ClienteJogoMesada.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void finalizarJogada(int idProximo) {
        byte dados[] = ("1009" + ";" + idProximo).getBytes();
        DatagramPacket msgPacket = new DatagramPacket(dados, dados.length, enderecoMulticast, portaClienteCliente);
        try {
            conexaoGrupo.send(msgPacket);
        } catch (IOException ex) {
            Logger.getLogger(ClienteJogoMesada.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void concursoBandaRock(int idProximoJogadorEvento) {
        byte dados[] = ("1010" + ";" + idProximoJogadorEvento).getBytes();
        DatagramPacket msgPacket = new DatagramPacket(dados, dados.length, enderecoMulticast, portaClienteCliente);
        try {
            conexaoGrupo.send(msgPacket);
        } catch (IOException ex) {
            Logger.getLogger(ClienteJogoMesada.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void participarBolao(int id) {
        byte dados[] = ("1011" + ";" + id).getBytes();
        DatagramPacket msgPacket = new DatagramPacket(dados, dados.length, enderecoMulticast, portaClienteCliente);
        try {
            conexaoGrupo.send(msgPacket);
        } catch (IOException ex) {
            Logger.getLogger(ClienteJogoMesada.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void resultadoBolao(int resutado) {
        byte dados[] = ("1012" + ";" + resutado).getBytes();
        DatagramPacket msgPacket = new DatagramPacket(dados, dados.length, enderecoMulticast, portaClienteCliente);
        try {
            conexaoGrupo.send(msgPacket);
        } catch (IOException ex) {
            Logger.getLogger(ClienteJogoMesada.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void organizadorBolao(int id) {
        byte dados[] = ("1013" + ";" + id).getBytes();
        DatagramPacket msgPacket = new DatagramPacket(dados, dados.length, enderecoMulticast, portaClienteCliente);
        try {
            conexaoGrupo.send(msgPacket);
        } catch (IOException ex) {
            Logger.getLogger(ClienteJogoMesada.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getIdOrganizadorBolao() {
        return idOrganizadorBolao;
    }

    public int getVencedorBolao() {
        return vencedorBolao;
    }

    public int getNumeroParticipantesBolao() {
        return numeroParticipantesBolao;
    }

    public boolean getControleBolao() {
        return controleBolao;
    }

    public static synchronized void setControleBolao(boolean controleBolao) {
        ClienteJogoMesada.controleBolao = controleBolao;
    }

    public synchronized void setControleConcursoBanda(boolean controle) {
        ClienteJogoMesada.controleConcursoBanda = controle;
    }

    public boolean getControleConcursoBanda() {
        return ClienteJogoMesada.controleConcursoBanda;
    }

    public int getIdProximoJOgadorEvento() {
        return idProximoJogadorEvento;
    }


    /**
     * Método responsável por obter o próximo jogador
     *
     * @return ID do proíxmo jogador.
     * @author Wanderson e Santana
     */

    public String getProximoJogador() {
        return proximoJogador;
    }

    public String getAtualJogador() {
        return atualJogador;
    }

    /**
     * Método responsável por obter o último resultado do dado.
     *
     * @return Número do dado.
     * @author Wanderson e Santana
     */

    public int getUltimoDado() {
        return ultimoDado;
    }

    /**
     * Método responsável por alterar a variável que verifica se uma nova jogada foi feita.
     *
     * @param valor - true ou false.
     * @author Wanderson e Santana
     */

    public static synchronized void setControleMsgJogada(boolean valor) {
        ClienteJogoMesada.controleMsgJogada = valor;
    }

    /**
     * Método responsável por modificar o valor do último dado.
     *
     * @param valor - Numero para modificar.
     * @author Wanderson e Santana
     */

    public static synchronized void setUltimoDado(int valor) {
        ClienteJogoMesada.ultimoDado = valor;
    }

    /**
     * Método responsável por obter o estado da variável que verifica se uma nova jogada foi feita.
     *
     * @return Valor da variável.
     * @author Wanderson e Santana
     */

    public synchronized boolean getControleMsgJogada() {
        return controleMsgJogada;
    }

    /**
     * Método responsável por obter o valor do sorte grande.
     *
     * @return Valor da variável.
     * @author Wanderson e Santana
     */

    public synchronized double getSorteGrande() {
        return ClienteJogoMesada.sorteGrande;
    }

    /**
     * Método responsável por obter o estado da variável que verifica se o sorte grande foi atualizado.
     *
     * @return Valor da variável.
     * @author Wanderson e Santana
     */

    public synchronized boolean getControleSorteGrande() {
        return controleSorteGrande;
    }

    /**
     * Método responsável por alterar o estado da variável que verifica se o sorte grande foi atualizado.
     *
     * @param valor - Valor da variável.
     * @author Wanderson e Santana
     */

    public synchronized void setControleSorteGrande(boolean valor) {
        ClienteJogoMesada.controleSorteGrande = valor;
    }

    /**
     * Método responsável por obter o estado da variável que verifica se uma transferência foi feita.
     *
     * @return Valor da variável.
     * @author Wanderson e Santana
     */

    public synchronized boolean getControleTransferenciaIn() {
        return ClienteJogoMesada.controleTransferenciaIn;
    }

    public synchronized boolean getControleTransferenciaOut() {
        return ClienteJogoMesada.controleTransferenciaOut;
    }

    /**
     * Método responsável por alterar o estado da variável que verifica se uma transferência foi feita.
     *
     * @param valor - Valor da variável.
     * @author Wanderson e Santana
     */

    public synchronized void setControleTransferenciaIn(boolean valor) {
        ClienteJogoMesada.controleTransferenciaIn = valor;
    }

    /**
     * Método responsável por alterar o estado da variável que verifica se uma transferência foi feita.
     *
     * @param valor - Valor da variável.
     * @author Wanderson e Santana
     */
    public synchronized void setControleTransferenciaOut(boolean valor) {
        ClienteJogoMesada.controleTransferenciaOut = valor;
    }

    public synchronized boolean isGatilhoInicioPartida() {
        return gatilhoInicioPartida;
    }

    public synchronized void setGatilhoInicioPartida(boolean gatilhoInicioPartida) {
        ClienteJogoMesada.gatilhoInicioPartida = gatilhoInicioPartida;
    }

    /**
     * Método responsável por obter o valor a ser transferido.
     *
     * @return Valor a ser transferido.
     * @author Wanderson e Santana
     */

    public synchronized double getValorTransferencia() {
        return ClienteJogoMesada.valorTransferencia;
    }

    public synchronized int getIdDestinoTranferenciaO() {
        return ClienteJogoMesada.idDestino;
    }

    /**
     * Esta classe interna implementa a Thread responsavel pela comunicaçao entre os jogadores.
     *
     * @author Wanderson e Santana
     * @version 1.0
     */

    private static class ThreadCliente extends Thread {

        private final MulticastSocket socketMulticast;

        public ThreadCliente(MulticastSocket socketMulticast) {
            this.socketMulticast = socketMulticast;
        }

        /**
         * Método responsável por executar a Thread criada.
         *
         * @author Wanderson e Santana
         */
        public void run() {
            try {

                /**
                 * Protocolo:
                 *
                 * 1001 - Jogar
                 * 1002 - Notificar Entrada
                 * 1003 -
                 * 1004 - Ordem Jogada
                 * 1005 - Sorte Grande
                 * 1006 - Transferir Valores
                 *
                 * @author Wanderson e Santana
                 */

                while (true) {
                    byte dados[] = new byte[1024];
                    DatagramPacket datagrama = new DatagramPacket(dados, dados.length);
                    socketMulticast.receive(datagrama);
                    String msg = new String(datagrama.getData());

                    if (msg.startsWith("1001")) {
                        String[] dadosRecebidos = msg.split(";");
                        atualJogador = proximoJogador;
                        proximoJogador = dadosRecebidos[1].trim();
                        ultimoDado = Integer.parseInt(dadosRecebidos[2].trim());
                        ClienteJogoMesada.controleMsgJogada = true;

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
                    } else if (msg.startsWith("1005")) {
                        String[] dadosRecebidos = new String[2];
                        dadosRecebidos = msg.split(";");
                        sorteGrande = Double.parseDouble(dadosRecebidos[1].trim());
                        ClienteJogoMesada.controleSorteGrande = true;
                    } else if (msg.startsWith("1006")) {
                        String[] dadosRecebidos = new String[3];
                        dadosRecebidos = msg.split(";");
                        idDestino = Integer.parseInt(dadosRecebidos[1].trim());
                        valorTransferencia = Double.parseDouble(dadosRecebidos[2].trim());
                        ClienteJogoMesada.controleTransferenciaIn = true;
                    } else if (msg.startsWith("1007")) {
                        String[] dadosRecebidos = new String[3];
                        dadosRecebidos = msg.split(";");
                        idDestino = Integer.parseInt(dadosRecebidos[1].trim());
                        valorTransferencia = Double.parseDouble(dadosRecebidos[2].trim());
                        ClienteJogoMesada.controleTransferenciaIn = true;
                    } else if (msg.startsWith("1008")) {
                        ClienteJogoMesada.gatilhoInicioPartida = true;
                    } else if (msg.startsWith("1009")) {
                        String[] dadosRecebidos = new String[2];
                        dadosRecebidos = msg.split(";");
                        atualJogador = proximoJogador;
                        proximoJogador = dadosRecebidos[1].trim();
                    } else if (msg.startsWith("1010")) {
                        String[] dadosRecebidos = msg.split(";");
                        idProximoJogadorEvento = Integer.parseInt(dadosRecebidos[1].trim());
                        ClienteJogoMesada.controleConcursoBanda = true;
                    } else if (msg.startsWith("1011")) {
                        String[] dadosRecebidos = msg.split(";");
                        ClienteJogoMesada.jogadoresBolao.add(dadosRecebidos[1].trim());
                    } else if (msg.startsWith("1012")) {
                        String[] dadosRecebidos = msg.split(";");
                        String resultado = dadosRecebidos[1].trim();
                        System.out.println("resultado: " + resultado);
                        System.out.println("N participantes :" + jogadoresBolao.size());
                        for (String x : jogadoresBolao) {
                            System.out.println(x);
                        }
                        for (String x : jogadoresBolao) {
                            if (x.equals(resultado)) {
                                vencedorBolao = Integer.parseInt(resultado);
                                System.out.println(vencedorBolao + "ganhou");
                                break;
                            } else vencedorBolao = -1;
                        }
                        numeroParticipantesBolao = jogadoresBolao.size();
                        ClienteJogoMesada.controleBolao = true;
                    } else if (msg.startsWith("1013")) {
                        String[] dadosRecebidos = msg.split(";");
                        idOrganizadorBolao = Integer.parseInt(dadosRecebidos[1].trim());
                        ClienteJogoMesada.controleBolao = true;
                    }
                    Thread.sleep(500);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}