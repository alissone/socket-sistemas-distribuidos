import java.net.*;
import java.io.*;

public class TCPClient {
    public static void main(String args[]) {
        // Os argumentos fornecem a mensagem e o nome de host de destino
        Socket s = null;
        try {
            int serverPort = 7896;
            s = new Socket(args[1], serverPort); // Conexão com o servidor especificado no segundo argumento
            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            out.writeUTF(args[0]); // Envia a mensagem para o servidor

            String data = in.readUTF(); // Aguarda a resposta do servidor
            System.out.println("Received: " + data); // Exibe a mensagem recebida do servidor

        } catch (UnknownHostException e) {
            System.out.println("Sock:" + e.getMessage()); // Trata exceção de host desconhecido
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage()); // Trata exceção de fim de arquivo
        } catch (IOException e) {
            System.out.println("IO:" + e.getMessage()); // Trata exceção de entrada/saída
        } finally {
            // Fecha a conexão com o servidor
            if (s != null)
                try {
                    s.close();
                } catch (IOException e) {
                    /* Falha ao fechar a conexão */
                }
        }
    }
}
