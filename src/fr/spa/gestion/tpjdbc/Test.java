package fr.spa.gestion.tpjdbc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import fr.spa.gestion.data.fieldType;
import fr.spa.gestion.entite.Exemple;

public class Test {
    public static void main(String[] args) throws SQLException {
        Connection db = null;
        Gestion gestion = null;

        try {
            db = Connexion.getConnexion();
            gestion = new Gestion();

            System.out.println("Commande disponibles :");
            System.out.println("  CREATE        -> créer la table produit");
            System.out.println("  INSERT        -> insérer un produit");
            System.out.println("  DISPLAY       -> afficher la table produit");
            System.out.println("  REMOVE <id>   -> supprimer le produit avec cet id");
            System.out.println("  STRUCT        -> afficher la structure de la table produit");
            System.out.println("  DROP          -> supprimer la table produit");
            System.out.println("  EXIT          -> quitter le programme");
            System.out.println();

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String ligne;
            while (true) {
                System.out.print(">> ");
                ligne = br.readLine();
                if (ligne == null) {
                    break;
                }
                ligne = ligne.trim();
                if (ligne.equalsIgnoreCase("EXIT") || ligne.equalsIgnoreCase("QUIT")) {
                    System.out.println("Fermeture du programme.");
                    break;
                }
                String[] tokens = ligne.split("\\s+");
                if (tokens.length == 0) {
                    continue;
                }
                String commande = tokens[0].toUpperCase();
                switch (commande) {
                    case "CREATE":
                        try{
                            String createQuery = "CREATE TABLE produit (id INT PRIMARY KEY, lot INT, nom VARCHAR(100), description VARCHAR(255), categorie VARCHAR(100), prix NUMERIC);";
                            gestion.execute(createQuery);
                            System.out.println("Table 'produit' créée.");
                        } catch (SQLException e) {
                            System.out.println("Erreur lors de la création e la table : " + e.getMessage());
                        }
                        break;
                    case "INSERT":
                        try {
                            System.out.print("Id (int) : ");
                            int id = Integer.parseInt(br.readLine().trim());
                            System.out.print("Lot (int) : ");
                            int lot = Integer.parseInt(br.readLine().trim());
                            System.out.print("Nom (String) : ");
                            String nom = br.readLine().trim();
                            System.out.print("Description (String) : ");
                            String description = br.readLine().trim();
                            System.out.print("Categorie (String) : ");
                            String categorie = br.readLine().trim();
                            System.out.print("Prix (double) : ");
                            double prix = Double.parseDouble(br.readLine().trim());
                            Exemple p = new Exemple(id, lot, nom, description, categorie, prix);
                            try {
                                gestion.insert(p, "produit");
                            } catch (SQLException e) {
                                System.out.println("Erreur lors de l'insertion : " + e.getMessage());
                            }
                        } catch (IOException | NumberFormatException e) {
                            System.out.println("Erreur de saisie : " + e.getMessage());
                        }
                        break;
                    case "DISPLAY":
                        try {
                            gestion.displayTable("produit");
                        } catch (SQLException e) {
                            System.out.println("Erreur lors de l'affichage : " + e.getMessage());
                        }
                        break;
                    case "REMOVE":
                        if (tokens.length < 2) {
                            System.out.println("Usage : REMOVE <id>");
                            break;
                        }
                        try {
                            int idASupprimer = Integer.parseInt(tokens[1]);
                            String deleteQuery = "DELETE FROM produit WHERE id = " + idASupprimer;
                            gestion.execute(deleteQuery);
                            System.out.println("Suppression effectué pour l'id " + idASupprimer);
                        } catch (NumberFormatException e) {
                            System.out.println("Id invalide.");
                        } catch (SQLException e) {
                            System.out.println("Erreur lors de la suppression : " + e.getMessage());
                        }
                        break;
                    case "STRUCT":
                        try {
                            HashMap<String, fieldType> struct = gestion.structTable("produis", true);
                        } catch (SQLException e) {
                            System.out.println("Erreur lors de la récupération de la structure : " + e.getMessage());
                        }
                        break;
                    case "DROP":
                        try {
                            String dropQuery = "DROP TABLE produit;";
                            gestion.execute(dropQuery);
                            System.out.println("Table 'produit' supprimé.");
                        } catch (SQLException e) {
                            System.out.println("Erreur lors de la suppression de la table : " + e.getMessage());
                        }
                        break;
                    default:
                        System.out.println("Commande inconue : " + commande);
                        break;
                }
            }

        } catch (IOException e) {
            System.out.println("Erreur d'entrée/sorti : " + e.getMessage());
        } finally {        	
        	db.close();
        }
    }
}
