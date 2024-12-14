package panels ;

import java.awt.* ;
import java.awt.event.* ;
import java.beans.* ;
import javax.swing.* ;

import annexe.Annexe;
import attente.* ;
import gestion.FichierTexte;
import gestion.utilisateurs.* ;
import java.io.* ;
import java.time.LocalDate;

/**
 * Cette classe représente la page de connexion de l'application.
 */
public class FenetreConnexion extends JPanel {
	private static final long serialVersionUID = 1L;
	/** Composants pour se connecter. **/
	private JLabel lblSeConnecter, lblPassword, lblUsername ;
	/** Bouton pour vérifier l'authentification. **/
	private JButton btnLogin ;
	/** Zone de texte pour entrer un nom d'utilisateur. **/
	private JTextField textFieldUsername ;
	/** Zone de saisie pour entrer un mot de passe. **/
	private JPasswordField passwordField ;
	/** Objet pour la gestion des levées d'évènements. **/
	protected final PropertyChangeSupport pcs = new PropertyChangeSupport(this) ;
	/** Panneau d'attente de la fenêtre de protection. **/
	private PanneauAttente panneauAttenteHorloge ;
	/** Symbole de cadenas fermé. **/
	private final String FERME = "\uD83D\uDD12" ;
	/** Variable contenant le symbole de cadenas ouvert ou fermé. **/
	private String symbole = FERME ;
	/** Emplacements des fichiers à chercher. **/
	private File dossier = Annexe.REPERTOIRE_CONNEXION ;
	private String dataBase = Annexe.NOM_CONNEXION ;
	/** Utilitaire pour la gestion des utilisateurs. **/
	private GestionnaireUtilisateurs gu ;
	private JButton btnMdePasseOublie;

	/**
	 * Create the panel.
	 */
	public FenetreConnexion() {
		gu = new GestionnaireUtilisateurs(Annexe.REPERTOIRE_CONNEXION, dataBase) ;
		
		setBackground(Color.BLACK);
		setLayout(null);

		lblSeConnecter = new JLabel(symbole + " CONNEXION " + symbole);
		lblSeConnecter.setBounds(0, 0, 0, 0);
		lblSeConnecter.setForeground(Color.WHITE) ;
		lblSeConnecter.setFont(new Font("Lucida Grande", Font.BOLD, 25));
		lblSeConnecter.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblSeConnecter);

		lblUsername = new JLabel("Nom d'utilisateur :");
		lblUsername.setBounds(0, 0, 0, 0);
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		add(lblUsername);

		textFieldUsername = new JTextField();
		textFieldUsername.setBounds(0, 0, 0, 0);
		add(textFieldUsername);
		textFieldUsername.setColumns(10);

		lblPassword = new JLabel("Mot de passe :");
		lblPassword.setBounds(0, 0, 0, 0);
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		add(lblPassword);

		passwordField = new JPasswordField();
		passwordField.setBounds(0, 0, 0, 0);
		add(passwordField);

		btnLogin = new JButton("Se connecter");
		btnLogin.setBounds(0, 0, 0, 0);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connexion();
			}
		});
		btnLogin.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		add(btnLogin);

		panneauAttenteHorloge = new PanneauAttente();
		panneauAttenteHorloge.setBounds(0, 0, 0, 0);
		panneauAttenteHorloge.setBackground(Color.RED);
		panneauAttenteHorloge.demarrer() ;
		add(panneauAttenteHorloge);
		
		btnMdePasseOublie = new JButton("Mot de passe oublié ?");
		btnMdePasseOublie.setBounds(110, 234, 161, 29);
		add(btnMdePasseOublie);

		// Ajouter un écouteur pour détecter le redimensionnement du panneau
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent evt) {
				resizeComponents();  // Appel de la méthode pour redimensionner les composants
			}
		});
	}

	// Méthode pour redimensionner les composants
	private void resizeComponents() {
		int panelWidth = getWidth() ;
		int panelHeight = getHeight() ;

		lblSeConnecter.setBounds(panelWidth / 10, 10, panelWidth * 8 / 10, panelHeight / 10);
		lblUsername.setBounds(panelWidth / 10, panelHeight / 5, panelWidth * 3 / 10, panelHeight / 10);
		textFieldUsername.setBounds(panelWidth / 2, panelHeight / 5, panelWidth * 3 / 10, panelHeight / 10);
		lblPassword.setBounds(panelWidth / 10, panelHeight / 3, panelWidth * 3 / 10, panelHeight / 10);
		passwordField.setBounds(panelWidth / 2, panelHeight / 3, panelWidth * 3 / 10, panelHeight / 10);
		btnMdePasseOublie.setBounds(passwordField.getX() + passwordField.getWidth() + 6, passwordField.getY(), panelWidth * 3 / 10, passwordField.getHeight()) ;
		btnLogin.setBounds(panelWidth / 2 - panelWidth / 8, panelHeight / 2, panelWidth / 4, panelHeight / 10);
		panneauAttenteHorloge.setBounds(panelWidth / 4, panelHeight * 3 / 5, panelWidth / 2, panelHeight / 3);
	}

	/**
	 * Méthode qui permet d'ajouter un écouteur de levée d'évènements.
	 * @param listener : L'écouteur.
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

	private void connexion() {
		char[] inputUsername = textFieldUsername.getText().toCharArray() ;
		char[] inputPassword = passwordField.getPassword() ;
		Utilisateur inputUser = new Utilisateur(inputUsername, inputPassword) ;
		
		boolean[] connexion = gu.authentifiate(inputUser) ;
		Object[] output = {connexion, inputUser} ;
		
		if (connexion[0]) {
			FichierTexte f = new FichierTexte(Annexe.REPERTOIRE, "logs") ;
			f.ecrire(new String(new String(inputUsername) + " logged in on " + Annexe.now())) ;
			
			this.pcs.firePropertyChange("connexionPossible", null, output) ;
			System.out.println("Connexion réussie.") ;
		} else {
			System.err.println("Connexion échouée.") ;
			JOptionPane.showMessageDialog(null, "Le nom d'utilisateur ou le mot de passe est erroné.") ;
		}
		
		// Réinitialiser les champs
		textFieldUsername.setText("") ;
		passwordField.setText("") ;
	}
	
	/**
	 * Méthode servant à modifier la base de données
	 * @param db : la nouvelle base de données.
	 */
	public void setDataBase(String db) {
		this.dataBase = db ;
		gu.setDataBaseName(this.dataBase) ;
	}
}
