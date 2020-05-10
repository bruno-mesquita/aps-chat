package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
  public static void main(String[] args) {
    try {
      final Socket client = new Socket("127.0.0.1", 3322);

      new Thread() {
        @Override
        public void run() {
          try {
            BufferedReader leitor = new BufferedReader(new InputStreamReader(client.getInputStream()));

            while(true) {
              String msg = leitor.readLine();
              if(msg == null || msg.isEmpty()) {
                continue;
              }
              System.out.println("O servidor disse: " + msg);
            }
          } catch (IOException e) {
            System.out.println("impossivel ler a mensagem do servidor");
            e.printStackTrace();
          }
        }
      }.start();

        PrintWriter escritor = new PrintWriter(client.getOutputStream(), true);
        BufferedReader terminal = new BufferedReader(new InputStreamReader(System.in));
        String msgTerminal = "";

        while(true) {
          msgTerminal = terminal.readLine();
          if(msgTerminal == null || msgTerminal.length() == 0) {
            continue;
          }
          escritor.println(msgTerminal);
          if(msgTerminal.equalsIgnoreCase("::SAIR")) {
            System.exit(0);
          }
        }

    } catch (UnknownHostException e) {
      System.out.println("O endereço passado é inválido");
      e.printStackTrace();
    } catch (IOException e) {
      System.out.println("o Servidor pode estar fora do ar");
      e.printStackTrace();
    }
  }
}
