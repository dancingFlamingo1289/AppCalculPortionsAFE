package application.configuration;

import java.io.* ;
import java.util.* ;
import javax.swing.* ;

/**
 * Classe représentant un fichier textuels. Celle-ci permet toute la gestion de l'écriture et de la lecture
 * de fichiers textuels.
 * @author Elias Kassas
 */
public class FichierConfiguration {
	/** Objet File représentant du présent fichier. **/
	private File fichier ;
	/** Dossier de l'utilisateur. **/
	private File dossier ;
	/** Nom du fichier à écrire. **/
	private String nomFichier ;
	/** Liste contenant le contenu du présent fichier. **/
	private HashMap<String, Object> contenuFichier ;

	/**
	 * Constructeur d'un fichier.
	 * @param dossier : Dossier dans lequel le fichier se trouvera.
	 * @param nomFichier : Nom du fichier en excluant le type de fichier (.txt, .jar, .mov, .bin, etc.).
	 */
	public FichierConfiguration(File dossier, String nomFichier) {
		this.nomFichier = nomFichier.replace(".config", "") + ".config" ;
		this.dossier = dossier ;

		// Créer le dossier s'il n'existe pas
		this.dossier.mkdirs() ;

		fichier = new File(this.dossier, this.nomFichier) ;
		this.contenuFichier = new HashMap<String, Object>() ;
	}

	/**
	 * Méthode permettant de renommer le nom du présent fichier.
	 * @param nouveauNom
	 */
	public void renommer(String nouveauNom) {
		nouveauNom += ".config" ;
		File nouveauFichier = new File(dossier, nouveauNom);
		if (fichier.renameTo(nouveauFichier)) {
			fichier = nouveauFichier;
		} else {
			System.err.println("Impossible de renommer le fichier.") ;
		}
	}

	public void ajouter(String key) {
		this.ajouter(key, null) ;
	}
	
	public void maj(String key, Object newValue) {
		if (contenuFichier.containsKey(key)) {
			 contenuFichier.replace(key, newValue) ;
			 ecrire(4) ;
		}
	}
	
	public void ajouter(String key, Object value) {
		this.contenuFichier.put(key, value) ;
		ecrire(4) ;
	}

	/**
	 * Méthode permettant de supprimer un objet d'un fichier.
	 * @param obj : L'objet à supprimer.
	 */
	public void supprimer(String key) {
		if (contenuFichier.containsKey(key)) {	
			contenuFichier.remove(key) ;
			ecrire(4) ;
		}
	}

	public boolean contientClef(String key) {
		return contenuFichier.containsKey(key) ;
	}
	
	public boolean contientValeur(Object value) {
		return contenuFichier.containsValue(value) ;
	}

	public void ecrirePublic(int indent) {
		ecrire(indent) ;
	}

	/**
	 * Méthode privée permettant d'écrire un fichier.
	 */
	private void ecrire(Integer indent) {
		try {
			FileWriter fw = new FileWriter(fichier) ;
			
			fw.write("{\n") ;
			
			String indentation = "" ; 
			for (int i = 0 ; i < indent ; i++) {
				indentation += "\t" ;
			}
			
			for (String key : contenuFichier.keySet()) {
				fw.write(indentation + "\"" + key + "\" = " + contenuFichier.get(key) + "\n") ;
			}
			
			fw.write("}") ;

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
	public static FichierConfiguration lire(File fichier) throws IOException {
		File fichierALire = new File(fichier.toString()) ;

		// Vérifier si le fichier existe et est lisible
		if (!fichierALire.exists() || !fichierALire.isFile()) {
			throw new IOException("Le fichier " + fichier.getName() + " n'existe pas ou n'est pas un fichier valide.");
		}

		FichierConfiguration leFichier = new FichierConfiguration(fichierALire.getParentFile(), fichierALire.getName()) ;

		// Utiliser try-with-resources pour s'assurer que le BufferedReader est fermé
		try (BufferedReader fluxEntree = new BufferedReader(new FileReader(fichierALire))) {
			String ligne ; // On ignore la première ligne qui doit forcément être "{".
			
			while ((ligne = fluxEntree.readLine()) != null) {
			    if ((ligne.strip().equals("{") || ligne.strip().equals("}")) || ligne.isBlank()) continue ;

			    try {
			        String[] brokenLine = lireLigne(ligne, "=");
			        leFichier.ajouter(brokenLine[0], brokenLine[1]);
			    } catch (IllegalArgumentException e) {
			        System.err.println("Ligne ignorée : " + ligne);
			    }
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("Erreur lors de la lecture du fichier : " + e.getMessage());
		}

		return leFichier ;
	}

	/**
	 * Méthode permettant de faire la gestion d'une ligne sous la forme  ""clef" = valeur"
	 * en tableau de chaînes de caractères de la forme {clef, valeur}.
	 * ATTENTION : La valeur sera une chaîne de caractères. Il faudra faire la conversion implicite de chaîne à l'objet voulu.
	 * @param line : La ligne à traiter.
	 * @param delimiter : Le délimiteur.
	 * @return {clef, valeur}
	 */
	private static String[] lireLigne(String line, String delimiter) {
	    if (line == null || !line.contains(delimiter)) {
	        throw new IllegalArgumentException("La ligne est invalide : " + line);
	    }

	    String[] spltLine = line.strip().split(delimiter, 2) ;

	    if (spltLine.length < 2) {
	        throw new IllegalArgumentException("Format incorrect : " + line);
	    }

	    spltLine[0] = spltLine[0].replace("\"", "").trim() ; // Nettoyage de la clé
	    spltLine[1] = spltLine[1].trim() ;                   // Nettoyage de la valeur

	    return spltLine;
	}
	
	public Object get(String key) {
		return this.contenuFichier.get(key) ;
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

	public HashMap<String, Object> getContenuFichier() {
		try {
			File dir = new File("CalculsAFE") ;
			if (!dir.exists()) {
				dir.mkdir(); // Crée le répertoire si il n'existe pas
			}
			
			return lire(new File(dossier, nomFichier)).contenuFichier ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace() ;
			JOptionPane.showMessageDialog(null, e.toString()) ;
		}

		return null ;
	}
	
	/**
	 * 
	 **/
	@Override
	public String toString() {
		return "Fichier"
				+ "\tnomFichier = " + nomFichier
				+ "\tcontenuFichier = " + contenuFichier + "" ;
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
	    contenuFichier = new HashMap<String, Object>() ;
	    ecrire(4) ;
	}
}
