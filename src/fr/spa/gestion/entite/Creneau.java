package fr.spa.gestion.entite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.HashMap;

import fr.spa.gestion.data.Entite;
import fr.spa.gestion.data.fieldType;

public class Creneau extends Entite {

    private static int lastId = 1;

    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;

    public Creneau() {
        this(null, null);
    }

    public Creneau(LocalDateTime dateDebut, LocalDateTime dateFin) {
        super(new HashMap<String, fieldType>(), Creneau.getLastId());
        Creneau.setLastId(Creneau.getLastId() + 1);

        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    @Override
    public void getStruct() {
        this.map.put("date_debut", fieldType.TIMESTAMP);
        this.map.put("date_fin", fieldType.TIMESTAMP);

        this.values = "("
                + this.id + ", "
                + "'" + this.dateDebut + "', "
                + "'" + this.dateFin + "'"
                + ")";
    }

    @Override
    public PreparedStatement getUpdateCmd(Connection db, String table) throws SQLException {
        String query = "UPDATE " + table + " SET "
                + "date_debut=?, date_fin=? "
                + "WHERE id_creneau=?";

        PreparedStatement st_u = db.prepareStatement(query);

        st_u.setObject(1, this.dateDebut, Types.TIMESTAMP);
        st_u.setObject(2, this.dateFin, Types.TIMESTAMP);

        st_u.setInt(3, this.id);

        return st_u;
    }

    public static int getLastId() {
        return lastId;
    }

    public static void setLastId(int lastId) {
        Creneau.lastId = lastId;
    }

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }
}