package fr.spa.gestion.shell.command;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import fr.spa.gestion.data.Command;
import fr.spa.gestion.exception.ArgsException;
import fr.spa.gestion.exception.EntiteNameException;
import fr.spa.gestion.shell.Shell;
import fr.spa.gestion.tpjdbc.Gestion;

public class Delete extends Command{

	public Delete(Shell s) {
		super(s, null, "delete <animal/personne/box/famille/sejour/emplacement/creneau/activite/soin> <id> - Supprime l'entité avec l'id correspondant.");
	}

	@Override
	public String getResult() throws ArgsException, EntiteNameException, SQLException {
		if (args == null || args.isBlank()) {
			throw new ArgsException("Veuillez donner un argument");
		}
		String entite = Gestion.validTable(args.split(" ")[0]);
		int id = Integer.parseInt(args.split(" ")[1]);
		String res = this.s.getDb().execute("DELETE FROM " + entite.toUpperCase() + " WHERE id_" + entite.toLowerCase() + "=" + id + ";");

		return res;
	}

}
