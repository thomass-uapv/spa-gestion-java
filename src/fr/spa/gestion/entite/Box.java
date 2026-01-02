package fr.spa.gestion.entite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;

import fr.spa.gestion.data.Entite;
import fr.spa.gestion.data.fieldType;

public class Box extends Entite {
	
	private static int lastId = 1;
	
	private String type;
	private Integer capaciteMax;
	
	public Box() {
		this(null, null);
	}
	
	public Box(String type, Integer capaciteMax) {
		super(new HashMap<String, fieldType>(), Box.getLastId());
		Box.setLastId(Box.getLastId() + 1);
		
		this.type = type;
		this.capaciteMax = capaciteMax;
	}

	@Override
	public void getStruct() {
		this.map.put("type", fieldType.VARCHAR);
		this.map.put("capacite_max", fieldType.INT);
		
		this.values = "("
				+ this.id + ", "
				+ "'" + this.type + "', "
				+ this.capaciteMax
				+ ")";
	}

	@Override
	public PreparedStatement getUpdateCmd(Connection db, String table) throws SQLException {
		String query = "UPDATE " + table + " SET "
				+ "type=?, capacite_max=?"
				+ "WHERE id_box=?";

		PreparedStatement st_u = db.prepareStatement(query);

		st_u.setObject(1,  this.type, Types.VARCHAR);
		st_u.setObject(2,  this.capaciteMax, Types.INTEGER);

		st_u.setInt(3, this.id);

		return st_u;
	}

	public static int getLastId() {
		return lastId;
	}

	public static void setLastId(int lastId) {
		Box.lastId = lastId;
	}

	public int getCapaciteMax() {
		return capaciteMax;
	}

	public void setCapaciteMax(int capaciteMax) {
		this.capaciteMax = capaciteMax;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
