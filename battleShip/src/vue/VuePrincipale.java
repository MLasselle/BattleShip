package vue;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import controleur.Controleur;



public class VuePrincipale extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BoutonJeu[] casePlayer;
	private JButton[] caseEnnemie;
	private JButton[] bateau;
	private Controleur controleur;
	private JPanel placementBateau = new JPanel();
	private JPanel viewPlayerPan;
	private int nbBateau;
	private JPanel centerPanel;
	private JLabel instruction;
	
	//********************Setter-Getter*********
	
	public Controleur getControleur() {
		return controleur;
	}
	public void setControleur(Controleur controleur) {
		this.controleur = controleur;
	}
	public BoutonJeu getCasePlayer(int indice) {
			return casePlayer[indice];
	}
	public void setVisibles(boolean visible)
	{
		this.setVisible(visible);
	}
	//-----------------------------------------
	public VuePrincipale(boolean client)
	{
		nbBateau = 4;
		bateau = new JButton[nbBateau];
		String[] nomDossier = new String[] {"image/bateau4case/fullBoat1Down.png","image/bateau3case2/fullBoatDown.png","image/bateau3case/fullBoatDown.png","image/bateau2case/fullBoatDown.png"};
		controleur = null;
		casePlayer = new BoutonJeu[121];
		caseEnnemie = new BoutonJeu[121];
		Container fenetrePrincipal = this.getContentPane();
		centerPanel = new JPanel();
		viewPlayerPan = new JPanel();
		JPanel viewEnnemiePan = new JPanel();
		JLabel logo = new JLabel(new ImageIcon("image/logo/logo.png"));
		placementBateau = new JPanel();
		placementBateau.setPreferredSize(new Dimension(120,400));
		JPanel makeSpace = new JPanel();
		JPanel makeSpace2 = new JPanel();
		instruction = new JLabel("test je suis du text au hasard ici pour donner les instruction");
		instruction.setForeground(Color.BLACK);
		instruction.setFont(new Font("arial", 1, 20));
		makeSpace.setPreferredSize(new Dimension(120,400));
		makeSpace2.setPreferredSize(new Dimension(400,30));
		makeSpace2.add(instruction);
		for(int i = 0; i < nbBateau; i++)
		{
			ImageIcon bateauIcon = new ImageIcon(nomDossier[i]);
			bateau[i] = new JButton(bateauIcon);
			bateau[i].setOpaque(true);
			bateau[i].setContentAreaFilled(false);
			bateau[i].setActionCommand("bateau" + i);
			bateau[i].addActionListener(new actionListener(this));
			bateau[i].setPreferredSize(new Dimension(50,200));
			placementBateau.add(bateau[i]);
		}
		
		viewPlayerPan.setLayout(new GridLayout(11,11));
		viewEnnemiePan.setLayout(new GridLayout(11,11));
		centerPanel.setLayout(new FlowLayout());
		for(int i = 0; i < 121; i++)
		{
			casePlayer[i] = new BoutonJeu(i);
			caseEnnemie[i] = new BoutonJeu(i);
			caseEnnemie[i].addMouseListener(new mouseListener2(this));
			casePlayer[i].addMouseListener(new mouseListener(this));
			if(client)
			{
				viewPlayerPan.add(caseEnnemie[i]);
				viewEnnemiePan.add(casePlayer[i]);
			}
			else
			{
				viewPlayerPan.add(casePlayer[i]);
				viewEnnemiePan.add(caseEnnemie[i]);
			}
		}
		placementBateau.setOpaque(false);
		centerPanel.setOpaque(false);
		logo.setOpaque(false);
		makeSpace.setOpaque(false);
		makeSpace2.setOpaque(false);
		
		centerPanel.add(viewPlayerPan);
		centerPanel.add(viewEnnemiePan);
		fenetrePrincipal.setBackground(Color.darkGray);
		fenetrePrincipal.add(logo, BorderLayout.NORTH);
		fenetrePrincipal.add(centerPanel, BorderLayout.CENTER);
		if(client)
		{
			fenetrePrincipal.add(placementBateau, BorderLayout.EAST);
			fenetrePrincipal.add(makeSpace, BorderLayout.WEST);
		}
		else
		{
			fenetrePrincipal.add(placementBateau, BorderLayout.WEST);
			fenetrePrincipal.add(makeSpace, BorderLayout.EAST);
		}
		
		fenetrePrincipal.add(makeSpace2, BorderLayout.SOUTH);
		this.pack();
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setVisible(true);
		try {
			startBGMusic( );
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//sert a placer une bordure rouge autour du panel placementBateau
	public void setInstruction(String instruction)
	{
		this.instruction.setText(instruction);
	}
	
	//sert a placer un image sur une case en particulier
	public void setfield(int indice, String nomDossier, int part, Boolean canPlace)
	{
		
		casePlayer[indice].setIcon(new ImageIcon("image/"+nomDossier+"/part"+part+".png"));
		if(!canPlace)
			casePlayer[indice].setIcon(new ImageIcon(mergeImage((ImageIcon) casePlayer[indice].getIcon(), 1)));
	}
	public void setFieldEnnemie(int indice, String nomDossier, int part)
	{
		//System.out.println("image/"+nomDossier+"/part"+part+".png");
		caseEnnemie[indice].setIcon(new ImageIcon( "image/"+nomDossier+"/part"+part+".png"));
		caseEnnemie[indice].setIcon(new ImageIcon(mergeImage((ImageIcon) caseEnnemie[indice].getIcon(), 0)));
		
	}
	public void colorShotPlayer(int indice, int toucher)
	{
		caseEnnemie[indice].setIcon(new ImageIcon(mergeImage((ImageIcon) caseEnnemie[indice].getIcon(), toucher)));
	}
	public void colorShotEnnemie(int indice, int toucher)
	{
		casePlayer[indice].setIcon(new ImageIcon(mergeImage((ImageIcon) casePlayer[indice].getIcon(), toucher)));
	}
	//sert a placer de l'eau sur une case en particulier
	public void setWater(int indice)
	{
		casePlayer[indice].setIcon(new ImageIcon("image/water/water.jpg"));
	}
	//couche 1 = hover rouge
	//couche 2 = hover bleu
	
	public BufferedImage mergeImage(ImageIcon image, int coucheAMettre)
	{
		BufferedImage image2 = new BufferedImage( image.getIconWidth() , image.getIconHeight() , BufferedImage.TYPE_INT_RGB );
		image.paintIcon(null, image2.getGraphics() , 0 , 0 );
		//BufferedImage im;
		BufferedImage im2;
		try {
			if(coucheAMettre == 1)
				im2 = ImageIO.read(new File("image/mauvaisPlacement/carreRouge.png"));
			else
				im2 = ImageIO.read(new File("image/mauvaisPlacement/carreJaune.png"));
			
			Graphics2D g = image2.createGraphics();
		    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
		    g.drawImage(im2, (image2.getWidth() - im2.getWidth()) / 2,
		        (image2.getHeight() - im2.getHeight()) / 2, null);
		    g.dispose();
		    return image2;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	public void cacherBateau(int idBateau)
	{
		bateau[idBateau].setVisible(false);
	}
	 public void startBGMusic() throws LineUnavailableException, UnsupportedAudioFileException, IOException 
	 { 
		 Clip clip = AudioSystem.getClip();
	        AudioInputStream ais = AudioSystem.getAudioInputStream(new File("sound/bgMusic/bgMusic.wav"));
	        
	        clip.open(ais);
	        
	        clip.loop(Clip.LOOP_CONTINUOUSLY);
	        FloatControl volCtrl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	        volCtrl.setValue(-10f);
	        
     }
	 public void setBordureHoverCaseEnnemie(BoutonJeu caseAModifier)
	 {
		 caseAModifier.setBorder(BorderFactory.createBevelBorder(1, Color.white, Color.BLACK));
	 }
	 public void resetBordureHoverCaseEnnemie(BoutonJeu caseAModifier)
	 {
		 caseAModifier.setBorder(null);
	 }
}
//**********************************Classe qui permet d'ecouter la souris sur les panneau des joueur **************************
class mouseListener implements MouseListener
{
	
	private VuePrincipale vue;
	
	public mouseListener(VuePrincipale vue)
	{
		this.vue = vue;
		
	}
	//Quand la souris entre sur une case 
	public void mouseEntered(MouseEvent evt) 
	{
		vue.getControleur().placerBateau(((BoutonJeu)evt.getSource()).getId());
    }
	//Quand la souris quitte une case 
	public void mouseExited(MouseEvent evt) 
	{
		vue.getControleur().placerEauSurVue();
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}
	//Quand un bouton de la souri est appuyer
	@Override
	public void mousePressed(MouseEvent e) {
		//bouton 1 == clique gauche
		if(e.getButton() == 1)
		{
			vue.getControleur().setSelectionBateau(((BoutonJeu)e.getSource()).getId());
		}
		else
		{
			vue.getControleur().changerDirectionBateau();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
class mouseListener2 implements MouseListener
{
	
	private VuePrincipale vue;
	
	public mouseListener2(VuePrincipale vue)
	{
		this.vue = vue;
		
	}
	//Quand la souris entre sur une case 
	public void mouseEntered(MouseEvent evt) 
	{
		if(((BoutonJeu)evt.getSource()).getEnabled())
		{
			vue.setBordureHoverCaseEnnemie((BoutonJeu)evt.getSource());		
		}
		
    }
	//Quand la souris quitte une case 
	public void mouseExited(MouseEvent evt) 
	{
		vue.resetBordureHoverCaseEnnemie(((BoutonJeu)evt.getSource()));
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		/*if(((BoutonJeu)e.getSource()).getEnabled())
		{
			vue.getControleur().analyserClickJoueur(((BoutonJeu)e.getSource()).getId());
		}*/
	}
	//Quand un bouton de la souri est appuyer
	@Override
	public void mousePressed(MouseEvent e) {
		if(((BoutonJeu)e.getSource()).getEnabled())
		{
			vue.getControleur().analyserClickJoueur(((BoutonJeu)e.getSource()).getId());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
//**********************************Classe qui permet d'ecouter la souris sur les bateau a placer **************************
class actionListener implements ActionListener
{
	private VuePrincipale vue;
	public actionListener(VuePrincipale vue)
	{
		this.vue = vue;
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		vue.getControleur().setSelectionBateau(e.getActionCommand());
	}
}
