package fr.spa.gestion.tpjdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import fr.spa.gestion.data.Entite;
import fr.spa.gestion.data.IData;
import fr.spa.gestion.data.fieldType;
import fr.spa.gestion.exception.IncorrectFieldTypeException;

public class Gestion {
	private Connection db;

	public Gestion() {
		super();
		this.db = Connexion.getConnexion();
	}
	
	public static String validTable(String inputTable) throws IllegalArgumentException {
	    ArrayList<String> tablesAutorisees = new ArrayList<String>(Arrays.asList("ANIMAL", "FAMILLE", "BOX", "SEJOUR", "EMPLACEMENT", "CRENEAU", "ACTIVITE", "PERSONNE", "SOIN", "RESERVATION_ACTIVITE", "ANIMAL_SOIN"));
	    
	    if (tablesAutorisees.contains(inputTable)) {
	        return inputTable;
	    } else {
	    	// TODO faire sa propre exception
	        throw new IllegalArgumentException("Table inconnue.");
	    }
	}

	// Retourne la structure d’une table sous la forme <nom de la colonne, type>
	public HashMap<String, fieldType> structTable(String validTable, boolean display) throws SQLException, IncorrectFieldTypeException{
		HashMap<String, fieldType> m = new HashMap<String, fieldType>();

		PreparedStatement st = db.prepareStatement("SELECT * FROM " + validTable);
		ResultSetMetaData meta = st.getMetaData();

		// ResultSetMetaData commence à l'indice 1 et se termine à l'indice n.
		for (int i = 1; i <= meta.getColumnCount(); i++) {
			fieldType ftype = convertType(meta.getColumnTypeName(i));
			if (ftype == null) {
				throw new IncorrectFieldTypeException("Le type d'une des colonnes est incorrecte.");
			}
			m.put(meta.getColumnName(i), ftype);
		}

		if(display) {
			System.out.println(m);
		}

		st.close();		
		return m;

	}

	// Affiche le contenu de n'importe quelle table à l’aide de la méthode
	// getMetaData de la classe PreparedStatement.
	public void displayTable(String table) throws SQLException{
		String validTable = validTable(table);
		PreparedStatement st = db.prepareStatement("SELECT * FROM " + validTable);
		ResultSet res = st.executeQuery();
		ResultSetMetaData meta = res.getMetaData();

		int nbCol = meta.getColumnCount();

		for (int i = 1; i <= nbCol; i++) {
			System.out.print(meta.getColumnName(i) + " | ");
		}
		System.out.println();

		while(res.next()) {
			for (int i = 1; i <= nbCol; i++) {
				System.out.print(res.getString(i) + " | ");
			}
			System.out.println();
		}

		res.close();
		st.close();
	}

	// Execute une requête hors insert.
	public String execute(String query) throws SQLException {
		Statement st = db.createStatement();
		boolean isSelectCmd = st.execute(query);
		String resPrint = "";
		if (isSelectCmd) {			
			ResultSet res = st.getResultSet();
			
			while(res.next()) {
				for (int i = 1; i <= res.getMetaData().getColumnCount(); i++) {
					resPrint += res.getString(i) + " | ";
				}
				resPrint += "\n";
			}
			
			res.close();
		} else {
			int updateCount = st.getUpdateCount();
			resPrint += "Requête exécutée avec succès.\n";
			resPrint += "Lignes modifiées : " + updateCount;
		}
		st.close();
		return resPrint;
	}

	/*
	 * Cette méthode permet l’insertion des valeurs des attributs d’une instance d’une classe
	 * (data par exemple) dans une table. Vous récupérerez dans un premier temps la structure de la table
	 * (structTable) puis celle de l’instance (data.getStruct()). Ensuite, vous vérifierez que l’instance
	 * et la table partage une même structure via la méthode data.check(). Enfin, vous procéderez à
	 * l’insertion à proprement dit dans la table.Si un élément est déjà contenu dans la table (id identique)
	 * vous mettrez à jour l’entrée de la table avec les nouveaux éléments et réaliserez la somme des prix
	 * (ancienne enregistrement + le prix de l’entité dupliquée) et concaténerez les deux descriptions avec
	 * un espace entre les deux chaînes de caractères (opérateur ||).
	 * */

	public void insert(IData data, String table) throws SQLException, IncorrectFieldTypeException {
		String validTable = validTable(table);
		HashMap<String, fieldType> struct = structTable(validTable, false);
		data.getStruct();

		if (data.check(struct)) {
			Entite entite = (Entite) data;
			String queryCheck = "SELECT COUNT(*) FROM " + validTable + " WHERE id_" + table.toLowerCase() + " = " + entite.getId() + ";";
			PreparedStatement st = db.prepareStatement(queryCheck);
			ResultSet res = st.executeQuery();
			if (res.next() && res.getInt(1) == 1) {
				// UPDATE si existe déjà
				PreparedStatement st_u = entite.getUpdateCmd(db, validTable);
				st_u.executeUpdate();
				st_u.close();
			} else {
				// Insertion
				PreparedStatement st_meta = db.prepareStatement("SELECT * FROM " + validTable + " WHERE 1=0"); // n'avoir que les noms des attributs
				ResultSetMetaData meta = st_meta.getMetaData();

				String attributs = "(";
				for (int i = 1; i <= meta.getColumnCount(); i++) {
					attributs += meta.getColumnName(i);
					if (i < meta.getColumnCount()) {
						attributs += ", ";
					}
				}
				attributs += ")";
				st_meta.close();

//				attributs += struct.keySet().toArray()[struct.size()-1];
				String query = "INSERT INTO " + validTable + attributs + " VALUES " + data.getValues() + ";";
				PreparedStatement st_i = db.prepareStatement(query);
				
				st_i.executeQuery();
				st_i.close();
			}
			st.close();
			res.close();
		}
		System.err.println("Erreur : structure incompatible"); //TODO - Exception
	}
	
	private fieldType convertType(String s) {
		if (s.equalsIgnoreCase("VARCHAR")) {
			return fieldType.VARCHAR;
		} else if (s.equalsIgnoreCase("INTEGER") || s.equalsIgnoreCase("INT4") || s.equalsIgnoreCase("INT")) {
			return fieldType.INT;
		} else if (s.equalsIgnoreCase("DATE")) {
			return fieldType.DATE;
		} else if (s.equalsIgnoreCase("TIMESTAMP")) {
			return fieldType.TIMESTAMP;
		}else if (s.equalsIgnoreCase("TEXT")) {
			return fieldType.VARCHAR;
		} else if (s.equalsIgnoreCase("BOOL")) {
			return fieldType.BOOL;
		}
		return null;
	}
	
//	public static void main(String[] args) throws SQLException {
//		Gestion g = new Gestion();
//		
//		g.execute("SELECT * FROM theatre;");
//		g.displayTable("theatre");
//		
//		g.structTable("theatre", false);
//		g.structTable("theatre", true);
//	}
}
