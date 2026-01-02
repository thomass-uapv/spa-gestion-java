package fr.spa.gestion.exception;

/**
 * Classe correspondant à l'Exception ESException héritant de SpaException.<br>
 * Sers à personnaliser le message d'erreur lorsqu'une IOException survient.
 */
public class ESException extends SpaException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8715592051330340742L;

	/**
	 * Constructeur de la classe ESException.
	 * @param msg
	 */
	public ESException(String msg) {
		super("ESException - " + msg);
	}
}
