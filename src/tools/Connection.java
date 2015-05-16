package tools;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import controller.Controller;

public class Connection extends Thread {

	//--- Propriétés ---
	private Object controller ;
	private ObjectInputStream in ;
	private ObjectOutputStream out ;
	
	//--- Constructeur ---
	public Connection (Socket socket, Object controller) {
		this.controller = controller ;
		try {
			this.out = new ObjectOutputStream(socket.getOutputStream()) ;
		} catch (IOException e) {
			System.out.println("Erreur création canal out"+e) ;
			System.exit(0) ;
		}
		try {
			this.in = new ObjectInputStream(socket.getInputStream()) ;
		} catch (IOException e) {
			System.out.println("Erreur création canal in"+e) ;
			System.exit(0) ;
		}		
		super.start() ;
		((controller.Controller)controller).setConnection(this);
	}
	
	//--- Ecoute des messages en provenance de l'ordinateur distant ---
	public void run() {
		boolean inOk = true ;
		Object reception ;
		while (inOk) {
			try {
				reception = this.in.readObject() ;
				((Controller)controller).getChat().reception(this, reception);
			} catch (IOException e) {
				inOk = false ;
				((Controller)controller).getChat().deconnection(this);
				try {
					this.in.close() ;
				} catch (IOException e1) {
					System.out.println("Erreur de fermeture du canal in :"+e) ;
				}
			} catch (ClassNotFoundException e) {
				System.out.println("Erreur de réception sur le type d'objet :"+e) ;
				System.exit(0) ;
			}
		}
	}
	
	//--- Envoi d'un message à destination de l'ordinateur distant ---
	public void send (Object object) {
		try {
			this.out.reset();
			this.out.writeObject(object) ;
			this.out.flush() ;
		} catch (IOException e) {
			System.out.println("Erreur canal out :"+e) ;
		}
	}
	
}
