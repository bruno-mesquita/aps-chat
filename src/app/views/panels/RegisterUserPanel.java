package app.views.panels;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.Font;

public class RegisterUserPanel extends JPanel {

  private static final long serialVersionUID = -3596955310852000860L;

  /** Title que vai ficar na barra da janela */
  private String title;

  /** input para nome do usuarios */
  private JTextField nameField;

  /** input para  o email valido  */
  private JTextField emailField;

  /** senha */
  private JPasswordField passwordField;

  /** confirmação da senha */
  private JPasswordField passwordConfirmedField;

  /** Botão usado para efetuar a ação de registrar o user */
  private JButton registerButon;

  private ImageIcon logo;

  private JLabel logoLabel;

	public RegisterUserPanel() {
    title = "Registrar";
    // Instanciando Componentes
		registerButon = new JButton();
		nameField = new JTextField();
		emailField = new JTextField();
		passwordField = new JPasswordField();
    passwordConfirmedField = new JPasswordField();
    logoLabel = new JLabel();
    logo = new ImageIcon("src\\app\\assets\\images\\logo.png");

    // Configurações dos componentes
    ComponentsProps();

    // Configurações do Panel
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

  public JTextField getNameField() {
    return this.nameField;
  }

  public void setNameField(JTextField nameField) {
    this.nameField = nameField;
  }

  public JTextField getEmailField() {
    return this.emailField;
  }

  public void setEmailField(JTextField emailField) {
    this.emailField = emailField;
  }

  public JPasswordField getPasswordField() {
    return this.passwordField;
  }

  public void setPasswordField(JPasswordField passwordField) {
    this.passwordField = passwordField;
  }

  public JPasswordField getPasswordConfirmedField() {
    return this.passwordConfirmedField;
  }

  public void setPasswordConfirmedField(JPasswordField passwordConfirmedField) {
    this.passwordConfirmedField = passwordConfirmedField;
  }

  public JButton getRegisterButon() {
    return this.registerButon;
  }

  public void setRegisterButon(JButton registerButon) {
    this.registerButon = registerButon;
  }

  private void ComponentsProps() {
    LogoLabelProps();
    LogoProps();
    NameFieldProps();
    EmailFieldProps();
    PasswordFieldProps();
    PasswordConfirmedFieldProps();
    RegisterButonProps();
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

  private void RegisterButonProps() {
    registerButon.setText("Registrar");
    registerButon.setBounds(192, 596, 329, 51);
    registerButon.setFont(new Font("Tahoma", Font.PLAIN, 18));
  }

  private void PasswordConfirmedFieldProps() {
		passwordConfirmedField.setBounds(384, 463, 329, 51);
  }

  private void PasswordFieldProps() {
    passwordField.setBounds(29, 463, 329, 51);
  }

  private void EmailFieldProps() {
    emailField.setBounds(384, 377, 329, 51);
  }

  private void NameFieldProps() {
    nameField.setBounds(29, 377, 329, 51);
		nameField.setColumns(10);
  }

  /**
   * Adiciona os componentes no panel
   */
  private void addComponents() {
		add(nameField);
		add(emailField);
		add(passwordField);
		add(passwordConfirmedField);
		add(registerButon);
  }

  public String getFormRegister() {
    String name = nameField.getText();
    String email = emailField.getText();
    String password = String.valueOf(passwordField.getPassword());
    String passwordConfirmed = String.valueOf(passwordConfirmedField.getPassword());

    if(!password.equals(passwordConfirmed)) {
      JOptionPane.showMessageDialog(RegisterUserPanel.this, "Senhas diferentes");

      return "FALHA";
    }

    String form = name + "," + email + "," + password + "," + passwordConfirmed;

    return form;
  }
}
