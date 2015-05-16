package model;

import tools.Connection;
import controller.Controller;

public abstract class Chat {
	
	//--- Propri�t�s ---
	protected Controller controller;
	
	//--- R�cup�ration de l'objet de connection ---
	public abstract void setConnection(Connection connection);
	
	//--- R�ception ---
	public abstract void reception(Connection connection, Object object);
	
	//--- Envoi ---
	public void send(Connection connection, Object object){connection.send(object);}
	
	//--- Deconnection ---
	public abstract void deconnection(Connection connection);
	
}






