package application.passwordFinder;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;

public class Hangman {
	/** Coordonnée du point **/
	private int x, y ;
	private double refLength ;
	private double width, heigth ;
	private int state ;
	private ArrayList<Shape> man ;
	private Color colour ;
	private final int MAX_STATE = 11 ;

	public Hangman(int x, int y, double refLength) {
		this.x = x ;
		this.y = y ;
		this.refLength = refLength ;
		this.state = 0 ;
		this.man = new ArrayList<Shape>() ;
		this.colour = Color.RED ; // Par défaut en noir

		// Appel à la méthode pour créer la géométrie de la potence et du bonhomme
		createGeometry() ;
	}

	private void createGeometry() {
		double headRadius = refLength ;
		double bodyHeight = refLength * 2 ;
		double limbLength = refLength * 1.5 ;
		double eyeRadius = refLength / 6 ;
		double noseWidth = refLength / 4 ;
		double mouthWidth = refLength / 2 ;

		this.man = new ArrayList<Shape>() ;
		// Head
		if (state >= 1) {
			man.add(new Ellipse2D.Double(x - headRadius / 2, y - headRadius, headRadius, headRadius)) ;
		}

		// Body
		if (state >= 2) {
			man.add(new Line2D.Double(x, y, x, y + bodyHeight)); // Corps
			System.out.println("corps") ;
		}

		// Arms
		if (state >= 3) {
			man.add(new Line2D.Double(x, y + refLength, x - limbLength, y + bodyHeight / 2)); // Left arm
			System.out.println("bras gauche") ;
		}

		if (state >= 4) {
			man.add(new Line2D.Double(x, y + refLength, x + limbLength, y + bodyHeight / 2)); // Right arm
			System.out.println("bras droit") ;
		}

		// Legs
		if (state >= 5) {
			man.add(new Line2D.Double(x, y + bodyHeight, x - limbLength, y + bodyHeight + limbLength)); // Left leg
			System.out.println("jambe gauche") ;
		}

		if (state >= 6) {
			man.add(new Line2D.Double(x, y + bodyHeight, x + limbLength, y + bodyHeight + limbLength)); // Right leg
			System.out.println("jambe droite") ;
		}

		// Eyes
		if (state >= 7) {
			double eyeY = y - headRadius / 2 + headRadius / 4;
			man.add(new Ellipse2D.Double(x - headRadius / 4 - eyeRadius / 2, eyeY, eyeRadius, eyeRadius));  // Left eye
			System.out.println("oeil gauche") ;
		}

		if (state >= 8) {
			double eyeY = y - headRadius / 2 + headRadius / 4;
			man.add(new Ellipse2D.Double(x + headRadius / 4 - eyeRadius / 2, eyeY, eyeRadius, eyeRadius));  // Right eye
			System.out.println("oeil droit") ;
		}

		// Nose
		if (state >= 9) {
			man.add(new Line2D.Double(x, y - headRadius / 2 + headRadius / 2, x, y - headRadius / 2 + headRadius / 1.5)) ;
			System.out.println("nez") ;
		}

		// Mouth
		if (state >= 10) {
			man.add(new Arc2D.Double(x - mouthWidth / 2, y - headRadius / 2 + headRadius / 1.5, 
					mouthWidth, mouthWidth / 2, 0, -180, Arc2D.OPEN)) ;
			System.out.println("bouche") ;
		}
	}

	public void draw(Graphics2D g2d) {
		Graphics2D myG2d = (Graphics2D) g2d.create() ;
		myG2d.setColor(colour) ;
		
		//System.out.println("Je dessine le Hangman.") ;
		for (int i = 0 ; i < this.state ; i++) {
			Shape s = man.get(i) ;

			if (i >= 7 && i <= 10) {
				myG2d.setColor(Color.BLUE) ;
			} else {
				myG2d.setColor(colour) ;
			}
			
			if (s instanceof Line2D.Double || s instanceof Path2D) {
				myG2d.draw(s) ;
			} else {
				myG2d.fill(s) ;
			}
		}
	}

	private void setState(int newState) {
		state = newState % MAX_STATE ;
		createGeometry() ;
	}

	public int getState() {
		return state ;
	}
	
	public int getMaxState() {
		return MAX_STATE ;
	}
	
	public void upgrade() {
		System.out.println(state) ;
		state += 1 ;
		state %= MAX_STATE ;
		System.out.println(state) ;

		createGeometry() ;
	}
}
