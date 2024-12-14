package application.administration;

import java.awt.* ;
import java.awt.event.* ;
import java.util.* ;
import javax.swing.* ;
import javax.swing.border.* ;

/**
 * 
 * @author Elias Kassas
 */
public class AppAdministrateur extends JFrame {
	private static final long serialVersionUID = 1L ;
	private JPanel contentPane ;
	private JPanel panGestion ;
	private JLabel lblTitre ;
	private JLabel lblNewLabel ;
	private ArrayList<JComponent> lesBoutonsDeGestion ; 
	private ArrayList<JPanel> lesPanels ;
	private int width, height ;
	private int nbBoutons = 0 ;
	private JPanel panAff ;
	private JPanel panAjout ;
	private Rectangle bdAff = new Rectangle(6, 6, 379, 242) ;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppAdministrateur frame = new AppAdministrateur() ;
					frame.setVisible(true) ;
				} catch (Exception e) {
					e.printStackTrace() ;
				}
			}
		}) ;
	}

	/**
	 * Create the frame.
	 */
	public AppAdministrateur() {
		// Dans le constructeur AppAdministrateur
		/*this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				panGestion.setBounds(45, 71, (int) (getWidth() / 1.25d), getHeight() - (lblTitre.getHeight() + lblNewLabel.getHeight() + 71)) ;
				width = panGestion.getWidth() - 10 ;
				height = panGestion.getHeight()/(nbBoutons + 1) ;
				
				lblTitre.setBounds(6, 6, getWidth() - 6, 25) ;
				lblNewLabel.setBounds(11, 43, getWidth() - 11, 16) ;
				revalidate() ;
			}
		}) ;*/

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
		setBounds(100, 100, 450, 621) ;
		contentPane = new JPanel() ;
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)) ;

		setContentPane(contentPane) ;
		contentPane.setLayout(null) ;

		lblTitre = new JLabel("Administrateur") ;
		lblTitre.setFont(new Font("Lucida Grande", Font.BOLD, 20)) ;
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER) ;
		lblTitre.setBounds(6, 6, getWidth() - 6, 25) ;
		contentPane.add(lblTitre) ;

		lblNewLabel = new JLabel("Que souhaitez-vous faire ?") ;
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER) ;
		lblNewLabel.setBounds(11, 43, getWidth() - 11, 16) ;
		contentPane.add(lblNewLabel) ;

		panGestion = new JPanel() ;
		panGestion.setBackground(Color.RED) ;
		panGestion.setBounds(29, 71, (int) (getWidth() / 1.15d), (getHeight() - (lblTitre.getHeight() + lblNewLabel.getHeight() + 71))/2) ;
		contentPane.add(panGestion) ;
		panGestion.setLayout(null) ;
		
		panAff = new JPanel() ;
		panAff.setBounds(29, 332, 391, 243) ;
		contentPane.add(panAff) ;
		panAff.setLayout(null) ;
		
		panAjout = new JPanel() ;
		panAjout.setBounds(6, 5, 379, 232) ;
		//panAff.add(panAjout) ;
		panAjout.setLayout(null) ;
		
		JLabel lblTitrePage = new JLabel("Ajouter un utilisateur".toUpperCase()) ;
		lblTitrePage.setBounds(0, 0, 379, 25) ;
		panAjout.add(lblTitrePage) ;
		lblTitrePage.setFont(new Font("Lucida Grande", Font.BOLD, 16)) ;
		lblTitrePage.setHorizontalAlignment(SwingConstants.CENTER) ;
		
		JSeparator separator = new JSeparator() ;
		separator.setForeground(Color.BLACK) ;
		separator.setBounds(29, 320, 391, 12) ;
		contentPane.add(separator) ;

		lesBoutonsDeGestion = new ArrayList<JComponent>() ;
		width = panGestion.getWidth() ;
		height = panGestion.getHeight()/(nbBoutons + 1) ;
		lesPanels = new ArrayList<JPanel>() ;

		ajouterLeBouton("Afficher les utilisateurs", new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				afficher(new PanAffichageUtilisateurs()) ;
			}
		}) ;
		
		afficherLesBoutons() ;
	}
	
	private void afficher(JPanel affichage) {
		panGestion.removeAll() ;
		
		//affichage.setBackground(Color.RED) ;
		affichage.setBounds(bdAff) ;
		panGestion.add(affichage) ;
		
		panGestion.revalidate() ;
		panGestion.repaint() ;
	}
	
	private void ajouterLeBouton(String nom, ActionListener comportement) {
	    JButton leBoutonAAjouter = new JButton(nom);
	    leBoutonAAjouter.addActionListener(comportement);
	    lesBoutonsDeGestion.add(leBoutonAAjouter);
	    height = panAff.getHeight() / (lesBoutonsDeGestion.size() + 1) ;
	    afficherLesBoutons() ;
	}

	private void afficherLesBoutons() {
		panAff.removeAll(); // Supprimer tous les composants avant de les réafficher
	    
	    for (int i = 0; i < lesBoutonsDeGestion.size(); i++) {
	        JComponent unBouton = lesBoutonsDeGestion.get(i);
	        unBouton.setBounds(0, (i + 1) * height, panGestion.getWidth(), height);
	        panAff.add(unBouton);
	    }
	    
	    panAff.revalidate() ;
	}

}
