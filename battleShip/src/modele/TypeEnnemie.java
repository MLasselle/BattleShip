package modele;

public interface TypeEnnemie {
	public void PlacementBateau();
	public void setDetecterBateau(boolean detecterBateau);
	public void setDerniereCaseBateauToucher(int derniereCaseBateauToucher);
	public int Tirer(Joueur joueur);
	public boolean analyzerSiTouchee(int idCaseAAnalyzer);
	public Bateau[] getBateau();
	public boolean checkIfDead();
	public void ajouterCaseDejaTirer(int caseAjouter);
}
