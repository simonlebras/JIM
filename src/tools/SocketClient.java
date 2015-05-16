package tools;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class SocketClient {

	//--- Propriétés ---
	private boolean connectionOK ;
		
	//--- Getter ---
	public boolean getConnectionOK () {return connectionOK ;}

	//--- Constructeur ---
	public SocketClient (String ip, int port, Object controller) {
		connectionOK = false ;
		Socket socket;
		try {
			socket = new Socket (ip, port) ;
			System.out.println("Connexion au serveur réussie") ;
			connectionOK = true ;
			new Connection(socket, controller) ;				
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "Serveur indisponible") ;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Problème de connexion") ;
		}
	}		
		
}
