package model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class CountryDao {

	private static CountryDao instance;
	private HashMap<String, Long> countries = null;
	
	public static synchronized CountryDao getInstance() throws SQLException{
		if(instance == null){
			instance = new CountryDao();
		}
		return instance;
	}
	
	private CountryDao() throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement stmt;
	
		stmt = con.prepareStatement("SELECT (country_id, country_name) FROM countries");
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			this.countries.put(rs.getString(2), rs.getLong(1));
		}
	}
	
	public HashMap<String, Long> getCountries() {
		return countries;
	}
	
	public long getGenreId(String name) {
		return countries.get(name);
	}
	
}
