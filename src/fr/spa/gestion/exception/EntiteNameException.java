package fr.spa.gestion.exception;

public class EntiteNameException extends SpaException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2662833892690053162L;

	public EntiteNameException(String msg) {
		super("EntiteNameException - " + msg);
	}

}
