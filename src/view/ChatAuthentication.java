package view;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import controller.Controller;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

@SuppressWarnings("serial")
public class ChatAuthentication extends JFrame {

	//--- Propriétés ---
	private Controller controller;
	private JPanel contentPane;
	private JTextField txtPseudo;
	private JTextField txtImage;
	private String type;
	private String url="";
	
	//--- Constructeur ---
	public ChatAuthentication(Controller controller,String type) {
		super();
		build();
		this.type=type;
		this.controller=controller;
	}

	//--- Construction de la fenêtre ---
	private void build() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450, 300);
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle("CONNEXION");
		setContentPane(getJContentPane());	
	}

	//--- Construction du panel principal ---
	private JPanel getJContentPane() {
		contentPane=new JPanel();
		contentPane.setLayout(null);		
		JButton btnBack = new JButton("");
		btnBack.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {btnBack_click();}
			public void mouseEntered(MouseEvent e) {contentPane.setCursor(new Cursor(Cursor.HAND_CURSOR));}
			public void mouseExited(MouseEvent e) {contentPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));}
		});
		btnBack.setBounds(10, 11, 24, 24);
		btnBack.setIcon(new ImageIcon("media/images/back.png"));
		contentPane.add(btnBack);		
		JLabel lblPseudo = new JLabel("Saisissez votre pseudo:");
		lblPseudo.setBounds(78, 40, 138, 20);
		contentPane.add(lblPseudo);		
		txtPseudo = new JTextField();
		txtPseudo.setBounds(246, 40, 126, 20);
		contentPane.add(txtPseudo);		
		JButton btnImage = new JButton("Choisir une image");
		btnImage.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JFileChooser dialog=new JFileChooser("media/images");
				int ok = dialog.showOpenDialog(null);
				if (ok == JFileChooser.APPROVE_OPTION) {
					url=dialog.getSelectedFile().toString();
					if(validExtension(url)&& (new File(url).exists())){
						txtImage.setText(url);
						url="media/images/"+dialog.getSelectedFile().getName();
					}else{
						url="";
						JOptionPane.showMessageDialog(null, "Extension invalide(JPG, PNG ou GIF) et/ou l'image spécifiée n'existe pas");
					}
				}
			}
			public void mouseEntered(MouseEvent e) {contentPane.setCursor(new Cursor(Cursor.HAND_CURSOR));}
			public void mouseExited(MouseEvent e) {contentPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));}
		});
		btnImage.setBounds(78, 99, 138, 20);
		contentPane.add(btnImage);		
		txtImage = new JTextField();
		txtImage.setBounds(246, 99, 126, 20);
		txtImage.setFocusable(false);
		contentPane.add(txtImage);
		txtImage.setColumns(10);		
		JButton btnSend = new JButton("Envoyer");
		btnSend.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {btnSend_click();}
			public void mouseEntered(MouseEvent e) {contentPane.setCursor(new Cursor(Cursor.HAND_CURSOR));}
			public void mouseExited(MouseEvent e) {contentPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));}
		});
		btnSend.setBounds(156, 162, 138, 20);
		contentPane.add(btnSend);		
		return contentPane;
	}
		
	//--- Vérification de l'extension de l'image ---
	private boolean validExtension(String url){
		if(url.endsWith(".jpg")||url.endsWith(".jpeg")||url.endsWith(".png")||url.endsWith(".gif")){
			return true;
		}else{
			return false;
		}		
	}
	
	//--- Clic sur le bouton "Retour" ---
	private void btnBack_click(){controller.viewEvent(this, "back");}
	
	//--- Clic sur le bouton "Envoyer" ---
	private void btnSend_click() {
		if(txtPseudo.getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "Veuillez saisir un pseudo!");
		}else{
			if(url.equals("")){
				url="media/images/anonymous.gif";
			}
			if(type.equals("server")){
				controller.viewEvent(this, "serverStart"+"~"+txtPseudo.getText()+"(ADMIN)"+"~"+url);
			}else{
				controller.viewEvent(this, "clientStart"+"~"+txtPseudo.getText()+"~"+url);
			}
		}		
	}
	
}
