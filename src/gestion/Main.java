package gestion;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        // Obtenir toutes les racines du système de fichiers
        File[] racines = File.listRoots();

        // Vérifier si des racines existent et afficher la première
        if (racines.length > 0) {
            System.out.println("Première racine : " + racines[0].getAbsolutePath());
        } else {
            System.out.println("Aucune racine trouvée.");
        }
    }
}

