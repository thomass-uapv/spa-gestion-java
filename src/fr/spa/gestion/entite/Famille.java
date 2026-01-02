package fr.spa.gestion.entite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;

import fr.spa.gestion.data.Entite;
import fr.spa.gestion.data.fieldType;

public class Famille extends Entite  {

	private static int lastId = 1;

	private String nom;
	private String telephone;
	private String adr;

	public Famille() {
		this(null, null, null);
	}

	public Famille(String nom, String tel, String adr) {
		super(new HashMap<String, fieldType>(), Famille.getLastId());
		Famille.setLastId(Famille.getLastId() + 1);

		this.nom = nom;
		this.telephone = tel;
		this.adr = adr;
	}

	@Override
	public void getStruct() {
		this.map.put("nom", fieldType.VARCHAR);
		this.map.put("telephone", fieldType.VARCHAR);
		this.map.put("adr", fieldType.VARCHAR);

		this.values = "("
				+ this.id + ", "
				+ "'" + this.nom + "', "
				+ "'" + this.telephone + "', "
				+ "'" + this.adr + "'"
				+ ")";

	}

	@Override
	public PreparedStatement getUpdateCmd(Connection db, String table) throws SQLException {
		String query = "UPDATE " + table + " SET "
				+ "nom=?, telephone=?, adr=?"
				+ "WHERE id_famille=?";

		PreparedStatement st_u = db.prepareStatement(query);

		st_u.setObject(1,  this.nom, Types.VARCHAR);
		st_u.setObject(2,  this.telephone, Types.VARCHAR);
		st_u.setObject(3,  this.adr, Types.VARCHAR);

		st_u.setInt(4, this.id); 

		return st_u;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAdr() {
		return adr;
	}

	public void setAdr(String adr) {
		this.adr = adr;
	}

	public static int getLastId() {
		return lastId;
	}

	public static void setLastId(int lastId) {
		Famille.lastId = lastId;
	}



}
