package model;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import controller.Controller;
import tools.Connection;

public class ChatClient extends Chat {

	//--- Propriété ---
	private Connection connection;
	
	//--- Contructeur ---
	public ChatClient(Controller controller){this.controller=controller;}

	//--- Ajout de l'objet permettant de communiquer avec le serveur ---
	public void setConnection(Connection connection) {this.connection=connection;}

	//--- Reception d'un message en provenance du serveur ---
	public void reception(Connection connection, Object object) {
		if(object instanceof ArrayList){
			controller.modelEvent(this, "updatePanel", object);
		}
		if(object instanceof String){
			controller.modelEvent(this, "updateConversation", object);
		}		
	}

	//--- Deconnection du serveur ---
	public void deconnection(Connection connection) {
		JOptionPane.showMessageDialog(null, "Le serveur s'est arrêté. Veuillez vous connecter à un nouveau serveur.");
		System.exit(0);
	}
	
	//--- Envoi d'un message au serveur ---
	public void send(Object object){connection.send(object);}

}
