package fr.spa.gestion.shell.command;

import fr.spa.gestion.data.Command;

public class Echo extends Command{

	public Echo() {
		super(null, null, "echo <Message> - Afficher un message");
	}

	@Override
	public String getResult(){
		return args;
	}

}
