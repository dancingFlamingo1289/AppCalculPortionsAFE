package application.configuration;

import java.io.File;
import java.io.IOException;

import annexe.Annexe;

public class ConfigurationManager {
	//Fichier configFile ;
	File configFolder ;
	
	public ConfigurationManager(File configFolder, String configFileName) {
		this.configFolder = configFolder ;
		//this.configFile = new Fichier(configFolder, configFileName, "config") ;
	}
	
	public static void main(String[] args) {
		FichierConfiguration maConfig = new FichierConfiguration(new File(System.getProperty("user.home"), "CalculsAFE"), 
				"userInput") ;
		System.out.println("Fichier créé !") ;
		maConfig.ajouter("volume") ;
		maConfig.ajouter("thème", "mayonnaise") ;
		maConfig.ajouter("angle", 360) ;
		System.out.println(maConfig.getContenuFichier()) ;
		
		try {
			FichierConfiguration f = FichierConfiguration.lire(new File(new File(System.getProperty("user.home"), "CalculsAFE"), 
					"maConfiguration1.config")) ;
			System.out.println(f.getContenuFichier()) ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
