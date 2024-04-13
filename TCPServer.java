import java.net.*;
import java.io.*;

public class TCPServer {
    public static void main(String args[]) {
        try {
            int serverPort = 7896;
            // Cria um socket de servidor e o associa à porta especificada
            ServerSocket listenSocket = new ServerSocket(serverPort);

            // Loop infinito para aceitar conexões de clientes
            while (true) {
                // Aguarda e aceita a conexão de um cliente
                Socket clientSocket = listenSocket.accept();

                // Inicia uma nova thread para lidar com a conexão do cliente
                Connection c = new Connection(clientSocket);
            }
        } catch (IOException e) {
            System.out.println("Listen :" + e.getMessage());
        }
    }
}

class Connection extends Thread {
    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;

    public Connection(Socket aClientSocket) {
        try {
            clientSocket = aClientSocket;
            // Cria fluxos de entrada e saída para comunicação com o cliente
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            this.start();
        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    public void run() {
        try {
            // Servidor de eco: lê os dados enviados pelo cliente e os envia de volta
            String data = in.readUTF();
            out.writeUTF(data);
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO:" + e.getMessage());
        } finally {
            try {
                // Fecha o socket do cliente após a comunicação
                clientSocket.close();
            } catch (IOException e) {
                /* tratamento de erro se o fechamento do socket falhar */
            }
        }
    }
}
