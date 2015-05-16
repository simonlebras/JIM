package model;

public class Client{
	
	//--- Propri�t�s ---
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
	
	//--- Initialisation des diff�rents labels du client ---
	public void initClient(String pseudo,String url){
		this.pseudo=pseudo+"~"+numClient;
		this.url=url+"~"+numClient;
	}
	
}
