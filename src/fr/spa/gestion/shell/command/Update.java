package fr.spa.gestion.shell.command;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import fr.spa.gestion.data.Command;
import fr.spa.gestion.exception.ArgsException;
import fr.spa.gestion.shell.Shell;
import fr.spa.gestion.tpjdbc.Gestion;

public class Update extends Command{

	public Update(Shell s) {
		super(s, null, "update <entite> <id> <nom_attribut>=<valeur_attribut> [nom_attribut_2]=[valeur_attribut_2] ... - Met à jour les attributs donnés en argument de l'entité correspondant à l'id donné.");
	}

	@Override
	public String getResult() throws SQLException, ArgsException {
		if (args == null || args.isBlank()) {
			throw new ArgsException("Veuillez donner un argument");
		}
		ArrayList<String> splited = new ArrayList<String>(Arrays.asList(args.split(" ")));
		String entite = Gestion.validTable(splited.get(0));
		String id = args.split(" ")[1];
		String query = "UPDATE " + entite + " SET ";

		for (int i = 1; i < splited.size(); i++) {
			query += splited.get(i);
			if (i < splited.size()-1) {
				query += ", ";
			}
		}

		query += "WHERE id_" + entite + "=" + id + ";";
		
		String res = this.s.getDb().execute(query);
		
		return res;
	}

}
