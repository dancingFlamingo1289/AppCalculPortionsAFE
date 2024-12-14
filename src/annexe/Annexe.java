package annexe;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;

import application.configuration.FichierConfiguration;
import gestion.FichierTexte;
import gestion.utilisateurs.Utilisateur;

public class Annexe {
	public static File REPERTOIRE = new File(System.getProperty("user.home"), "CalculsAFE"),
			REPERTOIRE_CONNEXION = new File(System.getProperty("user.home"), "CalculsAFE"), // <- À modifier pour son ordinateur...
			REPERTOIRE_UTILISATEURS = new File(REPERTOIRE, "users") ; // <- À modifier pour son ordinateur...
	public static String NOM_CONNEXION = "userList" ; // <- À modifier à votre convenance...

	// On exécute toujours ceci.
	static {
		/*FichierConfiguration config = new FichierConfiguration(REPERTOIRE, "userInput") ;
		if (!config.existe()) {
			config.ajouter("repertoire_connexion", REPERTOIRE_CONNEXION) ;
			config.ajouter("repertoire_utilisateurs", REPERTOIRE_UTILISATEURS) ;
			config.ajouter("nom_connexion", NOM_CONNEXION) ;
		} else {
			REPERTOIRE_CONNEXION = new File((String) config.get("repertoire_connexion")) ;
			REPERTOIRE_UTILISATEURS = new File((String) config.get("repertoire_utilisateurs")) ;
			NOM_CONNEXION = (String) config.get("nom_connexion") ;
		}*/

		System.out.println("Chemin de REPERTOIRE_CONNEXION : " + REPERTOIRE_CONNEXION) ;
		System.out.println("Chemin de REPERTOIRE_UTILISATEURS : " + REPERTOIRE_UTILISATEURS) ;

		if (REPERTOIRE_CONNEXION.mkdirs()) {
			System.out.println("Dossier " + REPERTOIRE_CONNEXION.toString() + " créé.") ;
		} 

		if (REPERTOIRE_UTILISATEURS.mkdirs()) {
			System.out.println("Dossier " + REPERTOIRE_UTILISATEURS.toString() + " créé.") ;
		}

		FichierTexte f = new FichierTexte(REPERTOIRE_CONNEXION, NOM_CONNEXION) ;
		if (f.estVide()) {
			Utilisateur u = new Utilisateur(JOptionPane.showInputDialog(null, "Quel sera votre nom d'utilisateur", 
					"Vous êtes le premier utilisateur/la première utilisatrice de l'application.", 
					JOptionPane.INFORMATION_MESSAGE).toCharArray(), 
					JOptionPane.showInputDialog(null, "Quel sera votre mot de passe ?", 
							"Premier utilisateur/Première utilisatrice", JOptionPane.INFORMATION_MESSAGE).toCharArray()) ;
			f.ecrire(u) ;
			JOptionPane.showMessageDialog(null, "Chemin du repertoire de connexion de l'application : " + 
					REPERTOIRE_CONNEXION.toString()) ;
		}
	}

	public static String now() {
		// Obtenir la date et l'heure actuelles
		LocalDateTime now = LocalDateTime.now() ;

		// Formater la date et l'heure pour les afficher dans un format lisible
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss") ;
		return now.format(formatter) ;
	}
}
