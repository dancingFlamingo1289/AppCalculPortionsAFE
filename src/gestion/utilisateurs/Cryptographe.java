package gestion.utilisateurs;

import java.math.BigInteger;
import java.util.Random;

public class Cryptographe {
	private static Random r = new Random() ;
	private static int p = generatePrime(1000, 100000), q = generatePrime(100, 100000), dephasage = 500 ;
	private static int n = p*q ;
	/*private static int x = (p - 1) * (q - 1) ;
	private static int d, e ;*/

	public static void main(String[] arg) {
		String chaine = "Bonjour maman ! Le facteur m'a mordu.", nouvChaine = "" ;
		char[] vectChaine = chaine.toCharArray() ;
		
		nouvChaine = new String(encoder(vectChaine)) ;
		System.out.println(chaine + "\n" + nouvChaine + "\n" + new String(decoder(encoder(vectChaine)))) ;
	}
	
	private static void initialize() {
		p = generatePrime(1000, 100000) ; 
		q = generatePrime(100, 100000) ;
		n = p*q ;
	}

	// Méthode pour définir de nouveaux p et q et recalculer n, phi, e, et d
	public static final void setPrimes(int p, int q) {
		Cryptographe.p = p ;
		Cryptographe.q = q ;
		//Cryptographe.n = p * q;
		//Cryptographe.x = (p - 1) * (q - 1) ;
		//findED() ;
	}

	// Méthode pour générer un nombre premier dans une plage donnée
	private static int generatePrime(int min, int max) {
		while (true) {
			int candidate = r.nextInt(max - min) + min ;
			if (BigInteger.valueOf(candidate).isProbablePrime(100)) {
				return candidate ;
			}
		}
	}

	/*private static void findED() {
		SecureRandom random = new SecureRandom();
		BigInteger phiBigInt = BigInteger.valueOf(x);

		// Choisir e
		e = 65537 ; // Choix commun pour e

		// Vérifier que e est copremier avec phi
		while (BigInteger.valueOf(e).gcd(phiBigInt).intValue() > 1) {
			e = random.nextInt(x - 2) + 2; // 2 <= e < phi
		}

		// Calculer d comme l'inverse modulaire de e modulo phi
		d = BigInteger.valueOf(e).modInverse(phiBigInt).intValue();
	}*/

	private int pgcd(int a, int b) {
		int a_reel = 0 ;

		while (b != 0) {
			a_reel = a ;
			a = b ;
			b = a_reel%b ;
		}

		return a ;
	}

	public static char[] encoder(char[] M) {
		char[] C = new char[M.length] ;
		
		for (int i = 0 ; i < M.length ; i++) {
			C[i] = (char) (M[i] + dephasage) ;
			System.out.println(M[i] + " -> " + C[i]) ;
		}
		
		return C ;
	}
	
	public static char[] decoder(char[] C) {
	    char[] M = new char[C.length] ;
	    
	    for (int i = 0; i < C.length; i++) {
	        // Récupérer la valeur après l'encodage
	        int value = C[i];
	        
	        // Inverser les opérations d'encodage
	        //value = (value * q) / p; // Remettre à l'état avant multiplication et division
	        //C[i] = (char) ((M[i] + dephasage)/(q*n)) ;
	        // q*n*C[i] = M[i] + deph
	        M[i] = (char) (C[i] - dephasage) ;
	        System.out.println(C[i] + " -> " + M[i]) ;
	        
	        // S'assurer que la valeur est dans la plage valide des caractères
	        //if (M[i] < 0) {
	        //    M[i] = 0; // Ou gérer autrement selon vos besoins
	        //}
	    }
	    
	    return M;
	}

	
	/*public static char[] encoder(char[] M) {
		char[] C = new char[M.length] ;
        BigInteger bigN = BigInteger.valueOf(n) ;
        BigInteger bigE = BigInteger.valueOf(e) ;

        for (int i = 0; i < M.length; i++) {
            BigInteger car = BigInteger.valueOf(M[i]);
            BigInteger encodedCar = car.modPow(bigE, bigN);
            C[i] = (char) encodedCar.intValue();
        }

        return C ;
	}*/

	/*public static char[] decoder(char[] C) {
		char[] M = new char[C.length];
		BigInteger bigN = BigInteger.valueOf(n);
		BigInteger bigD = BigInteger.valueOf(d);

		for (int i = 0; i < M.length; i++) {
			BigInteger car = BigInteger.valueOf(C[i]);
			BigInteger decodedCar = car.modPow(bigD, bigN);
			M[i] = (char) decodedCar.intValue();
		}

		return M;
	}*/

	public static char[] stringToCharArray(String s) {
		String[] splittedString = s.split("") ;
		char[] charArray = new char[splittedString.length] ;

		for (int i = 0 ; i < splittedString.length ; i++) {
			charArray[i] = splittedString[i].charAt(0) ;
		}

		return charArray ;
	}
}
