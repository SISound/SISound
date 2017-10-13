package model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeSet;

import model.Song;
import model.User;

public class UserDao {

	private static UserDao instance;
	
	private UserDao(){}
	
	public static synchronized UserDao getInstance(){
		if(instance==null){
			instance=new UserDao();
		}
		
		return instance;
	}
	
	public void insertUser(User u) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("INSERT INTO users (user_name, user_password, email) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, u.getUsername());
		stmt.setString(2, u.getPassword());
		stmt.setString(3, u.getEmail());
		ResultSet rs=stmt.executeQuery();
		rs.next();
		u.setUserID(rs.getLong(1));
	}
	
	public boolean existUser(String username, String password) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("SELECT count(*) as count FROM users where user_name=? AND user_password=?");
		stmt.setString(1, username);
		stmt.setString(2, password);
		ResultSet rs=stmt.executeQuery();
		rs.next();
		return rs.getInt("count")>0;
	}
	
	public User getUser(String username) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("SELECT u.user_id, u.user_name, u.email, u.first_name, u.last_name, u.city_name, u.country_name, "
				                                  + "u.bio, u.profile_picture, u.cover_photo FROM users as u JOIN cities as cty "
				                                  + "ON u.city_id=cty.city_id "
				                                  + "JOIN countries as cnt "
				                                  + "ON cty.country_id=cnt.country_id "
				                                  + "WHERE u.user_name=?");
		stmt.setString(1, username);
		ResultSet rs=stmt.executeQuery();
		TreeSet<Song> songs=new TreeSet<>();
		//TODO add songs, playlists and followers
	}
}
