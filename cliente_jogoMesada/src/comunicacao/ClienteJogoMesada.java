package comunicacao;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private Socket minhaConexao;
    
    /**
     * Construtor de Cliente utilizado quando a comunicação com o servidor já
     * foi estabelecida.
     *
     *
     * @author Santana
     */
    public ClienteJogoMesada() {

        try {
            this.minhaConexao = new Socket(host, portaServidor);
            this.saidaDados = new DataOutputStream(this.minhaConexao.getOutputStream());
            this.entradaDados = new BufferedReader(new InputStreamReader(this.minhaConexao.getInputStream()));

        } catch (IOException ex) {
            Logger.getLogger(ClienteJogoMesada.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
