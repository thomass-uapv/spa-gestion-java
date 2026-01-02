package fr.spa.gestion.entite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.HashMap;

import fr.spa.gestion.data.Entite;
import fr.spa.gestion.data.fieldType;

public class Soin extends Entite {

    private static int lastId = 1;

    private String description;
    private LocalDate dateSoin;

    public Soin() {
    	this(null, null);
    }

    public Soin(String description, LocalDate dateSoin) {
        super(new HashMap<String, fieldType>(), Soin.getLastId());
        Soin.setLastId(Soin.getLastId() + 1);

        this.description = description;
        this.dateSoin = dateSoin;
    }

    @Override
    public void getStruct() {
        this.map.put("description", fieldType.TEXT);
        this.map.put("date_soin", fieldType.DATE);

        this.values = "("
                + this.id + ", "
                + "'" + this.description + "', "
                + "'" + this.dateSoin + "'"
                + ")";
    }

    @Override
    public PreparedStatement getUpdateCmd(Connection db, String table) throws SQLException {
        String query = "UPDATE " + table + " SET "
                + "description=?, date_soin=? "
                + "WHERE id_soin=?";

        PreparedStatement st_u = db.prepareStatement(query);

        st_u.setObject(1, this.description, Types.VARCHAR);
        st_u.setObject(2, this.dateSoin, Types.DATE);

        st_u.setInt(3, this.id);

        return st_u;
    }

    public static int getLastId() {
        return lastId;
    }

    public static void setLastId(int lastId) {
        Soin.lastId = lastId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateSoin() {
        return dateSoin;
    }

    public void setDateSoin(LocalDate dateSoin) {
        this.dateSoin = dateSoin;
    }
}