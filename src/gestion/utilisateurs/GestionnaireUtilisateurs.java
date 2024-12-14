package gestion.utilisateurs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;

import annexe.Annexe;
import gestion.FichierTexte;

/**
 * Cette classe est un utilitaire pour la gestion efficace d'utilisateurs.
 */
public class GestionnaireUtilisateurs {
	/** Le dossier où aller chercher la base de données. **/
	private File dossier = new File("CalculsAFE/connexion") ;
	/** La fichier textuel contenant tous les utilisateurs enregistrés sur l'application. **/
	private FichierTexte userList ;
	/** La liste d'utilisateurs sauvegardée en mémoire pour une recherche plus rapide. **/
	private ArrayList<Utilisateur> reseau ;
	/** Le nom de la base de données contenant tous les utilisateurs enregistrés. **/
	private String dataBase ;

	public static void main(String[] args) {
		GestionnaireUtilisateurs gu = new GestionnaireUtilisateurs(Annexe.REPERTOIRE_CONNEXION, Annexe.NOM_CONNEXION) ;
		System.out.println(gu) ;
		
		System.out.println(gu.findUser("e05kassas")) ;
	}
	
	/**
	 * Constructeur de l'utilitaire.
	 * @param dossier : Le dossier où se trouvera la base de données.
	 * @param dataBase : Le nom de la base de données.
	 */
	public GestionnaireUtilisateurs(File dossier, String dataBase) {
	    this.dossier = new File(dossier.toString()) ;
	    this.dossier.mkdirs() ;
	    this.dataBase = dataBase ;
		
		this.userList = new FichierTexte(this.dossier, this.dataBase) ;
	    this.reseau = new ArrayList<Utilisateur>() ;
	    verif() ;  // Charger les utilisateurs au démarrage
	}

	/**
	 * Méthode permettant de charger dans la liste d'utilisateurs de la mémoire les utilisateurs de la base de données.
	 */
	private void verif() {
	    reseau.clear(); // Nettoyer avant de remplir
	    if (userList.existe()) {
	        for (Object userStr : userList.getContenuFichier()) {
	            Utilisateur u = Utilisateur.read(userStr.toString());
	            if (!reseau.contains(u)) {
	            	//System.out.println(u) ;
		            reseau.add(u) ;
	            }
	        }
	    }
	}

	/**
	 * Méthode servant à créer un utilisateur et l'ajouter à la liste des utilisateurs.
	 * @param username : Le nom d'utilisateur du nouvel utilisateur
	 * @param password : Le mot de passe du nouvel utilisateur
	 * @return Le nouvel objet Utilisateur
	 */
	public Utilisateur createAndAddNewUser(char[] username, char[] password) {
		verif() ;

		if (!isUsernamePresentInList(new String(username))) {
			Utilisateur u = new Utilisateur(username, password) ;
		    reseau.add(u) ;
		    userList.ecrire(u) ;
		    JOptionPane.showMessageDialog(null, "Utilisateur ajouté : " + new String(u.getNomUtil())) ;
			return u ;
		} else {
			JOptionPane.showMessageDialog(null, "L'utilisateur existe déjà.") ;
			return null ;
		}
	}

	/**
	 * Méthode servant à retirer un utilisateur de la liste tout en retirant la copie faite de celui-ci en mémoire.
	 * @param toRemove : L'utilisateur supprimé.
	 * @return L'utilisateur supprimé.
	 */
	public Utilisateur clearAndRemoveUser(Utilisateur toRemove) {
		verif() ;

		if (isPresentInList(toRemove)) {
			if (reseau.remove(toRemove)) {
		        userList.supprimer(toRemove);
		        return toRemove;
		    }
		}

		return null ;
	}


	/*public static Utilisateur clearAndRemoveUser(Utilisateur toRemove) {
		if (isPresentInList(toRemove)) {
			ArrayList<Object> listContent = userList.getContenuFichier() ;

			for (Object util : listContent) {
				Utilisateur verif = Utilisateur.read(util.toString()) ;

				if (toRemove.equals(verif) && verif != null) {
					userList.supprimer(toRemove) ;
				}
			}
		}

		return toRemove ;
	}*/

	/**
	 * Méthode servant à promouvoir un utilisateur au poste d'administrateur.
	 * @param promotedUser : L'utilisateur à promouvoir.
	 */
	public void promoteUser(Utilisateur promotedUser) {
		verif() ;

		if (isPresentInList(promotedUser)) {
			clearAndRemoveUser(promotedUser) ;  // Suppression de l'utilisateur normal
			//promotedUser.setEstAdministrateur(true) ;
			reseau.add(promotedUser) ;  // Ajout de l'administrateur dans la liste
			userList.ecrire(promotedUser) ;  // Mise à jour du fichier
		} else {
			JOptionPane.showMessageDialog(null, "L'utilisateur " + new String(promotedUser.getNomUtil()) + 
					" ne figure pas dans la liste des utilisateurs.");
		}
	}

	/**
	 * Méthode servant à vérifier si un utilisateur est présent dans la liste d'utilisateurs.
	 * @param username : Le nom d'utilisateur à vérifier.
	 * @return S'il est présent ou non dans la liste.
	 */
	public boolean isUsernamePresentInList(String username) {
	    verif() ;
		
		for (Utilisateur u : reseau) {
	        System.out.println(new String(u.getNomUtil())) ;
			if (Arrays.equals(u.getNomUtil(), username.toCharArray())) {
	            return true ;
	        }
	    }
		
	    return false;
	}
	
	/**
	 * Méthode servant à trouver un utilisateur dans la liste à partir de son nom d'utilisateur.
	 * @param username : Le nom d'utilisateur à chercher.
	 * @return L'utilisateur s'il est retrouvé.
	 */
	public Utilisateur findUser(String username) {
		verif() ;
		
		if (isUsernamePresentInList(username)) {
			for (Utilisateur u : reseau) {
				if (Arrays.equals(u.getNomUtil(), username.toCharArray())) {
					return u ;
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "L'utilisateur " + username + " n'est pas présent dans la liste.") ;
		}

		return null ;
	}

	//public static boolean authentifiate(Utilisateur connectingUser, Utilisateur userToAuthentifiate) {
	//	return authentifiate(userToAuthentifiate, findUser(new String(connectingUser.getNomUtil()))) ;
	//}

	/**
	 * Méthode servant à authentifier un probable utilisateur
	 * @param u : L'utilisateur à authentifier
	 * @return S'il peut entrer ou non.
	 */
	public boolean[] authentifiate(Utilisateur u) {
		boolean[] output = {authentifiate(u.getNomUtil(), u.getmDeP()), u instanceof Administrateur} ;
		return output ;
	}
	
	/**
	 * Méthode servant à authentifier un probable utilisateur.
	 * @param nom : Le nom d'utilisateur du probable utilisateur.
	 * @param motDePasse : Le mot de passe du probable utilisateur.
	 * @return S'il peut entrer ou non.
	 */
	public boolean authentifiate(char[] nom, char[] motDePasse) {
		verif() ;
		
		Utilisateur tempUser = new Utilisateur(nom, motDePasse);
		return reseau.contains(tempUser) ;
	}

	/*public static boolean authentif(Utilisateur connectingUser) {
		verif() ;

		boolean found = false ;
		//int i = 0 ;

		for (Utilisateur nomUtil : reseau) {
			Utilisateur verif = new Utilisateur(nomUtil.toCharArray(), reseau.get(nomUtil).toCharArray()) ;

			System.out.println("canConnect = " + (verif.equals(connectingUser) && !found)) ;
			if (verif.equals(connectingUser) && !found) {
				return true ;
			}
		}

		System.out.println("Échec de la connexion pour l'utilisateur : " + new String(connectingUser.getNomUtil()));
		return false ;
	}*/

	/**
	 * Méthode vérifiant si un utilisateur est présent dans la liste d'utilisateurs.
	 * @param u : L'utilisateur à vérifier.
	 * @return S'il est présent dans la liste.
	 */
	public boolean isPresentInList(Utilisateur u) {
		verif() ;

		return userList.contient(u) ;
	}

	/**
	 * Méthode servant à renommer la base de données qui sauvegarde tous les utilisateurs.
	 * @param newDataBase : Le nouveau nom de la base de données.
	 */
	public void setDataBaseName(String newDataBase) {
		this.dataBase = newDataBase ;
		this.userList.renommer(dataBase) ;
	}

	@Override
	public String toString() {
		return "GestionnaireUtilisateurs [dossier=" + dossier + ", userList=" + userList + ", reseau=" + reseau
				+ ", dataBase=" + dataBase + "]";
	}
	
	public ArrayList<Utilisateur> getUserList() {
		return GestionnaireUtilisateurs.getUserList(dossier, userList.getNom()) ;
	}
	
	public static ArrayList<Utilisateur> getUserList(File folder, String userListName) {
		try {
			ArrayList<String> list = FichierTexte.lire(new File(folder, userListName + ".txt")).getContenuFichier() ;
			ArrayList<Utilisateur> userList = new ArrayList<Utilisateur>() ;
			
			for (String utilStr : list) {
				System.out.println(Utilisateur.read(utilStr)) ;
				userList.add(Utilisateur.read(utilStr)) ;
			}
			
			return userList ;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null ;
	}
}
