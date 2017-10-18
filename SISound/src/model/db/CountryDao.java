package model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CountryDao {

	private static CountryDao instance;
	private final static Map<String, Long> COUNTRIES = new HashMap<>();
	
	private CountryDao() throws SQLException {
		getAllCountries();
	}
	
	public static synchronized CountryDao getInstance() throws SQLException {
		if(instance == null){
			instance = new CountryDao();
		}
		return instance;
	}
	
	public long getCountryId(String genre) throws SQLException {
		return COUNTRIES.get(genre);
	}

	private void getAllCountries() throws SQLException {
		if (COUNTRIES != null) {
			return;
		}
		
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("SELECT (country_id, country_name) FROM countries");
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()) {
			COUNTRIES.put(rs.getString("country_name"), rs.getLong("country_id"));
		}	
	}
}
