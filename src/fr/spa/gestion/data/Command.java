package fr.spa.gestion.data;

import java.sql.SQLException;

import fr.spa.gestion.exception.*;
import fr.spa.gestion.shell.Shell;


/**
 * Interface définissant les méthodes que chaque Commande doit implémenter.
 */
public abstract class Command {

	protected Shell s;
	
	protected String args;
	protected String description;

	public Command(Shell s, String args, String description) {
		super();
		this.args = args;
		this.description = description;
	}

	/**
	 * Renvoie le résultat de la commande une fois exécutée.
	 * @return String contenant le résultat de la commande une fois exécutée.
	 * @throws SQLException
	 * @throws ../......
	 */
	public abstract String getResult() throws  EntiteNameException, ArgsException, SQLException, ESException, IllegalArgumentException, IllegalAccessException, IncorrectFieldTypeException;

	/**
	 * Renvoie ce que fait la commande, comment l'utiliser et ses alias.
	 * @return String contenant la description.
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Défini les arguments pour la commande.
	 * @param args
	 */
	public void setArgs(String args) {
		this.args = args;
	}

	/**
	 * Renvoie les arguments
	 * @return String qui contient les arguments de la commande.
	 */
	public String getArgs() {
		return args;
	}
}
