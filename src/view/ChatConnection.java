package view;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.Controller;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class ChatConnection extends JFrame {

	//--- Propriétés ---
	private Controller controller;
	private JPanel contentPane;
	private JTextField txtIP;
	
	//--- Constructeur ---
	public ChatConnection(Controller controller){
		super();
		build();
		this.controller=controller;		
	}

	//--- Construction de la fenêtre ---
	private void build() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450, 300);
		setJMenuBar(createMenuBar());
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle("ACCUEIL");
		setContentPane(getJContentPane());		
	}

	//--- Construction du panel principal ---
	private JPanel getJContentPane() {		
		contentPane=new JPanel();
		contentPane.setLayout(null);		
		JLabel lblServer = new JLabel("D\u00E9marrer un serveur:");
		lblServer.setHorizontalAlignment(SwingConstants.LEFT);
		lblServer.setBounds(10, 11, 187, 36);
		contentPane.add(lblServer);		
		JButton btnServer = new JButton("D\u00E9marrer");
		btnServer.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {serverLaunch();}
			public void mouseEntered(MouseEvent e) {contentPane.setCursor(new Cursor(Cursor.HAND_CURSOR));}
			public void mouseExited(MouseEvent e) {contentPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));}
		});
		btnServer.setBounds(313, 7, 111, 44);
		contentPane.add(btnServer);		
		JLabel lblMessage = new JLabel("Se connecter \u00E0 un serveur existant:");
		lblMessage.setHorizontalAlignment(SwingConstants.LEFT);
		lblMessage.setBounds(10, 58, 202, 36);
		contentPane.add(lblMessage);		
		JLabel lblClient = new JLabel("IP serveur:");
		lblClient.setBounds(10, 105, 92, 36);
		contentPane.add(lblClient);		
		txtIP = new JTextField();
		txtIP.setText("127.0.0.1");
		txtIP.setBounds(126, 113, 86, 20);
		contentPane.add(txtIP);
		txtIP.setColumns(10);		
		JButton btnClient = new JButton("Se connecter");
		btnClient.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {clientLaunch();}
			public void mouseEntered(MouseEvent e) {contentPane.setCursor(new Cursor(Cursor.HAND_CURSOR));}
			public void mouseExited(MouseEvent e) {contentPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));}
		});
		btnClient.setBounds(313, 101, 111, 44);
		contentPane.add(btnClient);		
		JButton btnQuit = new JButton("Quitter");
		btnQuit.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {System.exit(0);}
			public void mouseEntered(MouseEvent e) {contentPane.setCursor(new Cursor(Cursor.HAND_CURSOR));}
			public void mouseExited(MouseEvent e) {contentPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));}
		});
		btnQuit.setBounds(313, 185, 111, 44);
		contentPane.add(btnQuit);		
		return contentPane;
	}
	
	//--- Création de la barre de menu ---
	private JMenuBar createMenuBar(){
		JMenuBar menuBar = new JMenuBar();		
		JMenu mnFile = new JMenu("Fichier");
		menuBar.add(mnFile);		
		JMenuItem mntmServer = new JMenuItem("D\u00E9marrer un serveur");
		mntmServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {serverLaunch();}
		});
		mnFile.add(mntmServer);		
		JMenuItem mntmClient = new JMenuItem("Se connecter \u00E0 un serveur");
		mntmClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {clientLaunch();}
		});
		mnFile.add(mntmClient);		
		JMenuItem mntmQuit = new JMenuItem("Quitter");
		mntmQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {System.exit(0);}
		});
		mnFile.add(mntmQuit);		
		JMenu mnOther = new JMenu("?");
		menuBar.add(mnOther);		
		JMenuItem mntmHelp = new JMenuItem("Aide");
		mntmHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {JOptionPane.showMessageDialog(null, "Serveur:\nCliquez sur le bouton \"Démarrer\" pour lancer un nouveau serveur.\n\nClient:\nEntrez l'adresse ip du serveur pour vous y connecter et cliquez\nsur \"Se connecter\".");}
		});
		mnOther.add(mntmHelp);		
		JMenuItem mntmAbout = new JMenuItem("A propos");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {JOptionPane.showMessageDialog(null, "JIM - Java Instant Messenger\n\nAuteur: Simon Le Bras (2012)\nLogiciel de messagerie instantanée");}
		});
		mnOther.add(mntmAbout);		
		return menuBar;
	}
	
	//--- Lancement d'un serveur ---
	private void serverLaunch () {controller.viewEvent(this, "server");}
	
	//--- Lancement d'un client ---
	private void clientLaunch () {controller.viewEvent(this, txtIP.getText());}
	
}