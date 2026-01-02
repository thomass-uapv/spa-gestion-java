package fr.spa.gestion.entite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;

import fr.spa.gestion.data.Entite;
import fr.spa.gestion.data.fieldType;

public class Activite extends Entite {

    private static int lastId = 1;

    private String description;
    private Integer nombreMin;

    public Activite() {
        this(null, null);
    }

    public Activite(String description, Integer nombreMin) {
        super(new HashMap<String, fieldType>(), Activite.getLastId());
        Activite.setLastId(Activite.getLastId() + 1);

        this.description = description;
        this.nombreMin = nombreMin;
    }

    @Override
    public void getStruct() {
        this.map.put("description", fieldType.TEXT);
        this.map.put("nombre_min", fieldType.INT);

        this.values = "("
                + this.id + ", "
                + "'" + this.description + "', "
                + this.nombreMin
                + ")";
    }

    @Override
    public PreparedStatement getUpdateCmd(Connection db, String table) throws SQLException {
        String query = "UPDATE " + table + " SET "
                + "description=?, nombre_min=? "
                + "WHERE id_activite=?";

        PreparedStatement st_u = db.prepareStatement(query);

        st_u.setObject(1, this.description, Types.VARCHAR);
        st_u.setObject(2, this.nombreMin, Types.INTEGER);

        st_u.setInt(3, this.id);

        return st_u;
    }

    public static int getLastId() {
        return lastId;
    }

    public static void setLastId(int lastId) {
        Activite.lastId = lastId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNombreMin() {
        return nombreMin;
    }

    public void setNombreMin(int nombreMin) {
        this.nombreMin = nombreMin;
    }
}