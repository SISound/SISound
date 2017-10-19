package model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class GenresDao {

	private static GenresDao instance;
	private final static Map<String, Long> GENRES = new HashMap<>();
	
	private GenresDao() throws SQLException {
		System.out.println("asdf");
		getAllGenres();
	}
	
	public static synchronized GenresDao getInstance() throws SQLException {
		if(instance == null){
			instance = new GenresDao();
		}
		return instance;
	}
	
	public long getGenreId(String genre) throws SQLException {
		return GENRES.get(genre);
	}

	private void getAllGenres() throws SQLException {
		if (!GENRES.isEmpty()) {
			return;
		}
		System.out.println("123");
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("SELECT genre_id, genre_title FROM music_genres");
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()) {
			GENRES.put(rs.getString("genre_title"), rs.getLong("genre_id"));
			System.out.println(rs.getString("genre_title"));
			System.out.println(GENRES.get(rs.getString("genre_title")));
		}	
	}
}