package app.views.panels;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JList;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ChatPanel extends JPanel {

  private static final long serialVersionUID = 1L;

  /** Title que vai ficar na barra da janela */
  private String title;

  /** Input para procurar um usuario / conversa especifica */
  private JTextField searchField;

  /** Panel - Aonde vai ser mostrado a conversa selecionada */
  private JPanel conversationPanel;

  /** Panel - aonde vai ficar os contatos  */
  private JPanel contactsPanel;

  /** Componente de lista aonde fica as conversas desse usuario */
  private JList listConversations;

  /** Componente de lista aonde fica os amigos desse usuario */
  private JList<String> listUsers;

  private JTextArea messageArea;

  private JTextArea sightArea;

  private JScrollPane scrollSightArea;

  private JScrollPane scrollContacts;

  public ChatPanel() {
    title = "Chat";
    // Instanciando Componentes
		conversationPanel = new JPanel();
		contactsPanel = new JPanel();
    listUsers = new JList();
		listConversations = new JList();
		messageArea = new JTextArea();
    searchField = new JTextField();
    sightArea = new JTextArea();
    scrollSightArea = new JScrollPane(sightArea);
    scrollContacts = new JScrollPane(listUsers);

    // Configurações dos componentes
    ComponentsProps();

    // Comfigurações do panel
    setBounds(100, 100, 1246, 846);
    setBorder(new EmptyBorder(5, 5, 5, 5));
    setLayout(null);
    addComponents();
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public JTextField getSearchField() {
    return this.searchField;
  }

  public void setSearchField(JTextField search) {
    this.searchField = search;
  }

  public JPanel getConversationPanel() {
    return this.conversationPanel;
  }

  public void setConversationPanel(JPanel conversationPanel) {
    this.conversationPanel = conversationPanel;
  }

  public JPanel getContactsPanel() {
    return this.contactsPanel;
  }

  public void setContactsPanel(JPanel contactsPanel) {
    this.contactsPanel = contactsPanel;
  }

  public JList getListConversations() {
    return this.listConversations;
  }

  public void setListConversations(JList listConversations) {
    this.listConversations = listConversations;
  }

  public JTextArea getMessageArea() {
    return this.messageArea;
  }

  public void setSightArea(JTextArea sightArea) {
    this.sightArea = sightArea;
  }

  public JTextArea getSightArea() {
    return this.sightArea;
  }

  public void setMessageArea(JTextArea messageArea) {
    this.messageArea = messageArea;
  }

  public JList getListUsers() {
    return this.listUsers;
  }

  public void setListUsers(JList listUsers) {
    this.listUsers = listUsers;
  }

  private void ComponentsProps() {
    listConversationsProps();
    messageAreaProps();
    searchFieldProps();
    sightAreaProps();
    listUsersProps();
    scrollSightAreaProps();
    scrollContactsProps();

    conversationPanelProps();
    contactsPanelProps();
  }

  private void searchFieldProps() {
		searchField = new JTextField();
		searchField.setBounds(23, 11, 254, 47);
		searchField.setColumns(10);
  }

  private void messageAreaProps() {
    messageArea.setBounds(0, 735, 898, 52);
  }

  private void scrollSightAreaProps() {
    scrollSightArea.setBounds(10, 11, 878, 703);
  }

  private void scrollContactsProps() {
    scrollContacts.setBounds(23, 117, 254, 620);
  }

  private void sightAreaProps() {
    sightArea.setBounds(10, 11, 878, 703);
    sightArea.setEditable(false);
  }

  private void listUsersProps() {
    listUsers.setBounds(23, 117, 254, 620);
  }

  private void contactsPanelProps() {
    contactsPanel.setBounds(10, 11, 304, 787);
    contactsPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
    contactsPanel.setLayout(null);
  }

  private void conversationPanelProps() {
    conversationPanel.setBounds(324, 11, 898, 787);
    conversationPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		conversationPanel.setLayout(null);
  }

  private void listConversationsProps() {
		listConversations.setBounds(0, 0, 110, 215);
  }

  /**
   * Pega todos os componentes e add ao panel
   */
  private void addComponents() {
    add(conversationPanel);
    add(contactsPanel);

    contactsPanel.add(searchField);
    contactsPanel.add(scrollContacts);

    conversationPanel.add(scrollSightArea);
    conversationPanel.add(messageArea);
  }
}
