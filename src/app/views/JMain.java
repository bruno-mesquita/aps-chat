package app.views;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import app.utils.Commands;
import app.views.panels.*;

public class JMain extends JFrame {

  private static final long serialVersionUID = 2443014596875140533L;
  private PrintWriter writer;
  private BufferedReader reader;
  private LoginPanel telaLogin;
  private RegisterUserPanel telaRegister;
  private ChatPanel telaChat;
  private String[] users = new String[] {};

  public JMain() throws IOException {
    // instanciando components
    telaLogin = new LoginPanel();
    telaRegister = new RegisterUserPanel();
    telaChat = new ChatPanel();



    // Config Frame
    setTitle(telaLogin.getTitle());
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setContentPane(telaLogin);
    pack();
    setVisible(true);

    initChat();
    addListeners();
    initReader();

    String[] users = new String[]{"Breno", "Mateus"};
    fillListUsers(users);
  }

  public PrintWriter getWriter() {
    return this.writer;
  }

  public void setWriter(PrintWriter writer) {
    this.writer = writer;
  }

  public BufferedReader getReader() {
    return this.reader;
  }

  public void setReader(BufferedReader reader) {
    this.reader = reader;
  }

  public LoginPanel getTelaLogin() {
    return this.telaLogin;
  }

  public void setTelaLogin(LoginPanel telaLogin) {
    this.telaLogin = telaLogin;
  }

  public RegisterUserPanel getTelaRegister() {
    return this.telaRegister;
  }

  public void setTelaRegister(RegisterUserPanel telaRegister) {
    this.telaRegister = telaRegister;
  }

  public ChatPanel getTelaChat() {
    return this.telaChat;
  }

  public void setTelaChat(ChatPanel telaChat) {
    this.telaChat = telaChat;
  }

  /**
   * Muda a tela da Janela principal
   *
   * @param panelAtual a tela que está em visivel atualmente
   * @param panelNext  a tela que deseja passar
   */
  public void changePanel(JPanel panelAtual, JPanel panelNext) {
    panelAtual.setVisible(false);

    panelNext.setVisible(true);
    setContentPane(panelNext);
  }

  /**
   * Pega todos os eventos e adiciona
   */
  public void addListeners() {
    ChangeToRegisterPanel();
    submitFormRegister();
    submitLogin();
    sendMessageListener();
  }

  public void searchOneUser(String name) {
    // Procurar esse user
  }

  /**
   * Faz a transição quando o user clica em uma outra conversa
   *
   * @param user o user da conversa destino
   */
  public void changeChat(String user) {
    /**
     * Pegar o usuario selecionado Buscar as mensagens no DB Mudar a conversa com as
     * novas mensagens
     */
  }

  /**
   * Preenche a lista de usuarios
   * @param users
   */
  public void fillListUsers(String[] users) {
    DefaultListModel model = new DefaultListModel();
    telaChat.getListUsers().setModel(model);

    for (String user : users) {
      model.addElement(user);
    }
  }

  public void updateListUsers() {
    writer.println(Commands.LIST_USERS);
  }

  /**
   * Inicia o chat, a socket em vi, levando em consideração de o proprio Swing é
   * uma Thread, então cada tela é uma conexão diferente
   */
  public void initChat() {
    try {
      // Iniciando conexão com o servidor
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

  public void initReader() {
    try {
      while(true) {
      // Pegando a mensagem
      String msg = reader.readLine();

      // Validando a está vazio ou se é null
      if(msg == null || msg.isEmpty()) {
        continue;
      }

      if(msg.equals(Commands.LIST_USERS)) {
        String[] users = reader.readLine().split(",");
        fillListUsers(users);
      }
      else if(msg.equals(Commands.LOGIN)) {
        return;
      }
      else if(msg.equals(Commands.LOGIN_NEGADO)) {
        JOptionPane.showMessageDialog(JMain.this, "O login é inválido");
      }
      else if(msg.equals(Commands.LOGIN_ACEITO)) {
        updateListUsers();
      }
      else {
        telaChat.getSightArea().append(msg);
        telaChat.getSightArea().append("\n");
        telaChat.getSightArea().setCaretPosition(telaChat.getSightArea().getDocument().getLength());
      }
      }
    } catch(IOException e) {
      System.out.println("impossivel ler a mensagem do servidor");
      e.printStackTrace();
    }
  }

  public void ChangeToRegisterPanel() {
    telaLogin.getRegisterButton().addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        changePanel(telaLogin, telaRegister);
      }
    });
  }

  public void submitFormRegister() {
    telaRegister.getRegisterButon().addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        System.out.println(telaRegister.getFormRegister());
      }
    });
  }

  public void submitLogin() {
    telaLogin.getLoginButton().addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        String login = telaLogin.getLogin();

        writer.println(login);

        changePanel(telaLogin, telaChat);
      }
    });
  }

  public void sendMessageListener() {
    telaChat.getMessageArea().addKeyListener(new KeyListener() {

      @Override
      public void keyTyped(KeyEvent e) {}

      @Override
      public void keyReleased(KeyEvent e) {}

      @Override
      public void keyPressed(KeyEvent e) {

        // validando a tecla apertadad
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
          // validando se o visor ta vazio
          if(telaChat.getMessageArea().getText().isEmpty()) {
            return;
          }

          // Pegando qual usuario está selecionada
          Object user = telaChat.getListUsers().getSelectedValue();

          // validando se o usuario é diferente null
          if(user != null) {
            // Colocando na tela
            telaChat.getSightArea().append("Eu: ");
            telaChat.getSightArea().append(telaChat.getMessageArea().getText());
            telaChat.getSightArea().append("\n");

            // Escrevendo para o servidor
            writer.println(Commands.ENVIAR_MENSAGEM + user);
            writer.println(telaChat.getMessageArea().getText());

            // Limpando o editor
            telaChat.getMessageArea().setText("");
            e.consume();
          } else {
            // se usuario for null, validar se está querendo sair da aplicação
            if(telaChat.getSightArea().getText().equalsIgnoreCase(Commands.SAIR)) {
              System.exit(0);
            }

            // senão manda selecionar um usuario para enviar a mensagem
            JOptionPane.showMessageDialog(JMain.this, "Selecione um usuario");
            return;
          }
        }
      }
    });
  }

  public static void main(String[] args) throws IOException {
    JMain janela = new JMain();
  }
}
