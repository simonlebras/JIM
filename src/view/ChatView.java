package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import controller.Controller;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;

import model.ChatServer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import tools.ImageTools;
import tools.Son;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class ChatView extends JFrame {

	//--- Propriétés ---
	private Controller controller;
	private JPanel contentPane;
	private JPanel jClients;
	private String pseudo;
	private String url;
	private JTextArea txtMessage;
	private JScrollPane spAvatar;
	private JTextArea txtConversation;
	private String type;
	private Son message;
	
	//--- Constructeur ---
	public ChatView(Controller controller,String pseudo,String url,String type){
		super();
		this.controller=controller;
		this.pseudo=pseudo;
		this.url=url;
		this.type=type;
		build();
		message=new Son("media/sounds/message.wav");
		if(type.equals("server")){
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent arg0) {
					try {
						ChatServer.file.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	//--- Construction de la fenêtre ---
	private void build() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 700);
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle("JIM");
		setContentPane(getJContentPane());
	}

	//--- Construction du panel principal ---
	private JPanel getJContentPane() {
		contentPane=new JPanel();
		contentPane.setLayout(null);		
		JScrollPane spConversation = new JScrollPane();
		spConversation.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		spConversation.setBounds(10, 11, 820, 361);
		contentPane.add(spConversation);		
		txtConversation = new JTextArea();
		txtConversation.setFocusable(false);
		spConversation.setViewportView(txtConversation);		
		JScrollPane spMessage = new JScrollPane();
		spMessage.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		spMessage.setBounds(10, 436, 820, 172);
		contentPane.add(spMessage);		
		txtMessage = new JTextArea();
		spMessage.setViewportView(txtMessage);		
		JButton btnSend = new JButton("Envoyer");
		btnSend.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {btnSend_click();}
			public void mouseEntered(MouseEvent e) {contentPane.setCursor(new Cursor(Cursor.HAND_CURSOR));}
			public void mouseExited(MouseEvent e) {contentPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));}
		});
		btnSend.setBounds(743, 627, 89, 32);
		contentPane.add(btnSend);		
		JLabel lblAvatar = new JLabel("");
		lblAvatar.setBounds(884, 436, 100, 100);
		ImageIcon icon = new ImageIcon(url);
		Image imageScaled = ImageTools.scaleImage(icon.getImage(), 100);
		lblAvatar.setIcon(new ImageIcon(imageScaled));		
		contentPane.add(lblAvatar);		
		JLabel lblPseudo = new JLabel("");
		lblPseudo.setForeground(Color.BLACK);
		lblPseudo.setBounds(884, 547, 100, 16);
		lblPseudo.setText(pseudo);
		contentPane.add(lblPseudo);		
		if(type.equals("server")){
			jClients=new JPanel();
			jClients.setMinimumSize(new Dimension(100, 361));
			jClients.setLayout(new BoxLayout(jClients, BoxLayout.PAGE_AXIS));			
			JLabel adminAvatar=new JLabel();
			adminAvatar.setMaximumSize(new Dimension(100, 100));
			adminAvatar.setPreferredSize(new Dimension(100, 100));
			adminAvatar.setIcon(new ImageIcon(imageScaled));
			jClients.add(adminAvatar);	
			JLabel adminPseudo=new JLabel();
			adminPseudo.setMaximumSize(new Dimension(100, 16));
			adminPseudo.setPreferredSize(new Dimension(100, 16));
			adminPseudo.setText(pseudo);
			jClients.add(adminPseudo);			
			spAvatar = new JScrollPane();
			spAvatar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			spAvatar.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			spAvatar.setBounds(875, 11, 119, 361);
			spAvatar.setViewportView(jClients);
			contentPane.add(spAvatar);
		}else{
			jClients=new JPanel();
			jClients.setMinimumSize(new Dimension(100, 361));
			jClients.setLayout(new BoxLayout(jClients, BoxLayout.PAGE_AXIS));
			spAvatar = new JScrollPane();
			spAvatar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			spAvatar.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			spAvatar.setBounds(875, 11, 119, 361);
			spAvatar.setViewportView(jClients);
			contentPane.add(spAvatar);
		}
		return contentPane;
	}
	
	//--- Clic sur le bouton "Envoyer" ---
	private void btnSend_click() {
		if(!txtMessage.getText().isEmpty()){
			controller.viewEvent(this, pseudo+"~"+txtMessage.getText());
			txtMessage.setText("");
			message.play();
		}
		txtMessage.requestFocus();
	}

	//--- Rafraichissement de la conversation ---
	public void updateConversation(String conversation) {
		message.play();
		txtConversation.setText(txtConversation.getText()+conversation);		
	}
	
	//--- Modification du panel des utilisateurs connectés ---
	public void updatePanel(ArrayList<String> users) {
		jClients.removeAll();
		for(int i=0;i<users.size();i+=2){
			JLabel lblPseudo=new JLabel();
			lblPseudo.setMaximumSize(new Dimension(100, 16));
			lblPseudo.setPreferredSize(new Dimension(100, 16));
			lblPseudo.setText((users.get(i).split("~"))[0]);
			JLabel lblAvatar=new JLabel();
			lblAvatar.setMaximumSize(new Dimension(100, 100));
			lblAvatar.setPreferredSize(new Dimension(100, 100));
			ImageIcon icon = new ImageIcon((users.get(i+1).split("~"))[0]);
			Image imageScaled = ImageTools.scaleImage(icon.getImage(), 100);
			lblAvatar.setIcon(new ImageIcon(imageScaled));
			jClients.add(lblAvatar);
			jClients.add(lblPseudo);
		}
		jClients.revalidate();
		jClients.repaint();
	}
}
