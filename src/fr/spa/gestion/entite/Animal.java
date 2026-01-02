package fr.spa.gestion.entite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.HashMap;

import fr.spa.gestion.data.Entite;
import fr.spa.gestion.data.fieldType;

public class Animal extends Entite {

	private static int lastId = 1;

	private String nom;
	private LocalDate dateArriveCentre;
	private String type;
	private String race;
	private Integer numPuce;
	private Famille famille;
	private LocalDate annNaissance;
	private Boolean adoptable;
	private Boolean okHumain;
	private Boolean okChien;
	private Boolean okChat;
	private Boolean okBebe;
	private LocalDate dateDeces;
	private String alimentationAdapte;
	private String soinAdapte;
	private String commentaire;

	public Animal() {
		this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
	}

	public Animal(String nom, LocalDate dateArriveCentre, String type, String race, Integer numPuce, 
			Famille famille, LocalDate annNaissance, Boolean adoptable, Boolean okHumain, 
			Boolean okChien, Boolean okChat, Boolean okBebe, LocalDate dateDeces, 
			String alimentationAdapte, String soinAdapte, String commentaire) {

		super(new HashMap<String, fieldType>(), Animal.getLastId());
		Animal.setLastId(Animal.getLastId() + 1);

		this.nom = nom;
		this.dateArriveCentre = dateArriveCentre;
		this.type = type;
		this.race = race;
		this.numPuce = numPuce;
		this.famille = famille;
		this.annNaissance = annNaissance;
		this.adoptable = adoptable;
		this.okHumain = okHumain;
		this.okChien = okChien;
		this.okChat = okChat;
		this.okBebe = okBebe;
		this.dateDeces = dateDeces;
		this.alimentationAdapte = alimentationAdapte;
		this.soinAdapte = soinAdapte;
		this.commentaire = commentaire;		
	}

	@Override
	public void getStruct() {
		this.map.put("nom", fieldType.VARCHAR);
		this.map.put("date_arrive_centre", fieldType.DATE);
		this.map.put("type", fieldType.VARCHAR);
		this.map.put("race", fieldType.VARCHAR);
		this.map.put("num_puce", fieldType.INT);
		this.map.put("id_famille", fieldType.INT); // Clé étrangère
		this.map.put("ann_naissance", fieldType.DATE);
		this.map.put("adoptable", fieldType.BOOL);
		this.map.put("ok_humain", fieldType.BOOL);
		this.map.put("ok_chien", fieldType.BOOL);
		this.map.put("ok_chat", fieldType.BOOL);
		this.map.put("ok_bebe", fieldType.BOOL);
		this.map.put("date_deces", fieldType.DATE);
		this.map.put("alimentation_adapte", fieldType.TEXT);
		this.map.put("soin_adapte", fieldType.TEXT);
		this.map.put("commentaire", fieldType.TEXT);

		String idFamilleVal;
		if (this.famille == null) {
			idFamilleVal = "null";
		} else {
			idFamilleVal = String.valueOf(famille.getId());
		}

		String dateDecesVal;
		if (this.dateDeces == null) {
			dateDecesVal = "null";
		} else {
			dateDecesVal = this.dateDeces.toString();
		}

		this.values = "("
				+ this.id + ", "
				+ "'" + this.nom + "', "
				+ "'" + this.dateArriveCentre + "', " // LocalDate.toString() renvoie 'YYYY-MM-DD'
				+ "'" + this.type + "', "
				+ "'" + this.race + "', "
				+ this.numPuce + ", "
				+ idFamilleVal + ","
				+ "'" + this.annNaissance + "', "
				+ this.adoptable + ", "
				+ this.okHumain + ", "
				+ this.okChien + ", "
				+ this.okChat + ", "
				+ this.okBebe + ", "
				// Gestion des dates nullables (si dateDeces est null, on met NULL sans quotes)
				+ "'" + dateDecesVal + "', "
				+ "'" + this.alimentationAdapte + "', " 
				+ "'" + this.soinAdapte + "', " 
				+ "'" + this.commentaire + "'"
				+ ")";
	}

	@Override
	public PreparedStatement getUpdateCmd(Connection db, String table) throws SQLException {
		String query = "UPDATE " + table + " SET "
				+ "nom=?, date_arrive_centre=?, type=?, race=?, num_puce=?, "
				+ "id_famille=?, ann_naissance=?, adoptable=?, "
				+ "ok_humain=?, ok_chien=?, ok_chat=?, ok_bebe=?, "
				+ "date_deces=?, alimentation_adapte=?, soin_adapte=?, commentaire=? "
				+ "WHERE id_animal=?;";

		PreparedStatement st_u = db.prepareStatement(query);

		Integer idFamilleVal;
		if (this.famille != null) {
			idFamilleVal = this.famille.getId();
		} else {
			idFamilleVal = null;
		}

		st_u.setObject(1,  this.nom, Types.VARCHAR);
		st_u.setObject(2,  this.dateArriveCentre, Types.DATE);
		st_u.setObject(3,  this.type, Types.VARCHAR);
		st_u.setObject(4,  this.race, Types.VARCHAR);
		st_u.setObject(5,  this.numPuce, Types.INTEGER);
		st_u.setObject(6,  idFamilleVal, Types.INTEGER);
		st_u.setObject(7,  this.annNaissance, Types.DATE);
		st_u.setObject(8,  this.adoptable, Types.BOOLEAN);
		st_u.setObject(9,  this.okHumain, Types.BOOLEAN);
		st_u.setObject(10, this.okChien, Types.BOOLEAN);
		st_u.setObject(11, this.okChat, Types.BOOLEAN);
		st_u.setObject(12, this.okBebe, Types.BOOLEAN);
		st_u.setObject(13, this.dateDeces, Types.DATE);
		st_u.setObject(14, this.alimentationAdapte, Types.VARCHAR);
		st_u.setObject(15, this.soinAdapte, Types.VARCHAR);
		st_u.setObject(16, this.commentaire, Types.VARCHAR);

		st_u.setInt(17, this.id); 

		return st_u;
	}

	public static int getLastId() {
		return lastId;
	}

	public static void setLastId(int lastId) {
		Animal.lastId = lastId;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public LocalDate getDateArriveCentre() {
		return dateArriveCentre;
	}

	public void setDateArriveCentre(LocalDate dateArriveCentre) {
		this.dateArriveCentre = dateArriveCentre;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public int getNumPuce() {
		return numPuce;
	}

	public void setNumPuce(int numPuce) {
		this.numPuce = numPuce;
	}

	public Famille getFamille() {
		return famille;
	}

	public void setFamille(Famille famille) {
		this.famille = famille;
	}

	public LocalDate getAnnNaissance() {
		return annNaissance;
	}

	public void setAnnNaissance(LocalDate annNaissance) {
		this.annNaissance = annNaissance;
	}

	public boolean isAdoptable() {
		return adoptable;
	}

	public void setAdoptable(boolean adoptable) {
		this.adoptable = adoptable;
	}

	public boolean isOkHumain() {
		return okHumain;
	}

	public void setOkHumain(boolean okHumain) {
		this.okHumain = okHumain;
	}

	public boolean isOkChien() {
		return okChien;
	}

	public void setOkChien(boolean okChien) {
		this.okChien = okChien;
	}

	public boolean isOkChat() {
		return okChat;
	}

	public void setOkChat(boolean okChat) {
		this.okChat = okChat;
	}

	public boolean isOkBebe() {
		return okBebe;
	}

	public void setOkBebe(boolean okBebe) {
		this.okBebe = okBebe;
	}

	public LocalDate getDateDeces() {
		return dateDeces;
	}

	public void setDateDeces(LocalDate dateDeces) {
		this.dateDeces = dateDeces;
	}

	public String getAlimentationAdapte() {
		return alimentationAdapte;
	}

	public void setAlimentationAdapte(String alimentationAdapte) {
		this.alimentationAdapte = alimentationAdapte;
	}

	public String getSoinAdapte() {
		return soinAdapte;
	}

	public void setSoinAdapte(String soinAdapte) {
		this.soinAdapte = soinAdapte;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}



}
