package model;

public class Client{
	
	//--- Propriétés ---
	private String pseudo;
	private String url;
	private static int clientsCount=1;
	private int numClient;
	
	//--- Constructeur ---
	public Client (){
		numClient=clientsCount;
		clientsCount++;
	}
	
	//--- Getters ---
	public String getPseudo () {return pseudo;}
	public String getUrl () {return url;}
	
	//--- Initialisation des différents labels du client ---
	public void initClient(String pseudo,String url){
		this.pseudo=pseudo+"~"+numClient;
		this.url=url+"~"+numClient;
	}
	
}
