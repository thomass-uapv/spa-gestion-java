package fr.spa.gestion.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;

import fr.spa.gestion.exception.*;
import fr.spa.gestion.shell.command.*;
import fr.spa.gestion.tpjdbc.Gestion;
/**
 * Un objet qui va s'occuper d'afficher dans la console les résultats des commandes, permettre à l'utilisateur de rentrer des commandes, etc.
 */
public class Shell {
	
	private Gestion db;

	/**
	 * Statut du shell : si quit est à Faux alors le shell continue à fonctionner. Permet d'indiquer au shell d'arrêter l'application correctement.
	 */
	private boolean quit;

	/**
	 * Objet qui va récupérer l'entrée de l'utilisateur. C'est un objet de type  Reader, on peut lire caractère par caractère avec .read().
	 */
	private InputStreamReader isr;

	/**
	 * Objet qui prend en entrée un Reader qu'on va utiliser pour lire ligne à ligne (.readLine()).
	 */
	private BufferedReader r;
	
	/**
	 * Constructeur qui va initialiser les attributs de la classe Shell.
	 */
	public Shell() {
		super();
		this.db = new Gestion();
		this.quit = false;
		this.isr = new InputStreamReader(System.in);
		this.r = new BufferedReader(isr);
	}
	
	/**
	 * Méthode qui contient la boucle pour faire fonctionner l'application. Elle contient également une instance de chaque commande.<br>
	 * Elle va afficher le terminal, vérifier quelles instructions (commandes) sont demandées et afficher les résultats de ces commandes.<br>
	 * Elle affichera également les retours de certaines Exceptions si il y en a.
	 * Elle contient également le fonctionnement de la commande quit.
	 */
	public void start() {
		Echo e = new Echo();
		Help h = new Help();
		SqlCmd sqlCmd = new SqlCmd(this);
		Add add = new Add(this);
		Delete delete = new Delete(this);
		Search search = new Search(this);
		Update update = new Update(this);

		ArrayList<String> listeCmd = new ArrayList<String>();
		listeCmd.add(h.getDescription());
		listeCmd.add(e.getDescription());
		listeCmd.add(add.getDescription());
		listeCmd.add(sqlCmd.getDescription());
		listeCmd.add(delete.getDescription());
		listeCmd.add(search.getDescription());
		listeCmd.add(update.getDescription());

		h.setListeCommands(listeCmd);

		while (!this.quit) {
			System.out.print("> ");
			try {
				String in = this.input().trim();

				int firstSpace = in.indexOf(" ");
				String command;
				String args;
				if (firstSpace != -1) {
					command = in.substring(0, firstSpace);
					args = in.substring(firstSpace+1);
				} else {
					command = in;
					args = null;
				}

				if (command.equalsIgnoreCase("echo")) {
					e.setArgs(args);
					String res = e.getResult();
					if (res != null) {							
						System.out.println(res);
					}
				} else if (command.equalsIgnoreCase("add")){
					add.setArgs(args);
					System.out.println(add.getResult());
				} else if (command.equalsIgnoreCase("delete")) {
					delete.setArgs(args);
					System.out.println(delete.getResult());
				} else if(command.equalsIgnoreCase("search") || command.equalsIgnoreCase("info")) {
					search.setArgs(args);
					System.out.println(search.getResult());
				} else if(command.equalsIgnoreCase("update")) {
					update.setArgs(args);
					System.out.println(update.getResult());
				} else if (command.equalsIgnoreCase("sqlCmd")){
					sqlCmd.setArgs(args);
					System.out.println(sqlCmd.getResult());
				} else if (command.equals("?") || command.equalsIgnoreCase("help")) {
					System.out.println(h.getResult());
				} else if (command.equalsIgnoreCase("quit") || command.equalsIgnoreCase("stop") || command.equalsIgnoreCase("exit")) {
					System.out.println("Arrêt de l'application");
					this.quit = true;
					try {
						r.close();
						isr.close();
					} catch (IOException e1) {
						throw new ESException("Erreur sur la fermeture du Shell.");
					}
				} else {
					System.err.println("Commande incorrecte ou inexistante...");
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}


	/**
	 * Récupère l'entrée écrite par l'utilisateur.
	 * @return Renvoie l'entrée écrite par l'utilisateur
	 * @throws ESException
	 */
	public String input() throws ESException {
		String s;
		try {
			s = this.r.readLine();
			return s;
		} catch (IOException e) {
			throw new ESException("Erreur sur la lecture de l'entrée de l'utilisateur !");
		}
	}

	public Gestion getDb() {
		return db;
	}	
	
}
