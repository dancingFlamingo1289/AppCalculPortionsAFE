package application;

import java.awt.*;
import java.awt.print.*;
import java.io.*;
import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;
import javax.swing.*;

public class Imprimante {
	public static void imprimerFichier(File fichier) {
		try {
			PrinterJob job = PrinterJob.getPrinterJob();
			job.setPrintable(new Printable() {
				@Override
				public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
					if (pageIndex > 0) {
						return NO_SUCH_PAGE;
					}

					Graphics2D g2d = (Graphics2D) g;
					g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

					try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
						String line;
						int y = 0;
						while ((line = br.readLine()) != null) {
							g2d.drawString(line, 0, y);
							y += 12; // Hauteur de ligne approximative
						}
					} catch (IOException e) {
						e.printStackTrace();
					}

					return PAGE_EXISTS;
				}
			});

			// Définir les attributs d'impression
			PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
			attributes.add(MediaSizeName.ISO_A4);
			attributes.add(new PrinterResolution(300, 300, PrinterResolution.DPI));
			attributes.add(Chromaticity.MONOCHROME); // Impression en noir et blanc

			// Trouver les services d'impression disponibles
			DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
			PrintService[] printServices = PrintServiceLookup.lookupPrintServices(flavor, attributes);

			if (printServices.length > 0) {
				String message = "Voici les services d'impression disponibles. Lequel souhaitez-vous utiliser ?" ;
				for (int i = 0 ; i < printServices.length ; i++)
					message += i + " - " + printServices[i] ;
				System.out.println(message) ;

				// Utiliser le premier service d'impression disponible
				PrintService printService = printServices[0];
				job.setPrintService(printService);
				job.print(attributes);
			} else {
				JOptionPane.showMessageDialog(null, "Aucun service d'impression trouvé.");
			}
		} catch (PrinterException e) {
			e.printStackTrace();
		}
	}

	public static void imprimerFichier2(File fichier) {
		try {
			PrinterJob job = PrinterJob.getPrinterJob();
			job.setPrintable(new Printable() {
				@Override
				public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
					if (pageIndex > 0) {
						return NO_SUCH_PAGE;
					}

					Graphics2D g2d = (Graphics2D) g;
					g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

					try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
						String line;
						int y = 0;
						while ((line = br.readLine()) != null) {
							g2d.drawString(line, 0, y);
							y += 12; // Hauteur de ligne approximative
						}
					} catch (IOException e) {
						e.printStackTrace();
					}

					return PAGE_EXISTS;
				}
			});

			// Afficher la boîte de dialogue d'impression
			if (job.printDialog()) {
				// Définir les attributs d'impression
				PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
				attributes.add(MediaSizeName.ISO_A4);
				attributes.add(new PrinterResolution(300, 300, PrinterResolution.DPI));
				attributes.add(Chromaticity.MONOCHROME); // Impression en noir et blanc

				// Configurer l'attribut de couleur pour l'impression en noir et blanc
				PrintService printService = job.getPrintService();
				if (printService != null) {
					job.setPrintService(printService);
					job.print(attributes);
				} else {
					System.out.println("Aucun service d'impression disponible.");
				}
			}
		} catch (PrinterException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		imprimerFichier(new File("AFE")) ;
	}
}
