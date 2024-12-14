package application.passwordFinder;

import java.awt.Color ;
import java.awt.Font ;
import java.awt.FontMetrics ;
import java.awt.Graphics ;
import java.awt.Graphics2D ;
import java.awt.event.FocusAdapter ;
import java.awt.event.KeyAdapter ;
import java.awt.event.KeyEvent ;
import java.awt.geom.Line2D ;
import java.util.ArrayList ;
import javax.swing.JPanel ;
import annexe.Annexe ;
import gestion.utilisateurs.GestionnaireUtilisateurs ;
import gestion.utilisateurs.Utilisateur ;

public class PasswordGame extends JPanel {
	private static final long serialVersionUID = 1L ;
	private char[] searchedString ;
	private boolean[] shownSymbols ;
	private GestionnaireUtilisateurs gu = new GestionnaireUtilisateurs(Annexe.REPERTOIRE_CONNEXION, Annexe.NOM_CONNEXION) ;
	private Hangman hangman ;
	private boolean firstTime = true ;
	private ArrayList<String> goodSymbols, badSymbols ;

	/**
	 * Create the panel.
	 */
	public PasswordGame() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				System.out.println("On entre dans la gestion du clavier...") ;
				grabFocus() ;
				handleKey(e) ;
			}
		}) ;
		addFocusListener(new FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				System.out.println("J'ai gagné le focus !") ;
				setBackground(Color.YELLOW);  // Change la couleur de fond lorsque le champ est en focus
			}

			public void focusLost(java.awt.event.FocusEvent evt) {
				System.out.println("J'ai perdu le focus ! :(") ;
				setBackground(Color.WHITE);  // Rétablit la couleur de fond lorsque le champ perd le focus
			}
		}) ;

		setFocusable(true) ;
		setLayout(null) ;
		requestFocusInWindow() ;
		goodSymbols = new ArrayList<String>() ;
		badSymbols = new ArrayList<String>() ;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g) ;
		Graphics2D g2d = (Graphics2D) g ;

		if (firstTime) {
			hangman = new Hangman(getWidth() / 2, 30, 30) ;
			g2d.setFont(new Font(getFont().getFontName(), Font.PLAIN, 20)) ;
			firstTime = false ;
		}

		hangman.draw(g2d) ;
		//drawHangingDevice(g2d) ;

		hangman.draw(g2d) ;

		drawSymbols(g2d) ;

		grabFocus() ;
	}

	private int getLength(char[] chars, FontMetrics fontMetrics) {
		int totalTextWidth = 0 ;

		for (int i = 0; i < chars.length; i++) {
			String car = chars[i] + "" ;
			if (shownSymbols[i] || car.equals(" ")) {
				totalTextWidth += fontMetrics.stringWidth(" " + car);  // Ajouter la largeur du caractère
			} else {
				totalTextWidth += fontMetrics.stringWidth(" _");  // Ajouter la largeur de l'underscore
			}
		}

		return totalTextWidth ;
	}

	private void drawSymbols(Graphics2D g2d) {
		Graphics2D myG2d = (Graphics2D) g2d.create() ;

		int totalTextWidth = getLength(searchedString, myG2d.getFontMetrics()) ;

		int spacing = 10 ;  // Facteur d'espacement entre les caractères
		float posX = (getWidth() - totalTextWidth - (spacing * (searchedString.length - 1))) / 2 ;
		float posY = (4.0f/5.0f) * getHeight() ;

		FontMetrics fontMetrics = myG2d.getFontMetrics();
		myG2d.setColor(Color.RED) ;
		for (int i = 0; i < searchedString.length; i++) {
			String car = searchedString[i] + "" ;

			// Incrémenter la position en X après chaque caractère
			posX += spacing ;
			if (shownSymbols[i] || car.equals(" ")) {
				// Le caractère en cours a été trouvé ou il s'agit d'un espace.
				g2d.drawString(car, posX, posY) ;
			} else {
				// Le caractère en cours n'a pas encore été trouvé.
				g2d.drawString("_", posX, posY);
			}

			posX += fontMetrics.stringWidth("_") + spacing ;
		}
	}

	private int[] getCharacterPositions(char[] characters, char character) {
		int count = 0 ;
		for (char c : characters) {
			if (c == character) {
				count++ ;
			}
		}

		int[] positions = new int[count] ;

		if (count > 0) {
			int index = 0;
		    for (int i = 0; i < characters.length; i++) {
		        if (characters[i] == character) {
		            positions[index] = i ;
		            index++;
		        }
		    }
		}

		return positions ;
	}

	private boolean[] showCharacters(char[] characters, int[] positions) {
		boolean[] shownCharacters = new boolean[characters.length] ;

	    // Initialiser toutes les positions comme non affichées
	    for (int i = 0; i < characters.length; i++) {
	        shownCharacters[i] = false ;
	    }

	    // Marquer les positions correspondantes comme affichées
	    for (int pos : positions) {
	        if (pos >= 0 && pos < characters.length) {
	            shownCharacters[pos] = true ;
	        }
	    }

	    return shownCharacters ;
	}
	
	private void showCharacters(int[] positions) {
	    for (int pos : positions) {
	        if (pos >= 0 && pos < shownSymbols.length) {
	            shownSymbols[pos] = true ;
	        }
	    }
	}


	private void drawHangingDevice(Graphics2D g2d) {
		Graphics2D myG2d = (Graphics2D) g2d.create() ;
		myG2d.setFont(new Font(getFont().getFontName(), Font.PLAIN, 20)) ;

		int x = 50, y = 50;
		double heightStick = 100;
		double widthBar = 80;
		double widthRope = 30;

		// Dessiner le poteau vertical
		myG2d.setColor(Color.BLACK);
		myG2d.draw(new Line2D.Double(x, y, x, y + (int) heightStick)); // Poteau vertical

		// Dessiner la barre horizontale
		myG2d.setColor(Color.RED);
		myG2d.drawLine(x - (int) widthBar / 2, y, x + (int) widthBar / 2, y); // Barre horizontale

		// Dessiner la corde verticale
		myG2d.setColor(Color.GREEN);
		myG2d.drawLine(x, y + (int) heightStick, x, y + (int) heightStick + (int) widthRope); // Corde
	}

	private void handleKey(KeyEvent k) {
	    char theChar = k.getKeyChar() ;

	    // Vérifier si le caractère est valide pour le jeu (lettre, chiffre ou symbole)
	    if (isValidCharacter(theChar)) {
	        int[] pos = getCharacterPositions(searchedString, theChar) ;
	        for (int index : pos) {
	        	System.out.println(index + " " + shownSymbols[index]) ;
	        }

	        if (pos.length > 0) {
	            System.out.println("C'est gagné !") ;
	            showCharacters(pos) ;
	            
	            for (int index = 0 ; index < shownSymbols.length ; index++) {
		        	System.out.println(index + " " + shownSymbols[index]) ;
		        }
	        } else {
	        	System.out.println("Perdu !") ;
	            // Augmenter la partie du pendu si la lettre n'est pas dans searchedString
	            this.hangman.upgrade() ;
	        }
	    }

	    repaint() ;
	}

	// Fonction pour vérifier si le caractère est un caractère valide (lettre, chiffre, ou symbole)
	private boolean isValidCharacter(char c) {
	    return Character.isLetterOrDigit(c) || isSpecialCharacter(c);
	}

	// Exemple de vérification des caractères spéciaux, si nécessaire
	private boolean isSpecialCharacter(char c) {
	    return "!@#$%^&*()_-+=<>?/|".indexOf(c) >= 0;
	}

	public void setSearchedUsername(String username) {
		Utilisateur u = gu.findUser(username) ;

		if (u != null) {
			searchedString = u.getmDeP() ;

			shownSymbols = new boolean[searchedString.length] ;
			for (int i = 0 ; i < shownSymbols.length ; i++) {
				shownSymbols[i] = false ;
			}
		}

		reinitialiser() ;
		repaint() ;
	}

	private void reinitialiser() {

	}
}
