package application.aPropos;
public class HtmlFormatter {

    public static void main(String[] args) {
        String htmlContent = generateHtmlContent();
        System.out.println(htmlContent);
    }

    public static String generateHtmlContent() {
        // Titre principal
        StringBuilder html = new StringBuilder();
        html.append("<html>\n")
            .append("<head>\n")
            .append("<title>Fonctionnalités de l'application</title>\n")
            .append("</head>\n")
            .append("<body>\n")
            .append("<h1>Fonctionnalités de l'application</h1>\n");

        // Section FenetreConnexion
        html.append("<h2>FenetreConnexion (Panneau de connexion)</h2>\n");
        html.append("<ul>\n");
        html.append("<li><strong>Interface de connexion</strong> : Permet à l'utilisateur de saisir ses identifiants (nom d'utilisateur et mot de passe).</li>\n");
        html.append("<li><strong>Authentification de l'utilisateur</strong> : Vérifie les informations saisies (nom d'utilisateur et mot de passe) en les comparant avec une liste d'utilisateurs existants.</li>\n");
        html.append("<li><strong>Message d'erreur</strong> : Affiche un message d'erreur si l'utilisateur entre des informations incorrectes.</li>\n");
        html.append("<li><strong>Connexion réussie</strong> : Permet l'accès à l'application une fois que l'authentification est réussie.</li>\n");
        html.append("<li><strong>Sécurisation de l'interface</strong> : Assure que l'utilisateur ne peut pas accéder à l'application sans passer par la fenêtre de connexion.</li>\n");
        html.append("<li><strong>Interface responsive</strong> : S'adapte automatiquement à la taille de la fenêtre pour une meilleure expérience utilisateur.</li>\n");
        html.append("<li><strong>Déconnexion</strong> : Permet de revenir à la fenêtre de connexion après avoir quitté l'application ou après une déconnexion.</li>\n");
        html.append("</ul>\n");

        // Section PanelCalculs
        html.append("<h2>PanelCalculs (Panneau des calculs financiers)</h2>\n");
        html.append("<ul>\n");
        html.append("<li><strong>Affichage des montants de l'aide financière</strong> : Affiche le montant total des prêts et des bourses. Affiche les pourcentages relatifs entre la bourse et le prêt. Affiche un tableau avec la répartition mensuelle des aides financières.</li>\n");
        html.append("<li><strong>Saisie des montants de prêts et bourses</strong> : Permet à l'utilisateur de saisir les montants annuels des bourses et des prêts via des champs spécifiques.</li>\n");
        html.append("<li><strong>Calcul et mise à jour de la répartition mensuelle</strong> : Calcule automatiquement la répartition des prêts et des bourses selon les montants saisis. Réajuste les montants mensuels pour chaque mois de l'année en fonction des entrées de l'utilisateur.</li>\n");
        html.append("<li><strong>Sauvegarde des résultats calculés</strong> : Permet de sauvegarder les résultats dans un fichier pour les récupérer et consulter plus tard.</li>\n");
        html.append("<li><strong>Impression des résultats</strong> : Permet d'imprimer directement les résultats calculés. L'impression est effectuée en noir et blanc pour une consultation hors ligne.</li>\n");
        html.append("<li><strong>Sélection de l'année de début pour les calculs</strong> : Permet à l'utilisateur de choisir l'année de début afin de définir la période des calculs (cela peut affecter la répartition mensuelle).</li>\n");
        html.append("<li><strong>Réinitialisation des calculs</strong> : Permet de réinitialiser le tableau de répartition mensuelle pour saisir de nouvelles données sans avoir à fermer l'application.</li>\n");
        html.append("<li><strong>Chargement des données existantes</strong> : Permet de récupérer et d'afficher des données sauvegardées précédemment, facilitant la consultation des résultats antérieurs.</li>\n");
        html.append("<li><strong>Affichage des résultats dans une interface graphique</strong> : Présente les résultats sous forme de tableaux et graphiques pour une lecture claire et compréhensible des données.</li>\n");
        html.append("<li><strong>Déconnexion</strong> : Permet à l'utilisateur de se déconnecter et de revenir à l'écran de connexion, protégeant ainsi l'accès aux informations financières.</li>\n");
        html.append("</ul>\n");

        // Fermeture de la balise html
        html.append("</body>\n")
            .append("</html>");

        return html.toString();
    }
}
