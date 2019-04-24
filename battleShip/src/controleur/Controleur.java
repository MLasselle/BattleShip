package controleur;

import java.util.Vector;
import modele.Bateau;
import modele.Partie;
import principale.Principale;
import vue.VuePrincipale;

public class Controleur {
	private VuePrincipale vue;
	private Partie partie;

	public Controleur(VuePrincipale vue, Partie partie) {
		this.vue = vue;
		this.partie = partie;
	}

	public void setInstruction(String instruction) {
		vue.setInstruction(instruction);

	}

	// Permet de savoir quelle bateau a ete cliquer
	public void setSelectionBateau(String actionCommand) {
		if (actionCommand.equals("bateau0")) {
			partie.setBoatChoose(0);
		} else if (actionCommand.equals("bateau1")) {
			partie.setBoatChoose(1);
		} else if (actionCommand.equals("bateau2")) {
			partie.setBoatChoose(2);
		} else if (actionCommand.equals("bateau3")) {
			partie.setBoatChoose(3);
		}

		partie.managerPartie();

	}

	// sert a modifier la position du bateau si il a un bateau de selectionner
	public void changerDirectionBateau() {
		if (partie.getJoueur().getIndiceBateauEnCours() != -1) {
			placerEauSurVue();
			this.partie.getJoueur().modifierDirection();
			placerBateauSurVue();
		}
	}

	// sert a savoir si on peu positionner le bateau ici
	public void setSelectionBateau(int caseId) {
		if (partie.getJoueur().getIndiceBateauEnCours() != -1) {
			if (partie.verifierSiPoseBateauPossible()) {
				vue.cacherBateau(partie.getJoueur().getIndiceBateauEnCours());
				partie.getJoueur().addCaseUse();
				partie.setBoatChoose(-1);
				partie.setBateauAPlacer();
				partie.managerPartie();

			}
		}
	}

	// sert a placer le bateau sur une case specifique quand la souris se deplace
	public void placerBateau(int idCase) {
		if (partie.getJoueur().getIndiceBateauEnCours() != -1) {
			partie.placerBateau(idCase);
			placerBateauSurVue();
		}
	}

	// sert a dessiner le bateau sur les case que contient les valeur du tableau de
	// la class bateau
	public void placerBateauSurVue() {
		int compteur = 1;
		if (partie.verifierSiPoseBateauPossible()) {
			for (int i : partie.getJoueur().getBateau()[partie.getJoueur().getIndiceBateauEnCours()].getCaseOnId()) {
				if (i != -1)
					vue.setfield(i, partie.getJoueur().getBateau()[partie.getJoueur().getIndiceBateauEnCours()]
							.getImageDirectoryFinal(), compteur, true);

				compteur++;
			}
		} else {
			for (int i : partie.getJoueur().getBateau()[partie.getJoueur().getIndiceBateauEnCours()].getCaseOnId()) {
				if (i != -1)
					vue.setfield(i, partie.getJoueur().getBateau()[partie.getJoueur().getIndiceBateauEnCours()]
							.getImageDirectoryFinal(), compteur, false);

				compteur++;
			}
		}
	}

	public void placerBateauSurVueEnnemie(Vector<Integer> caseUtiliser) {

		for (Bateau bateau : partie.getEnnemie().getBateau()) {
			int compteur = 1;
			if (bateau.checkIfDead(caseUtiliser)) {
				for (int i : bateau.getCaseOnId()) {
					if (i != -1)
						vue.setFieldEnnemie(i, bateau.getImageDirectoryFinal(), compteur);

					compteur++;
				}
			}
		}
	}

	// Sert a remettre leau sur les case ou le bateau est passer lors du placement
	public void placerEauSurVue() {
		if (partie.getJoueur().getIndiceBateauEnCours() != -1) {
			for (int i : partie.getJoueur().getBateau()[partie.getJoueur().getIndiceBateauEnCours()].getCaseOnId()) {
				if (i != -1)
					vue.setWater(i);
			}
		}
	}

	public void analyserClickJoueur(int idCaseAnalyzer) {
		if (partie.getBatteauEnnemiePlacer()) {
			if (partie.getJoueur().verifierSiDejaTirer(idCaseAnalyzer) == false) {

				if (partie.getEnnemie().analyzerSiTouchee(idCaseAnalyzer)) {
					vue.colorShotPlayer(idCaseAnalyzer, 1);
				} else {
					vue.colorShotPlayer(idCaseAnalyzer, 0);
				}
				if (!Principale.solo) {
					partie.setCaseJouer(idCaseAnalyzer);
				}
				partie.getJoueur().ajouterCaseDejaTirer(idCaseAnalyzer);
				partie.setTourJoueur(false);

				partie.managerPartie();

			} else {
				vue.setInstruction("Vous ne pouver pas tirer 2 foix sur la meme case !!");
			}
		}
	}

	public void analyserClickEnnemie(int idCaseAnalyzer) {
		if (partie.getJoueur().analyzerSiTouchee(idCaseAnalyzer)) {
			vue.colorShotEnnemie(idCaseAnalyzer, 1);
		} else {
			vue.colorShotEnnemie(idCaseAnalyzer, 0);
			partie.getEnnemie().setDetecterBateau(true);
			partie.getEnnemie().setDerniereCaseBateauToucher(idCaseAnalyzer);
		}

		partie.setTourJoueur(true);
		partie.managerPartie();

	}

}
