package application;

import java.awt.*;
import java.awt.event.* ;
import javax.swing.* ;
import javax.swing.border.* ;
import javax.swing.event.* ;
import attente.* ;

/**
 * @author Elias Kassas
 */
public class Reglages extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private PanneauAttente panneauAttenteDem;
	private JLabel lblNbSecondes;
	private long nbSecondes = 0 ;
	private long tempsTotal = 30 ; // secondes
	private boolean chronoLance = false ;
	private Timer timer ;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Reglages frame = new Reglages();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Reglages() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 559);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(6, 49, 438, 66);
		contentPane.add(panel);
		panel.setLayout(null);

		JSlider sldVitesse = new JSlider();
		sldVitesse.setValue(24);
		sldVitesse.setMaximum(120);
		sldVitesse.setMinimum(1);
		sldVitesse.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				panneauAttenteDem.setVitesse(sldVitesse.getValue()) ;			
			}
		});
		sldVitesse.setMinorTickSpacing(5);
		sldVitesse.setMajorTickSpacing(20);
		sldVitesse.setPaintTicks(true);
		sldVitesse.setPaintLabels(true);
		sldVitesse.setBounds(161, 13, 225, 40);
		panel.add(sldVitesse);

		JLabel lblNewLabel_1 = new JLabel("Vitesse d'animation :");
		lblNewLabel_1.setBounds(6, 6, 154, 16);
		panel.add(lblNewLabel_1);

		JTextArea txtrPlusCestGrand = new JTextArea();
		txtrPlusCestGrand.setText("Plus c'est grand, plus ce sera rapide.");
		txtrPlusCestGrand.setEditable(false);
		txtrPlusCestGrand.setWrapStyleWord(true);
		txtrPlusCestGrand.setLineWrap(true);
		txtrPlusCestGrand.setBounds(6, 26, 154, 34);
		panel.add(txtrPlusCestGrand);

		JLabel lblNewLabel = new JLabel("fps");
		lblNewLabel.setBounds(398, 26, 34, 16);
		panel.add(lblNewLabel);

		JLabel lblTitre = new JLabel("Réglages");
		lblTitre.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setBounds(6, 6, 438, 31);
		contentPane.add(lblTitre);

		panneauAttenteDem = new PanneauAttente();
		panneauAttenteDem.setBounds(155, 127, 140, 134);
		contentPane.add(panneauAttenteDem);

		JButton btnNewButton = new JButton("Lancer l'aperçu");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!chronoLance) {
					chronoLance = true ;
					lancerChrono() ;
				}
				/*panneauAttenteDem.demarrer() ;
				while (nbSecondes < tempsTotal) {
					nbSecondes += 1 ;
					lblNbSecondes.setText(nbSecondes/1 + " s") ;
					System.out.println(nbSecondes/1 + " s") ;
					try {
						Thread.sleep(1000l) ;
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				panneauAttenteDem.arreter() ;*/
			}
		});
		btnNewButton.setBounds(155, 273, 140, 29);
		contentPane.add(btnNewButton);

		lblNbSecondes = new JLabel("0 s");
		lblNbSecondes.setHorizontalAlignment(SwingConstants.CENTER);
		lblNbSecondes.setBounds(31, 184, 61, 16);
		contentPane.add(lblNbSecondes);
	}

	private void lancerChrono() {
		nbSecondes = 0;

		panneauAttenteDem.demarrer();

		timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nbSecondes++ ;
				lblNbSecondes.setText(nbSecondes + " s") ;

				if (nbSecondes >= tempsTotal || !chronoLance) {
					timer.stop() ;
					panneauAttenteDem.arreter() ;
					chronoLance = false ;
				}
			}
		});
		timer.start();
	}
}
