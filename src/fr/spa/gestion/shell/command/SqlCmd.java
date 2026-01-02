package fr.spa.gestion.shell.command;

import java.sql.SQLException;

import fr.spa.gestion.data.Command;
import fr.spa.gestion.shell.Shell;

public class SqlCmd extends Command{

	public SqlCmd(Shell s) {
		super(s, null, "sql <SQL_Command> - Permet d'exécuter des requêtes SQL sur la base.");
	}

	@Override
	public String getResult() throws SQLException {
		String res = this.s.getDb().execute(args.trim());
		return res;
	}

}
