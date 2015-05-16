package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tools.Connection;
import controller.Controller;

public class ChatServer extends Chat {
	
	//---Propriétés ---
	private String text;
	public static  FileWriter file;
	private Hashtable<Connection, Client> clients=new Hashtable<Connection, Client>();
	public static ArrayList<String> components=new ArrayList<String>();
	
	//--- Constructeur ---
	public ChatServer(Controller controller,String information){
		this.controller=controller;
		String[] informationComponents=information.split("~");
		String pseudo=informationComponents[0];
		String url=informationComponents[1];
		components.add(pseudo);
		components.add(url);		
		File newFile=null;
		int i=0;
		while(new File("conversation/"+i+".txt").exists()){
			i++;
		}
		newFile=new File("conversation/"+i+".txt");
		try {
			newFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			file=new FileWriter(newFile, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//--- Ajout du nouveau client dans le dictionnaire ---
	public void setConnection(Connection connection) {clients.put(connection, new Client());}

	//--- Reception d'un message d'un client ---
	public void reception(Connection connection, Object information) {	
		String[]informationComponents=((String)information).split("~");
		String order=informationComponents[0];
		if(order.equals("ADD")){
			String pseudo=informationComponents[1];
			String url=informationComponents[2];
			sendMessage("#####"+pseudo+" vient de se connecter#####\n\n",connection);
			Client newClient=clients.get(connection);
			newClient.initClient(pseudo, url);
			components.add(clients.get(connection).getPseudo());
			components.add(clients.get(connection).getUrl());
			controller.modelEvent(this,"updatePanel", components);
			for(Connection key : clients.keySet()){
				super.send(key, components);
			}			
		}		
		if(order.equals("MESSAGE")){
			this.sendMessage(informationComponents[1]+"~"+informationComponents[2]);
		}
	}

	//--- Déconnection d'un client ---
	public void deconnection(Connection connection) {
		String pseudo=clients.get(connection).getPseudo();
		components.remove(pseudo);
		components.remove(clients.get(connection).getUrl());
		clients.remove(connection);		
		sendMessage("#####"+(pseudo.split("~"))[0]+" vient de se déconnecter#####\n\n"+"~"+" ");
		controller.modelEvent(this, "updatePanel", components);
		for(Connection key : clients.keySet()){
			super.send(key, components);
		}
	}
	
	//--- Envoi du message au serveur et aux clients ---
	public void sendMessage(String information){
		String[] informationComponents=((String)information).split("~");
		String pseudo =informationComponents[0];
		String message=informationComponents[1];
		Pattern pattern = Pattern.compile("^@(.*) (.*)$");
		Matcher m = pattern.matcher(message);
		if (m.matches()) {
			text=pseudo+" dit :\n"+m.group(2)+"\n\n";
			if(m.group(1).endsWith("(ADMIN)")){
				controller.modelEvent(this, "updateConversation", text);
			}
			for(Connection key:clients.keySet()){
				if(m.group(1).equals((clients.get(key).getPseudo().split("~"))[0])){
					super.send(key, text);
				}
			}
		}else{
			text=pseudo+" dit :\n"+message+"\n\n";
			if(pseudo.startsWith("#####")){
				text=pseudo;
			}
			try {
				file.write(text);
			} catch (IOException e) {
				e.printStackTrace();
			}		
			controller.modelEvent(this, "updateConversation", text);
			for(Connection key : clients.keySet()){
				super.send(key, text);
			}
		}
	}
	
	//--- Envoi du message au serveur et aux clients(sauf le nouveau) ---
	public void sendMessage(String information,Connection connection){
		text=information;
		try {
			file.write(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
		controller.modelEvent(this, "updateConversation", text);
		for(Connection key : clients.keySet()){
			if(key!=connection){
				super.send(key, text);
			}
		}	
	}

}
