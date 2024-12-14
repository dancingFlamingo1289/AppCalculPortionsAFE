package application.configuration;

/**
 * Classe contenant toutes les informations d'une fenêtre de configuration.
 * Il sera plus simple de traiter les PropertyChange avec cette classe et modifier les informations 
 * reçues par l'application.
 * @author Elias Kassas
 */
public class InfoConfiguration {
	/** Thème choisi par l'utilisateur. **/
	private Theme theme ;
	/** La police de caractère. **/
	private String policeDeCaractere ;
	/** Variable booléenne servant à savoir si l'utilisateur veut de la musique ou non. **/
	private boolean musique ;
	/** Titre du choix de la musique fait par l'utilisateur. **/
	private String choixMusique ;
	/** Touches associées au flippers. **/
	private char toucheFlipperDroit, toucheFlipperGauche ;

	/**
	 * Constructeur d'un objet InfoConfiguration.
	 * @param theme : Thème de l'application.
	 * @param policeDeCaractere : Police de caractère de l'application.
	 * @param musique : Musique voulue ou non dans l'application.
	 * @param choixMusique : Choix de la musique voulue dans l'application.
	 * @param toucheFlipperDroit : Touche associée au flipper droit.
	 * @param toucheFlipperGauche : Touche associée au flipper gauche.
	 */
	// Par Elias Kassas
	public InfoConfiguration(Theme theme, String policeDeCaractere, boolean musique, 
			String choixMusique, char toucheFlipperDroit, char toucheFlipperGauche) {
		this.theme = theme;
		this.policeDeCaractere = policeDeCaractere;
		this.musique = musique;
		this.choixMusique = choixMusique;
		this.toucheFlipperDroit = toucheFlipperDroit;
		this.toucheFlipperGauche = toucheFlipperGauche;
	}

	/**
	 * Méthode servant à voir le thème actuel de l'application.
	 * Note : Le thème est de type Object pour le moment, mais nous créerons un objet de type 
	 * Thème à la troisième mêlée.
	 * @return le thème actuel de l'application
	 */
	public Theme getTheme() {
		return theme;
	}

	/**
	 * Méthode servant à voir le thème actuel de l'application.
	 * Note : Le thème est de type Object pour le moment, mais nous créerons un objet de type 
	 * Thème à la troisième mêlée.
	 * @return le thème actuel de l'application
	 */
	// Par Elias Kassas
	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	/**
	 * Méthode permettant d'obtenir la police de caractère actuelle.
	 * @return La police de caractère actuelle.
	 */
	// Par Elias Kassas
	public String getPoliceDeCaractere() {
		return policeDeCaractere ;
	}

	/**
	 * Méthode permettant de modifier la police de caractère actuelle.
	 * @param policeDeCaractere : La police de caractère actuelle.
	 */
	// Par Elias Kassas
	public void setPoliceDeCaractere(String policeDeCaractere) {
		this.policeDeCaractere = policeDeCaractere ;
	}

	/**
	 * Méthode permettant d'obtenir la décision de l'utilisateur quant à l'ajout de musique.
	 * @return La décision de l'utilisateur quant à l'ajout de musique ou non.
	 */
	// Par Elias Kassas
	public boolean isMusique() {
		return musique;
	}

	/**
	 * Méthode permettant de modifier la décision de l'utilisateur quant à l'ajout de musique.
	 * @param musique : La décision de l'utilisateur quant à l'ajout de musique ou non.
	 */
	// Par Elias Kassas
	public void setMusique(boolean musique) {
		this.musique = musique;
	}

	/**
	 * Méthode permettant d'obtenir la musique choisie par l'utilisateur.
	 * @return La musique choisie par l'utilisateur.
	 */
	// Par Elias Kassas
	public String getChoixMusique() {
		return choixMusique;
	}

	/**
	 * Méthode permettant de modifier la musique choisie par l'utilisateur.
	 * @param choixMusique : La musique choisie par l'utilisateur.
	 */
	// Par Elias Kassas
	public void setChoixMusique(String choixMusique) {
		this.choixMusique = choixMusique;
	}

	/**
	 * Méthode permettant d'obtenir la touche associée au flipper droit.
	 * @return Le caractère unicode de la touche associée au flipper droit
	 */
	// Par Elias Kassas
	public char getToucheFlipperDroit() {
		return toucheFlipperDroit;
	}

	/**
	 * Méthode permettant de modifier la touche associée au flipper droit.
	 * @param toucheFlipperDroit : Le caractère unicode de la touche associée au flipper droit
	 */
	// Par Elias Kassas
	public void setToucheFlipperDroit(char toucheFlipperDroit) {
		this.toucheFlipperDroit = toucheFlipperDroit;
	}

	/**
	 * Méthode permettant d'obtenir la touche associée au flipper gauche.
	 * @return Le caractère unicode de la touche associée au flipper gauche.
	 */
	// Par Elias Kassas
	public char getToucheFlipperGauche() {
		return toucheFlipperGauche;
	}

	/**
	 * Méthode permettant de modifier la touche associée au flipper gauche.
	 * @param toucheFlipperGauche : Le caractère unicode de la touche associée au flipper gauche.
	 */
	// Par Elias Kassas
	public void setToucheFlipperGauche(char toucheFlipperGauche) {
		this.toucheFlipperGauche = toucheFlipperGauche;
	}

	/**
	 * Méthode faisant une chaîne de caractères avec les propriétés d'un objet InfoConfiguration.
	 * @return Les propriétés en chaîne de caractères. 
	 */
	// Par Elias Kassas
	@Override
	public String toString() {
		return "InfoConfiguration [theme = " + theme + ", policeDeCaractere = " + policeDeCaractere + 
				", musique = " + musique + ", choixMusique = " + choixMusique + ", toucheFlipperDroit "
				+ "= " + toucheFlipperDroit + ", toucheFlipperGauche = " 
				+ toucheFlipperGauche + "]" ;
	}
}
