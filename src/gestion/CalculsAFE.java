package gestion;

import java.io.File;
import java.util.Collection;

public class CalculsAFE {
	public static double somme(double a, double b) {
		return a + b ;
	}

	public static double somme(double[] tableau) {
		double somme = 0 ;

		for (double nb : tableau)
			somme += nb ;

		return somme ;
	}

	public static double pourcentage(double valObtenue, double valRef) {
		return valObtenue / valRef * 100.0d ;
	}

	/**
	 * Méthode effectuant le calcul de prêts et de bourses par mois.
	 * @param pourcPrets : Le pourcentage de prêts pour l'année.
	 * @param pourcBourses : Le pourcentage de bourses pour l'année.
	 * @param totalPourLeMois : Le total des prêts et bourses accordés pour le mois.
	 * @return Un tableau de longueur 2 contenant le montant des prêts (index = 0) et des bourses (index = 1) par mois.
	 */
	public static double[] calculParMois(double pourcPrets, double pourcBourses, double totalPourLeMois) {
		pourcPrets /= 100.0 ;
		pourcBourses /= 100.0 ;

		double[] allocParMois = {totalPourLeMois * pourcPrets, totalPourLeMois * pourcBourses} ;
		return allocParMois ;
	}

	
	public static File findMinimumValue(File[] listOfFiles) throws Exception {
		if (listOfFiles != null) {
			int anneeDebut = 1970 ;
			File fichMinimum = null ;
			for (File file : listOfFiles) {
				if (file.isFile()) {
					String nomFichier = file.getName() ;
					System.out.println("Fichier : " + file.getName()) ;
					int posEspace = nomFichier.lastIndexOf(" "), posTiret = nomFichier.indexOf("-") ;
					int anneeDebutProv = Integer.parseInt(nomFichier.substring(posEspace + 1, posTiret)) ;

					if (anneeDebut < anneeDebutProv) {
						anneeDebut = anneeDebutProv ;
						fichMinimum = file ;
					}
				} 
			}
			
			return fichMinimum ;
		} else 
			throw new Exception("CalculsAFE - Erreur : La liste de fichiers n'a pas été initialisée.") ;
	}

	public static void findMinimumValue(Collection<File> listOfFiles) {
		
	}
}
