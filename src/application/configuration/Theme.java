package application.configuration;

import java.awt.Color;

/**
 * Cette classe représente un thème.
 * Un thème possède un nom, une couleur primaire (couleur de fond), une couleur secondaire 
 * (couleur de fond des composants), une couleur tertiaire (couleur des bordures des composants) ainsi qu'une
 * couleur de police pour le texte affiché.
 * @author Elias Kassas
 */
public class Theme {
	/** Nom du thème. **/
	private String nom ;
	/** Couleurs primaire, secondaire et tertiaire du thème. **/
	private Color couleurPrimaire, couleurSecondaire, couleurTertiaire ;
	/** Couleur de la police de caractère du thème. **/
	private Color couleurPolice ;

	/**
	 * Constructeur d'un thème prenant en paramètre les couleurs primaire et secondaire.
	 * @param couleurPrimaire : La couleur primaire du thème.
	 * @param couleurSecondaire : La couleur secondaire du thème.
	 * @param couleurPolice : La couleur de la police de caractère.
	 */
	public Theme(String nom, Color couleurPrimaire, Color couleurSecondaire, Color couleurPolice) {
		this.nom = nom ;
		this.couleurPrimaire = couleurPrimaire ;
		this.couleurSecondaire = couleurSecondaire ;
		this.couleurPolice = couleurPolice ;
	}

	/**
	 * Constructeur d'un thème prenant en paramètre les couleurs primaire, secondaire et tertiaire.
	 * @param couleurPrimaire : La couleur primaire du thème.
	 * @param couleurSecondaire : La couleur secondaire du thème.
	 * @param couleurTertiaire : La couleur tertiaire du thème. 
	 * @param couleurPolice : La couleur de la police de caractère.
	 */
	public Theme(String nom, Color couleurPrimaire, Color couleurSecondaire, Color couleurTertiaire, 
			Color couleurPolice) {
		this.nom = nom ;
		this.couleurPrimaire = couleurPrimaire ;
		this.couleurSecondaire = couleurSecondaire ;
		this.couleurTertiaire = couleurTertiaire ;
		this.couleurPolice = couleurPolice ;
	}

	/**
	 * Méthode permettant d'obtenir la couleur primaire d'un thème.
	 * @return La couleur primaire.
	 */
	public Color getCouleurPrimaire() {
		return couleurPrimaire ;
	}

	/**
	 * Méthode permettant de modifier la couleur primaire d'un thème.
	 * @param couleurPrimaire : La couleur primaire.
	 */
	public void setCouleurPrimaire(Color couleurPrimaire) {
		this.couleurPrimaire = couleurPrimaire ;
	}

	/**
	 * Méthode permettant d'obtenir la couleur secondaire d'un thème.
	 * @return La couleur secondaire.
	 */
	public Color getCouleurSecondaire() {
		return couleurSecondaire ;
	}

	/**
	 * Méthode permettant d'obtenir la couleur secondaire d'un thème.
	 * @param couleurSecondaire : La couleur secondaire.
	 */
	public void setCouleurSecondaire(Color couleurSecondaire) {
		this.couleurSecondaire = couleurSecondaire ;
	}

	/**
	 * Méthode permettant d'obtenir la couleur tertiaire d'un thème.
	 * @return La couleur tertiaire.
	 */
	public Color getCouleurTertiaire() {
		return couleurTertiaire ;
	}

	/**
	 * Méthode permettant d'obtenir la couleur tertiaire d'un thème.
	 * @param couleurTertiaire : La couleur tertiaire.
	 */
	public void setCouleurTertiaire(Color couleurTertiaire) {
		this.couleurTertiaire = couleurTertiaire ;
	}

	/**
	 * Méthode permettant d'obtenir la couleur de la police d'un thème.
	 * @return La couleur de la police.
	 */
	public Color getCouleurPolice() {
		return couleurPolice ;
	}

	/**
	 * Méthode permettant de modifier la couleur de la police d'un thème.
	 * @param couleurPolice : La couleur de la police.
	 */
	public void setCouleurPolice(Color couleurPolice) {
		this.couleurPolice = couleurPolice ;
	}

	/**
	 * Méthode permettant d'obtenir le nom d'un thème.
	 * @return Le nom du thème.
	 */
	public String getNom() {
		return nom ;
	}

	/**
	 * Méthode permettant de modifier le nom d'un thème.
	 * @param nouvNom : Le nouveau nom du thème.
	 */
	public void setNom(String nouvNom) {
		nom = nouvNom ;
	}

	/**
	 * Méthode toString d'un objet Theme.
	 * @return La chaîne de caractère contenant les informations d'un thème.
	 */
	@Override
	public String toString() {
		if (couleurPrimaire == null)
			couleurPrimaire = new Color(0, 0, 0) ;
		
		if (couleurSecondaire == null)
			couleurSecondaire = new Color(0, 0, 0) ;
		
		if (couleurTertiaire == null)
			couleurTertiaire = new Color(0, 0, 0) ;
		
		if (couleurPolice == null)
			couleurPolice = new Color(0, 0, 0) ;

		return "Theme("
		+ "nom = " + nom + ", "
		+ "couleurPrimaire = " + couleurPrimaire + ", "
		+ "couleurSecondaire = " + couleurSecondaire + ", "
		+ "couleurTertiaire = " + couleurTertiaire + ", "
		+ "couleurPolice = " + couleurPolice + ")" ;
	}
	
	public Theme read(String toString) {
		String[] spltString = toString.replace("Theme(", "").replace(")", "").strip().split(",", 5) ;
		
		return new Theme(spltString[0], colorFromString(spltString[1]), colorFromString(spltString[2]), 
				colorFromString(spltString[3]), colorFromString(spltString[4])) ;
	}
	
	private Color colorFromString(String colorString) {
        // Format de la chaîne attendu : java.awt.Color[r=<r>,g=<g>,b=<b>]
        if (colorString != null && colorString.startsWith("java.awt.Color")) {
            try {
                // Extraire les valeurs de RGB de la chaîne
                String[] parts = colorString.substring(colorString.indexOf('[') + 1, colorString.indexOf(']')).split(",");
                int red = Integer.parseInt(parts[0].split("=")[1].trim());
                int green = Integer.parseInt(parts[1].split("=")[1].trim());
                int blue = Integer.parseInt(parts[2].split("=")[1].trim());

                // Retourner la couleur créée à partir des composantes RGB
                return new Color(red, green, blue);
            } catch (Exception e) {
                // Si la chaîne n'est pas au format attendu, on retourne null
                e.printStackTrace();
            }
        }
        return null; // Retourne null si la chaîne ne correspond pas au format attendu
    }
}
