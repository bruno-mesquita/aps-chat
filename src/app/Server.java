package app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
  public static void main(String[] args) throws IOException {
    ServerSocket server = new ServerSocket(3322);

    while(true) {
      Socket client = server.accept();
      new Manager(client);
    }
  }
}
