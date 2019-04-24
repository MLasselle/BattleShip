package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import principale.Principale;

public class Menu extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// private static final long serialVersionUID = 1L;
	// -1 = pas de Choix
	// 0 = partie solo
	// 1 = creer serveur
	// 2 = creer client
	//private int choix;
	private JLabel conclusionParti;
	private JPanel fenetreFinal;
	private JLabel message;
	public void setMessageErreur(boolean visible) {
		this.message.setVisible(visible);
	}

	public void setConclusionParti(int joueurGagne) {
		switch(joueurGagne)
		{
		case 0:
			conclusionParti.setIcon(new ImageIcon("image/conclusion/victoire.png"));
			break;
		case 1:
			conclusionParti.setIcon(new ImageIcon("image/conclusion/defaite1.png"));
			break;
		case 2:
			conclusionParti.setIcon(new ImageIcon("image/conclusion/connexion.png"));
			break;
		case 3:
			conclusionParti.setIcon(new ImageIcon("image/conclusion/egaliter.png"));
			break;
		}
		this.setContentPane(fenetreFinal);
	}

	/*public void setChoix(int choix) {
		this.choix = choix;
	}

	public int getChoix() {
		return choix;
	}*/

	public Menu() {
		//choix = -1;
		JPanel fenetrePrincipale = new ImagePanel();
		fenetreFinal = new ImagePanel();
		conclusionParti = new JLabel();
		JLabel logo = new JLabel(new ImageIcon("image/logo/logo2.png"));
		message = new JLabel("Connexion echouer");
		
		message.setForeground(Color.red);
		message.setFont(new Font("arial", Font.BOLD, 45));
		message.setVisible(false);
		
		JPanel conteneurDeBouton = new JPanel();
		JPanel conteneurDeBoutonConclusion = new JPanel();
		JPanel makespaceConclusion = new JPanel();
		JPanel makespaceConclusion2 = new JPanel();
		JPanel makeSpaceX = new JPanel();
		JPanel makeSpaceY = new JPanel();
		JPanel makeSpaceX2 = new JPanel();
		JPanel makeSpaceY2 = new JPanel();
		JButton[] btnFinal = new JButton[2];
		JButton[] bouton = new JButton[3];
		String[] nomBouton = { "Jouer en solo", "Creer serveur local", "Rejoindre un serveur locale" };
		String[] nomBoutonFinal = { "Rejouer", "Quitter" };

		fenetrePrincipale.setLayout(new BorderLayout());
		conteneurDeBouton.setLayout(new GridLayout(3, 1, 10, 10));

		for (int i = 0; i < 2; i++) {
			btnFinal[i] = new JButton(nomBoutonFinal[i]);
			btnFinal[i].setPreferredSize(new Dimension(200, 50));
			btnFinal[i].setBackground(Color.black);
			btnFinal[i].setForeground(Color.white);
			btnFinal[i].addActionListener(new boutonMenuEcouteur(this));
			conteneurDeBoutonConclusion.add(btnFinal[i]);
		}

		btnFinal[0].setActionCommand("rejouer");
		btnFinal[1].setActionCommand("quitter");
		for (int i = 0; i < 3; i++) {
			bouton[i] = new JButton(nomBouton[i]);
			bouton[i].setBackground(Color.BLACK);
			bouton[i].setForeground(Color.white);
			bouton[i].addActionListener(new boutonMenuEcouteur(this));
		}
		bouton[0].setActionCommand("0");
		bouton[1].setActionCommand("1");
		bouton[2].setActionCommand("2");

		makeSpaceX.setPreferredSize(new Dimension(350, 400));
		makeSpaceY.setPreferredSize(new Dimension(1000, 300));
		makeSpaceX2.setPreferredSize(new Dimension(350, 400));
		makeSpaceY2.setPreferredSize(new Dimension(1000, 250));
		makespaceConclusion.setPreferredSize(new Dimension(1000, 100));
		makespaceConclusion2.setPreferredSize(new Dimension(1000, 75));

		makeSpaceX.setOpaque(false);
		makeSpaceY.setOpaque(false);
		makeSpaceX2.setOpaque(false);
		makeSpaceY2.setOpaque(false);
		conteneurDeBouton.setOpaque(false);
		conteneurDeBoutonConclusion.setOpaque(false);
		makespaceConclusion2.setOpaque(false);
		makespaceConclusion.setOpaque(false);
		conteneurDeBoutonConclusion.setOpaque(false);
		
		makeSpaceY2.add(message);
		conteneurDeBouton.add(bouton[0]);
		conteneurDeBouton.add(bouton[1]);
		conteneurDeBouton.add(bouton[2]);
		makeSpaceY.add(logo);

		fenetrePrincipale.add(makeSpaceY, BorderLayout.PAGE_START);
		fenetrePrincipale.add(conteneurDeBouton, BorderLayout.CENTER);
		fenetrePrincipale.add(makeSpaceX, BorderLayout.LINE_START);
		fenetrePrincipale.add(makeSpaceX2, BorderLayout.LINE_END);
		fenetrePrincipale.add(makeSpaceY2, BorderLayout.PAGE_END);

		fenetreFinal.add(makespaceConclusion);
		fenetreFinal.add(conclusionParti);
		fenetreFinal.add(makespaceConclusion2);
		fenetreFinal.add(conteneurDeBoutonConclusion);

		this.setSize(1000, 800);
		this.setResizable(false);
		this.setContentPane(fenetrePrincipale);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public void setFenetreVisible(boolean visible) {
		this.setVisible(visible);
	}
}

class boutonMenuEcouteur implements ActionListener {
	Menu vue;

	public boutonMenuEcouteur(Menu vue) {
		this.vue = vue;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "0") {
			Principale.setGame(null);
		} else if (e.getActionCommand() == "1") {
			Principale.setGameServeur();
		} else if (e.getActionCommand() == "2") {
			Object result = JOptionPane.showInputDialog(vue, "Entrer un adresse IP local:");
			Principale.setGameClient(result.toString());
		} else if (e.getActionCommand() == "quitter") {
			System.exit(0);
		} else if (e.getActionCommand() == "rejouer") {
			Principale.resetGame();
		}
	}

}

class ImagePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage image;

	public ImagePanel() {
		try {
			image = ImageIO.read(new File("image/menu/background.jpg"));
		} catch (IOException ex) {
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);
	}

}