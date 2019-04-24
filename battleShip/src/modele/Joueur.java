package modele;

import java.util.Vector;

import modele.Bateau.Direction;

//class qui creer le joueur avec 4 bateau
public class Joueur extends Player {
	public Joueur() {
		super();
		indiceBateauEnCours = -1;
	}

	public void modifierDirection() {
		switch (this.bateau[this.indiceBateauEnCours].direction) {
		case right:
			this.bateau[this.indiceBateauEnCours].direction = Bateau.Direction.down;
			this.bateau[this.indiceBateauEnCours]
					.setImageDirectoryFinal(this.bateau[this.indiceBateauEnCours].getImageDirectory() + "/down");
			break;
		case down:
			this.bateau[this.indiceBateauEnCours].direction = Direction.left;
			this.bateau[this.indiceBateauEnCours]
					.setImageDirectoryFinal(this.bateau[this.indiceBateauEnCours].getImageDirectory() + "/left");
			break;
		case left:
			this.bateau[this.indiceBateauEnCours].direction = Direction.up;
			this.bateau[this.indiceBateauEnCours]
					.setImageDirectoryFinal(this.bateau[this.indiceBateauEnCours].getImageDirectory() + "/up");
			break;
		case up:
			this.bateau[this.indiceBateauEnCours]
					.setImageDirectoryFinal(this.bateau[this.indiceBateauEnCours].getImageDirectory() + "/right");
			this.bateau[this.indiceBateauEnCours].direction = Direction.right;
			break;
		}
		modifierPosition(this.bateau[this.indiceBateauEnCours].getCaseOnId()[0]);
	}
	
	public Vector<Integer> preparerVectorPourJouerReseau() {
		Vector<Integer> returnVector = new Vector<Integer>();
		for (Bateau bateau : bateau) {
			for (int i : bateau.getCaseOnId()) {
				returnVector.add(i);
			}
		}
		return returnVector;
	}
}
