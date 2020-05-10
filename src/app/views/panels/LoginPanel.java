package app.views.panels;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;

public class LoginPanel extends JPanel {

  private static final long serialVersionUID = -7726636143889750790L;

  /** Title que vai ficar na barra da janela */
  private String title;

  private Dimension dimension;

  /** Nome do usuario cadastrado */
  private JTextField emailField;

  /** Senha do usuario cadastrado */
  private JPasswordField passwordField;

  /** Botão - realiza a ação de login */
  private JButton loginButton;

  /** Botão - Realiza a ação encaminhar o usuario para a tela de cadastro */
  private JButton registerButton;

  private ImageIcon logo;

  private JLabel logoLabel;

  public LoginPanel() throws IOException {
    // Instanciando Componentes
    title = "Login";
    passwordField = new JPasswordField();
    emailField = new JTextField();
    loginButton = new JButton("Login");
    registerButton = new JButton("Registar");
    logoLabel = new JLabel();
    logo = new ImageIcon("src\\app\\assets\\images\\logo.png");


    // inicinando configurações dos componentes
    componentsProps();

    // Propriedades do Panel
    setBackground(Color.WHITE);
    setBorder(new EmptyBorder(5, 5, 5, 5));
    setBounds(100, 100, 770, 867);
    setLayout(null);
		addComponents();
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public JTextField getemailField() {
    return this.emailField;
  }

  public void setemailField(JTextField emailField) {
    this.emailField = emailField;
  }

  public JPasswordField getPasswordField() {
    return this.passwordField;
  }

  public void setPasswordField(JPasswordField passwordField) {
    this.passwordField = passwordField;
  }

  public JButton getLoginButton() {
    return this.loginButton;
  }

  public void setLoginButton(JButton loginButton) {
    this.loginButton = loginButton;
  }

  public JButton getRegisterButton() {
    return this.registerButton;
  }

  public void setRegisterButton(JButton registerButton) {
    this.registerButton = registerButton;
  }

  public Dimension getDimension() {
    return dimension;
  }

  public void setDimension(Dimension dimension) {
    this.dimension = dimension;
  }

  private void componentsProps() {
    LogoLabelProps();
    LogoProps();
    emailFieldProps();
    passwordFieldProps();
    loginButtonProps();
    registerButtonProps();
  }

  /**
   * configurando as propriedades do LogoLabel
   */
  private void LogoLabelProps() {
    logoLabel.setIcon(logo);
		logoLabel.setBounds(192, 60, 329, 258);
  }

  /**
   * configurando as propriedades do Logo
   */
  private void LogoProps() {
    logo.setImage(logo.getImage().getScaledInstance(logoLabel.getWidth(), logoLabel.getHeight(), 1));
  }

  /**
   * configurando as propriedades do RegisterButton
   */
  private void registerButtonProps() {
		registerButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		registerButton.setBounds(392, 664, 129, 30);
  }

  /**
   * configurando as propriedades do LoginButton
   */
  private void loginButtonProps() {
    loginButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		loginButton.setBounds(192, 581, 329, 40);
  }

  /**
   * configurando as propriedades do emailField
   */
  private void emailFieldProps() {
    emailField.setText("Username");
    emailField.setBounds(192, 379, 329, 40);
		emailField.setColumns(10);
  }

  /**
   * configurando as propriedades do PasswordField
   */
  private void passwordFieldProps() {
    passwordField.setBounds(192, 469, 329, 40);
  }

  /**
   * Adiciona os componentes no Panel principal
   */
  private void addComponents() {
    add(logoLabel);
    add(emailField);
		add(passwordField);
    add(loginButton);
    add(registerButton);
  }

  public String getLogin() {
    String email = emailField.getText();
    String password = String.valueOf(passwordField.getPassword());

    return email /* + ";" + password */;
  }
}
