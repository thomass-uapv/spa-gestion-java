package fr.spa.gestion.entite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.HashMap;

import fr.spa.gestion.data.Entite;
import fr.spa.gestion.data.fieldType;

public class Sejour extends Entite {
	
	private static int lastId = 1;
	
	private Integer idAnimal;
	private Integer idFamille;
	private String typeFamille;
	private LocalDate dateDebut;
	private LocalDate dateFin;
	
	public Sejour() {
		this(null, null, null, null, null);
	}
	
	public Sejour(Integer idAnimal, Integer idFamille, String typeFamille, LocalDate dateDebut, LocalDate dateFin) {
		super(new HashMap<String, fieldType>(), Sejour.getLastId());
		Sejour.setLastId(Sejour.getLastId() + 1);
		
		this.idAnimal = idAnimal;
		this.idFamille = idFamille;
		this.typeFamille = typeFamille;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
	}

	@Override
	public void getStruct() {
		this.map.put("id_animal", fieldType.INT);
		this.map.put("id_famille", fieldType.INT);
		this.map.put("type_famille", fieldType.VARCHAR);
		this.map.put("date_debut", fieldType.DATE);
		this.map.put("date_fin", fieldType.DATE);
		
		this.values = "("
				+ this.id + ", "
				+ this.idAnimal + ", "
				+ this.idFamille + ", "
				+ "'" + this.typeFamille + "', "
				+ "'" + this.dateDebut + "', "
				+ "'" + this.dateFin + "'"
				+ ")";
	}

	@Override
	public PreparedStatement getUpdateCmd(Connection db, String table) throws SQLException {
		String query = "UPDATE " + table + " SET "
				+ "id_animal=?, id_famille=?, type_famille=?, date_debut=?, date_fin=?"
				+ "WHERE id_sejour=?";

		PreparedStatement st_u = db.prepareStatement(query);

		st_u.setObject(1,  this.idAnimal, Types.INTEGER);
		st_u.setObject(2,  this.idFamille, Types.INTEGER);
		st_u.setObject(3,  this.typeFamille, Types.VARCHAR);
		st_u.setObject(4,  this.dateDebut, Types.DATE);
		st_u.setObject(5,  this.dateFin, Types.DATE);

		st_u.setInt(6, this.id);

		return st_u;
	}

	public static int getLastId() {
		return lastId;
	}

	public static void setLastId(int lastId) {
		Sejour.lastId = lastId;
	}

	public int getIdAnimal() {
		return idAnimal;
	}

	public void setIdAnimal(int idAnimal) {
		this.idAnimal = idAnimal;
	}

	public int getIdFamille() {
		return idFamille;
	}

	public void setIdFamille(int idFamille) {
		this.idFamille = idFamille;
	}

	public String getTypeFamille() {
		return typeFamille;
	}

	public void setTypeFamille(String typeFamille) {
		this.typeFamille = typeFamille;
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
