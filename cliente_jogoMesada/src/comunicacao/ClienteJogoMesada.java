package comunicacao;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.*;


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
    private static int portaServidor;
    private BufferedReader entradaDados;
    private DataOutputStream saidaDados;
    private Socket conexaoClienteServidor;
    private DatagramSocket cone;

    /**
     * 
     *
     *
     * @author Wanderson e Santana
     */
    public ClienteJogoMesada() {

        try {
            this.conexaoClienteServidor = new Socket(host, portaServidor);
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

    public String entrar(String usuario) {

        if (conexaoClienteServidor.isConnected()) {
            try {
                saidaDados.writeBytes("001" + ";" + usuario + '\n');

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

}
