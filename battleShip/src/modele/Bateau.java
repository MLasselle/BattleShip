package modele;

import java.util.Vector;

public class Bateau {
	public static Vector<Integer> caseUse = new Vector<Integer>();
	public enum Direction {
		up, down, left, right;
	}
	private int grandeur;
	private String imageDirectoryFinal;
	private String imageDirectory;
	private boolean dead;
	private int[] caseOnId;

	// ----------------------------getter/setter-------------------------
	public void setCaseOnId(int caseOnId, int indice) {
		this.caseOnId[indice] = caseOnId;
	}
	public boolean isDead() {
		return dead;
	}
	public void setImageDirectoryFinal(String imageDirectoryFinal) {
		this.imageDirectoryFinal = imageDirectoryFinal;
	}

	public String getImageDirectory() {
		return imageDirectory;
	}

	public int[] getCaseOnId() {
		return caseOnId;
	}

	public String getImageDirectoryFinal() {
		return imageDirectoryFinal;
	}

	public int getGrandeur() {
		return grandeur;
	}

	public void setGrandeur(int grandeur) {
		this.grandeur = grandeur;
	}

	// ------------------------------------------------------------------
	Direction direction;

	public Bateau(int grandeur, String imageDirectory) {

		direction = Direction.right;
		this.dead = false;
		this.setGrandeur(grandeur);
		this.imageDirectory = imageDirectory;
		this.imageDirectoryFinal = this.imageDirectory + "/right";
		this.caseOnId = new int[this.getGrandeur()];
	}

	public boolean checkIfDead(Vector<Integer> caseAAnalayser) {
		int compteur = 0;
		for (int j : caseOnId) {
			for (int i : caseAAnalayser) {
				if (j == i) {
					compteur++;
				}
			}
		}
		if (compteur == grandeur) {
			dead = true;
			return true;
		} else {

			return false;
		}

	}
}
