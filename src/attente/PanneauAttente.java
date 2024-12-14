package attente;

import java.awt.*;
import java.awt.geom.*;
import javax.management.timer.Timer ;
import javax.swing.* ;

public class PanneauAttente extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	private boolean premiereFois = true, enCoursDAnimation ;
	private double rayonHorl ;
	private Ellipse2D.Double cadre ;
	private Arc2D.Double arc ;
	private Color coulArc = Color.blue ;
	/** Angle trigonométrique fait par le bras de l'horloge en radians. **/
	private double angleTrait = 0 ;
	/** Incrément de l'angle trigonométrique fait par le bras de l'horloge en degrés. **/
	private final double INCREMENT_ANGLE = 360/60 ;
	private long vitesse = 24l ;
	private long tempsSleep = Timer.ONE_SECOND/vitesse ;
	private long tempsTotal = 0l ;
	//private int x = 0 ; // secondes

	/**
	 * Create the panel.
	 */
	public PanneauAttente() {
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		// Recalcule la taille à chaque redimensionnement
		rayonHorl = Math.min(getWidth(), getHeight()) / 2 - 5;
		cadre = new Ellipse2D.Double(getWidth() / 2 - rayonHorl, getHeight() / 2 - rayonHorl, 2 * rayonHorl, 2 * rayonHorl);

		if (premiereFois) {
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setStroke(new BasicStroke(4));
			
			// Dessiner l'arrière-plan de l'horloge
			g2d.setColor(Color.white);
			g2d.fill(cadre);
			
			// Changement de la couleur pour dessiner la section de cercle...
			g2d.setColor(Color.pink) ;
		}

		// Rotation de l'arc pour créer l'animation
		g2d.rotate(-Math.PI / 2, getWidth() / 2, getHeight() / 2);
		arc = new Arc2D.Double(getWidth() / 2 - rayonHorl + 1, getHeight() / 2 - rayonHorl + 1, 
				2 * rayonHorl - 2, 2 * rayonHorl - 2, 0, -angleTrait, Arc2D.PIE) ;
		g2d.fill(arc) ;
	}

	@Override
	public void run() {
		while(enCoursDAnimation) {
			angleTrait += INCREMENT_ANGLE ;
			tempsTotal += tempsSleep ;

			if (tempsTotal % 2 == 0) 
				coulArc = new Color((float) Math.random() * (float) Math.random(), 
						(float) Math.random() * (float) Math.random(), (float) Math.random() * (float) Math.random()) ;

			if (angleTrait >= 360) {
				angleTrait -= 360 ;
			}

			if (angleTrait < 0) {
				angleTrait += 360 ;
			}

			try {
				Thread.sleep(tempsSleep) ;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			repaint() ;
		}
	}

	public void demarrer() {
		if (!enCoursDAnimation) { 
			Thread processusAnim = new Thread(this) ;
			processusAnim.start() ;
			enCoursDAnimation = true ;
		}
	}

	public void arreter() {
		enCoursDAnimation = false ;
		reinitialiser() ;
	}

	private void reinitialiser() {
		angleTrait = 0 ;
		repaint() ;
	}

	public void setVitesse(long vit) {
		vitesse = vit ;
		boolean anim = enCoursDAnimation ;
		tempsSleep = Timer.ONE_SECOND/vitesse ;
		arreter() ;

		try {
			Thread.sleep(100l) ;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (anim) 
			demarrer() ;
	}
}
