package fr.spa.gestion.shell.command;

import fr.spa.gestion.exception.*;
import fr.spa.gestion.shell.Shell;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import fr.spa.gestion.data.Command;
import fr.spa.gestion.data.IData;
import fr.spa.gestion.entite.Animal;
import fr.spa.gestion.entite.Box;
import fr.spa.gestion.entite.Famille;
import fr.spa.gestion.entite.Personne;

public class Add extends Command{

	public Add(Shell s) {
		super(s, null, "add <animal/personne/box/famille/sejour/emplacement/creneau/activite/soin> - Ajouter l'entité indiqué en argument à la base.");
	}

	@Override
	public String getResult() throws ArgsException, SQLException, ESException, IllegalArgumentException, IllegalAccessException, IncorrectFieldTypeException {
		if (args == null || args.isBlank()) {
			throw new ArgsException("Veuillez donner un argument");
		}
		String entite = args.split(" ")[0];
		String res = "";
		if (entite.equalsIgnoreCase("animal")){
			Animal animal = new Animal();
			this.remplirObjetAutomatiquement(animal);
			this.s.getDb().insert(animal, "animal");
		} else if (entite.equalsIgnoreCase("personne")) {
			Personne prs = new Personne();
			this.remplirObjetAutomatiquement(prs);
			this.s.getDb().insert(prs, "personne");
		} else if (entite.equalsIgnoreCase("box")) {
			Box box = new Box();
			this.remplirObjetAutomatiquement(box);
			this.s.getDb().insert(box, "box");
		} else if (entite.equalsIgnoreCase("famille")) {
			Famille famille = new Famille();
			this.remplirObjetAutomatiquement(famille);
			this.s.getDb().insert(famille, "famille");
		}
		return res;
	}

	/**
	 * Cette méthode parcourt tous les champs de l'objet et demande à l'utilisateur de les remplir.
	 * Elle gère les types String, int, boolean et LocalDate.
	 * @throws ESException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	private void remplirObjetAutomatiquement(IData data) throws ESException, IllegalArgumentException, IllegalAccessException {
		Class<?> classe = data.getClass(); //Class<?> => Une Classe de n'importe quel type ('<?>')
		Field[] attributs = classe.getDeclaredFields(); // Récupère tous les attributs

		System.out.println("--- Ajout de " + classe.getSimpleName() + " ---");

		for (Field atr : attributs) {
			// On rend l'attribut accessible même s'il est private
			atr.setAccessible(true);

			if (atr.getName().equals("id") || atr.getType().getName().contains("Map")) {
				continue;
			}

			boolean valeurValide = false;
			while (!valeurValide) {
				try {
					System.out.print("Entrez la valeur pour '" + atr.getName() + "' (" + atr.getType().getSimpleName() + ") : ");
					String input = this.s.input();

					// Conversion dynamique selon le type du champ
					if (atr.getType().equals(String.class)) {
						atr.set(data, input);
					} else if (atr.getType().equals(int.class) || atr.getType().equals(Integer.class)) {
						atr.set(data, Integer.parseInt(input));
					} else if (atr.getType().equals(boolean.class) || atr.getType().equals(Boolean.class)) {
						// On utilise du Regex pour déterminer si c'est true ou false. Si ça correspond alors ce sera true, sinon false.
						// (?i) est une option pour dire insensible à la casse.
						atr.set(data, input.trim().matches("(?i)true|vrai|oui|1"));
					} else if (atr.getType().equals(LocalDate.class)) {
						// Format attendu YYYY-MM-DD
						if (!input.isBlank()) {
							atr.set(data, LocalDate.parse(input));
						} else {
							atr.set(data, null); // Date vide autorisée
						}
					}
					valeurValide = true;
				} catch (NumberFormatException e) {
					System.out.println(">> Format de nombre invalide, veuillez réessayer.");
				} catch (DateTimeParseException e) {
					System.out.println(">> Format de date invalide, veuillez réessayer en respectant ce format : (YYYY-MM-DD)");
				}
			}
		}
	}

}
