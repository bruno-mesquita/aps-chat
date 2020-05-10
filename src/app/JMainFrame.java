package app;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class JMainFrame extends JFrame {

  private static final long serialVersionUID = -2395356509322488435L;
  private PrintWriter writer;
  private BufferedReader reader;
  private JTextArea taEditor = new JTextArea("Digite aqui sua mensagem");
  private JTextArea taVisor = new JTextArea();
  private JList liUsuarios = new JList();
  private JScrollPane scrollTaVisor = new JScrollPane(taVisor);

  public JMainFrame() {
    setTitle("Chat Aps");
    setLayout(new BorderLayout());
    setLocationRelativeTo(null);

    liUsuarios.setBackground(Color.GRAY);
    taEditor.setBackground(Color.CYAN);

    taEditor.setPreferredSize(new Dimension(400, 40));
    taVisor.setEditable(false);
    liUsuarios.setPreferredSize(new Dimension(100, 140));

    add(taEditor, BorderLayout.SOUTH);
    add(scrollTaVisor, BorderLayout.CENTER);
    add(new JScrollPane(liUsuarios), BorderLayout.WEST);

    pack();
    setVisible(true);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    String[] usuarios = new String[]{};
    preencherListaUsuarios(usuarios);
  }

  /**
   * Preenche a lista de usuarios
   * @param usuarios
   */
  private void preencherListaUsuarios(String[] usuarios) {
    DefaultListModel modelo = new DefaultListModel();
    liUsuarios.setModel(modelo);
    for(String usuario: usuarios) {
      modelo.addElement(usuario);
    }
  }

  private void iniciarEscritor() {
    taEditor.addKeyListener(new KeyListener() {

      @Override
      public void keyTyped(KeyEvent e) {}

      @Override
      public void keyReleased(KeyEvent e) {}

      @Override
      public void keyPressed(KeyEvent e) {

        // validando a tecla apertadad
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {

          // validando se o visor ta vazio
          if(taVisor.getText().isEmpty()) {
            return;
          }

          // Pegando qual usuario está selecionada
          Object usuario = liUsuarios.getSelectedValue();

          // validando se o usuario é diferente null
          if(usuario != null) {
            // Colocando na tela
            taVisor.append("Eu: ");
            taVisor.append(taEditor.getText());
            taVisor.append("\n");

            // Escrevendo para o servidor
            writer.println(Commands.ENVIAR_MENSAGEM + usuario);
            writer.println(taEditor.getText());

            // Limpando o editor
            taEditor.setText("");
            e.consume();
          } else {
            // se usuario for null, validar se está querendo sair da aplicação
            if(taVisor.getText().equalsIgnoreCase(Commands.SAIR)) {
              System.exit(0);
            }

            // senão manda selecionar um usuario para enviar a mensagem
            JOptionPane.showMessageDialog(JMainFrame.this, "Selecione um usuario");
            return;
          }
        }
      }
    });
  }

  public void iniciarChat() {
    try {
      // Iniciando conexão com o server
      final Socket client = new Socket("127.0.0.1", 3322);

      // Instanciando writer e reader
      writer = new PrintWriter(client.getOutputStream(), true);
      reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

    } catch (UnknownHostException e) {
      System.out.println("O endereço passado é inválido");
      e.printStackTrace();
    } catch (IOException e) {
      System.out.println("o Servidor pode estar fora do ar");
      e.printStackTrace();
    }
  }

  private void iniciarLeitor() {
    try {
      while(true) {
        // Pegando a mensagem
        String msg = reader.readLine();

        // Validando ser está vazio
        if(msg == null || msg.isEmpty()) {
          continue;
        }

        // Rebecendo texto
        if(msg.equals(Commands.LISTA_USUARIOS)) {
          String[] usuarios = reader.readLine().split(",");
          preencherListaUsuarios(usuarios);

        } else if(msg.equals(Commands.LOGIN)) {
          String login = JOptionPane.showInputDialog("Qual o seu login?");
          writer.println(login);

        } else if(msg.equals(Commands.LOGIN_NEGADO)) {
          JOptionPane.showMessageDialog(JMainFrame.this, "O login é inválido");

        } else if(msg.equals(Commands.LOGIN_ACEITO)) {
          atualizarListaUsuarios();
        }
         else {
          taVisor.append(msg);
          taVisor.append("\n");
          taVisor.setCaretPosition(taVisor.getDocument().getLength());
        }
      }
    } catch (IOException e) {
      System.out.println("impossivel ler a mensagem do servidor");
      e.printStackTrace();
    }
  }

  private void atualizarListaUsuarios() {
    writer.println(Commands.LISTA_USUARIOS);
  }

  public static void main(String[] args) {
    final JMainFrame client = new JMainFrame();
    client.iniciarChat();
    client.iniciarEscritor();
    client.iniciarLeitor();
  }
}
