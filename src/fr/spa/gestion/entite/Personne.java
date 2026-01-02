package fr.spa.gestion.entite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;

import fr.spa.gestion.data.Entite;
import fr.spa.gestion.data.fieldType;

public class Personne extends Entite {

    private static int lastId = 1;

    private String nom;
    private String telephone;
    private String login;
    private String passwordHash;
    private String type;

    public Personne() {
    	this(null, null, null, null, null);
    }

    public Personne(String nom, String telephone, String login, String passwordHash, String type) {
        super(new HashMap<String, fieldType>(), Personne.getLastId());
        Personne.setLastId(Personne.getLastId() + 1);

        this.nom = nom;
        this.telephone = telephone;
        this.login = login;
        this.passwordHash = passwordHash;
        this.type = type;
    }

    @Override
    public void getStruct() {
        this.map.put("nom", fieldType.VARCHAR);
        this.map.put("telephone", fieldType.VARCHAR);
        this.map.put("login", fieldType.VARCHAR);
        this.map.put("password_hash", fieldType.TEXT);
        this.map.put("type", fieldType.VARCHAR);

        this.values = "("
                + this.id + ", "
                + "'" + this.nom + "', "
                + "'" + this.telephone + "', "
                + "'" + this.login + "', "
                + "'" + this.passwordHash + "', "
                + "'" + this.type + "'"
                + ")";
    }

    @Override
    public PreparedStatement getUpdateCmd(Connection db, String table) throws SQLException {
        String query = "UPDATE " + table + " SET "
                + "nom=?, telephone=?, login=?, password_hash=?, type=? "
                + "WHERE id_personne=?";

        PreparedStatement st_u = db.prepareStatement(query);

        st_u.setObject(1, this.nom, Types.VARCHAR);
        st_u.setObject(2, this.telephone, Types.VARCHAR);
        st_u.setObject(3, this.login, Types.VARCHAR);
        st_u.setObject(4, this.passwordHash, Types.VARCHAR);
        st_u.setObject(5, this.type, Types.VARCHAR);

        st_u.setInt(6, this.id);

        return st_u;
    }


    public static int getLastId() {
        return lastId;
    }

    public static void setLastId(int lastId) {
        Personne.lastId = lastId;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}