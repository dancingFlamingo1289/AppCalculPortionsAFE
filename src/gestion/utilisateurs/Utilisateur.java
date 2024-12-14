package gestion.utilisateurs ;

import java.io.* ;
import java.util.* ;

/**
 * 
 */
public class Utilisateur implements Serializable {
	private static final long serialVersionUID = 1L;
	/** Longueur d'une séquence. **/
	private final int LONGUEUR = (int) (20*Math.random() + 1) ;
	private HashMap<String, char[]> hm ;
	protected static String type ;

	public Utilisateur() {
		hm = new HashMap<>() ;

		hm.put("nomUtil", genererUneSequence(LONGUEUR)) ;
		hm.put("mDeP", genererUneSequence(LONGUEUR)) ;

		this.type = "Utilisateur" ;
	}

	public Utilisateur(char[] nomUtil, char[] mDeP) {
		hm = new HashMap<String, char[]>() ;

		hm.put("nomUtil", nomUtil) ;
		hm.put("mDeP", mDeP) ;

		this.type = "Utilisateur" ;
	}

	/*public Utilisateur(Utilisateur u) {
		this(u.nomUtil, u.mDeP) ;
	}*/

	/**
	 * Méthode permettant de générer une séquence de chiffres aléatoire d'une certaine longueur et la retourne dans un tableau 
	 * de caractères.
	 * @param longueur : La longueur de la séquence de chiffres.
	 * @return La séquence de chiffres dans un tableau.
	 */
	private static char[] genererUneSequence(int longueur) {
		char[] sequence = new char[longueur] ;
		Random r = new Random() ;

		for (int i = 0 ; i < longueur ; i++) {
			String symbole = r.nextInt(0, 9) + "" ; // de longueur 1
			sequence[i] = symbole.charAt(0) ;
		}

		return sequence ;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(hm.get("nomUtil"));
		result = prime * result + Arrays.hashCode(hm.get("mDeP"));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true ;
		}

		if (!(obj instanceof Utilisateur)) {
			return false ;
		}

		Utilisateur other = (Utilisateur) obj ;

		if (other.getNomUtil() == null) {
			return false ;
		}

		if (other.getmDeP() == null) {
			return Arrays.equals(this.getNomUtil(), other.getNomUtil()) ;
		}

		return Arrays.equals(hm.get("mDeP"), other.getmDeP()) && Arrays.equals(hm.get("nomUtil"), other.getNomUtil());
	}

	/**
	 * Authentifie un utilisateur en vérifiant si le nom d'utilisateur et le mot de passe
	 * correspondent à ceux enregistrés pour cet utilisateur.
	 * @param u : Un autre utilisateur.
	 */
	public boolean authentifiate(Utilisateur u) {
		// Si les noms d'utilisateur correspondent
		if (Arrays.equals(hm.get("nomUtil"), u.getNomUtil())) {
			// Si les mots de passe correspondent
			if (Arrays.equals(hm.get("mDeP"), u.getmDeP())) {
				return true;  // Authentification réussie
			} else {
				System.err.println("Mot de passe incorrect.") ;
				return false; // Le mot de passe est incorrect
			}
		} else {
			System.err.println("Nom d'utilisateur incorrect.") ;
			return false;  // Le nom d'utilisateur est incorrect
		}
	}

	/**
	 * Méthode vérifiant si deux tableaux de caractères sont égaux ou non.
	 * @param mot1 : Le premier.
	 * @param mot2 : Le second.
	 * @return Si les deux sont égaux ou non.
	 */
	private boolean egal(char[] mot1, char[] mot2) {
		String mot1Str = new String(mot1) ;
		String mot2Str = new String(mot2) ;

		return mot1Str.equals(mot2Str) ;
	}

	/**
	 * Method to get the current username.
	 * @return the current username.
	 */
	public char[] getNomUtil() {
		return hm.get("nomUtil") ;
	}

	/**
	 * Method to set the current username.
	 * @param nomUtil : the new username.
	 */
	public void setNomUtil(char[] nomUtil) {
		hm.put("nomUtil", nomUtil) ;
	}

	/**
	 * Method to get the current password.
	 * @return the current password.
	 */
	public char[] getmDeP() {
		return hm.get("mDeP") ;
	}

	/**
	 * Method to set the current password.
	 * @param mDeP : The new password.
	 */
	public void setmDeP(char[] mDeP) {
		hm.put("mDeP", mDeP) ;
	}

	/**
	 * Méthode permettant d'obtenir les informations d'un objet Utilisateur encodées.
	 * @return Les informations d'un objet Utilisateur.
	 */
	@Override
	public String toString() {
		String output = type + "   {" + new String(getNomUtil()) + " -> " + new String(getmDeP()) + "}" ;

		return output ;
	}

	/**
	 * Méthode statique qui prend une chaîne de caractères et la convertit en instance d'Utilisateur.
	 * @param str : La chaîne de caractères à lire, formatée comme "nom,motDePasse".
	 * @return Un objet Utilisateur, ou null si la chaîne est mal formatée.
	 */
	/*public static Utilisateur read(String str) {
        if (str == null || str.isEmpty()) {
            return null; // Vérification de sécurité
        }

        String[] details = str.split(",");
        if (details.length == 2) {
            return new Utilisateur(details[0], details[1]);
        } else {
            System.out.println("La chaîne n'est pas bien formatée.");
            return null; // Retourne null si le format est incorrect
        }
    }*/

	/**
	 * Méthode permettant de créer un utilisateur à partir de sa chaîne toString.
	 * @param toString : La chaîne toString de l'utilisateur enregistré.
	 * @return un objet utilisateur correspondant aux informations de la chaîne toString.
	 */
	public static Utilisateur read(String toString) {
		Utilisateur tempUtilisateur = new Utilisateur(); // Utilisateur temporaire pour accéder à type
		String typeAttendu = tempUtilisateur.type ;

		if (toString.startsWith("Administrateur")) {
	        return Administrateur.read(toString);
	    } else if (toString.startsWith("Utilisateur")) {
	    	String newStr = toString.replace(typeAttendu, "").trim() ;

			// On s'attend à ce que la chaîne contienne deux parties séparées par un tabulateur
			String[] firstlySplitted = newStr.split(",") ;

			// Récupération du nom d'utilisateur et du mot de passe
			String userInfo = firstlySplitted[0].replace("{", "").replace("}", "") ;

			String[] userInfoSplitted = userInfo.split(" -> ") ;

			if (userInfoSplitted.length == 2) {
				return new Utilisateur(userInfoSplitted[0].toCharArray(), userInfoSplitted[1].toCharArray()) ;
			} else 
				return null ;
	    } /*else {
	    	System.err.println("Format incorrect : " + toString + "\n"
					+ "On s'attend à la chaîne de caractères '" + type + "'.") ;
			return null ;  // Retourne null si le format est incorrect
	    }*/ 
		return null ;
	}
}
