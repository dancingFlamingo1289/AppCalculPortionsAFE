package application ;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import application.aPropos.FenetreAPropos;
import gestion.utilisateurs.Utilisateur;
import panels.FenetreConnexion;
import panels.PanelCalculs;

/**
 * Application servant à calculer par mois la portion de prêts et bourses en fonction du montant annuel et total.
 * @author Elias Kassas
 */
public class ApplicationAFE extends JFrame {
	/** Coefficient de sérialisation. **/
	private static final long serialVersionUID = 1L ;
	/** Fenêtre principale. **/
	private JPanel contentPane ;
	/** La page qui affiche toutes les informations. **/
	private PanelCalculs panCalculs ;
	/** La page de connexion. **/
	private FenetreConnexion fenConnexion ;
	/** Le panel qui change selon le temps. **/
	private JPanel panel ;
	/** On détermine s'il s'agit de la première fois. **/
	private boolean premiereFois = true ;
	/** La barre de menu de l'application. **/
	private JMenuBar menuBar ;
	/** On détermine si l'affichage administrateur devrait avoir lieu. **/
	private boolean affAdmin = false ;
	/** L'utilisateur actuel utilisant l'application. **/
	private Utilisateur utilisateurActuel ;

	/**
	 * Lancer l'application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApplicationAFE frame = new ApplicationAFE();
					frame.setVisible(true) ;
				} catch (Exception e) {
					e.printStackTrace() ;
				}
			}
		});
	}

	/**
	 * Créer le design.
	 */
	public ApplicationAFE() {
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setBounds(100, 100, 642, 775) ;
	    
	    menuBar = new JMenuBar() ;
	    setJMenuBar(menuBar) ;
	    
	    JMenu mnInformations = new JMenu("Informations") ;
	    menuBar.add(mnInformations) ;

		JMenuItem mntmAPropos = new JMenuItem("À propos...");
		mntmAPropos.setSelected(true);
		mntmAPropos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aPropos() ;
			}
		}) ;
		mnInformations.add(mntmAPropos) ;
		
		JMenuItem mntmQuitter = new JMenuItem("Quitter") ;
		mntmQuitter.setSelected(true) ;
		mntmQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0) ;
			}
		}) ;
		mnInformations.add(mntmQuitter) ;
	    
	    // Utiliser un BorderLayout pour permettre à tous les composants de s'adapter à la taille de la fenêtre
	    contentPane = new JPanel();
	    contentPane.setBackground(Color.PINK);
	    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	    contentPane.setLayout(new BorderLayout()); // Changer le layout manager ici
	    setContentPane(contentPane);

	    panel = new JPanel() ;
	    panel.setLayout(new BorderLayout()) ; // Utiliser BorderLayout pour le panneau aussi
	    contentPane.add(panel, BorderLayout.CENTER) ; // Ajouter le panneau au centre du layout principal

	    // Gestion de la connexion
	    fenConnexion = new FenetreConnexion();
	    fenConnexion.addKeyListener(new KeyAdapter() {
	    	@Override
	    	public void keyPressed(KeyEvent e) {
	    		fenConnexion.requestFocusInWindow() ;
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					//System.out.println("ENTER !!!") ;
					//gestionPages(null) ;
				}
				requestFocusInWindow() ;
	    	}
	    });
	    fenConnexion.addPropertyChangeListener(new PropertyChangeListener() {
	        public void propertyChange(PropertyChangeEvent evt) {
	            gestionPages(evt);
	        }
	    });

	    if (premiereFois) {
	        contentPane.remove(panel);

	        panel = fenConnexion;
	        //panel = panCalculs ;
	        contentPane.add(panel, BorderLayout.CENTER); // Ajouter le panneau de connexion au centre
	        premiereFois = false;
	    }

	    // Gestion des calculs
	    panCalculs = new PanelCalculs() ;
	    panCalculs.addPropertyChangeListener(new PropertyChangeListener() {
	        public void propertyChange(PropertyChangeEvent evt) {
	            gestionPages(evt);
	        }
	    }) ;
	    panel = fenConnexion;

	    // Ajouter le panneau de connexion au centre
	    contentPane.add(panel, BorderLayout.CENTER);
	}

	/**
	 * Méthode servant à faire l'affichage des composants réservés aux administrateurs.
	 */
	private void affichageAdmin() {
		if (affAdmin) {
			JMenu mnAdministration = new JMenu("Administration") ;
		    menuBar.add(mnAdministration) ;

			JMenuItem mntmAppli = new JMenuItem("Ouvrir l'application.") ;
			mntmAppli.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, "L'affichage pour administrateurs est en cours de développement.") ;
				}
			}) ;
			mnAdministration.add(mntmAppli) ;
		} else {
			for (Component comp : menuBar.getComponents()) {
				if (comp instanceof JMenu) {
	                JMenu menu = (JMenu) comp;
	                // Si c'est le menu "Administration", on le retire
	                if ("Administration".equals(menu.getText())) {
	                    menuBar.remove(menu) ;
	                }
	            }
			}
		}
		
		setJMenuBar(menuBar) ;
		revalidate() ;
	}
	
	/**
	 * Méthode permettant d'afficher la fenêtre à propos de l'application.
	 */
	private void aPropos() {
		JOptionPane.showMessageDialog(null, "...À propos...\n"
				+ "Cette fenêtre n'a pas encore été créée.") ;
		JFrame frame = new JFrame() ;
		new FenetreAPropos(frame).setVisible(true) ;
	}
	
	/**
	 * Méthode permettant la gestion des pagea à l'aide d'une levée d'évènements.
	 * @param evt : L'évènement.
	 */
	private void gestionPages(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("connexionPossible")) {
			Object[] outputConnexion = (Object[]) evt.getNewValue() ;
			boolean[] connexionPossible = (boolean[]) outputConnexion[0] ;
			
			contentPane.remove(panel) ;
			
			if (connexionPossible[0]) {
				panel = panCalculs ;
				utilisateurActuel = (Utilisateur) outputConnexion[1] ;
				panCalculs.setUtilisateurActuel(new String(utilisateurActuel.getNomUtil())) ;
				
				affAdmin = false ;
				System.out.println(affAdmin) ;
				affichageAdmin() ;
			} else {
				if (!this.getContentPane().equals(fenConnexion)) {
					panel = fenConnexion ;
					affAdmin = !affAdmin ;
					affichageAdmin() ;
				}
			}
			
			contentPane.add(panel, BorderLayout.CENTER);
			revalidate() ;
		}
	}
}
