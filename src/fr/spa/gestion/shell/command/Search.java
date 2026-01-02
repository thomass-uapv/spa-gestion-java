package fr.spa.gestion.shell.command;

import java.sql.SQLException;

import fr.spa.gestion.data.Command;
import fr.spa.gestion.exception.ArgsException;
import fr.spa.gestion.shell.Shell;

public class Search extends Command {

	public Search(Shell s) {
		super(s, null, "search <entite> <nom_attribut>=<valeur_attribut> - Renvoie la liste des enregistrements de l'entité conformément à la condition donnée (Alias : info)");
	}

	@Override
	public String getResult() throws SQLException, ArgsException {
		if (args == null || args.isBlank()) {
			throw new ArgsException("Veuillez donner un argument");
		}
		String[] splited = args.split(" ");
		String res = this.s.getDb().execute("SELECT * FROM " + splited[0] + "WHERE " + splited[1] + ";");
		return res;
	}

}
