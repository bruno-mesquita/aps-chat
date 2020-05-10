package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Manager extends Thread {
  private Socket client;
  private String nameClient;
  private BufferedReader reader;
  private PrintWriter writer;
  private static final Map<String, Manager> clients = new HashMap<String, Manager>();

  public Manager(Socket client) {
    this.client = client;
    start();
  }

  @Override
  public void run() {
    try {
      reader = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
      writer = new PrintWriter(this.client.getOutputStream(), true);

      login();

      String msg;

      while(true) {
        // Pegando a mensagem recebida
        msg = reader.readLine();

        // Comando para SAIR da aplicção
        if(msg.equalsIgnoreCase(Commands.SAIR)) {
          this.client.close();

        } else if(msg.startsWith(Commands.ENVIAR_MENSAGEM)) {  // Comando para Enviar uma mensagem
          // Pegando o nome do destinatario
          String nomeDestinatario = msg.substring(Commands.ENVIAR_MENSAGEM.length(), msg.length());

          // Buscando Destinatario
          Manager destinatario = clients.get(nomeDestinatario);

          // Validando se o destinatario existe
          if(destinatario == null) {
            writer.println("O Cliente informado não existe");
          } else {
            // writer.println("Dgite uma mensagem para " + destinatario.getNameClient());
            destinatario.getWriter().println(this.nameClient + ": " + reader.readLine());
          }

        } else if(msg.equals(Commands.LISTA_USUARIOS)) { // Comando para Listar Clientes logados
          updateListUsers(this);
        } /* else {
          writer.println(this.nameClient + "disse: " + msg);
        } */
      }

    } catch (IOException e) {
      System.err.println("O Cliente fechou a conexão");
      clients.remove(this.nameClient);
      e.printStackTrace();
    }
  }

  private void login() throws IOException {
    // While para o usuario pode refazer o login
    while(true) {
      writer.println(Commands.LOGIN);
      this.nameClient = reader.readLine().toLowerCase().replace(",", "");

      if(this.nameClient.equalsIgnoreCase("null") || this.nameClient.isEmpty()) {
        writer.println(Commands.LOGIN_NEGADO);
      }
      // Verificando se o cliente já está logado
      else if(clients.containsKey(this.nameClient)) {
        writer.println(Commands.LOGIN_NEGADO);

      } else {
        writer.println(Commands.LOGIN_ACEITO);
        clients.put(this.nameClient, this);

        for(String cliente: clients.keySet()) {
          updateListUsers(clients.get(cliente));
        }
        break;
      }
    }
  }

  private void updateListUsers(Manager manager) {
    StringBuffer str = new StringBuffer();
    for(String client: clients.keySet()) {
      if(manager.getNameClient().equals(client))
        continue;

      str.append(client);
      str.append(",");
    }

    if(str.length() > 0)
      str.delete(str.length() - 1, str.length());

    manager.getWriter().println(Commands.LISTA_USUARIOS);
    manager.getWriter().println(str.toString());
  }

  /**
   * @return the writer
   */
  public PrintWriter getWriter() {
    return this.writer;
  }

  /**
   * @return the nomeClient
   */
  public String getNameClient() {
    return this.nameClient;
  }
}
