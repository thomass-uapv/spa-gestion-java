package fr.spa.gestion.exception;

public class SpaException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2042862843123602514L;

	public SpaException(String msg) {
		super("SpaException - " + msg);
	}

	
}
