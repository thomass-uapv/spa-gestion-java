package fr.spa.gestion.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public abstract class Entite implements IData {
	
	protected int id;
	
	protected String values;
	protected HashMap<String, fieldType> map;
		
	public Entite(HashMap<String, fieldType> map, int id) {
		super();
		this.values = "";
		this.map = map;
		this.id = id;
	}

	public void setValues(String values) {
		this.values = values;
	}

	public void setMap(HashMap<String, fieldType> map) {
		this.map = map;
	}

	@Override
	public String toString() {
		return values;
	}
	
	@Override
	public String getValues() {
		return values;
	}
	
	@Override
	public HashMap<String, fieldType> getMap() {
		return map;
	}
	
	@Override
	public boolean check(HashMap<String, fieldType> tableStruct) {
		if (tableStruct.size() == this.map.size()) {
			for (int i = 0; i < tableStruct.size(); i++) {
				if (tableStruct.keySet().toArray()[i] == this.map.keySet().toArray()[i]) {
					if (tableStruct.get(tableStruct.keySet().toArray()[i]) != this.map.get(this.map.keySet().toArray()[i])) {
						return false;
					}
				} else {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public int getId() {
		return id;
	}

	/**
	 * @param db
	 * @param table : Le nom d'une table valide.
	 * @return
	 * @throws SQLException
	 */
	public abstract PreparedStatement getUpdateCmd(Connection db, String table) throws SQLException;
	
}
