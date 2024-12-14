package application.passwordFinder;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Test extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private PasswordGame passwordGame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test frame = new Test();
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
	public Test() {
		/*addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				passwordGame.requestFocusInWindow() ;
			}
		});
		addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		    	passwordGame.requestFocusInWindow() ; // Reprendre le focus après un clic
		    }
		}) ;
		addFocusListener(new FocusAdapter() {
		    @Override
		    public void focusLost(FocusEvent e) {
		        System.out.println("Focus perdu !") ;
		        passwordGame.requestFocusInWindow() ; // Essayez de reprendre le focus immédiatement
		    }
		}) ;*/

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		passwordGame = new PasswordGame();
		passwordGame.setBorder(new LineBorder(new Color(0, 0, 0)));
		passwordGame.setBounds(6, 6, 438, 260) ;
		passwordGame.setSearchedUsername(JOptionPane.showInputDialog(null, "Username ?")) ;
		contentPane.add(passwordGame);
	}
}
