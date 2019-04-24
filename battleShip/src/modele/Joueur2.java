package modele;

import java.util.Vector;

public class Joueur2 extends Player implements TypeEnnemie {

	private Vector<Integer> ordreCaseBateau;

	public void setOrdreCaseBateau(Vector<Integer> ordreCaseBateau) {
		this.ordreCaseBateau = ordreCaseBateau;
	}

	@Override
	public void PlacementBateau() {
		int compteur = 0;
		for (Bateau bateau : bateau) {
			for (int i = 0; i < bateau.getGrandeur(); i++) {
				bateau.setCaseOnId(ordreCaseBateau.get(compteur), i);
				compteur++;
				System.out.println(bateau.getImageDirectory() + ": case " + i + " = " + bateau.getCaseOnId()[i]);
			}
		}

	}

	@Override
	public void setDetecterBateau(boolean detecterBateau) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDerniereCaseBateauToucher(int derniereCaseBateauToucher) {
		// TODO Auto-generated method stub

	}

	@Override
	public int Tirer(Joueur joueur) {
		// TODO Auto-generated method stub
		return 0;
	}

}
