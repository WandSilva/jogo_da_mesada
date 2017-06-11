package controller;

import comunicacao.ClienteJogoMesada;
import model.Jogador;
import model.OrdemJogada;

import java.util.ArrayList;

/**
 * Esta classe é responsável por delegar as tarefas que envolvam
 * Cliente-Servidor e Cliente-Cliente.
 *
 * @author Wanderson e Santana
 * @version 1.0
 */
public class ControllerComunicacao {

    private static ControllerComunicacao INSTANCE = null;
    private ClienteJogoMesada cliente;
    ArrayList<OrdemJogada> usuarios;
    private String endIP;

    /**
     * Método privado Construtor da Classe.
     *
     * @author Wanderson e Santana
     */
    private ControllerComunicacao() {
        this.usuarios = new ArrayList<>();
    }

    /**
     * Método para modificar o IP do Servidor.
     *
     * @param ip - Endereço do Servidor.
     * @author Wanderson e Santana
     */
    public void setIP(String ip) {
        this.endIP = ip;
        cliente = new ClienteJogoMesada(ip);
    }

    public ClienteJogoMesada getCliente() {
        return cliente;
    }

    /**
     * Método Construtor da Classe.
     *
     * @author Wanderson e Santana
     */
    public synchronized static ControllerComunicacao getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ControllerComunicacao();
        }

        return INSTANCE;
    }

    /**
     * Método que conecta um Jogador ao Servidor.
     *
     * @param nomeUsuario - Nome do Jogador
     * @return Resposta do Servidor.
     * @author Wanderson e Santana
     */
    public String conectarCliente(String nomeUsuario) {

        return cliente.entrar(nomeUsuario);
    }

    /**
     * Método para obter o ID do Jogador.
     *
     * @return id - ID.
     * @author Wanderson e Santana
     */
    public int getIdJogador() {
        int id = 0;
        String nome = Jogador.getInstance().getNome();
        ArrayList<OrdemJogada> lista = usuariosConectados();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getNome().equals(nome)) {
                id = lista.get(i).getId();
            }
        }
        return id;
    }

    /**
     * Método responsável por notificar todos quando um novo jogador entrar na
     * sala.
     *
     * @param nomeUsuario - Nome do Jogador.
     * @author Wanderson e Santana
     */
    public void enviarNotificacao(String nomeUsuario) {
        cliente.entrouNaSala(nomeUsuario);
    }

    /**
     * Método que envia uma jogada.
     *
     * @param id      - ID do Jogador.
     * @param numDado - Numero´sorteado no dado.
     * @author Wanderson e Santana
     */
    public void enviarJogada(int id, int numDado) {
        cliente.jogar(id, numDado);
    }

    /**
     * Método que obtem o próximo jogador.
     *
     * @author Wanderson e Santana
     */
    public int getProximoJogador() {
        int idProximo = Integer.parseInt(cliente.getProximoJogador());
        return idProximo;
    }

    /**
     * Método que obtem o último dado.
     *
     * @author Wanderson e Santana
     */
    public int getUltimoDado() {
        return cliente.getUltimoDado();
    }

    /**
     * Método que obtém se uma nova jogada foi feita.
     *
     * @return true ou false.
     * @author Wanderson e Santana
     */
    public boolean getControle() {
        return cliente.getControleMsgJogada();
    }

    /**
     * Método que obtém a lista de usuários conectados na sala.
     *
     * @return Lista de Jogadores.
     * @author Wanderson e Santana
     */
    public ArrayList<OrdemJogada> usuariosConectados() {
        ArrayList<String> nomeUsuarios = cliente.usuariosConectados();

        usuarios.clear();
        for (int i = 0; i < nomeUsuarios.size(); i++) {
            usuarios.add(new OrdemJogada(nomeUsuarios.get(i), i));
        }
        return usuarios;
    }

    public String getNomeUsiarioPorId(int id){
        String nome = null;

        for (OrdemJogada ordemJogada:usuarios) {
            if (ordemJogada.getId() ==id)
                nome = ordemJogada.getNome();
        }
        return nome;
    }

    /**
     * Método que inicia a partida.
     *
     * @return Ordem de Jogada.
     * @author Wanderson e Santana
     */
    public ArrayList<String> iniciarPartida() {
        return cliente.iniciarPartida();
    }

    /**
     * Método que envia a ordem de jogadas.
     *
     * @param ordemJogada - Ordem com as jogadas.
     * @author Wanderson e Santana
     */
    public void enviarOrdemJogada(ArrayList<String> ordemJogada) {
        cliente.enviarOrdemJogada(ordemJogada);
    }

    /**
     * Método que modifica a variável de jogada feita.
     *
     * @param controle - true ou false.
     * @author Wanderson e Santana
     */
    public void setControle(boolean controle) {
        this.cliente.setControleMsgJogada(controle);
    }

    /**
     * Método que desconecta do Servidor.
     *
     * @author Wanderson e Santana
     */

    public void sairServidor() {
        cliente.sair();
    }

    /**
     * Método que finaliza uma jogada.
     *
     * @param dado - Número sorteado.
     * @author Wanderson e Santana
     */

    public void informarJogada(int dado) {
        cliente.jogar(this.getIdJogador(), dado);
    }


    public void finalizarJogada(int atualJogador) {
        int meuId = this.getIdJogador();
        int proximo;

        if (meuId == usuarios.size() - 1) {
            proximo = 0;
        } else {
            proximo = meuId + 1;
        }
        cliente.finalizarJogada(proximo);
    }

    /**
     * Método que obtém o valor do sorte grande.
     *
     * @author Wanderson e Santana
     */

    public double getSorteGrande() {
        return cliente.getSorteGrande();
    }

    /**
     * Método que obtém a variável que verifica se o sorte grande foi atualizado.
     *
     * @author Wanderson e Santana
     */
    public boolean getControleSorteGrande() {
        return cliente.getControleSorteGrande();
    }

    /**
     * Método que modifica a variável que verifica se o sorte grande foi atualizado.
     *
     * @author Wanderson e Santana
     */

    public void setControleSorteGrande(boolean valor) {
        cliente.setControleSorteGrande(valor);
    }

    public void setControleTranferenciaIn(boolean controle) {
        this.cliente.setControleTransferenciaIn(controle);
    }

    public void setControleTranferenciaOut(boolean controle) {
        this.cliente.setControleTransferenciaOut(controle);
    }

    public boolean getControleTranferenciaIn() {
        return cliente.getControleTransferenciaIn();
    }

    public boolean getControleTranferenciaOut() {
        return cliente.getControleTransferenciaOut();
    }

    public int getIdJogadorTranferencia() {
        return cliente.getIdDestinoTranferenciaO();
    }

    public double getValorTranferencia() {
        return cliente.getValorTransferencia();
    }

    public boolean getGatilhoInicioPartida() {
        return this.cliente.isGatilhoInicioPartida();
    }

    public void setGatilhoInicioPartida(boolean gatilho) {
        this.cliente.setGatilhoInicioPartida(gatilho);
    }

    public void iniciarTabuleiro() {
        this.cliente.iniciarTabuleiro();
    }

    public int getAtualJogador() {
        return Integer.parseInt(this.cliente.getAtualJogador());
    }

    public void concursoBandaRock(int idProximo){
        this.cliente.concursoBandaRock(idProximo);
    }
    public void participarBolao(int id){
        this.cliente.participarBolao(id);
    }

    public void setControleConcursoBanda(boolean controle){
        this.cliente.setControleConcursoBanda(controle);
    }
    public boolean getControleConcursoBanda(){
        return this.cliente.getControleConcursoBanda();
    }
    public int getIdProxJogadorEvento(){
        return this.cliente.getIdProximoJOgadorEvento();
    }
    public int getReultadoBolao(){
        return this.cliente.getVencedorBolao();
    }
    public boolean getControleBolao(){
        return this.cliente.getControleBolao();
    }
    public void setControleBolao(boolean controleBolao){
        this.cliente.setControleBolao(controleBolao);
    }

    public int getNumeroParticipantesBolao(){
        return this.cliente.getNumeroParticipantesBolao();
    }

    public void organizadorBolao(int id){
        this.cliente.organizadorBolao(id);
    }
    public int getOrganizadorBolao(){
        return this.cliente.getIdOrganizadorBolao();
    }

    public void resultadoBolao(int resultado){
        this.cliente.resultadoBolao(resultado);
    }
}
