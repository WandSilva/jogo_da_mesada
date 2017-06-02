package view.tabuleiro;

import controller.Facade;
import exception.SaldoInsuficienteException;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.CartaCompra;
import model.CartaCorreio;
import model.OrdemJogada;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FXMLViewController implements Initializable {

    //********DECLARAÇÃO DOS PANES PRESENTES NO GRID**********/
    @FXML
    private Pane casa23;
    @FXML
    private Pane casa01;
    @FXML
    private Pane casa00;
    @FXML
    private Pane casa22;
    @FXML
    private Pane casa44;
    @FXML
    private Pane casa03;
    @FXML
    private Pane casa02;
    @FXML
    private Pane casa24;
    @FXML
    private Pane casa04;
    @FXML
    private Pane casa61;
    @FXML
    private Pane casa60;
    @FXML
    private Pane casa63;
    @FXML
    private Pane casa41;
    @FXML
    private Pane casa62;
    @FXML
    private Pane casa40;
    @FXML
    private Pane casa43;
    @FXML
    private Pane casa21;
    @FXML
    private Pane casa42;
    @FXML
    private Pane casa20;
    @FXML
    private Pane casa64;
    @FXML
    private Pane casa12;
    @FXML
    private Pane casa34;
    @FXML
    private Pane casa33;
    @FXML
    private Pane casa11;
    @FXML
    private Pane casa14;
    @FXML
    private Pane casa13;
    @FXML
    private Pane casa50;
    @FXML
    private Pane casa52;
    @FXML
    private Pane casa30;
    @FXML
    private Pane casa51;
    @FXML
    private Pane casa32;
    @FXML
    private Pane casa10;
    @FXML
    private Pane casa54;
    @FXML
    private Pane casa53;
    @FXML
    private Pane casa31;
    //////////////////

    @FXML
    private GridPane grid;
    @FXML
    private Label labelSaldo;
    @FXML
    private Label labelDivida;
    @FXML
    private ComboBox<String> comboCorreio;
    @FXML
    private ComboBox<String> comboCompras;
    @FXML
    private TextArea textCorreio;
    @FXML
    private TextArea textContas;
    @FXML
    private TextArea txtJogadores;
    @FXML
    private Label labelSorteGrande;

    //botões
    @FXML
    private Button botaoJogar;
    @FXML
    private Button botaoPagarDivida;
    @FXML
    private Button botaoSorGrande;
    @FXML
    private Button botaoEmprestimo;
    @FXML
    private Button botaoVenderCarta;
    @FXML
    private Button botaoAcaoCarta;
    @FXML
    private Button botaoFinalizar;

    private ArrayList<Peao> peoes = new ArrayList<>();

    private int dado;

    private Facade facade;

    private ArrayList<String> ordemJogadas = new ArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.facade = Facade.getInstance();
        ordemJogadas = this.facade.iniciarPartida();
        this.facade.enviarOrdemJogada(ordemJogadas);

        this.atualizarValoresTela();
        this.desabilitarBotoes();
        this.criarPeoes(this.facade.getUsuariosConectados().size());
        this.adicionarImagensTabuleiro();
        this.mostrarCartasCorreio();
        this.mostrarCartasCompra();
        this.atualizarSortegrande();
        this.removerJogadorServidor();
        this.mostraJogadorConectado();
        this.atualizarJogadas();

    }


    public void criarPeoes(int numeroJogadores) {
        AparenciaPeao aparenciaPeao = new AparenciaPeao();
        for (int i = 0; i < numeroJogadores; i++) {
            Peao peao = new Peao();
            peao.setPeao(aparenciaPeao.getAparenciaPeoes().get(i));
            peoes.add(peao);
            grid.add(peao.getPeao(), 0, 0);
        }
    }

    /**
     * evento do boão Jogar. rola o dado, move o peão e verifica a ação da casa.
     *
     * @param event
     */
    @FXML
    public void jogar(ActionEvent event) {
        this.dado = facade.rolarDado();
        JOptionPane.showMessageDialog(null, "Valor sorteado: " + dado);

        this.moverPeao(peoes.get(facade.getIdJogador()), dado);
        this.realizarAcaoCasa(peoes.get(facade.getIdJogador()).getColuna(), peoes.get(facade.getIdJogador()).getLinha());
    }

    public void finalizarJogada() {
        this.facade.finalizarJogada(this.dado);
    }

    public void moverPeao(Peao peao, int dado) {

        if (peao.getLinha() == 4 && peao.getColuna() == 3) {
            peao.setLinha(0);
            peao.setColuna(dado);
        } else {
            peao.setColuna(peao.getColuna() + dado);
            if (peao.getColuna() > 6) {
                peao.setColuna(dado - (7 - ((peao.getColuna() - dado))));
                peao.setLinha(peao.getLinha() + 1);
            }
            if (peao.getLinha() > 3 && peao.getColuna() > 2) {
                peao.setLinha(4);
                peao.setColuna(3);
            }
        }
        grid.getChildren().remove(peao.getPeao());
        grid.add(peao.getPeao(), peao.getColuna(), peao.getLinha());
    }

    void atualizarJogadas() {
        Task t = new Task() {
            @Override
            protected Object call() throws Exception {
                while (true) {
                    Platform.runLater(() -> {

                        int jogadorPassado = facade.getProximoJogador() - 1;
                        if (jogadorPassado < 0)
                            jogadorPassado = facade.getUsuariosConectados().size()-1;

                        if (facade.getControle() && (jogadorPassado != facade.getIdJogador())) {
                            /* no peoes.get() abaixo tem quem passar o id do jogador que
                             vc quer mover o peão. Para mover, basta colocar o valor que saiu
                             no dado dele*/

                            moverPeao(peoes.get(jogadorPassado), facade.getUltimoDado());

                            /*aqui vc passa o ID do jogador no get para verficar se onde
                             * ele caiu tem algum evento que outros jogadores precisam interagir*/
                            acaoSeAlguemCaiuNaCasa(peoes.get(jogadorPassado).getColuna(), peoes.get(jogadorPassado).getLinha());
                            facade.setControle(false);
                        }
                        if (facade.getIdJogador() == facade.getProximoJogador()) {
                            habilitarBotoes();
                        } else if (facade.getIdJogador() != facade.getProximoJogador()) {
                            desabilitarBotoes();
                        }
                    });


                    Thread.sleep(2000);
                }
            }
        };
        new Thread(t).start();
    }

    /**
     * Verifica onde o peão do jogador está e realiza a ação da casa
     *
     * @param coluna
     * @param linha
     */
    public void realizarAcaoCasa(int coluna, int linha) {

        //correio de 1 carta
        if ((coluna == 1 && linha == 0) || (coluna == 4 && linha == 1) || (coluna == 5 && linha == 2) || (coluna == 1 && linha == 3)) {
            facade.receberCartaCorreio(facade.pegarCartaCorreio());
            this.mostrarCartasCorreio();
            mostrarAlerta("Correios", "", "Você recebeu 1 carta");
        } //casa premio
        else if (coluna == 2 && linha == 0) {
            facade.acaCasaPremio();
            atualizarValoresTela();
        } //correio de 3 cartas
        else if ((coluna == 3 && linha == 0) || (coluna == 2 && linha == 2)) {
            facade.receberCartaCorreio(facade.pegarCartaCorreio());
            facade.receberCartaCorreio(facade.pegarCartaCorreio());
            facade.receberCartaCorreio(facade.pegarCartaCorreio());
            this.mostrarCartasCorreio();
            mostrarAlerta("Correios", "", "Você recebeu 3 cartas");
        } //correio de 2 cartas
        else if ((coluna == 5 && linha == 0) || (coluna == 3 && linha == 3)) {
            facade.receberCartaCorreio(facade.pegarCartaCorreio());
            facade.receberCartaCorreio(facade.pegarCartaCorreio());
            this.mostrarCartasCorreio();
            mostrarAlerta("Correios", "", "Você recebeu 2 cartas");
        } //compras e entretenimento
        else if ((coluna == 4 && linha == 0) || (coluna == 5 && linha == 1) || (coluna == 1 && linha == 2) || (coluna == 4 && linha == 3)) {
            comprarCartaEntretenimento();
            atualizarValoresTela();
        } //bolao de esportes
        else if ((coluna == 6 && linha == 0) || (coluna == 6 && linha == 1) || (coluna == 6 && linha == 2) || (coluna == 6 && linha == 3)) {
            //  facade.acaCasaPremio();
            atualizarValoresTela();
        } //achou um comprador
        else if ((coluna == 2 && linha == 1) || (coluna == 3 && linha == 2) || (coluna == 2 && linha == 3) || (coluna == 5 && linha == 3) || (coluna == 1 && linha == 4)) {
            mostrarAlerta("Achou um comprador", "", "Agora você pode vender uma carta 'Compras e entretenimento', caso possua uma");
            atualizarValoresTela();
        } //praia no domingo
        else if (coluna == 0 && linha == 1) {
            try {
                facade.acaoCasaPraiaNodomingo();
            } catch (SaldoInsuficienteException e) {
                JOptionPane.showMessageDialog(null, "Saldo insuficiente, faça um empréstimo", "Saldo insuficiente", JOptionPane.ERROR_MESSAGE);
            }
            atualizarValoresTela();
            atualizarSortegrande();
        } //concurso banda de rock
        else if (coluna == 1 && linha == 1) {
            facade.casaConcursoArrocha(0);
            atualizarValoresTela();
        } //feliz aniversário
        else if (coluna == 3 && linha == 1) {
            try {
                facade.acaoCasaFelizAniversario(true);
            } catch (SaldoInsuficienteException e) {
                e.printStackTrace();
            }
            atualizarValoresTela();
        } //ajude a floresta
        else if (coluna == 0 && linha == 2) {
            try {
                facade.acaoCasaAjudeaFloresta();
            } catch (SaldoInsuficienteException e) {
                JOptionPane.showMessageDialog(null, "Saldo insuficiente, faça um empréstimo", "Saldo insuficiente", JOptionPane.ERROR_MESSAGE);
            }
            atualizarValoresTela();
            atualizarSortegrande();
        } //lanchonete
        else if (coluna == 4 && linha == 2) {
            try {
                facade.acaoCasaLanchonete();
            } catch (SaldoInsuficienteException e) {
                JOptionPane.showMessageDialog(null, "Saldo insuficiente, faça um empréstimo", "Saldo insuficiente", JOptionPane.ERROR_MESSAGE);
            }
            atualizarValoresTela();
            atualizarSortegrande();
        } //negocio de ocasião
        else if (coluna == 0 && linha == 3) {

            Alert dialogoExe = new Alert(Alert.AlertType.CONFIRMATION);
            ButtonType btnSim = new ButtonType("Sim");
            ButtonType btnNao = new ButtonType("Não", ButtonBar.ButtonData.CANCEL_CLOSE);

            dialogoExe.setTitle("Negócio de ocasião");
            dialogoExe.setHeaderText("Você pode comprar uma carta ''Compras e Entretenimento''"
                    + " por 100 x valor sorteado no dado. ");
            dialogoExe.setContentText("Deseja comprar?");
            dialogoExe.getButtonTypes().setAll(btnSim);
            dialogoExe.showAndWait().ifPresent(b -> {
                if (b == btnSim) {
                    try {
                        int valorDado = facade.rolarDado();
                        CartaCompra cartaCompra = facade.pegarCartaCompra();
                        String carta = cartaCompra.getNome();
                        facade.pagarNeogocioOcasiao(valorDado);
                        facade.comprarCartaCompraEntretenimento(0, cartaCompra);
                        mostrarAlerta("", "valor sorteado: " + valorDado, "Você pegou a carta ''" + carta + "''.\nEla custou " + 100 * valorDado + " reais");
                        comboCompras.getItems().add(carta);
                        mostrarCartasCompra();
                        atualizarValoresTela();
                    } catch (SaldoInsuficienteException e) {
                        e.printStackTrace();
                    }
                }
            });
        } //compras no shopping
        else if (coluna == 0 && linha == 4) {
            try {
                facade.acaoCasaShopping();
            } catch (SaldoInsuficienteException e) {
                JOptionPane.showMessageDialog(null, "Saldo insuficiente, faça um empréstimo", "Saldo insuficiente", JOptionPane.ERROR_MESSAGE);
            }
            atualizarValoresTela();
            atualizarSortegrande();
        } //maratona beneficente
        else if (coluna == 2 && linha == 4) {
            atualizarValoresTela();
            atualizarSortegrande();
        }
        //dia da mesada
        if (coluna == 3 && linha == 4) {
            facade.acaoCasaDiaDaMesada();
            this.atualizarValoresTela();
        }
    }

    /**
     * verifica onde o peão de outro jogador está para informar sobre eventos
     * coletivos acionados por ele.
     *
     * @param coluna
     * @param linha
     */
    public void acaoSeAlguemCaiuNaCasa(int coluna, int linha) {
        //casa feliz aniversário
        if (coluna == 3 && linha == 1) {
            try {
                facade.acaoCasaFelizAniversario(false);
                this.atualizarValoresTela();
                mostrarAlerta("Feliz aniversário", "", "Um jogador está fazendo "
                        + "aniversário e vc esta dando 100 reais de presente");
            } catch (SaldoInsuficienteException e) {
                e.printStackTrace();
            }
        } //maratona beneficente
        else if (coluna == 2 && linha == 4) {
            Alert dialogoExe = new Alert(Alert.AlertType.CONFIRMATION);
            ButtonType rolarDado = new ButtonType("rolar o dado");

            dialogoExe.setTitle("Maratona beneficente");
            dialogoExe.setHeaderText("Um jogador caiu na casa maratona beneficente. "
                    + "Role o dado para saber o valor da sua doação");
            dialogoExe.setContentText("");
            dialogoExe.getButtonTypes().setAll(rolarDado);
            dialogoExe.showAndWait().ifPresent(b -> {
                if (b == rolarDado) {

                    try {
                        int valorDado = facade.rolarDado();
                        mostrarAlerta("", "", "valor sorteado: " + valorDado + "\nVocê doará " + 100 * valorDado + " reais");
                        facade.acaoCasaMaratonaBeneficente(valorDado);
                        atualizarValoresTela();
                        atualizarSortegrande();
                    } catch (SaldoInsuficienteException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @FXML
    public void fazerEmprestimo() {
        String valor = JOptionPane.showInputDialog("Valor do emprestimo:");
        facade.fazerEmprestimo(Double.parseDouble(valor));
        labelDivida.setText("Dívida: " + facade.verDividaJogador());
        labelSaldo.setText("Saldo: " + facade.verSaldoJogador());
    }

    @FXML
    public void ganharSorteGrande(ActionEvent event) {
        if (dado == 6) {
            JOptionPane.showMessageDialog(null, "Parabéns, você recebeu R$" + labelSorteGrande.getText(), "Sorte grande", JOptionPane.INFORMATION_MESSAGE);
            facade.acaoCasaSorteGrande(true);
            atualizarSortegrande();
            atualizarValoresTela();
        } else {
            JOptionPane.showMessageDialog(null, "Tentando trapacear? Voce não tirou 6 no dado!", "Tentando trapacear?", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void comprarCartaEntretenimento() {
        Alert dialogoExe = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType btnSim = new ButtonType("Comprar");
        ButtonType btnEmprestimo = new ButtonType("Pagar com empréstimo");
        ButtonType btnNao = new ButtonType("Não", ButtonBar.ButtonData.CANCEL_CLOSE);

        CartaCompra cartaCompra = facade.pegarCartaCompra();
        String nome = cartaCompra.getNome();
        Double valor = cartaCompra.getValorInicial();
        Double vRevenda = cartaCompra.getValorRevenda();

        dialogoExe.setTitle("Compras e entretenimento");
        dialogoExe.setHeaderText("Você pegou a carta ''" + nome + "''. Ela custa R$" + valor + " e "
                + "pode ser revendida por " + vRevenda);
        dialogoExe.setContentText("Deseja comprar?");
        dialogoExe.getButtonTypes().setAll(btnSim, btnEmprestimo, btnNao);
        dialogoExe.showAndWait().ifPresent(b -> {
            if (b == btnSim) {
                try {
                    facade.comprarCartaCompraEntretenimento(cartaCompra);
                    mostrarCartasCompra();
                } catch (SaldoInsuficienteException e) {
                    JOptionPane.showMessageDialog(null, "Saldo insuficiente, você deveria ter feito um empréstimo", "Que pena!", JOptionPane.WARNING_MESSAGE);
                }
            } else if (b == btnEmprestimo) {
                double aux = valor - facade.verSaldoJogador();
                if (aux < 0) {
                    aux = 0;
                }
                facade.fazerEmprestimo(aux);
                try {
                    facade.comprarCartaCompraEntretenimento(cartaCompra);
                    mostrarCartasCompra();
                } catch (SaldoInsuficienteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    public void venderCartaCompra(ActionEvent e) {
        if (comboCompras.getValue() != null) {
            int coluna = peoes.get(facade.getIdJogador()).getColuna();
            int linha = peoes.get(facade.getIdJogador()).getLinha();

            if ((coluna == 2 && linha == 1) || (coluna == 3 && linha == 2) || (coluna == 2 && linha == 3) || (coluna == 5 && linha == 3) || (coluna == 1 && linha == 4)) {
                facade.venderCartaCompraEntretenimento(comboCompras.getValue());
                comboCompras.getItems().remove(comboCompras.getValue());
                this.atualizarValoresTela();
                textContas.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Tentando trapacear? Voce não está na casa 'Achou um coprador'!", "Tentando trapacear?", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @FXML
    public void acaoCartaCorreio(ActionEvent e) {
        if (comboCorreio.getValue() != null) {
            if (comboCorreio.getValue().equals("va para frente agora")) {
                this.acaoVaParaFrenteAgora();
                comboCorreio.getItems().remove(comboCorreio.getValue());
            } else {
                try {
                    facade.acaoCartas(true, comboCorreio.getValue());
                    comboCorreio.getItems().remove(comboCorreio.getValue());
                    textCorreio.setText("");
                    this.atualizarValoresTela();
                    this.atualizarSortegrande();
                } catch (SaldoInsuficienteException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private void acaoVaParaFrenteAgora() {
        Peao peao = peoes.get(facade.getIdJogador());
        int linha = peao.getLinha();
        int coluna = peao.getColuna();

        ChoiceDialog dialogoRegiao = new ChoiceDialog("Compras e entretenimento", "Achou um comprador");
        dialogoRegiao.setTitle("Va para frente agora");
        dialogoRegiao.setHeaderText("Escolha seu destino");
        dialogoRegiao.setContentText("Destino:");
        dialogoRegiao.showAndWait().ifPresent(r -> {
            if (r == "Compras e entretenimento") {

                if (((coluna == 1) && (linha == 0)) ||
                        ((coluna == 1) && (linha == 3))) {
                    this.moverPeao(peoes.get(facade.getIdJogador()), 3);
                } else if (((coluna == 3) && (linha == 0)) ||
                        ((coluna == 4) && (linha == 1)) ||
                        ((coluna == 3) && (linha == 3))) {
                    this.moverPeao(peoes.get(facade.getIdJogador()), 1);
                } else if ((coluna == 5) && (linha == 0)) {
                    this.moverPeao(peoes.get(facade.getIdJogador()), 7);
                } else if ((coluna == 2) && (linha == 2)) {
                    this.moverPeao(peoes.get(facade.getIdJogador()), 9);
                } else if ((coluna == 5) && (linha == 2)) {
                    this.moverPeao(peoes.get(facade.getIdJogador()), 6);
                } else
                    JOptionPane.showMessageDialog(null, "Voce não está na casa ''Correios''!", "Tentando trapacear?", JOptionPane.ERROR_MESSAGE);
                ;
            } else if (r == "Achou um comprador") {
                if ((coluna == 1) && (linha == 0)) {
                    this.moverPeao(peoes.get(facade.getIdJogador()), 8);
                } else if (((coluna == 3) && (linha == 0)) ||
                        ((coluna == 4) && (linha == 1))) {
                    this.moverPeao(peoes.get(facade.getIdJogador()), 6);
                } else if (((coluna == 5) && (linha == 0)) ||
                        ((coluna == 5) && (linha == 2))) {
                    this.moverPeao(peoes.get(facade.getIdJogador()), 4);
                } else if (((coluna == 2) && (linha == 2)) ||
                        (((coluna == 1) && (linha == 3)))) {
                    this.moverPeao(peoes.get(facade.getIdJogador()), 1);
                } else if ((coluna == 3) && (linha == 3)) {
                    this.moverPeao(peoes.get(facade.getIdJogador()), 2);
                } else
                    JOptionPane.showMessageDialog(null, "Voce não está na casa ''Correios''!", "Tentando trapacear?", JOptionPane.ERROR_MESSAGE);
                ;
            }
        });
    }

    @FXML
    public void pagarDivida(ActionEvent e) {
        int coluna = peoes.get(facade.getIdJogador()).getColuna();
        int linha = peoes.get(facade.getIdJogador()).getLinha();

        if (linha == 4 && coluna == 3) {
            Alert dialogoExe = new Alert(Alert.AlertType.CONFIRMATION);
            ButtonType btnJuros = new ButtonType("apenas juros");
            ButtonType btnParte = new ButtonType("parte da dívida");
            ButtonType btnTudo = new ButtonType("toda a dívida");

            dialogoExe.setTitle("Compras e entretenimento");
            dialogoExe.setHeaderText("");
            dialogoExe.setContentText("Pagar");
            dialogoExe.getButtonTypes().setAll(btnJuros, btnParte, btnTudo);
            dialogoExe.showAndWait().ifPresent(b -> {
                if (b == btnJuros) {
                    try {
                        facade.pagarJuros();
                        this.atualizarValoresTela();
                    } catch (SaldoInsuficienteException e1) {

                    }
                } else if (b == btnParte) {
                    try {
                        Double valor = Double.parseDouble(JOptionPane.showInputDialog("Valor da pagamento"));
                        facade.pagarDividaParcial(valor);
                        this.atualizarValoresTela();
                    } catch (SaldoInsuficienteException e1) {
                        JOptionPane.showMessageDialog(null, "Saldo insuficiente, faça um empréstimo", "Saldo insuficiente", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (b == btnTudo) {
                    try {
                        facade.pagarDividaTotal();
                        this.atualizarValoresTela();
                    } catch (SaldoInsuficienteException e1) {
                        JOptionPane.showMessageDialog(null, "Saldo insuficiente, faça um empréstimo", "Saldo insuficiente", JOptionPane.ERROR_MESSAGE);
                    }
                }

            });
        } else {
            JOptionPane.showMessageDialog(null, "Voce não está na casa 'Dia da mesada'!", "Calma aí amigão", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    public void descricaoCartaCompra(ActionEvent e) {
        CartaCompra cartaCompra = null;

        for (CartaCompra aux : facade.verCartasCompraJogador()) {
            if (aux.getNome() == comboCompras.getValue()) {
                cartaCompra = aux;
            }
        }

        if (cartaCompra != null) {
            textContas.setText("Valor Inicial: R$" + cartaCompra.getValorInicial() + "\n"
                    + "Valor de revenda: R$" + cartaCompra.getValorRevenda());
        }
    }

    @FXML
    public void descricaoCartaCorreio(ActionEvent e) {
        CartaCorreio cartaCorreio = null;

        for (CartaCorreio aux : facade.verCartasCorreioJogador()) {
            if (aux.getTipo() == comboCorreio.getValue()) {
                cartaCorreio = aux;
            }
        }
        if (cartaCorreio != null) {

            textCorreio.setText("Carta: " + cartaCorreio.getNome() + "\n\n"
                    + "Valor:" + cartaCorreio.getValor());
        }
    }

    public void mostraJogadorConectado() {
        ArrayList<OrdemJogada> lista = new ArrayList();
        lista = facade.getUsuariosConectados();
        String usuarios = "";
        for (int i = 0; i < lista.size(); i++) {
            usuarios = usuarios + lista.get(i).getNome() + "\n";
        }
        txtJogadores.setText(usuarios);
    }

    public void atualizarValoresTela() {
        labelDivida.setText("Dívida: " + facade.verDividaJogador());
        labelSaldo.setText("Saldo: " + facade.verSaldoJogador());
    }

    public void atualizarSortegrande() {
        String valor = Double.toString(facade.getValorSorteGrande());
        this.labelSorteGrande.setText("R$" + valor);
    }

    public void mostrarCartasCorreio() {
        comboCorreio.getItems().clear();
        for (int i = 0; i < facade.verCartasCorreioJogador().size(); i++) {
            comboCorreio.getItems().addAll(facade.verCartasCorreioJogador().get(i).getTipo());
        }
    }

    public void mostrarCartasCompra() {
        comboCompras.getItems().clear();
        for (int i = 0; i < facade.verCartasCompraJogador().size(); i++) {
            comboCompras.getItems().addAll(facade.verCartasCompraJogador().get(i).getNome());
        }
    }

    public void desabilitarBotoes() {
        this.botaoAcaoCarta.setDisable(true);
        this.botaoEmprestimo.setDisable(true);
        this.botaoJogar.setDisable(true);
        this.botaoPagarDivida.setDisable(true);
        this.botaoSorGrande.setDisable(true);
        this.botaoVenderCarta.setDisable(true);
        this.botaoFinalizar.setDisable(true);
    }

    public void habilitarBotoes() {
        this.botaoAcaoCarta.setDisable(false);
        this.botaoEmprestimo.setDisable(false);
        this.botaoJogar.setDisable(false);
        this.botaoPagarDivida.setDisable(false);
        this.botaoSorGrande.setDisable(false);
        this.botaoVenderCarta.setDisable(false);
        this.botaoFinalizar.setDisable(false);
    }

    public void mostrarAlerta(String titulo, String cabecalho, String corpo) {
        Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
        dialogoInfo.setTitle(titulo);
        dialogoInfo.setHeaderText(cabecalho);
        dialogoInfo.setContentText(corpo);
        dialogoInfo.showAndWait();
    }

    private void adicionarImagensTabuleiro() {
        ArrayList<Pane> casas = organizarCasas();

        for (Pane casa : casas) {
            BackgroundImage bi = new BackgroundImage(new Image(casa.getId() + ".png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
            casa.setBackground(new Background(bi));
        }
    }

    public void removerJogadorServidor() {
        Stage stage = Tabuleiro.getStage();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                Facade facade = Facade.getInstance();
                facade.removerJogadorServidor();
                Platform.exit();
                System.exit(0);
            }
        });
    }

    private ArrayList<Pane> organizarCasas() {
        ArrayList<Pane> casas = new ArrayList<>();
        casas.add(casa00);
        casas.add(casa01);
        casas.add(casa02);
        casas.add(casa03);
        casas.add(casa04);
        casas.add(casa10);
        casas.add(casa11);
        casas.add(casa12);
        casas.add(casa13);
        casas.add(casa14);
        casas.add(casa20);
        casas.add(casa21);
        casas.add(casa22);
        casas.add(casa23);
        casas.add(casa24);
        casas.add(casa30);
        casas.add(casa31);
        casas.add(casa32);
        casas.add(casa33);
        casas.add(casa34);
        casas.add(casa40);
        casas.add(casa41);
        casas.add(casa42);
        casas.add(casa43);
        casas.add(casa50);
        casas.add(casa51);
        casas.add(casa52);
        casas.add(casa53);
        casas.add(casa60);
        casas.add(casa61);
        casas.add(casa62);
        casas.add(casa63);
        casas.add(casa64);

        return casas;
    }
}
