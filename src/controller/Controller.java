package controller;

import java.util.ArrayList;

import model.Chat;
import model.ChatClient;
import model.ChatServer;
import tools.Connection;
import tools.SocketClient;
import tools.SocketServer;
import view.ChatAuthentication;
import view.ChatConnection;
import view.ChatView;

public class Controller {

	//--- Propriétés ---
	private ChatConnection frmConnection;
	private ChatView frmView;
	private ChatAuthentication frmAuthentication;
	private Chat chat;
	private final int PORT=6666;
	private String ip;
	private Connection connection;
	
	//--- Getter ---
	public Chat getChat () {return chat;}
	
	//--- Setter ---
	public void setConnection(Connection connection){
		this.connection=connection;
		if(chat instanceof ChatServer){
			chat.setConnection(connection);
		}
	}
	
	//--- Constructeur ---
	public Controller (){
		frmConnection=new ChatConnection(this);
		frmConnection.setVisible(true);
	}
	
	//--- Main ---
	public static void main (String[] args) {new Controller();}
	
	//****************************************************************************
	// Evénements provenant de la vue
	//****************************************************************************
	public void viewEvent(Object view,Object information){
		if(view instanceof ChatConnection){chatConnectionEvent(information);}
		if(view instanceof ChatAuthentication){chatAuthenticationEvent(information);}
		if(view instanceof ChatView ){chatViewEvent(information);}
	}
	
	//--- Réception d'un evenement provenant de ChatConnection ---
	private void chatConnectionEvent(Object information) {
		if((String)information=="server"){
			frmConnection.dispose();
			frmAuthentication=new ChatAuthentication(this,"server");
			frmAuthentication.setVisible(true);
		}else{
			ip=(String)information;
			frmConnection.dispose();
			frmAuthentication=new ChatAuthentication(this,"client");
			frmAuthentication.setVisible(true);			
		}
	}
	
	//--- Réception d'un evenement provenant de ChatAuthentication ---
	private void chatAuthenticationEvent(Object information) {
		String[] informationComponents=((String)information).split("~");
		String order=informationComponents[0];
		if(order.equals("back")){
			frmAuthentication.dispose();
			frmConnection=new ChatConnection(this);
			frmConnection.setVisible(true);
		}else{
			String pseudo=informationComponents[1];
			String url=informationComponents[2];
			if(order.equals("serverStart")){
				new SocketServer(PORT,this);
				chat=new ChatServer(this,pseudo+"~"+url);				
				frmAuthentication.dispose();
				frmView=new ChatView(this,pseudo,url,"server");
				frmView.setVisible(true);
			}
			if(order.equals("clientStart")){
				if(new SocketClient(ip, PORT, this).getConnectionOK()){
					chat=new ChatClient(this);					
					chat.setConnection(connection);
					frmView=new ChatView(this,pseudo,url,"client");
					((ChatClient)chat).send("ADD"+"~"+pseudo+"~"+url);
					frmAuthentication.dispose();					
					frmView.setVisible(true);
				}
			}
		}		
	}
	
	//--- Réception d'un evenement provenant de ChatView ---
	private void chatViewEvent(Object information) {
		if(chat instanceof ChatClient){
			((ChatClient)chat).send("MESSAGE"+"~"+information);
		}else{
			((ChatServer) chat).sendMessage((String) information);
		}
		
	}

	//****************************************************************************
	// Evénements provenant du modèle
	//****************************************************************************
	public void modelEvent(Object chat, String order, Object information){
		if(chat instanceof ChatServer){
			chatServerEvent(order,information);
		}else{
			chatClientEvent(order,information);
		}
	}

	//--- Réception d'un evenement provenant de ChatClient ---
	@SuppressWarnings("unchecked")
	private void chatClientEvent(String order,Object information) {
		if(order.equals("updatePanel")){
			frmView.updatePanel((ArrayList<String>)information);
		}
		if(order.equals("updateConversation")){
			frmView.updateConversation((String)information);
		}
	}

	//--- Réception d'un evenement provenant de ChatServer ---
	@SuppressWarnings("unchecked")
	private void chatServerEvent(String order, Object information) {
		if(order.equals("updateConversation")){
			frmView.updateConversation((String)information);
		}
		if(order.equals("updatePanel")){
			frmView.updatePanel((ArrayList<String>)information);
		}
	}
	
}
