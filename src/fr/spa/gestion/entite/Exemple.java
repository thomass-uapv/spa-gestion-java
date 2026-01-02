package fr.spa.gestion.entite;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import fr.spa.gestion.data.Entite;
import fr.spa.gestion.data.fieldType;

public class Exemple extends Entite{
	
	private static int lastId = 1;
	
	private int numLotAchat;
	private String nom;
	private String description;
	private String categorie;
	private double prix;
		
	public Exemple(int numLotAchat, String nom, String description, String categorie, double prix) {
		super(new HashMap<String, fieldType>(), Exemple.getLastId());
		Exemple.setLastId(Exemple.getLastId() + 1);
		
		this.numLotAchat = numLotAchat;
		this.nom = nom;
		this.description = description;
		this.categorie = categorie;
		this.prix = prix;
		
		
		this.getStruct(); // A eviter
	}
	
	@Override
	public void getStruct() {
		this.map.put("numLotAchat", fieldType.INT);
		this.map.put("nom", fieldType.VARCHAR);
		this.map.put("description", fieldType.VARCHAR);
		this.map.put("categorie", fieldType.VARCHAR);
		this.map.put("prix", fieldType.DOUBLE);
		
		this.values = "(" + this.id + ", " + this.numLotAchat + ", '" + this.nom + "', '" + this.description + "', '" + this.categorie + "', " + this.prix + ")";
	}
	
	public int getNumLotAchat() {
		return numLotAchat;
	}

	public String getNom() {
		return nom;
	}

	public String getDescription() {
		return description;
	}

	public String getCategorie() {
		return categorie;
	}

	public double getPrix() {
		return prix;
	}

	public static int getLastId() {
		return lastId;
	}

	public static void setLastId(int lastId) {
		Exemple.lastId = lastId;
	}
	
	public PreparedStatement getUpdateCmd(Connection db, String table) throws SQLException {
		String query = "UPDATE ? SET prix = ?, description = ? WHERE idProduit = ?;";
		PreparedStatement st_u = db.prepareStatement(query);
		st_u.setString(1, table);
		st_u.setDouble(2, this.prix);
		st_u.setString(3, this.description);
		st_u.setInt(4, this.id);
		
		return st_u;
	}
	
	
	
//	public static void main(String[] args) {
//		// int idProduit, int numLotAchat, String nom, String description, String categorie, float prix
//		Produit p = new Produit(1, 12, "Compote", "Des compotes fraiches à la pomme", "Dessert", 12.42);
//		
//		HashMap<String, fieldType> m = new HashMap<String, fieldType>();
//		m.put("idProduit", fieldType.NUMERIC);
//		m.put("numLotAchat", fieldType.INT4);
//		m.put("nom", fieldType.VARCHAR);
//		m.put("description", fieldType.VARCHAR);
//		m.put("categorie", fieldType.VARCHAR);
//		m.put("prix", fieldType.FLOAT8);
//		
//		HashMap<String, fieldType> m2 = new HashMap<String, fieldType>();
//		m2.put("idProduit", fieldType.VARCHAR);
//		m2.put("numLotAchat", fieldType.INT4);
//		m2.put("anom", fieldType.VARCHAR);
//		m2.put("description", fieldType.VARCHAR);
//		m2.put("categorie", fieldType.VARCHAR);
//		m2.put("prix", fieldType.FLOAT8);
//		
//		System.out.println(p);
//		System.out.println(p.getMap());
//		System.out.println(p.check(m));
//		System.out.println(p.check(m2));
//	}
	
}
