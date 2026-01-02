package fr.spa.gestion.exception;

/**
 * Class correspondant à l'Exception ArgsException héritant de CommandException.<br>
 * Survient lorsqu'il y a un problème avec les arguments données dans les commandes.
 */
public class ArgsException extends SpaException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4171855726855179392L;

	/**
	 * Constructeur de la classe ArgsException.
	 * @param msg
	 */
	public ArgsException(String msg) {
		super("ArgsNullException - " + msg);
	}

}
