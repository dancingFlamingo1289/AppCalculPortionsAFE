# **AppCalculPortionsAFE**

### **Description du projet**
**AppCalculPortionsAFE** est une application Java conçue pour faciliter le calcul, l'affichage et l'impression des portions de prêts et bourses attribuées dans le cadre de l'Aide Financière aux Études (AFE). Grâce à une interface utilisateur intuitive, l'application permet aux utilisateurs de visualiser les répartitions mensuelles, gérer des utilisateurs, configurer des paramètres et produire des rapports imprimables.

---

## **Fonctionnalités**
- **Gestion des prêts et bourses :**
  - Calcul automatique des portions.
  - Affichage détaillé des répartitions mensuelles.

- **Gestion des utilisateurs :**
  - Authentification des utilisateurs.
  - Gestion des administrateurs et des accès.

- **Configuration :**
  - Personnalisation des thèmes et des paramètres.
  - Gestion de fichiers de configuration.

- **Impression et rapports :**
  - Génération de documents imprimables en noir et blanc.
  - Impression automatisée sans options supplémentaires.

- **Sécurité :**
  - Gestion des mots de passe.

---

## **Technologies utilisées**
- **Langage :** Java (SE 21)  
- **Framework graphique :** Swing  
- **Architecture :** Orientée Objet (POO)  
- **Gestion de fichiers :** Fichier texte `.txt` via la classe `FichierTexte`.  
- **Sécurité :** Implémentation de chiffrement pour la gestion des mots de passe.  

---

## **Prérequis**
- **JDK 21** installé sur votre machine.  
- Environnement de développement compatible comme **Eclipse**, **IntelliJ IDEA** ou **Visual Studio Code**.  
- Aucune bibliothèque externe n'est requise.

---

## **Installation**
1. **Cloner le dépôt :**
   ```bash
   git clone https://github.com/dancingFlamingo1289/AppCalculPortionsAFE.git
   cd AppCalculPortionsAFE
   ```

2. **Importer dans votre IDE :**
   - Ouvrez Eclipse ou IntelliJ IDEA.  
   - Importez le projet en tant que **Projet Java existant**.  

3. **Compiler et exécuter :**
   - Lancez la classe principale : `src/application/Main.java`.  

---

## **Structure du projet**

Voici l'organisation des fichiers et dossiers de l'application :

```plaintext
AppCalculPortionsAFE/
│
├── src/
│   ├── annexe/
│   │   └── Annexe.java          # Classe annexe
│   │
│   ├── application/
│   │   ├── ApplicationAFE.java  # Logique principale de l'application
│   │   ├── Imprimante.java      # Gestion de l'impression
│   │   └── Reglages.java        # Gestion des réglages (en cours)
│   │
│   ├── application.administration/
│   │   ├── AppAdministrateur.java        # Interface administrateur (en cours)
│   │   └── PanAffichageUtilisateurs.java # Panel affichant les utilisateurs
│   │
│   ├── application.aPropos/
│   │   └── FenetreAPropos.java  # Fenêtre "À propos" (en cours)
│   │
│   ├── application.configuration/      # (en cours de développement)
│   │   ├── Configuration.java          # Configuration de base
│   │   ├── ConfigurationManager.java   # Gestionnaire de configuration
│   │   ├── FichierConfiguration.java   # Gestion des fichiers de configuration
│   │   ├── InfoConfiguration.java      # Informations de configuration
│   │   └── Theme.java                  # Thèmes visuels de l'application
│   │
│   ├── application.passwordFinder/
│   │   ├── Hangman.java           # Jeu pour trouver un mot de passe
│   │   ├── PasswordFinderUI.java  # Interface utilisateur pour mots de passe
│   │   └── PasswordGame.java      # Logique du jeu de mot de passe
│   │
│   ├── application.remboursement/   # (en cours de développement)
│   │   └── PanelRemboursement.java  # Panel pour les remboursements
│   │
│   ├── attente/
│   │   └── PanneauAttente.java   # Gestion des écrans d'attente
│   │
│   ├── gestion/
│   │   ├── CalculsAFE.java       # Calcul des portions de l'AFE
│   │   └── FichierTexte.java     # Gestion des fichiers texte
│   │
│   ├── gestion.utilisateurs/
│   │   ├── Administrateur.java           # Classe Administrateur
│   │   ├── Cryptographe.java             # Chiffrement des données
│   │   ├── GestionnaireUtilisateurs.java # Gestion des utilisateurs
│   │   └── Utilisateur.java              # Modèle d'utilisateur
│   │
│   └── panels/
│       ├── FenetreConnexion.java  # Interface de connexion utilisateur
│       └── PanelCalculs.java      # Panel pour afficher les calculs
│
└── JRE System Library [JavaSE-21] # Bibliothèque JDK utilisée
```

---

## **Exécution de l'application**
1. Lancez le programme en exécutant la classe `ApplicationAFE.java` depuis le package **`application`**.  
2. Connectez-vous avec un utilisateur existant ou créez-en un via l'interface d'administration. 
3. Naviguez à travers les différents panneaux pour :
   - Calculer et afficher les portions AFE.  
   - Configurer les paramètres de l'application.  
   - Imprimer les rapports.

---

## **Droits d'auteur**
Ce projet est soumis à des **droits d'auteur**. Toute utilisation, reproduction ou distribution sans autorisation explicite est strictement interdite.

---

## **Crédits**
Développé par **Elias Kassas** dans le cadre d'un projet Java SE avec Swing.
