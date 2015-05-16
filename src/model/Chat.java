package model;

import tools.Connection;
import controller.Controller;

public abstract class Chat {
	
	//--- Propriétés ---
	protected Controller controller;
	
	//--- Récupération de l'objet de connection ---
	public abstract void setConnection(Connection connection);
	
	//--- Réception ---
	public abstract void reception(Connection connection, Object object);
	
	//--- Envoi ---
	public void send(Connection connection, Object object){connection.send(object);}
	
	//--- Deconnection ---
	public abstract void deconnection(Connection connection);
	
}






