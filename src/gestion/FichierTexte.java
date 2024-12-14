package gestion;

import java.io.* ;
import java.util.* ;
import javax.swing.* ;

/**
 * Classe représentant un fichier textuels. Celle-ci permet toute la gestion de l'écriture et de la lecture
 * de fichiers textuels.
 * @author Elias Kassas
 */
public class FichierTexte {
	/** Objet File représentant du présent fichier. **/
	private File fichier ;
	/** Dossier de l'utilisateur. **/
	private File dossier ;
	/** Nom du fichier à écrire. **/
	private String nomFichier ;
	/** Liste contenant le contenu du présent fichier. **/
	private ArrayList<String> contenuFichier ;

	/**
	 * Constructeur d'un fichier.
	 * @param dossier : Dossier dans lequel le fichier se trouvera.
	 * @param nomFichier : Nom du fichier en excluant le type de fichier (.txt, .jar, .mov, .bin, etc.).
	 */
	public FichierTexte(File dossier, String nomFichier) {
		this.nomFichier = nomFichier.replace(".txt", "") + ".txt" ;
		this.dossier = dossier ;

		// Créer le dossier s'il n'existe pas
		this.dossier.mkdirs() ;

		fichier = new File(this.dossier, this.nomFichier) ;
		this.contenuFichier = new ArrayList<String>() ;
	}

	/**
	 * Méthode permettant de renommer le nom du présent fichier.
	 * @param nouveauNom
	 */
	public void renommer(String nouveauNom) {
		nouveauNom += ".txt" ;
		File nouveauFichier = new File(dossier, nouveauNom);
		if (fichier.renameTo(nouveauFichier)) {
			fichier = nouveauFichier;
		} else {
			System.err.println("Impossible de renommer le fichier.") ;
		}
	}

	/**
	 * Méthode permettant d'écrire dans le présent fichier un caractère.
	 * @param c : Le caractère à écrire.
	 */
	public void ecrire(Character c) {
		if (contenuFichier.add(c.toString())) { 
			ecrire() ;
		}
	}

	/**
	 * Méthode permettant de supprimer une chaîne de caractères d'un fichier.
	 * @param s : La chaîne à supprimer.
	 */
	public void supprimer(Character c) {
		if (contenuFichier.remove(c.toString())) {
			ecrire() ;
		}
	}

	/**
	 * Méthode permettant d'écrire dans le présent fichier un entier.
	 * @param i : L'entier à écrire.
	 */
	public void ecrire(int i) {
		if (contenuFichier.add(Integer.valueOf(i).toString())) { 
			ecrire() ;
		}
	}

	/**
	 * Méthode permettant de supprimer une chaîne de caractères d'un fichier.
	 * @param s : La chaîne à supprimer.
	 */
	public void supprimer(Integer i) {
		if (contenuFichier.remove(i.toString())) {
			ecrire() ;
		}
	}

	/**
	 * Méthode permettant d'écrire dans le présent fichier une chaîne de caractères.
	 * @param s : La chaîne de caractères à écrire.
	 */
	public void ecrire(String s) {
		if (contenuFichier.add(s)) { 
			ecrire() ;
		}
	}

	/**
	 * Méthode permettant de supprimer une chaîne de caractères d'un fichier.
	 * @param s : La chaîne à supprimer.
	 */
	public void supprimer(String s) {
		if (contenuFichier.remove(s)) {
			ecrire() ;
		}
	}

	/**
	 * Méthode permettant d'écrire dans le présent fichier un objet.
	 * @param obj : L'objet à écrire.
	 */
	public void ecrire(double d) {
		if (contenuFichier.add(Double.valueOf(d).toString())) {
			ecrire() ;
		}
	}

	/**
	 * Méthode permettant de supprimer un nombre réel d'un fichier.
	 * @param obj : L'objet à supprimer.
	 */
	public void supprimer(double d) {
		if (contenuFichier.remove(d)) {
			ecrire() ;
		}
	}

	/**
	 * Méthode permettant d'écrire dans le présent fichier un tableau d'objets.
	 * @param obj : Le tableau d'objets à écrire.
	 */
	public void ecrire(Object o) {
		if (implementeSerializable(o.getClass())) {
			if (contenuFichier.add(o.toString())) { 
				ecrire() ;
			}
		} else {
			JOptionPane.showMessageDialog(null, "ERREUR 1 : L'objet n'implémente pas l'interface Serializable."
					+ "\nImpossible de continuer.") ;
		}
	}

	/**
	 * Méthode permettant de supprimer un objet d'un fichier.
	 * @param obj : L'objet à supprimer.
	 */
	public void supprimer(Object obj) {
		if (contenuFichier.remove(obj.toString())) {
			ecrire() ;
		}
	}

	/**
	 * Méthode permettant d'écrire dans le présent fichier un tableau d'objets.
	 * @param obj : Le tableau d'objets.
	 */
	public void ecrire(Object[] obj) {
		if (implementeSerializable(obj.getClass())) {
			for (Object o : obj)
				this.ecrire(o) ;
			
			ecrire() ;
		} else {
			JOptionPane.showMessageDialog(null, "ERREUR 1 : L'objet n'implémente pas l'interface Serializable.\n"
					+ "Impossible de continuer.") ;
		}
	}

	/**
	 * Méthode permettant de supprimer un tableau d'objets d'un fichier.
	 * @param obj : Le tableau d'objets à supprimer.
	 */
	public void supprimer(Object[] obj) {
		if (contenuFichier.remove(obj)) {
			ecrire() ;
		}
	}

	public boolean contient(int i) {
		return contenuFichier.contains(i) ;
	}

	public boolean contient(double d) {
		return contenuFichier.contains(d) ;
	}

	public boolean contient(String s) {
		return contenuFichier.contains(s) ;
	}

	public boolean contient(char c) {
		return contenuFichier.contains(c) ;
	}

	public boolean contient(Object o) {
		return contenuFichier.contains(o) ;
	}

	public void ecrirePublic() {
		ecrire() ;
	}

	/**
	 * Méthode privée permettant d'écrire un fichier.
	 */
	private void ecrire() {
		try {
			FileWriter fw = new FileWriter(fichier) ;

			for (Object element : contenuFichier) {
				fw.write(element.toString() + "\n") ;
			}

			fw.close() ;
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.toString()) ;
		}
	}

	/**
	 * Méthode permettant de lire un fichier et qui retourne son contenu dans une liste.
	 * @param dossier : Le dossier contenant le fichier à lire.
	 * @param nomFichier : Le nom du fichier à lire sans l'extension.
	 * @return Le contenu du fichier sous forme de liste de chaînes de caractères.
	 * @throws IOException : Si le fichier ne peut pas être lu.
	 */
	public static FichierTexte lire(File fichier) throws IOException {
		File fichierALire = new File(fichier.toString()) ;

		// Vérifier si le fichier existe et est lisible
		if (!fichierALire.exists() || !fichierALire.isFile()) {
			//System.out.println(fichierALire.exists()) ;
			//System.out.println(fichierALire.isFile()) ;
			throw new IOException("Le fichier " + fichier.getName() + " n'existe pas ou n'est pas un fichier valide.");
		}

		FichierTexte fichierLu = new FichierTexte(fichierALire.getParentFile(), fichierALire.getName()) ;

		// Utiliser try-with-resources pour s'assurer que le BufferedReader est fermé
		try (BufferedReader fluxEntree = new BufferedReader(new FileReader(fichierALire))) {
			String ligne ;
			while ((ligne = fluxEntree.readLine()) != null) {
				fichierLu.ecrire(ligne) ;
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("Erreur lors de la lecture du fichier : " + e.getMessage());
		}

		return fichierLu ;
	}


	/**
	 * Méthode servant à obtenir la localisation d'un fichier sur l'ordinateur de l'utilisateur.
	 * @return La localisation du fichier sur l'ordinateur de l'utilisateur
	 */
	public String getCheminDuFichier() {
		return fichier.getAbsolutePath() ;
	}

	/**
	 * Méthode vérifiant si un objet implémente l'interface Serializable.
	 * @param classe : La classe de l'objet.
	 * @return S'il implémente ou non l'interface Serializable.
	 */
	private boolean implementeSerializable(Class<?> classe) {
		Class<?>[] interfaces = classe.getInterfaces();
		for (Class<?> iface : interfaces) {
			if (iface == Serializable.class) {
				return true ;
			}
		}

		return false ;
	}

	/**
	 * 
	 **/
	@Override
	public String toString() {
		return "FichierTexte"
				+ "\tnomFichier = " + nomFichier
				+ "\tcontenuFichier = " + contenuFichier + "" ;
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<String> getContenuFichier() {
		try {
			File dir = new File("CalculsAFE");
			if (!dir.exists()) {
				dir.mkdir(); // Crée le répertoire si il n'existe pas
			}
			
			contenuFichier = lire(new File(dossier, nomFichier)).contenuFichier ;
			return contenuFichier ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace() ;
		}

		return new ArrayList<String>() ;
	}

	/**
	 * 
	 * @param a
	 * @return
	 */
	private ArrayList<Object> convertToObject(ArrayList<String> a) {
		ArrayList<Object> aObj = new ArrayList<Object>() ;

		for (String elem : a) {
			aObj.add(elem) ;
		}

		return aObj ;
	}

	/**
	 * 
	 * @return
	 */
	public boolean existe() {
		return fichier.exists() ;
	}

	/**
	 * 
	 * @return
	 */
	public String getNom() {
		return nomFichier ;
	}
	
	public boolean estVide() {
		return this.getContenuFichier().isEmpty() ;
	}
	
	public void effacer() {
	    contenuFichier = new ArrayList<String>() ;
	    ecrire() ;
	}
}
