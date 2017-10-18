package model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

import com.sun.xml.internal.ws.addressing.EndpointReferenceUtil;


public class GenresDao {

	private static GenresDao instance;
	private HashMap<String, Long> genres = null;
	
	private GenresDao() throws SQLException {}
	
	public static synchronized GenresDao getInstance() throws SQLException{
		if(instance == null){
			instance = new GenresDao();
		}
		return instance;
	}
	
	public long getGenreId(String genre) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("SELECT genre_id FROM music_genres WHERE genre_title=?");
		stmt.setString(1, genre);
		ResultSet rs=stmt.executeQuery();
		rs.next();
		long l=rs.getLong(1);
		return l;
	}
}
