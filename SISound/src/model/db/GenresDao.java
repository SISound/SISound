package model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;


public class GenresDao {

	private static GenresDao instance;
	private HashMap<Long, String> genres = null;
	
	public static synchronized GenresDao getInstance(){
		if(instance == null){
			instance = new GenresDao();
		}
		return instance;
	}
	
	private GenresDao() {
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement("SELECT (genre_id, genre_title) FROM music_genres");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				this.genres.put(rs.getLong(1), rs.getString(2));
			}
		} catch (SQLException e) {
			System.out.println("Couldn't load genres");
		}
	}
	
	public HashMap<Long, String> getGenres(){
		return this.genres;
	}
}
