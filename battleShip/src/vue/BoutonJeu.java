package vue;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;

//**********************************Classe qui permet de creer les bouton sur le terrain **************************
public class BoutonJeu extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;

	public int getId() {
		return id;
	}

	public BoutonJeu(int id) {
		Character caseLetter = (char) ('A' + (id / 11) - 1);
		this.id = id;
		this.setActionCommand(id + "");
		this.setPreferredSize(new Dimension(50, 50));
		if (id <= 10 || id % 11 == 0) {
			this.setEnabled(false);
			this.setBackground(Color.black);
			if (id <= 10) {
				this.setText(id + "");
			} else {
				this.setText(caseLetter.toString());
			}
		} else {
			this.setIcon(new ImageIcon("image/water/water.jpg"));
		}
		this.setBorder(null);
	}

	public Boolean getEnabled() {
		return this.isEnabled();
	}
}
