package annexe;

import java.awt.Robot ;
import java.awt.AWTException ;
import java.awt.event.InputEvent ;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import application.configuration.FichierConfiguration;

import java.awt.* ;

public class Main {
	public static void main2(String[] args) throws AWTException, InterruptedException {
		Robot robot = new Robot();

		// Paramètres du cercle
		int centerX = 800 ; // Coordonnée X du centre du cercle
		int centerY = 500 ; // Coordonnée Y du centre du cercle
		int radius = 300 ;  // Rayon du cercle
		int delay = 100 ;    // Délai entre chaque mouvement, en millisecondes

		// Angle initial
		double angle = 90 ;

		boolean cond = JOptionPane.showInputDialog(null, "On commence ?").equals("oui") ;
		// Boucle infinie pour déplacer la souris en cercle
		while (cond) {
			// Calcul des coordonnées x et y sur le cercle
			int x = centerX + (int) (radius * Math.cos(angle)) ;
			int y = centerY + (int) (radius * Math.sin(angle)) ;

			// Déplacer la souris à la position calculée
			robot.mouseMove(x, y) ;
			
			// Incrémenter l'angle pour avancer le long du cercle
			angle += 0.05 ; // Ajustez cette valeur pour contrôler la vitesse de rotation
			
			// Remettre l'angle à 0 quand il fait un tour complet
			if (angle >= 2 * Math.PI) {
				angle = 0 ;
				cond = JOptionPane.showInputDialog(null, "On continue ?").equals("oui") ;
			}
			
			// Pause pour éviter un mouvement trop rapide
			Thread.sleep(delay);
		}
	}

	public static void main1(String[] args) {
		try {
			// Crée une instance de Robot
			Robot robot = new Robot();

			// Déplace la souris à une position spécifique (exemple : x=500, y=300)
			robot.mouseMove(500, 300);

			// Attendre un moment pour que l'utilisateur puisse voir le déplacement
			robot.delay(1000);

			// Simuler un clic gauche
			robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

			// Attendre un moment avant d'effectuer d'autres actions
			robot.delay(500);

			// Simuler un clic droit
			robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
			robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);

			// Effectuer un défilement de la molette de la souris
			robot.mouseWheel(5); // Défiler vers le bas
			robot.delay(500);
			robot.mouseWheel(-5); // Défiler vers le haut
		} catch (AWTException e) {
			System.out.println("Erreur lors de la création de l'instance de Robot : " + e.getMessage());
		}
	}
}
