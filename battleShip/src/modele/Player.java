package modele;

import java.util.Random;
import java.util.Vector;

import modele.Bateau.Direction;

public abstract class Player {
	protected Bateau[] bateau;
	protected boolean dead;
	protected int indiceBateauEnCours;
	protected Vector<Integer> caseUse;
	protected Vector<Integer> caseEssayer;

	public Direction randomizeDirection() {
		int rand = new Random().nextInt(3);
		switch (rand) {
		case 0:
			return Direction.down;
		case 1:
			return Direction.up;
		case 2:
			return Direction.left;
		case 3:
			return Direction.right;
		}
		return null;
	}

	public int getIndiceBateauEnCours() {
		return indiceBateauEnCours;
	}

	public Bateau[] getBateau() {
		return bateau;
	}

	public void setIndiceBateauEnCours(int indiceBateauEnCours) {
		this.indiceBateauEnCours = indiceBateauEnCours;
	}

	public Boolean verifierSiDejaTirer(int caseVerifier) {
		for (int i : caseEssayer) {
			if (caseVerifier == i)
				return true;
		}
		return false;
	}

	public void ajouterCaseDejaTirer(int caseAjouter) {
		caseEssayer.add(caseAjouter);
	}

	public Player() {
		bateau = new Bateau[4];
		caseUse = new Vector<Integer>();
		bateau[0] = new Bateau(4, "bateau4case");
		bateau[1] = new Bateau(3, "bateau3case2");
		bateau[2] = new Bateau(3, "bateau3case");
		bateau[3] = new Bateau(2, "bateau2case");
		caseUse = new Vector<Integer>();
		caseEssayer = new Vector<Integer>();
	}

	public void modifierPosition(int pointDepart) {
		Boolean out = false;
		switch (this.bateau[indiceBateauEnCours].direction) {
		case right:
			for (int i = 0; i < this.bateau[indiceBateauEnCours].getGrandeur(); i++) {
				if ((pointDepart + i) % 11 != 0 && (pointDepart + i) > 10 && out == false
						&& siPlacementPossible(pointDepart + i)) {
					this.bateau[indiceBateauEnCours].getCaseOnId()[i] = pointDepart + i;
				} else {
					out = true;
					this.bateau[indiceBateauEnCours].getCaseOnId()[i] = -1;
				}
			}
			break;
		case left:
			for (int i = 0; i < this.bateau[indiceBateauEnCours].getGrandeur(); i++) {
				if ((pointDepart - i) % 11 != 0 && (pointDepart - i) > 10 && out == false
						&& siPlacementPossible(pointDepart - i)) {
					this.bateau[indiceBateauEnCours].getCaseOnId()[i] = pointDepart - i;
				} else {
					out = true;
					this.bateau[indiceBateauEnCours].getCaseOnId()[i] = -1;
				}
			}
			break;
		case down:
			for (int i = 0; i < this.bateau[indiceBateauEnCours].getGrandeur(); i++) {
				if (pointDepart + (i * 11) < 121 && (pointDepart + (i * 11)) > 10 && out == false
						&& (pointDepart % 11) != 0 && siPlacementPossible(pointDepart + (i * 11))) {
					this.bateau[indiceBateauEnCours].getCaseOnId()[i] = pointDepart + (i * 11);
				} else {
					out = true;
					this.bateau[indiceBateauEnCours].getCaseOnId()[i] = -1;
				}
			}
			break;
		case up:
			for (int i = 0; i < this.bateau[indiceBateauEnCours].getGrandeur(); i++) {
				if (pointDepart - (i * 11) < 121 && (pointDepart - (i * 11)) > 10 && out == false
						&& (pointDepart % 11) != 0 && siPlacementPossible(pointDepart - (i * 11))) {
					this.bateau[indiceBateauEnCours].getCaseOnId()[i] = pointDepart - (i * 11);
				} else {
					out = true;
					this.bateau[indiceBateauEnCours].getCaseOnId()[i] = -1;
				}
			}
			break;
		}
	}

	public Boolean siPlacementPossible(int idAVerifier) {
		for (int i : this.caseUse) {
			if (i == idAVerifier)
				return false;
		}

		return true;
	}

	public boolean analyzerSiTouchee(int idCaseAAnalyzer) {
		for (Bateau bateau : bateau) {
			for (int i : bateau.getCaseOnId()) {
				if (i == idCaseAAnalyzer) {
					return false;
				}
			}
		}
		return true;
	}

	public void addCaseUse() {
		for (int i : this.bateau[indiceBateauEnCours].getCaseOnId()) {
			caseUse.add(i);
		}
	}
	public boolean checkIfDead() {
		for (Bateau bateau : bateau) {
			if (!bateau.isDead())
				return false;
		}
		return true;
	}
}
