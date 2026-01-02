package fr.spa.gestion.tpjdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// final : je ne peux pas surcharger la classe
public final class Connexion {
	
	public static Connection getConnexion() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String url = "jdbc:postgresql://pedago.univ-avignon.fr:5432/etd";
		try {
			Connection db = DriverManager.getConnection(url, "uapv2503875", "I3QEg9DYLxgG");
			System.out.println("Connexion réussie");
			return db;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
}