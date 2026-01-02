package fr.spa.gestion.entite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.HashMap;

import fr.spa.gestion.data.Entite;
import fr.spa.gestion.data.fieldType;

public class Emplacement extends Entite{
	
	private static int lastId = 1;
	
	private Integer idAnimal;
	private Integer idBox;
	private LocalDate dateDebut;
	private LocalDate dateFin;
	
	public Emplacement() {
		this(null, null, null, null);
	}
	
	public Emplacement(Integer idAnimal, Integer idBox, LocalDate dateDebut, LocalDate dateFin) {
		super(new HashMap<String, fieldType>(), Emplacement.getLastId());
		Emplacement.setLastId(Emplacement.getLastId() + 1);
		
		this.idAnimal = idAnimal;
		this.idBox = idBox;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
	}

	@Override
	public void getStruct() {
		this.map.put("id_animal", fieldType.INT);
		this.map.put("id_box", fieldType.INT);
		this.map.put("date_debut", fieldType.DATE);
		this.map.put("date_fin", fieldType.DATE);

		this.values = "("
				+ this.id + ", "
				+ this.idAnimal + ", "
				+ this.idBox + ", "
				+ "'" + this.dateDebut + "', "
				+ "'" + this.dateFin + "'"
				+ ")";
		
	}

	@Override
	public PreparedStatement getUpdateCmd(Connection db, String table) throws SQLException {
		String query = "UPDATE " + table + " SET "
				+ "id_animal=?, id_box=?, date_debut=?, date_fin=?"
				+ "WHERE id_emplacement=?";

		PreparedStatement st_u = db.prepareStatement(query);

		st_u.setObject(1,  this.idAnimal, Types.INTEGER);
		st_u.setObject(2,  this.idBox, Types.INTEGER);
		st_u.setObject(3,  this.dateDebut, Types.DATE);
		st_u.setObject(3,  this.dateFin, Types.DATE);

		st_u.setInt(4, this.id); 

		return st_u;
	}

	public static int getLastId() {
		return lastId;
	}

	public static void setLastId(int lastId) {
		Emplacement.lastId = lastId;
	}

	public int getIdAnimal() {
		return idAnimal;
	}

	public void setIdAnimal(int idAnimal) {
		this.idAnimal = idAnimal;
	}

	public int getIdBox() {
		return idBox;
	}

	public void setIdBox(int idBox) {
		this.idBox = idBox;
	}

	public LocalDate getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(LocalDate dateDebut) {
		this.dateDebut = dateDebut;
	}

	public LocalDate getDateFin() {
		return dateFin;
	}

	public void setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
	}

}
