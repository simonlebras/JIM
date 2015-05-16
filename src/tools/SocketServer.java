package tools;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer extends Thread {
	
	//--- Propriétés ---
	private Object controller ;
	private ServerSocket serverSocket ;
	
	//--- Constructeur ---
	public SocketServer (int port, Object controller) {
		this.controller = controller ;
		try {
			serverSocket = new ServerSocket(port) ;
		} catch (IOException e) {
			System.out.println("Erreur creation serverSocket: "+e) ;
			System.exit(0) ;
		}
		super.start();
	}
	
	//--- Thread à l'écoute de la connexion d'un nouveau client ---
	public void run() {
		Socket socket ;
		while (true) {
			try {
				socket = serverSocket.accept() ;
				System.out.println("Un nouveau client vient de se connecter") ;
				new Connection(socket, controller) ;
			} catch (IOException e) {
				System.out.println("Erreur sur l'attente d'un client"+e) ;
				System.exit(0) ;
			}
		}
	}

}
