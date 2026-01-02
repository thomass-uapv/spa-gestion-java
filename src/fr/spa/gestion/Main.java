package fr.spa.gestion;

import fr.spa.gestion.shell.Shell;

/**
 * Classe Main, c'est le point d'entrée du projet.
 */
public class Main {
	/**
	 * Méthode main qui s'exécute au lancement du programme.
	 * @param args
	 */
	public static void main(String[] args){
		Shell s = new Shell();
		s.start();
	}
}
