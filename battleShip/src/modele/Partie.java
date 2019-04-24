package modele;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;

import javax.swing.ImageIcon;

import controleur.Controleur;
import principale.Principale;

public class Partie {
	private int bateauAPlacer;
	private Boolean batteauEnnemiePlacer;
	private int caseJouer;
	private Socket socket;
	Joueur joueur;
	TypeEnnemie ennemie;
	private Boolean tourJoueur;
	private Boolean achieveBoatPlacement;
	private Controleur controleur;

	// --------------------------getter/setter-----------------------
	public void setControleur(Controleur controleur) {
		this.controleur = controleur;
	}

	public void setBoatChoose(int boatChoose) {
		this.joueur.indiceBateauEnCours = boatChoose;
	}

	public Boolean getTourJoueur() {
		return tourJoueur;
	}

	public void setTourJoueur(Boolean tourJoueur) {
		this.tourJoueur = tourJoueur;
	}

	public Boolean getBatteauEnnemiePlacer() {
		return batteauEnnemiePlacer;
	}

	public void setCaseJouer(int caseJouer) {
		this.caseJouer = caseJouer;
	}

	public Joueur getJoueur() {
		return joueur;
	}

	public TypeEnnemie getEnnemie() {
		return ennemie;
	}

	public void setBateauAPlacer() {
		this.bateauAPlacer--;
	}

	// ----------------------------------------------------------------
	public Partie(Socket socket) {

		this.socket = socket;
		this.batteauEnnemiePlacer = false;
		this.tourJoueur = true;
		this.bateauAPlacer = 4;
		achieveBoatPlacement = false;
		joueur = new Joueur();
		if (socket == null)
			ennemie = new Ennemie();
		else
			ennemie = new Joueur2();
	}

	public void managerPartie() {
		if (bateauAPlacer != 0) {
			if (achieveBoatPlacement == false) {
				if (joueur.indiceBateauEnCours == -1) {
					controleur.setInstruction("Selectionner un bateau a placer");
				} else {
					controleur.setInstruction("Placer le bateau selectionner sur votre grille");
				}
			} else {
				controleur.setInstruction("Attendre que lennemie est fini de placer cest bateau");
			}
		} else {
			if (!batteauEnnemiePlacer) {
				if (socket == null) {
					this.ennemie.PlacementBateau();
					batteauEnnemiePlacer = true;
				} else {
					try {
						Vector<Integer> test = new Vector<Integer>();
						PrintWriter out = new PrintWriter(socket.getOutputStream());
						for (int j : joueur.preparerVectorPourJouerReseau()) {
							out.write(j);
						}
						out.flush();
						BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						for (int i = 0; i < 12; i++) {
							int j = in.read();
							test.add(j);
						}
						((Joueur2) ennemie).setOrdreCaseBateau(test);
						ennemie.PlacementBateau();
					} catch (IOException e) {
						Principale.afficherFenetreFinal(2);
						e.printStackTrace();
					}
					batteauEnnemiePlacer = true;
				}
			} else {
				if (tourJoueur) {
					controleur.setInstruction("C'est maintenant votre tour ");
				} else {

					controleur.setInstruction("C'est maintenant au tour de l'ennemie ");
					if (socket == null) {
						int idAAnalyzer = ennemie.Tirer(joueur);
						controleur.analyserClickEnnemie(idAAnalyzer);
						controleur.placerBateauSurVueEnnemie(joueur.caseEssayer);
					} else {
						try {
							PrintWriter out = new PrintWriter(socket.getOutputStream());
							out.write(caseJouer);
							out.flush();
							BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
							int idRecu = in.read();
							ennemie.ajouterCaseDejaTirer(idRecu);
							controleur.analyserClickEnnemie(idRecu);
							controleur.placerBateauSurVueEnnemie(joueur.caseEssayer);
						} catch (IOException e) {
							Principale.afficherFenetreFinal(2);
							e.printStackTrace();
						}
					}

					for (Bateau bateau : joueur.getBateau()) {
						if (socket == null)
							bateau.checkIfDead(((Ennemie) ennemie).caseEssayer);
						else
							bateau.checkIfDead(((Joueur2) ennemie).caseEssayer);
					}
				}
				if (joueur.checkIfDead() && ennemie.checkIfDead()) {
					if(socket != null)
					{
						try {
							socket.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					Principale.afficherFenetreFinal(3);
				}
				else if (joueur.checkIfDead()) {
					if(socket != null)
					{
					try {
						socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
					Principale.afficherFenetreFinal(1);
				} else if (ennemie.checkIfDead()) {
					if(socket!=null)
					{
					try {
						socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
					Principale.afficherFenetreFinal(0);
				}
			}
		}
	}

	public void placerBateau(int idCase) {
		this.joueur.modifierPosition(idCase);
	}

	public Boolean verifierSiPoseBateauPossible() {
		for (int i : this.joueur.getBateau()[this.joueur.indiceBateauEnCours].getCaseOnId()) {
			if (i == -1) {
				return false;
			}
		}
		return true;
	}
}
