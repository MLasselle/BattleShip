package principale;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import controleur.Controleur;

import modele.Partie;
import vue.VuePrincipale;

import vue.Menu;

public class Principale {
	private static Menu menu;
	private static VuePrincipale vue;
	private static boolean coteClient = false;
	public static boolean solo = true;

	public static void main(String[] args) {
		menu = new Menu();
	}
	public static void resetGame()
	{
		menu.dispose();
		vue.dispose();
		menu = new Menu();
	}
	public static void setGame(Socket socket) {
		if(socket != null)
			solo = false;
		menu.setVisible(false);
		Partie partie = new Partie(socket);
		vue = new VuePrincipale(coteClient);
		Controleur controleur = new Controleur(vue, partie);
		vue.setControleur(controleur);
		partie.setControleur(controleur);
		partie.managerPartie();
		
	}

	public static void setGameServeur() 
	{
		menu.setMessageErreur(false);
		coteClient = false;
		ServerSocket serveur = null;
		Socket socket = null;
		try {
			serveur = new ServerSocket(8756);
			serveur.setSoTimeout(5000);
			socket = serveur.accept();
			//serveur.setSoTimeout(1000);
		} catch (IOException e) {
			try {
				serveur.close();
			} catch (IOException e1) {
				
			}
			menu.setMessageErreur(true);
		}
		if(socket != null)
			setGame(socket);
	}

	public static void setGameClient(String ip) 
	{
		menu.setMessageErreur(false);
		coteClient = true;
		Socket socket = null;
		try {
			socket = new Socket(ip, 8756);
			socket.setSoTimeout(5000);
		} catch (IOException e) {
			menu.setMessageErreur(true);
		}
		if(socket != null)
			setGame(socket);
			
	}
	public static void afficherFenetreFinal(int joueurGagne)
	{
		vue.setVisibles(false);
		menu.setConclusionParti(joueurGagne);
		menu.setVisible(true);
	}
	
}