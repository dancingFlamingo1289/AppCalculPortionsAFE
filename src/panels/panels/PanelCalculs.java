package panels;

import java.awt.* ;
import java.awt.event.* ;
import java.beans.* ;
import java.io.* ;
import java.text.* ;
import java.time.* ;
import java.util.* ;
import javax.swing.* ;
import javax.swing.border.* ;
import javax.swing.event.* ;
import javax.swing.table.* ;

import annexe.Annexe;
import application.* ;
import gestion.* ;
import gestion.utilisateurs.* ;

/**
 * Cette page sert à afficher les résultats des calculs.
 */
public class PanelCalculs extends JPanel {
	/** Coefficient de sérialisation. **/
	private static final long serialVersionUID = 1L ;
	/** Fenêtre principale. **/
	private JPanel contentPane ;
	/** L'étiquette contenant le total. **/
	private JLabel lblTotal ;
	/** Somme des prêts et bourses. **/
	private double total = 0.00 ;
	/** Pourcentages associés aux prêts et aux bourses. **/
	private double pourcPrets = 0.00, pourcBourses = 0.00 ;
	/** Tourniquets pour les montants des prêts et des bourses. **/
	private JSpinner spnMntBoursesAnn, spnMntPretsAnn ;
	/** Étiquettes contenant les pourcentages de prêts et bourses. **/
	private JLabel lblPourcPrets, lblPourcBourses ;
	private DefaultTableModel model ;
	private JSpinner spnAnneeDebut ;
	private JLabel lblAnneeFin ;
	private String[] months = new String[12] ;
	/** Emplacements pour aller chercher les fichiers et les créer. **/
	private File repertoire = Annexe.REPERTOIRE_UTILISATEURS, repertoireUtil ;
	/** Console de l'application. **/
	private JTextArea txtaMessage ;
	/** Tableau dans lequel seront affichés les résultats des calculs. **/
	private JTable table ;
	/** Objet pour la gestion des levées d'évènements. **/
	protected final PropertyChangeSupport pcs = new PropertyChangeSupport(this) ;
	/** Nom d'utilisateur de l'utilisateur actuel. **/
	private String utilActuel = "", utilVoulu = "" ;
	/** Message de bienvenue. **/
	private final String BIENVENUE = "Bienvenue ! Cette application sert à calculer la portion de prêts et de bourses "
			+ "perçue par mois de la part de l'AFE.\n" ;

	/**
	 * Create the panel.
	 */
	public PanelCalculs() {
		setBackground(Color.PINK);
		setBounds(100, 100, 642, 775);
		setLayout(null);

		JLabel lblTitre = new JLabel("Calculer la portion par mois de prêts et bourses de l'AFE");
		lblTitre.setBounds(3, 0, 634, 26);
		add(lblTitre);
		lblTitre.setForeground(Color.YELLOW);
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setFont(new Font("Lucida Grande", Font.BOLD, 16));

		JLabel lblSommeAlloueAux = new JLabel("Somme allouée aux prêts pour l'année :");
		lblSommeAlloueAux.setBounds(13, 33, 263, 16);
		add(lblSommeAlloueAux);
		lblSommeAlloueAux.setForeground(Color.YELLOW);

		JLabel lblMontantAllouAux = new JLabel("Somme allouée aux bourses pour l'année :");
		lblMontantAllouAux.setBounds(13, 61, 274, 16);
		add(lblMontantAllouAux);
		lblMontantAllouAux.setForeground(Color.YELLOW);

		JLabel lblNewLabel_2 = new JLabel("Total :");
		lblNewLabel_2.setBounds(13, 89, 61, 16);
		add(lblNewLabel_2);
		lblNewLabel_2.setForeground(Color.YELLOW);

		spnMntPretsAnn = new JSpinner();
		spnMntPretsAnn.setBounds(288, 28, 219, 26);
		add(spnMntPretsAnn);
		spnMntPretsAnn.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				affecter() ;
			}
		});
		spnMntPretsAnn.setModel(new SpinnerNumberModel(0d, 0d, null, 0.50d)) ;

		spnMntBoursesAnn = new JSpinner();
		spnMntBoursesAnn.setBounds(288, 56, 219, 26);
		add(spnMntBoursesAnn);
		spnMntBoursesAnn.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				affecter() ;
			}
		});
		spnMntBoursesAnn.setModel(new SpinnerNumberModel(0d, 0d, null, 0.50d)) ;

		JLabel lblDollar0 = new JLabel("$");
		lblDollar0.setBounds(519, 33, 39, 16);
		add(lblDollar0);
		lblDollar0.setForeground(Color.YELLOW);

		JLabel lblDollar1 = new JLabel("$");
		lblDollar1.setBounds(519, 61, 39, 16);
		add(lblDollar1);
		lblDollar1.setForeground(Color.YELLOW);

		lblTotal = new JLabel(String.format("%.0f", total) + "") ;
		lblTotal.setBounds(288, 89, 219, 16);
		add(lblTotal);
		lblTotal.setForeground(new Color(128, 0, 128));
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel lblNewLabel_4 = new JLabel("$");
		lblNewLabel_4.setBounds(519, 89, 39, 16);
		add(lblNewLabel_4);
		lblNewLabel_4.setForeground(Color.YELLOW);

		lblPourcPrets = new JLabel(String.format("%.2f", pourcPrets) + "%");
		lblPourcPrets.setBounds(562, 33, 61, 16);
		add(lblPourcPrets);
		lblPourcPrets.setForeground(new Color(128, 0, 128));
		lblPourcPrets.setHorizontalAlignment(SwingConstants.CENTER);

		lblPourcBourses = new JLabel(String.format("%.2f", pourcBourses) + "%");
		lblPourcBourses.setBounds(562, 61, 61, 16);
		add(lblPourcBourses);
		lblPourcBourses.setForeground(new Color(128, 0, 128));
		lblPourcBourses.setHorizontalAlignment(SwingConstants.CENTER);

		spnAnneeDebut = new JSpinner();
		spnAnneeDebut.setBounds(188, 184, 82, 26);
		add(spnAnneeDebut);
		spnAnneeDebut.setForeground(new Color(128, 0, 128));
		spnAnneeDebut.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				lblAnneeFin.setText(((int) spnAnneeDebut.getValue() + 1) + "");
				updateMonths() ;
				resetTable() ;
				spnMntPretsAnn.setValue(0.00) ;
				spnMntBoursesAnn.setValue(0.00) ;
				recuperer() ;
			}
		});
		spnAnneeDebut.setModel(new SpinnerNumberModel(LocalDate.now().getYear(), 1970, LocalDate.now().getYear() + 1, 1)) ;

		JLabel lblNewLabel = new JLabel("-");
		lblNewLabel.setBounds(282, 189, 61, 16);
		add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

		lblAnneeFin = new JLabel(((int) spnAnneeDebut.getValue() + 1) + "");
		lblAnneeFin.setBounds(355, 182, 61, 26);
		add(lblAnneeFin);
		lblAnneeFin.setForeground(new Color(128, 0, 128));
		lblAnneeFin.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblAnnees = new JLabel("Pour l'année scolaire : ");
		lblAnnees.setBounds(13, 189, 163, 16);
		add(lblAnnees);
		lblAnnees.setForeground(Color.YELLOW);

		JPanel panel = new JPanel();
		panel.setBounds(30, 451, 571, 50);
		add(panel);
		panel.setBackground(Color.PINK);
		panel.setBorder(new LineBorder(new Color(102, 204, 255), 1, true));

		JButton btnCalculer = new JButton("Calculer");
		btnCalculer.setForeground(new Color(128, 0, 128));
		btnCalculer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calculer() ;
			}
		});
		btnCalculer.setBounds(228, 0, 117, 50);
		panel.add(btnCalculer);
		panel.setLayout(null) ;

		JButton btnGenererFich = new JButton("Sauvegarder");
		btnGenererFich.setForeground(new Color(128, 0, 128));
		btnGenererFich.setToolTipText("");
		btnGenererFich.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calculer() ;
				sauvegarderSurOrdinateur() ;
			}
		});
		btnGenererFich.setBounds(357, 0, 117, 50);
		panel.add(btnGenererFich);

		JButton btnImprimer = new JButton("Imprimer");
		btnImprimer.setForeground(new Color(128, 0, 128));
		btnImprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Imprimante.imprimerFichier2(new File("/Users/elias/.CalculsAFE/Calcul allocation par mois pour "
				//+ (int) spnAnneeDebut.getValue() + "-" + lblAnneeFin.getText() + ".txt")) ;
				impr() ;
			}
		});
		btnImprimer.setToolTipText("");
		btnImprimer.setBounds(99, 0, 117, 50);
		panel.add(btnImprimer);

		JScrollPane spMessage = new JScrollPane();
		spMessage.setBounds(28, 513, 574, 116);
		add(spMessage);
		spMessage.setAutoscrolls(true);
		spMessage.setInheritsPopupMenu(true);
		spMessage.setIgnoreRepaint(true);
		spMessage.setFocusTraversalPolicyProvider(true);
		spMessage.setFocusCycleRoot(true);
		spMessage.setDoubleBuffered(true);
		spMessage.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		txtaMessage = new JTextArea();
		txtaMessage.setText("Bienvenue ! Cette application sert à calculer la portion de prêts et de bourses perçue par mois de la part "
				+ "de l'AFE.\n") ;
		spMessage.setViewportView(txtaMessage);
		txtaMessage.setLineWrap(true);
		txtaMessage.setWrapStyleWord(true);
		txtaMessage.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		txtaMessage.setEditable(false);

		// Définir les colonnes de la table
		String[] columnNames = {"Mois", "Montant des prêts", "Montant des bourses", "Total du mois"};

		// Initialiser le modèle de la table avec les colonnes
		model = new DefaultTableModel(columnNames, 12) {
			// Méthode pour rendre les cellules non modifiables
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 3 ; // Seule la colonne du total par mois est modifiable.
			}
		};

		// Créer la table avec le modèle de données
		table = new JTable(model);
		table.setFillsViewportHeight(true);
		JTableHeader header = table.getTableHeader() ;
		header.setFont(new Font("Sans Serif", Font.BOLD, 14));
		header.setBackground(new Color(70, 130, 180));
		header.setForeground(Color.WHITE);
		header.setBorder(new LineBorder(Color.DARK_GRAY));

		// Définir la largeur des colonnes (facultatif)
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(170);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);
		updateMonths() ;

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(25, 215, 581, 224); // Ajustez les dimensions et la position selon vos besoins
		add(scrollPane);

		JButton btnDeconnexion = new JButton("Se déconnecter");
		btnDeconnexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deconnexion() ;
			}
		});
		btnDeconnexion.setForeground(new Color(128, 0, 128));
		btnDeconnexion.setBounds(225, 633, (int) 177, (int) 75);
		add(btnDeconnexion);
		
		JPanel panel_1 = new JPanel() ;
		panel_1.setBorder(new TitledBorder(null, "Titre", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(13, 117, 610, 57) ;
		add(panel_1) ;
		panel_1.setLayout(null) ;
		
		JComboBox<String> comboBox = new JComboBox<String>() ;
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setUtilisateurVoulu((String) comboBox.getSelectedItem()) ;
			}
		});
		comboBox.setModel(new DefaultComboBoxModel<String>(getDirectoryNames(repertoire))) ;
		comboBox.setBounds(173, 15, 264, 27) ;
		panel_1.add(comboBox) ;
	}

	public static String[] getDirectoryNames(File folder) {
        ArrayList<String> directoryNames = new ArrayList<>() ;
        
        // Vérifie que le chemin est un dossier
        if (folder.isDirectory()) {
            // Liste les fichiers et dossiers et filtre uniquement les dossiers
            File[] directories = folder.listFiles(File::isDirectory);
            if (directories != null) {
                for (File dir : directories) {
                    directoryNames.add(dir.getName());
                }
            }
        }

        String[] dirs = new String[directoryNames.size()] ;
        for (int i = 0 ; i < dirs.length ; i++) {
        	dirs[i] = directoryNames.get(i) ;
        }
        directoryNames.clear() ;
        
        return dirs ;
    }
	
	private void updateMonths() {
		String[] months = {
				"Janvier (" + lblAnneeFin.getText() + ")", "Février (" + lblAnneeFin.getText() + ")", 
				"Mars (" + lblAnneeFin.getText() + ")", "Avril (" + lblAnneeFin.getText() + ")", 
				"Mai (" + lblAnneeFin.getText() + ")", "Juin (" + lblAnneeFin.getText() + ")", 
				"Juillet (" + lblAnneeFin.getText() + ")", "Août (" + lblAnneeFin.getText() + ")", 
				"Septembre (" + spnAnneeDebut.getValue() + ")", "Octobre (" + spnAnneeDebut.getValue() + ")", 
				"Novembre (" + spnAnneeDebut.getValue() + ")", "Décembre (" + spnAnneeDebut.getValue() + ")"
		};
		this.months = months ;

		for (int i = 0; i < 12; i++) {
			table.getModel().setValueAt(this.months[i], i, 0);
		}
	}

	private void sauvegarderSurOrdinateur() {
		updateMonths() ;
		double[][] bilan = calculerEtRenvoyer() ;

		if (bilan != null) {
			FichierTexte f = new FichierTexte(repertoireUtil, "Calcul allocation par mois pour " + (int) spnAnneeDebut.getValue() + "-" 
					+ lblAnneeFin.getText()) ;

			f.ecrire("\nPour l'année scolaire : " + (int) spnAnneeDebut.getValue() + "-" + lblAnneeFin.getText());

			f.ecrire("Montant annuel pour les prêts ($) : " + (double) spnMntPretsAnn.getValue()) ;
			f.ecrire("Montant annuel pour les bourses ($) : " + (double) spnMntBoursesAnn.getValue()) ;
			f.ecrire("Total : " + String.format("%.2f", total) + "$\n") ;

			f.ecrire("Mois \t Montant des prêts \t Montant des bourses \t Total") ;

			for (int i = 0 ; i < bilan.length ; i++) 
				f.ecrire(this.months[i] + "\t\t" + String.format("%.2f", bilan[i][0]) + "$\t\t" + 
						String.format("%.2f", bilan[i][1]) + "$\t\t" + table.getModel().getValueAt(i, 3)) ;

			f.ecrire("\nDate de la dernière génération du fichier : " + LocalDate.now().toString()) ;		
			messageSauvegarde() ;
		} else {
			recuperer() ;
		}
	}

	/**
	 * Méthode servant à récupérer les fichiers et afficher les informations de ce fichier.
	 */
	public void recuperer() {
		String nomTronque = "Calcul allocation par mois pour "
				+ (int) spnAnneeDebut.getValue() + "-" + lblAnneeFin.getText() + ".txt",
				nomFichier = repertoireUtil + "/" + nomTronque ;
		System.out.println(nomFichier) ;
		
		try {
			ArrayList<String> fichier = FichierTexte.lire(new File(nomFichier)).getContenuFichier() ;
			
			int pret = 0, bourse = 0 ;
			int ligneDebut = 7, ligneAvant = 2 ;
			for (int i = ligneDebut ; i < fichier.size() - ligneAvant ; i++) {
				String ligne = fichier.get(i) ;
				String[] ligneSep = ligne.split("\t\t") ;

				NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
				try {
					// Modification du montant de prêts par mois
					ligneSep[1] = ligneSep[1].substring(0, ligneSep[1].length());
					table.getModel().setValueAt(ligneSep[1], i - ligneDebut, 1);
					pret += format.parse(ligneSep[1]).doubleValue() ;

					// Modification du montant de bourses par mois
					ligneSep[2] = ligneSep[2].substring(0, ligneSep[2].length());
					table.getModel().setValueAt(ligneSep[2], i - ligneDebut, 2);
					bourse += format.parse(ligneSep[2]).doubleValue() ;
				} catch (ParseException e) {
					e.printStackTrace() ;
				}
				
				// Modification du montant total par mois
				table.getModel().setValueAt(ligneSep[3], i - ligneDebut, 3) ;
			}

			pret = Math.abs(pret) ;
			bourse = Math.abs(bourse) ;
			spnMntPretsAnn.setValue(pret) ;
			spnMntBoursesAnn.setValue(bourse) ;
			affecter() ;
		} catch (IOException e1) {
			erreurLecture(nomFichier) ;
			//e1.printStackTrace() ;
		}
	}

	private void affecter() {
		total = CalculsAFE.somme(((Number) spnMntPretsAnn.getValue()).intValue(), 
				((Number) spnMntBoursesAnn.getValue()).intValue()) ;
		pourcPrets = CalculsAFE.pourcentage(((Number) spnMntPretsAnn.getValue()).doubleValue(), total) ;
		pourcBourses = CalculsAFE.pourcentage(((Number) spnMntBoursesAnn.getValue()).doubleValue(), total) ;

		lblTotal.setText(String.format("%.2f", total));
		lblPourcPrets.setText(String.format("%.2f", pourcPrets) + "%");
		lblPourcBourses.setText(String.format("%.2f", pourcBourses) + "%");
	}

	private void calculer() {
		double totalDbl = 0 ;
		int totalInt = 0 ;
		for (int i = 0 ; i < 12 ; i++) {
			double[] montantDuMois = CalculsAFE.calculParMois(pourcPrets, pourcBourses, 
					Double.parseDouble((String) table.getModel().getValueAt(i, 3))) ;
			//System.out.println((i+1) + "\t" + String.format("%.2f", montantDuMois[0]) + "$\t" + String.format("%.2f", montantDuMois[1]) + "$") ;

			totalDbl += montantDuMois[0] + montantDuMois[1] ;
			table.getModel().setValueAt(String.format("%.2f", montantDuMois[0]), i, 1) ;
			table.getModel().setValueAt(String.format("%.2f", montantDuMois[1]), i, 2) ;
		}
		totalInt = (int) Math.round(totalDbl) ;

		if (totalInt != total) {
			txtaMessage.append("(f) Le total annuel entré ne correspond pas avec la somme des totaux mensuels calculée (" 
					+ totalInt + "$)."
					+ "\nS'il vous plaît corriger cela en éditant les nombres entrés dans la colonne "
					+ "\"Total du mois\" ou en éditant "
					+ "les tourniquets associés aux montants annuels de prêts et de bourses.\n") ;
			recuperer() ;
		}
	}

	/**
	 * Méthode permettant de faire le calcul et retourner la matrice contenant les parts de prêts et bourses.
	 * @return la matrice contenant les parts de prêts et bourses
	 */
	private double[][] calculerEtRenvoyer() {
		double[][] bilanAnnee = new double[12][2] ;

		double totalDbl = 0 ;
		int totalInt = 0 ;
		for (int i = 0 ; i < 12 ; i++) {
			double[] montantDuMois = CalculsAFE.calculParMois(pourcPrets, pourcBourses, 
					Double.parseDouble((String) table.getModel().getValueAt(i, 3))) ;
			bilanAnnee[i] = montantDuMois ;
			totalDbl += montantDuMois[0] + montantDuMois[1] ;
		}

		totalInt = (int) Math.round(totalDbl) ;
		if (totalInt != total) {
			txtaMessage.append("(f) Le total annuel entré ne correspond pas avec la somme des totaux mensuels calculée (" + totalInt + "$)."
					+ "\nS'il vous plaît corriger cela en éditant les nombres entrés dans la colonne \"Total du mois\" ou en éditant "
					+ "les tourniquets associés aux montants annuels de prêts et de bourses.\n") ;
			recuperer() ;
			return null ;
		}

		return bilanAnnee ;
	}

	/**
	 * Méthode permettant de réinitialiser le tableau.
	 */
	public void resetTable() {
		for (int i = 0; i < table.getRowCount(); i++) {
			table.getModel().setValueAt("0.00", i, 1); // Réinitialiser les montants des prêts
			table.getModel().setValueAt("0.00", i, 2); // Réinitialiser les montants des bourses
			table.getModel().setValueAt("0.00", i, 3); // Réinitialiser le total du mois
		}
	}

	private void imprimer() {
		ArrayList<Object> message = new ArrayList<Object>() ;
		message.add("Sélectionnez les années que vous souhaitez inclure dans le calcul.") ;

		if (!repertoire.exists() || !repertoire.isDirectory()) {
			JOptionPane.showMessageDialog(null, "Le répertoire \".CalculsAFE\" est introuvable.") ;
			return ;
		}

		File[] listeDeFichiers = repertoire.listFiles() ;
		if (listeDeFichiers == null) {
			JOptionPane.showMessageDialog(null, "Erreur lors de la lecture des fichiers dans le répertoire \"" + repertoire.getName() + "\".");
			return ;
		}

		for (int i = 1 ; i <= listeDeFichiers.length ; i++) {
			File fichier = listeDeFichiers[i - 1] ;
			if (fichier.isFile())
				message.add(new JCheckBox(i + " - " + fichier.getName())) ;
		}

		Object[] messageArr = message.toArray() ;
		JOptionPane.showInputDialog(null, messageArr) ;

		FichierTexte f = new FichierTexte(repertoire, "zero") ;

		f.ecrire(0);
	}

	private void impr() {
		String nomFichier = "/Users/elias/CalculsAFE/Calcul allocation par mois pour "
				+ (int) spnAnneeDebut.getValue() + "-" + lblAnneeFin.getText() + ".txt" ;
		Imprimante.imprimerFichier2(new File(nomFichier)) ;
	}

	private void messageSauvegarde() {
		txtaMessage.append("(s) La configuration pour " + (int) spnAnneeDebut.getValue() + "-" + ((int) spnAnneeDebut.getValue() + 1)  + 
				" a été sauvegardée. Pour la récupérer, il vous suffit d'utiliser le tourniquet pour indiquer l'année de début.\n"
				+ "Vous pouvez également utiliser le tourniquet pour l'imprimer à partir du bouton \"Imprimer\".\n") ;
	}

	private void erreurLecture(String nomTronque) {
		txtaMessage.append("(f) Le fichier \"" + nomTronque + "\" est inexistant dans la base de données. \n"
				+ "Il faut impérativement entrer les données pour les sauvegarder.\n") ;
	}

	private void deconnexion() {
		boolean[] canConnect = {false, false} ;
		Object[] output = {canConnect, new Utilisateur()} ;

		FichierTexte f = new FichierTexte(Annexe.REPERTOIRE, "logs") ;
		f.ecrire(new String(utilActuel + " logged out on " + Annexe.now())) ;
		
		pcs.firePropertyChange("connexionPossible", null, output) ;
		reinitialiser() ;
	}

	private void reinitialiser() {
		spnAnneeDebut.setValue(LocalDate.now().getYear()) ;
		txtaMessage.setText(BIENVENUE) ;
		spnMntPretsAnn.setValue(0) ;
		spnMntBoursesAnn.setValue(0) ;
		resetTable() ;
	}

	/**
	 * Méthode qui permet d'ajouter un écouteur de levée d'évènements.
	 * @param listener : L'écouteur.
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

	// GETTERS ET SETTERS
	public void setUtilisateurActuel(String nouv) {
		this.utilActuel = nouv ;
		String repUtil = repertoire + "/" + utilActuel + "/" ;
		
		repertoireUtil = new File(repUtil) ;
		//repertoireUtil = new File(repUtil) ;
		System.out.println("repUtil = " + repertoireUtil) ;
		recuperer() ;
	}
	
	public void setUtilisateurVoulu(String nouv) {
		this.utilVoulu = nouv ;
		String repUtil = repertoire + "/" + utilVoulu + "/" ;
		
		repertoireUtil = new File(repUtil) ;
		//repertoireUtil = new File(repUtil) ;
		System.out.println("repUtil = " + repertoireUtil) ;
		recuperer() ;
	}
}
