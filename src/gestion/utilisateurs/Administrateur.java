package gestion.utilisateurs;

// TODO Fix the inheritence problem by figuring out a way to work with the String *type*.
public class Administrateur extends Utilisateur {
	private final String TYPE = "Administrateur" ;
	
	public static void main(String[] args) {
		Utilisateur u = new Administrateur() ;
		System.out.println(u instanceof Administrateur) ;
	}
	
	public Administrateur(char[] nomUtil, char[] mDeP) {
		super(nomUtil, mDeP) ;
	}
	
	public Administrateur() {
		super() ;
	}
	
	public String toString() {
		return this.type + " {" + new String(getNomUtil()) + " -> " + new String(getmDeP()) + "}" ;
	}
	
	public static Administrateur read(String toString) {
		String typeAttendu = "Administrateur" ;
		
		if (!toString.trim().startsWith(typeAttendu)) {
			System.err.println("Format incorrect : " + toString + "\n"
					+ "On s'attend à la chaîne de caractères '" + typeAttendu + "'.") ;
			return null ; // Retourne null si le format est incorrect
		}

		String newStr = toString.substring(typeAttendu.length()).trim() ;
		
		// On s'attend à ce que la chaîne contienne deux parties séparées par un tabulateur
		String[] firstlySplitted = newStr.split(",") ;

		// Récupération du nom d'utilisateur et du mot de passe
		String userInfo = firstlySplitted[0].replace("{", "").replace("}", "") ;

		String[] userInfoSplitted = userInfo.split("->") ;

		if (userInfoSplitted.length == 2) {
			String username = userInfoSplitted[0].trim() ;
			String password = userInfoSplitted[1].trim() ;
			
			return new Administrateur(username.toCharArray(), password.toCharArray()) ;
		} else {
			return null ;
		}
	}
}
