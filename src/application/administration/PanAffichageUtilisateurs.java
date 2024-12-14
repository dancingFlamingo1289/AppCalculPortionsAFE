package application.administration;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import annexe.Annexe;
import gestion.FichierTexte;
import gestion.utilisateurs.GestionnaireUtilisateurs;
import gestion.utilisateurs.Utilisateur;

public class PanAffichageUtilisateurs extends JPanel {
	private JTable table;
	private DefaultTableModel tableModel ;
	private FichierTexte userList ;
	private boolean isMasked = true; // Variable pour déterminer si les mots de passe doivent être masqués
	private JCheckBox checkboxMasquerMotDePasse; // Case à cocher pour masquer ou afficher les mots de passe
	private String[] columnNames = {"Nom d'utilisateur", "Mot de passe", "Administrateur"};
	private GestionnaireUtilisateurs gu;

	/**
	 * Constructeur
	 */
	public PanAffichageUtilisateurs() {
		userList = new FichierTexte(Annexe.REPERTOIRE_CONNEXION, Annexe.NOM_CONNEXION);
		gu = new GestionnaireUtilisateurs(Annexe.REPERTOIRE_CONNEXION, Annexe.NOM_CONNEXION);

		// Utiliser un BorderLayout
		setLayout(new BorderLayout());

		// Créer la case à cocher pour masquer le mot de passe
		checkboxMasquerMotDePasse = new JCheckBox("Masquer les mots de passe", isMasked); // Par défaut, masqué
		checkboxMasquerMotDePasse.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// Mettre à jour l'état de la case et actualiser l'affichage
				isMasked = checkboxMasquerMotDePasse.isSelected();
				table.setEnabled(!isMasked);
				recupererEtActualiser();
			}
		});

		// Créer le panneau pour la case à cocher
		JPanel panelTop = new JPanel();
		panelTop.add(checkboxMasquerMotDePasse);

		// Créer la JTable avec le modèle
		tableModel = new DefaultTableModel(columnNames, 0);
		table = new JTable(tableModel) ;
		table.setEnabled(!isMasked);
		table.setFillsViewportHeight(true);

		// Ajouter la table dans un JScrollPane pour la rendre scrollable
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		// Ajouter la case à cocher et la table au panneau
		add(panelTop, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);

		// Ajouter le panneau d'enregistrement avec le bouton
		JPanel panEnreg = new JPanel();
		JButton btnActualiser = new JButton("Enregistrer");
		btnActualiser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!isMasked) {
					enregistrer();
				}
			}
		});
		panEnreg.add(btnActualiser);

		// Ajouter les boutons "Ajouter" et "Retirer" au bas du panneau
		JPanel panelAR = new JPanel();
		JButton btnAjout = new JButton("Ajouter un utilisateur");
		btnAjout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ajouterLigne();
			}
		});
		panelAR.add(btnAjout);

		JButton btnRetrait = new JButton("Retirer l'utilisateur");
		btnRetrait.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				retirer(table.getSelectedRow());
			}
		});
		panelAR.add(btnRetrait);

		// Panneau englobant pour "Enregistrer", "Ajouter" et "Retirer"
		JPanel panelBottom = new JPanel();
		panelBottom.setLayout(new BoxLayout(panelBottom, BoxLayout.Y_AXIS)); // Organisation verticale
		panelBottom.add(panEnreg);
		panelBottom.add(panelAR);

		add(panelBottom, BorderLayout.SOUTH);

		// Remplir le modèle avec les utilisateurs
		recupererEtActualiser();
	}

	private void ajouterLigne() {
		tableModel.addRow(new Object[]{"", "", false});
	}

	private void retirer(int index) {
		tableModel.removeRow(index);
	}

	private void enregistrer() {
		// Vider le fichier avant d'écrire les nouvelles données
		userList.effacer();

		// Récupérer les anciens utilisateurs du fichier
		ArrayList<Utilisateur> nouveau = new ArrayList<Utilisateur>();

		// Récupérer les nouvelles informations de la JTable
		for (int i = 0; i < table.getModel().getRowCount(); i++) {
			char[] username = ((String) table.getModel().getValueAt(i, 0)).toCharArray();
			char[] password = ((String) table.getModel().getValueAt(i, 1)).toCharArray();
			boolean admin = (Boolean) table.getModel().getValueAt(i, 2) ;
			nouveau.add(new Utilisateur(username, password)) ;
		}

		// Écrire les nouveaux utilisateurs dans le fichier
		for (Utilisateur nouvUtil : nouveau) {
			userList.ecrire(nouvUtil);
		}

		// Mettre à jour l'affichage après l'enregistrement
		recupererEtActualiser();
	}

	/**
	 * Méthode pour récupérer les utilisateurs et actualiser la JTable
	 */
	public void recupererEtActualiser() {
		ArrayList<Utilisateur> users = GestionnaireUtilisateurs.getUserList(Annexe.REPERTOIRE_CONNEXION, Annexe.NOM_CONNEXION);

		tableModel = new DefaultTableModel(columnNames, 0) ; // Effacer les anciennes données

		for (Utilisateur u : users) {
			if (u != null) { // Vérification que u n'est pas null
		        String motDePasse = isMasked ? getMaskedPassword(new String(u.getmDeP())) : new String(u.getmDeP());
		        tableModel.addRow(new Object[]{
		                new String(u.getNomUtil()), motDePasse
		        });
		    } else {
		        System.err.println("Utilisateur null dans la liste.");
		    }
			/*String motDePasse = isMasked ? getMaskedPassword(new String(u.getmDeP())) : new String(u.getmDeP());
			tableModel.addRow(new Object[]{
					new String(u.getNomUtil()), motDePasse, u.estAdministrateur()
			}) ;*/
		}

		table.setModel(tableModel);
		revalidate();
	}

	/**
	 * Méthode pour générer le mot de passe masqué avec des astérisques
	 */
	private String getMaskedPassword(String password) {
		int randomLength = (int) (12*Math.random() + 1) ;
		if (password == null || password.isEmpty()) {
			return "" ;
		}

		StringBuilder maskedPassword = new StringBuilder(password.length());
		for (int i = 0; i < randomLength ; i++) {
			maskedPassword.append("*");
		}

		return maskedPassword.toString();
	}

	/**
	 * Méthode pour changer l'état du masque
	 */
	private void setMasked(boolean isMasked) {
		this.isMasked = isMasked ;
		// Mise à jour de la table après avoir modifié l'état du masque
		recupererEtActualiser();
	}
}
