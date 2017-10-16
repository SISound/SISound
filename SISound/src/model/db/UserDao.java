package model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashSet;
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
	
	public synchronized void insertUser(User u) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("INSERT INTO users (user_name, user_password, email) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, u.getUsername());
		stmt.setString(2, u.getPassword());
		stmt.setString(3, u.getEmail());
		ResultSet rs=stmt.executeQuery();
		rs.next();
		u.setUserID(rs.getLong(1));
	}
	
	public synchronized boolean existUser(String username, String password) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("SELECT count(*) as count FROM users where user_name=? AND user_password=?");
		stmt.setString(1, username);
		stmt.setString(2, password);
		ResultSet rs=stmt.executeQuery();
		rs.next();
		return rs.getInt("count")>0;
	}
	
	public synchronized User getUser(String username) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("SELECT u.user_id, u.user_name, u.user_password, u.email, u.first_name, u.last_name, u.city_name, u.country_name, "
				                                  + "u.bio, u.profile_picture, u.cover_photo FROM users as u JOIN cities as cty "
				                                  + "ON u.city_id=cty.city_id "
				                                  + "JOIN countries as cnt "
				                                  + "ON cty.country_id=cnt.country_id "
				                                  + "WHERE u.user_name=?");
		stmt.setString(1, username);
		ResultSet rs=stmt.executeQuery();
		
		User u=new User(rs.getLong(1), rs.getString(5), rs.getString(6), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11));
		u.setSongs(SongDao.getInstance().getSongsForUser(u));
		u.setPlaylists(PlaylistDao.getInstance().getPlaylistsForUser(u));
		u.setFollowers(this.getFollowers(u));
		
		return u;
	}
	
	private synchronized LinkedHashSet<User> getFollowers(User u) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("SELECT f.user_id, f.user_name, f.user_password, f.email, f.first_name, f.last_name, f.city_name, f.country_name, "
				                                  + "f.bio, f.profile_picture, f.cover_photo FROM follows as f JOIN users as u"
				                                  + "ON f.follower_id=u.user_id "
				                                  + "WHERE f.followed_id=?");
		stmt.setLong(1, u.getUserID());
		ResultSet rs=stmt.executeQuery();
		LinkedHashSet<User> followers=new LinkedHashSet<>();
		while(rs.next()){
			followers.add(new User(rs.getLong(1), rs.getString(5), rs.getString(6), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11)));
		}
		
		return followers;
	}
	
	public synchronized User searchUserByUsername(String username) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("SELECT user_id, first_name, last_name, user_name, password, email, city_name, country_name, bio, profile_pic, cover_photo "
				                                  + "FROM users WHERE user_name=?");
		stmt.setString(1, username);
		ResultSet rs=stmt.executeQuery();
		rs.next();
		User u=new User(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), 
				        rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11));
		
		u.setSongs(SongDao.getInstance().getSongsForUser(u));
		u.setPlaylists(PlaylistDao.getInstance().getPlaylistsForUser(u));
		u.setFollowers(this.getFollowers(u));
		
		return u;
	}
	
	public synchronized void followUser(long followerId, long followedId) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("INSERT INTO follows (follower_id, followed_id) VALUES (?, ?)");
		stmt.setLong(1, followerId);
		stmt.setLong(2, followedId);
		stmt.executeQuery();
	}
	
	public synchronized void unfollowUser(long followerId, long followedId) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("DELETE FROM follows WHERE follower_id=? AND followed_id=?");
		stmt.setLong(1, followerId);
		stmt.setLong(2, followedId);
		stmt.executeQuery();
	}
}
