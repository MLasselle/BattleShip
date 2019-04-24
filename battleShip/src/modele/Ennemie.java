package modele;

import java.util.Random;

public class Ennemie extends Player implements TypeEnnemie {
	private int bateauAPlacer;
	private int derniereCaseBateauToucher;
	private int directionBateauTrouver = -1;
	private boolean detecterBateau;

	// --------------------------setter/ghetter-------------------------
	public void setDirectionBateauTrouver(int directionBateauTrouver) {
		this.directionBateauTrouver = directionBateauTrouver;
	}

	public int getDirectionBateauTrouver() {
		return directionBateauTrouver;
	}

	public int getDerniereCaseBateauToucher() {
		return derniereCaseBateauToucher;
	}

	public void setDerniereCaseBateauToucher(int derniereCaseBateauToucher) {
		this.derniereCaseBateauToucher = derniereCaseBateauToucher;
	}

	public void setDetecterBateau(boolean detecterBateau) {
		this.detecterBateau = detecterBateau;
	}

	// @Override
	public boolean isDetecterBateau() {
		return detecterBateau;
	}

	// ---------------------------------------------------------------------
	public Ennemie() {
		super();
		indiceBateauEnCours = 0;
		bateauAPlacer = 4;
		detecterBateau = false;
		derniereCaseBateauToucher = 0;
	}

	@Override
	public void PlacementBateau() {
		while (bateauAPlacer != 0) {
			int rand = new Random().nextInt(120);
			this.bateau[this.indiceBateauEnCours].direction = randomizeDirection();
			this.bateau[this.indiceBateauEnCours]
					.setImageDirectoryFinal(this.bateau[this.indiceBateauEnCours].getImageDirectory() + "/"
							+ this.bateau[this.indiceBateauEnCours].direction);
			// System.out.println(this.bateau[this.indiceBateauEnCours].getImageDirectoryFinal());
			this.modifierPosition(rand);
			if (siPositionValide()) {
				this.addCaseUse();
				bateauAPlacer--;
				indiceBateauEnCours++;
			}
		}
	}

	@Override
	public boolean analyzerSiTouchee(int idCaseAAnalyzer) {
		for (Bateau bateau : bateau) {
			for (int i : bateau.getCaseOnId()) {
				if (i == idCaseAAnalyzer) {
					System.out.println("Toucher le bateau " + bateau.getImageDirectory());

					return false;
				}
			}
		}
		return true;
	}

	public Boolean siPositionValide() {
		for (int i : bateau[this.indiceBateauEnCours].getCaseOnId()) {
			if (i == -1) {
				return false;
			}

		}
		return true;
	}

	public int Tirer(Joueur joueur) {
		if (isDetecterBateau() == false) {
			int rand = new Random().nextInt(121);
			while ((rand) % 11 == 0 || rand <= 10 || verifierSiDejaTirer(rand)) {
				rand = new Random().nextInt(121);
			}
			ajouterCaseDejaTirer(rand);
			return rand;
		} else if (getDirectionBateauTrouver() == -1) {
			int nombre = detecterDirection(joueur);
			ajouterCaseDejaTirer(nombre);
			return nombre;
		} else {
			int nombre = killBoat(joueur);
			ajouterCaseDejaTirer(nombre);
			return nombre;
		}

	}

	public int detecterDirection(Joueur joueur) {
		if (!verifierSiDejaTirer(getDerniereCaseBateauToucher() + 1)
				&& (getDerniereCaseBateauToucher() + 1) % 11 != 0) {
			if (!joueur.analyzerSiTouchee(getDerniereCaseBateauToucher() + 1)) {
				setDirectionBateauTrouver(0);
			}
			return getDerniereCaseBateauToucher() + 1;
		} else if (!verifierSiDejaTirer(getDerniereCaseBateauToucher() - 1)
				&& (getDerniereCaseBateauToucher() - 1) % 11 != 0) {
			if (!joueur.analyzerSiTouchee(getDerniereCaseBateauToucher() - 1)) {
				setDirectionBateauTrouver(1);
			}
			return getDerniereCaseBateauToucher() - 1;
		} else if (!verifierSiDejaTirer(getDerniereCaseBateauToucher() + 11)
				&& (getDerniereCaseBateauToucher() + 11) < 120) {
			if (!joueur.analyzerSiTouchee(getDerniereCaseBateauToucher() + 11)) {
				setDirectionBateauTrouver(2);
			}
			return getDerniereCaseBateauToucher() + 11;
		} else {
			if (!joueur.analyzerSiTouchee(getDerniereCaseBateauToucher() - 11)) {
				setDirectionBateauTrouver(3);
			}
			return getDerniereCaseBateauToucher() - 11;
		}

	}

	public int killBoat(Joueur joueur) {
		if (getDirectionBateauTrouver() == 0) {

			if (joueur.analyzerSiTouchee(getDerniereCaseBateauToucher() + 1)) {

				int compteur = 1;
				while (verifierSiDejaTirer(getDerniereCaseBateauToucher() - compteur)) {
					compteur++;
				}
				if (joueur.analyzerSiTouchee(getDerniereCaseBateauToucher() - compteur)) {
					setDirectionBateauTrouver(-1);
					setDetecterBateau(false);
					int rand = new Random().nextInt(121);
					while ((rand) % 11 == 0 || rand <= 10 || verifierSiDejaTirer(rand)) {
						rand = new Random().nextInt(121);
					}
					ajouterCaseDejaTirer(rand);
					return rand;
				} else {

					setDirectionBateauTrouver(1);

					return (getDerniereCaseBateauToucher() + 1);
				}
			}
			int compteur = 0;
			while (verifierSiDejaTirer(getDerniereCaseBateauToucher() + compteur))
				compteur++;
			return (getDerniereCaseBateauToucher() + compteur);

		} else if (getDirectionBateauTrouver() == 1) {
			if (joueur.analyzerSiTouchee(getDerniereCaseBateauToucher() - 1)) {

				int compteur = 1;
				while (verifierSiDejaTirer(getDerniereCaseBateauToucher() + compteur)) {
					compteur++;
				}
				if (joueur.analyzerSiTouchee(getDerniereCaseBateauToucher() + compteur)) {
					setDirectionBateauTrouver(-1);
					setDetecterBateau(false);
					int rand = new Random().nextInt(121);
					while ((rand) % 11 == 0 || rand <= 10 || verifierSiDejaTirer(rand)) {
						rand = new Random().nextInt(121);
					}
					ajouterCaseDejaTirer(rand);
					return rand;
				} else {
					setDirectionBateauTrouver(0);

					return (getDerniereCaseBateauToucher() - 1);
				}
			}
			int compteur = 0;
			while (verifierSiDejaTirer(getDerniereCaseBateauToucher() - compteur))
				compteur++;
			return (getDerniereCaseBateauToucher() - compteur);
		} else if (getDirectionBateauTrouver() == 2) {
			if (joueur.analyzerSiTouchee(getDerniereCaseBateauToucher() + 11)) {

				int compteur = 11;
				while (verifierSiDejaTirer(getDerniereCaseBateauToucher() - compteur)) {
					compteur += 11;
				}
				if (joueur.analyzerSiTouchee(getDerniereCaseBateauToucher() - compteur)) {
					setDirectionBateauTrouver(-1);
					setDetecterBateau(false);
					int rand = new Random().nextInt(121);
					while ((rand) % 11 == 0 || rand <= 10 || verifierSiDejaTirer(rand)) {
						rand = new Random().nextInt(121);
					}
					ajouterCaseDejaTirer(rand);
					return rand;
				} else {
					setDirectionBateauTrouver(3);
					return (getDerniereCaseBateauToucher() - compteur);
				}
			}
			int compteur = 11;
			while (verifierSiDejaTirer(getDerniereCaseBateauToucher() + compteur))
				compteur += 11;
			return (getDerniereCaseBateauToucher() + compteur);
		} else {
			if (joueur.analyzerSiTouchee(getDerniereCaseBateauToucher() - 11)) {

				int compteur = 11;
				while (verifierSiDejaTirer(getDerniereCaseBateauToucher() + compteur)) {
					compteur += 11;
				}
				if (joueur.analyzerSiTouchee(getDerniereCaseBateauToucher() + compteur)) {
					setDirectionBateauTrouver(-1);
					setDetecterBateau(false);
					int rand = new Random().nextInt(121);
					while ((rand) % 11 == 0 || rand <= 10 || verifierSiDejaTirer(rand)) {
						rand = new Random().nextInt(121);
					}
					ajouterCaseDejaTirer(rand);
					return rand;
				} else {
					setDirectionBateauTrouver(2);
					return (getDerniereCaseBateauToucher() - compteur);
				}
			}
			int compteur = 11;
			while (verifierSiDejaTirer(getDerniereCaseBateauToucher() - compteur))
				compteur += 11;
			return (getDerniereCaseBateauToucher() - compteur);

		}
	}
}
