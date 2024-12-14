package application.aPropos;

import javax.swing.*;
import java.awt.*;

public class FenetreAPropos extends JDialog {
    private JTabbedPane tabbedPane;

    public FenetreAPropos(JFrame parent) {
        super(parent, "À propos", true);
        setLayout(new BorderLayout());

        // Informations générales sur l'application
        JLabel appNameLabel = new JLabel("Nom de l'application : MonApplication");
        JLabel versionLabel = new JLabel("Version : 1.0.0");
        JLabel authorLabel = new JLabel("Auteur : John Doe");

        // Panneau pour les informations générales
        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        infoPanel.add(appNameLabel);
        infoPanel.add(versionLabel);
        infoPanel.add(authorLabel);

        // Utiliser un JTabbedPane pour organiser la documentation de chaque fonctionnalité
        tabbedPane = new JTabbedPane();

        // Exemple d'onglets ajoutés dynamiquement
        addTab("Fenêtre de connexion", "(Panneau de connexion)\n"
        		+ "Interface de connexion : Permet à l'utilisateur de saisir ses identifiants "
        		+ "(nom d'utilisateur et mot de passe).\n"
        		+ "Authentification de l'utilisateur : Vérifie les informations saisies (nom d'utilisateur et mot de passe) "
        		+ "en les comparant avec une liste d'utilisateurs existants.\n"
        		+ "Message d'erreur : Affiche un message d'erreur si l'utilisateur entre des informations incorrectes.\n"
        		+ "Connexion réussie : Permet l'accès à l'application une fois que l'authentification est réussie.\n"
        		+ "Sécurisation de l'interface : Assure que l'utilisateur ne peut pas accéder à l'application sans passer par "
        		+ "la fenêtre de connexion.\n"
        		+ "Interface responsive : S'adapte automatiquement à la taille de la fenêtre pour une meilleure expérience "
        		+ "utilisateur.\n"
        		+ "Déconnexion : Permet de revenir à la fenêtre de connexion après avoir quitté l'application ou après une "
        		+ "déconnexion.") ;
        addTab("Fonctionnalité 2", "Explication complète et exemples d'utilisation...");
        addTab("Fonctionnalité 3", "Guide de l'utilisateur et conseils...");

        // Bouton pour ajouter un nouvel onglet
        JButton addTabButton = new JButton("Ajouter un onglet");
        addTabButton.addActionListener(e -> {
            String title = JOptionPane.showInputDialog("Entrez le titre de l'onglet:");
            if (title != null && !title.trim().isEmpty()) {
                String content = JOptionPane.showInputDialog("Entrez le contenu de l'onglet:");
                if (content != null && !content.trim().isEmpty()) {
                    addTab(title, content);
                }
            }
        });

        // Bouton de fermeture
        JButton closeButton = new JButton("Fermer");
        closeButton.addActionListener(e -> dispose());

        // Ajouter les composants à la boîte de dialogue
        add(infoPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
        add(addTabButton, BorderLayout.SOUTH);
        add(closeButton, BorderLayout.SOUTH);

        // Réglage de la taille de la fenêtre
        setSize(500, 400) ;
        setLocationRelativeTo(parent) ; // Centrer la fenêtre par rapport à la fenêtre parent
    }

    // Méthode pour ajouter un onglet
    private void addTab(String title, String content) {
        JTextArea textArea = new JTextArea(content);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        textArea.setBackground(getBackground());
        JScrollPane scrollPane = new JScrollPane(textArea);
        tabbedPane.addTab(title, scrollPane);
    }
}
