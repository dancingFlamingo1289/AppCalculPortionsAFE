package application.configuration;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

/**
 * Un panneau de configuration qui va permettre à l'utilisateur de configurer la fenêtre principale comme il
 * le souhaite.
 * La classe Configuration hérite de la classe mère JFrame.
 * @author Aimé Melançon
 * @author Elias Kassas
 */
public class Configuration extends JFrame {
	/**  Identifiant de classe **/
	private static final long serialVersionUID = 1L;
	/**Le panneau englobant tout les composants. **/
	private JPanel contentPane;
	/**Variable de type JTextField qui permet à l'utilisateur de sélectionner ses touches de claviers (Côté droit et Côté gauche) **/
	private JTextField txtFCoteDroit,txtFCoteGauche;
	/**Variable de type JLabel qui permet d'afficher le titre.**/
	private JLabel lblTitreConfig;
	/**Choix de musique libre de droit implémenté dans l'application. **/
	private JComboBox<Font> cBoxMusique;
	/**Choix de la police de caractère selon plusieurs choix mis à la disposition de l'utilisateur. **/
	private JComboBox<Font> cBoxPolice;
	/**Choix du thème de l'application. **/
	private JComboBox<Theme> cBoxModeApp;
	/**Texte présentant le choix du thème. **/
	private JLabel lblApparence;
	/**Texte présentant le bouton radio de la musique. **/
	private JLabel lblActivationMusique;
	/** Texte présentant le choix de musique**/
	private JLabel lblMusiqueChoisis;
	/**Texte présentant le choix de la touche droite. **/
	private JLabel lblChoixDroit;
	/**Texte présentant le choix de la touche gauche. **/
	private JLabel lblChoixGauche;
	/**Texte présentant le choix de la police de caractère. **/
	private JLabel lblChoixPolice;
	/** Texte présentant l'avertissement du fonctionnement d'assignement de touche **/
	private JLabel lblAvertissementTouche;
	/** Support pour lancer des évènements de type PropertyChange. **/
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this) ;
	/**Permet de sélectionner une musique libre de droit.**/
	private JCheckBox chckBoxMusique;
	/** Zone de saisie à multiples lignes prévue pour les logs du panneau de configuration. **/
	private JTextArea txtaLogsConfig ;
	//private final InfoConfiguration IC_DEFAUT ;
	/** Objet contenant les paramètres de configuration du panneau de configuration. **/
	private InfoConfiguration ic = null ;
	/**Variable permettant de choisir d'utiliser ou non les flèches du clavier pour manipuler les flippers. **/
	private JCheckBox chckbxFleche;
	/** Variable de type boolean permettant de choisir si on veut manipuler les flippers avec les touches ou
	 * les flèches. **/
	private static boolean fleche=false;
	/**Variable de type char qui possède la touche attribuée pour la droite. **/
	private  char coteDroit ='d' ;
	/**Variable de type char qui possède la touche attribuée pour la gauche. **/
	private  char coteGauche = 'a' ;
	/** PropertyChange pour changer la touche affichée sur le flipper droit**/
	private PropertyChangeSupport toucheDroite = new PropertyChangeSupport(this) ;
	/** PropertyChange pour changer la touche affichée sur le flipper gauche **/
	private PropertyChangeSupport toucheGauche = new PropertyChangeSupport(this) ;
	/** Thèmes disponibles dans l'application. **/
	private Theme[] themesDispo = {new Theme("Halloween", Color.orange, new Color(175, 122, 197), Color.black,
			Color.black), new Theme("BleuVertJauneRouge", Color.blue, Color.green, Color.yellow, Color.red),
			new Theme("Extra-terrestre", new Color(102, 153, 255), new Color(102, 255, 102), Color.black)} ;
	/** Polices de caractères disponibles pour l'application. **/
	private Font[] policesDispo = new Font[] {
			new Font("New Times Romans", Font.PLAIN, 14), new Font("Comic Sans Ms", Font.PLAIN, 14)} ;
	/** Liste des composants du panneau de configuration. **/
	private ArrayList<Component> lesComposants ;
	/** Zone d'écriture des logs du panneau de configuration. **/
	private JScrollPane spLogsConfig;
	/** Zone de saisie du nom du thème créé dans le créateur de thèmes. **/
	private JTextField txtNom;
	/** Zone de dessin pour afficher la couleur primaire du thème créé. **/
	private JPanel panCoulPrim;
	/** Zone de dessin pour afficher la couleur secondaire du thème créé. **/
	private JPanel panCoulSeco;
	/** Zone de dessin pour afficher la couleur tertiaire du thème créé. **/
	private JPanel panCoulTert;
	/** Zone de dessin pour afficher la couleur de la police du thème créé. **/
	private JPanel panCoulPol;
	/** Bouton de modification de la couleur primaire d'un thème. **/
	private JButton btnModCoulPrim;
	/** Bouton de modification de la couleur du texte d'un thème. **/
	private JButton btnModCoulPol;

	/**
	 * Création du panneau
	 */
	//Aimé Melançon
	public Configuration() {
		lesComposants = new ArrayList<Component>() ;

		setTitle("Configuration");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 518, 1080);
		contentPane = new JPanel();
		contentPane.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				testKey(e);
			}
		});
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblTitreConfig = new JLabel("Configuration");
		lblTitreConfig.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitreConfig.setFont(new Font("Times New Roman", Font.BOLD, 30));
		lblTitreConfig.setBounds(15, 0, 488, 94);
		contentPane.add(lblTitreConfig);
		enregistrerUnComposant(lblTitreConfig) ;

		txtFCoteDroit = new JTextField("d");
		txtFCoteDroit.addActionListener(new ActionListener() {
			// Il faut mettre le code présent dans cet écouteur dans une méthode privée.
			public void actionPerformed(ActionEvent e) {
				contentPane.requestFocusInWindow();

				gererCoteDroit();
			}
		});
		txtFCoteDroit.setBounds(371, 665, 89, 81);
		contentPane.add(txtFCoteDroit);
		txtFCoteDroit.setColumns(10);
		txtFCoteDroit.setHorizontalAlignment(SwingConstants.CENTER);
		txtFCoteDroit.setFont(new Font("Tahoma", Font.PLAIN, 40));
		enregistrerUnComposant(txtFCoteDroit) ;

		txtFCoteGauche = new JTextField("a");
		txtFCoteGauche.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.requestFocusInWindow();
				gererCoteGauche();
			}
		});
		txtFCoteGauche.setHorizontalAlignment(SwingConstants.CENTER);
		txtFCoteGauche.setFont(new Font("Tahoma", Font.PLAIN, 40));
		txtFCoteGauche.setColumns(10);
		txtFCoteGauche.setBounds(371, 756, 89, 81);
		contentPane.add(txtFCoteGauche);
		enregistrerUnComposant(txtFCoteGauche) ;

		lblChoixDroit = new JLabel("Changer la touche du flipper droit :");
		lblChoixDroit.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblChoixDroit.setBounds(10, 678, 319, 50);
		contentPane.add(lblChoixDroit);
		enregistrerUnComposant(lblChoixDroit) ;

		lblChoixGauche = new JLabel("Changer la touche du flipper gauche :");
		lblChoixGauche.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblChoixGauche.setBounds(10, 773, 351, 50);
		contentPane.add(lblChoixGauche);
		enregistrerUnComposant(lblChoixGauche) ;

		lblMusiqueChoisis = new JLabel("Choix de musique :");
		lblMusiqueChoisis.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblMusiqueChoisis.setBounds(10, 555, 256, 50);
		contentPane.add(lblMusiqueChoisis);
		enregistrerUnComposant(lblMusiqueChoisis) ;

		lblActivationMusique = new JLabel("Activer la musique :");
		lblActivationMusique.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblActivationMusique.setBounds(10, 473, 256, 50);
		contentPane.add(lblActivationMusique);
		enregistrerUnComposant(lblActivationMusique) ;

		lblChoixPolice = new JLabel("Police de caractère :");
		lblChoixPolice.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblChoixPolice.setBounds(10, 411, 256, 50);
		contentPane.add(lblChoixPolice);
		enregistrerUnComposant(lblChoixPolice) ;

		lblApparence = new JLabel("Apparence de l'interface :");
		lblApparence.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblApparence.setBounds(10, 106, 256, 50);
		contentPane.add(lblApparence);
		enregistrerUnComposant(lblApparence) ;

		cBoxMusique = new JComboBox();
		cBoxMusique.setModel(new DefaultComboBoxModel(new String[] {"Sélectionner une musique.", "alien.wav", "Musique_pop.wav", "musique_Calme.wav", "Musique_Douce.wav", "Musique_Rock.wav"}));
		cBoxMusique.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//gererChoixMusique();
				contentPane.requestFocusInWindow();

			}
		});
		cBoxMusique.setBounds(294, 560, 216, 40);
		contentPane.add(cBoxMusique);
		enregistrerUnComposant(cBoxMusique) ;

		cBoxPolice = new JComboBox<Font>();
		cBoxPolice.setModel(new DefaultComboBoxModel(new String[] {
				policesDispo[0].getName(), policesDispo[1].getName()
		}));
		cBoxPolice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.requestFocusInWindow();
				afficherMessageEtLancerPropertyChange("Vous avez modifié la police d'écriture de "
						+ "l'application pour : " + cBoxPolice.getSelectedItem()) ;
			}
		});
		cBoxPolice.setBounds(278, 416, 216, 40);
		contentPane.add(cBoxPolice);
		enregistrerUnComposant(cBoxPolice) ;

		cBoxModeApp = new JComboBox() ;
		cBoxModeApp.setModel(new DefaultComboBoxModel(new String[] {"Sélectionner un thème.", "Halloween", "BGY", "Alien"}));
		cBoxModeApp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*System.out.println(cBoxModeApp.getSelectedIndex()) ;
				if (cBoxModeApp.getSelectedIndex() != 0) {
					ic.setTheme(themesDispo[cBoxModeApp.getSelectedIndex() - 1]);
					appliquerTheme(ic) ;

					gest.modifierThemeActuel(themesDispo[cBoxModeApp.getSelectedIndex() - 1]) ;
					afficherMessageEtLancerPropertyChange("Vous avez modifié le thème de l'application "
							+ "pour : " + cBoxModeApp.getSelectedItem()) ;
				}*/
			}
		});
		cBoxModeApp.setBounds(278, 116, 217, 40);
		contentPane.add(cBoxModeApp);
		enregistrerUnComposant(cBoxModeApp) ;

		lblAvertissementTouche = new JLabel("N’oubliez pas d’utiliser la touche entrée pour "
				+ "certifier vos choix de touches.");
		lblAvertissementTouche.setBounds(10, 847, 500, 23);
		contentPane.add(lblAvertissementTouche);
		enregistrerUnComposant(lblAvertissementTouche) ;

		chckBoxMusique = new JCheckBox("Musique ?");
		chckBoxMusique.setSelected(true);
		chckBoxMusique.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.requestFocusInWindow();
				//musiqueActiverDesactiver();
			}
		});
		chckBoxMusique.setFont(new Font("Times New Roman", Font.BOLD, 20));
		chckBoxMusique.setBounds(378, 478, 132, 40);
		contentPane.add(chckBoxMusique);
		enregistrerUnComposant(chckBoxMusique) ;

		chckbxFleche = new JCheckBox("Flèches clavier ?");
		chckbxFleche.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.requestFocusInWindow();
				choixFlecheOuTouche();

			}
		});
		chckbxFleche.setFont(new Font("Tahoma", Font.PLAIN, 20));
		chckbxFleche.setBounds(294, 617, 200, 30);
		contentPane.add(chckbxFleche);
		enregistrerUnComposant(chckbxFleche) ;

		JLabel lblToucheFleche = new JLabel("Pour utiliser les flèches :");
		lblToucheFleche.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblToucheFleche.setBounds(10, 608, 281, 50);
		contentPane.add(lblToucheFleche);
		enregistrerUnComposant(lblToucheFleche) ;

		spLogsConfig = new JScrollPane();
		spLogsConfig.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		spLogsConfig.setBounds(8, 884, 502, 162);
		contentPane.add(spLogsConfig);

		txtaLogsConfig = new JTextArea();
		spLogsConfig.setViewportView(txtaLogsConfig);
		txtaLogsConfig.setEditable(false);
		txtaLogsConfig.setWrapStyleWord(true);
		txtaLogsConfig.setLineWrap(true);
		enregistrerUnComposant(txtaLogsConfig) ;

		JPanel panCreateurTheme = new JPanel();
		panCreateurTheme.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.WHITE, new Color(0, 0, 0)));
		panCreateurTheme.setBounds(6, 156, 504, 243);
		contentPane.add(panCreateurTheme);
		panCreateurTheme.setLayout(null);
		enregistrerUnComposant(panCreateurTheme) ;

		JButton btnAjouterTheme = new JButton("Appliquer le thème");
		btnAjouterTheme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*contentPane.requestFocusInWindow();
				Theme themeCree = new Theme(txtNom.getText(), panCoulPrim.getBackground(),
						panCoulSeco.getBackground(), panCoulTert.getBackground(),
						panCoulPol.getBackground()) ;
				gest.modifierThemeActuel(themeCree) ;
				afficherMessageEtLancerPropertyChange("Vous avez créé un thème : " + txtNom.getText()) ;
				//cBoxModeApp.add ;*/
			}
		});
		btnAjouterTheme.setBounds(149, 208, 205, 29);
		panCreateurTheme.add(btnAjouterTheme);
		enregistrerUnComposant(btnAjouterTheme) ;

		JLabel lblNom = new JLabel("Nom du dernier thème créé : ");
		lblNom.setBounds(6, 12, 188, 16);
		panCreateurTheme.add(lblNom);
		enregistrerUnComposant(lblNom) ;

		JLabel lblPrimaire = new JLabel("Couleur primaire : ");
		lblPrimaire.setBounds(6, 55, 188, 16);
		panCreateurTheme.add(lblPrimaire);
		enregistrerUnComposant(lblPrimaire) ;

		JLabel lblSecondaire = new JLabel("Couleur secondaire : ");
		lblSecondaire.setBounds(6, 98, 188, 16);
		panCreateurTheme.add(lblSecondaire);
		enregistrerUnComposant(lblSecondaire) ;

		JLabel lblTertiaire = new JLabel("Couleur tertiaire : ");
		lblTertiaire.setBounds(6, 141, 188, 16);
		panCreateurTheme.add(lblTertiaire);
		enregistrerUnComposant(lblTertiaire);

		JLabel lblPolice = new JLabel("Couleur de la police : ");
		lblPolice.setBounds(6, 184, 188, 16);
		panCreateurTheme.add(lblPolice);
		enregistrerUnComposant(lblPolice);

		txtNom = new JTextField(/*gest.obtenirThemeActuel().getNom()*/) ;
		txtNom.setBounds(206, 7, 292, 26);
		panCreateurTheme.add(txtNom);
		txtNom.setColumns(10);
		enregistrerUnComposant(txtNom);

		panCoulPrim = new JPanel();
		panCoulPrim.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panCoulPrim.setBounds(206, 55, 180, 16);
		panCreateurTheme.add(panCoulPrim);
		enregistrerUnComposant(panCoulPrim);

		btnModCoulPrim = new JButton("Modifier");
		btnModCoulPrim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*contentPane.requestFocusInWindow();
				Color coul = JColorChooser.showDialog(null, "Modifier la couleur primaire de votre "
						+ "thème", null) ;
						//gest.obtenirThemeActuel().getCouleurPrimaire()) ;
				
				if (!coul.equals(panCoulPol.getBackground()))
					panCoulPrim.setBackground(coul) ;
				else
					JOptionPane.showMessageDialog(null, "La couleur primaire ne peut pas être identique à la "
							+ "couleur du texte.") ;*/
			}
		});
		btnModCoulPrim.setBounds(398, 49, 100, 29);
		panCreateurTheme.add(btnModCoulPrim);
		enregistrerUnComposant(btnModCoulPrim) ;

		panCoulSeco = new JPanel();
		panCoulSeco.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panCoulSeco.setBounds(206, 98, 180, 16);
		panCreateurTheme.add(panCoulSeco);
		enregistrerUnComposant(panCoulSeco) ;

		JButton btnModCoulSeco = new JButton("Modifier");
		btnModCoulSeco.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*contentPane.requestFocusInWindow();
				Color coul = JColorChooser.showDialog(null, "Modifier la couleur secondaire de votre "
						+ "thème",
						gest.obtenirThemeActuel().getCouleurPrimaire()) ;
				panCoulSeco.setBackground(coul) ;*/
			}
		});
		btnModCoulSeco.setBounds(398, 92, 100, 29);
		panCreateurTheme.add(btnModCoulSeco);
		enregistrerUnComposant(btnModCoulSeco) ;

		panCoulTert = new JPanel();
		panCoulTert.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panCoulTert.setBounds(206, 141, 180, 16);
		panCreateurTheme.add(panCoulTert);
		enregistrerUnComposant(panCoulTert) ;

		JButton btnModCoulTert = new JButton("Modifier");
		btnModCoulTert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*contentPane.requestFocusInWindow();
				Color coul = JColorChooser.showDialog(null, "Modifier la couleur tertiaire de votre "
						+ "thème",
						gest.obtenirThemeActuel().getCouleurPrimaire()) ;
				panCoulTert.setBackground(coul) ;*/
			}
		});
		btnModCoulTert.setBounds(398, 135, 100, 29);
		panCreateurTheme.add(btnModCoulTert);
		enregistrerUnComposant(btnModCoulTert) ;

		panCoulPol = new JPanel();
		panCoulPol.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panCoulPol.setBounds(206, 184, 180, 16);
		panCreateurTheme.add(panCoulPol);
		enregistrerUnComposant(panCoulPol) ;

		btnModCoulPol = new JButton("Modifier");
		btnModCoulPol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*contentPane.requestFocusInWindow();
				Color coul = JColorChooser.showDialog(null, "Modifier la couleur de la police de votre "
						+ "thème",
						gest.obtenirThemeActuel().getCouleurPrimaire()) ;
				
				if (!coul.equals(panCoulPrim.getBackground()))
					panCoulPol.setBackground(coul) ;
				else
					JOptionPane.showMessageDialog(null, "La couleur primaire ne peut pas être identique à la "
							+ "couleur du texte.") ;*/
			}
		});
		btnModCoulPol.setBounds(398, 178, 100, 29);
		panCreateurTheme.add(btnModCoulPol);
		enregistrerUnComposant(btnModCoulPol) ;
		//enregistrerUnComposant(lblErreurTheme) ;

		ic = new InfoConfiguration(null/*gest.obtenirThemeActuel()*/,
				(String) cBoxPolice.getSelectedItem(), chckBoxMusique.isSelected(),
				(String) cBoxMusique.getSelectedItem(), 'A', 'D') ;
		appliquerTheme(ic) ;
	}
	/**
	 * Méthode permettant de choisir si on veut contrôler les flippers avec les touches ou les flèches.
	 */
	//Aimé Melançon
	private void choixFlecheOuTouche() {
		if(chckbxFleche.isSelected()) {
			txtFCoteDroit.setEnabled(false);
			txtFCoteGauche.setEnabled(false);
			toucheDroite.firePropertyChange("Droite","Marcus","->");
			toucheGauche.firePropertyChange("Gauche","Krankenhaus","<-");
			fleche=true;
		}else {
			txtFCoteDroit.setEnabled(true);
			txtFCoteGauche.setEnabled(true);
			toucheGauche.firePropertyChange("Gauche", "Patate", String.valueOf(coteGauche).toUpperCase());
			toucheDroite.firePropertyChange("Droite","Anticonstituionnellement",String.valueOf(coteDroit).toUpperCase());
			fleche=false;
		}

	}
	
	/**
	 * Méthode permettant de  gérer l'assignation de la touche du flipper gauche.
	 */
	private void gererCoteGauche() {
		int nbLettre = txtFCoteGauche.getText().length();

		if(nbLettre == 1) {
			char mot =	txtFCoteGauche.getText().charAt(0);
			coteGauche= mot;
			String correction = mot+"";
			txtFCoteGauche.setText(correction);
			afficherMessageEtLancerPropertyChange("Vous avez modifié la touche gauche pour : " + coteGauche);
			toucheGauche.firePropertyChange("Gauche", "Patate", String.valueOf(mot).toUpperCase());

			//afficherMessageEtLancerPropertyChange("Vous avez modifié la touche gauche "
			//		+ "du flipper pour : " + correction) ;
		}
	}
	
	/**
	 * Méthode permettant de gérer l'assignation de la touche du flipper droit.
	 */
	//Aimé Melançon
	protected void gererCoteDroit() {
		int nbLettre = txtFCoteDroit.getText().length();

		if(nbLettre == 1) {
			char mot =	txtFCoteDroit.getText().charAt(0);
			coteDroit =mot;
			String correction = mot + "";
			txtFCoteDroit.setText(correction);

			afficherMessageEtLancerPropertyChange("Vous avez modifié la touche droit pour : " + coteDroit);
			toucheDroite.firePropertyChange("Droite","Anticonstituionnellement",String.valueOf(mot).toUpperCase());


			//afficherMessageEtLancerPropertyChange("Vous avez modifié la touche droite "
			//+ "du flipper pour : " + correction, "Droite", String.valueOf(mot).toUpperCase()) ;
		}
	}

	/**
	 * Méthode servant à ajouter un composant dans la liste des composants pour la modification.
	 * @param composant : Le composant à ajouter dans la liste.
	 */
	// Par Elias Kassas
	private void enregistrerUnComposant(Component composant) {
		lesComposants.add(composant) ;
	}

	/**
	 * Méthode privée servant à appliquer un thème à l'application.
	 * @param ic : Le nouvel objet InfoConfiguration à appliquer sur l'application.
	 */
	// Par Elias Kassas
	public void appliquerTheme(InfoConfiguration ic) {
		for (Component composant : lesComposants) {
			Font policeAct = composant.getFont() ;
			if (policeAct == null)
				revalidate() ;
			else {
				composant.setFont(new Font(ic.getPoliceDeCaractere(), policeAct.getStyle(), policeAct.getSize())) ;
				composant.setForeground(ic.getTheme().getCouleurPolice()) ;
			}

			if (composant instanceof JPanel) {
				composant.setBackground(ic.getTheme().getCouleurSecondaire()) ;
				((JComponent) composant).setBorder(new LineBorder(ic.getTheme().getCouleurTertiaire(),
						1, true)) ;
			}
		}

		/*txtNom.setText(/*gest.obtenirThemeActuel().getNom()) ;
		panCoulPrim.setBackground(gest.obtenirThemeActuel().getCouleurPrimaire()) ;
		panCoulSeco.setBackground(gest.obtenirThemeActuel().getCouleurSecondaire()) ;
		panCoulTert.setBackground(gest.obtenirThemeActuel().getCouleurTertiaire()) ;
		panCoulPol.setBackground(gest.obtenirThemeActuel().getCouleurPolice()) ;*/

		contentPane.setBackground(ic.getTheme().getCouleurPrimaire()) ;
		//gest.modifierThemeActuel(ic.getTheme()) ;
		revalidate() ;
		repaint() ;
	}

	/**
	 * Méthode permettant d'avoir l'indice de choix de l'utilisateur en terme de touche.
	 * @return Si l'utilisateur choisis d'utiliser les flèches ou des touches personnalisé.
	 */
	//Aimé Melançon
	public static boolean isFleche() {
		return fleche;
	}

	/**
	 * Méthode permettant de connaître la valeur d'entrée sélectionnée du côté droit.
	 * @return La touche du côté droit
	 */
	//Aimé Melançon
	public  char getCoteDroit() {
		return coteDroit;
	}

	/**
	 * Méthode permettant de connaître la valeur d'entrée sélectionnée du côté gauche.
	 * @return La touche du côté gauche
	 */
	//Aimé Melançon
	public  char getCoteGauche() {
		return coteGauche;
	}

	/**
	 * Méthode servant à ajouter un PropertyChangeListener sur un graphique.
	 * @param listener : L'écouteur de PropertyChange.
	 */
	// Par Elias Kassas
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
		this.toucheDroite.addPropertyChangeListener(listener);
		this.toucheGauche.addPropertyChangeListener(listener);
	}

	/**
	 * Méthode privée permettant d'afficher un message dans les logs de la configuration.
	 * @param message : Le message à afficher.
	 */
	// Par Elias Kassas
	private void afficherMessageEtLancerPropertyChange(String message) {
		txtaLogsConfig.append("+ " + message + ".\n") ;
		System.out.println("On envoie à l'application la propriété ic.") ;
		//this.pcs.firePropertyChange(propriete, !true, objetAEnvoyer) ;
		ic = new InfoConfiguration(null, //gest.obtenirThemeActuel(),
				(String) cBoxPolice.getSelectedItem(),
				chckBoxMusique.isSelected(), (String) cBoxMusique.getSelectedItem(),
				txtFCoteDroit.getText().charAt(0), txtFCoteGauche.getText().charAt(0)) ;

		this.pcs.firePropertyChange("ic", null, ic) ;
		appliquerTheme(ic) ;
	}

	/**
	 * Méthode permettant d'obtenir l'objet InfoConfiguration associé à la présente page.
	 * @return l'objet InfoConfiguration associé à la présente page.
	 */
	// Par Elias Kassas
	public InfoConfiguration obtenirConfiguration() {
		return ic ;
	}

	/**
	 * Méthode permettant de tester l'attribution des touches.
	 * @param e La touche du clavier sélectionné
	 */
	//Aimé Melançon
	private void testKey(KeyEvent e) {
		if(!fleche) {
			char code = e.getKeyChar();
			if(code== coteGauche) {
				System.out.println("Gauche");
			}
			if(code == coteDroit) {
				System.out.println("Droit");
			}
		}else {
			int code = e.getKeyCode();

			switch(code){
			case KeyEvent.VK_LEFT :
				System.out.println("Gauche");
				break;
			case KeyEvent.VK_RIGHT :
				System.out.println("Droit");
				break;
			}
		}
	}

	/**
	 * Méthode permettant logiquement de réinitialiser les touches.
	 */
	//Aimé Melançon
	public void reinitialiserTouches() {
		txtFCoteDroit = new JTextField("d");
		txtFCoteGauche = new JTextField("a");
		coteGauche = 'a' ;
		coteDroit = 'd' ;
	}
}