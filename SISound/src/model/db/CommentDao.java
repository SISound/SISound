package model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.HashMap;
import java.util.TreeSet;

import model.Actionable;
import model.Comment;



public class CommentDao {

	private static CommentDao instance;
	
	private CommentDao(){}
	
	public static synchronized CommentDao getInstance(){
		if(instance==null){
			instance=new CommentDao();
		}
		
		return instance;
	}
	
	public synchronized void insertComment(Comment comment, Actionable commented) throws SQLException{
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement("INSERT INTO ? user_id, comment_text, upload_date, song_id, parent_id) "
				                                  + "VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, commented.isSong() ? "songs_comments" : "playlists_comments");
		stmt.setLong(2, comment.getUser().getUserID());
		stmt.setString(3, comment.getText());
		stmt.setTimestamp(4, Timestamp.valueOf(comment.getDate()));
		stmt.setLong(5, commented.getId());
		stmt.setLong(6, comment.getParentComment().getId());
		ResultSet rs = stmt.executeQuery();
		rs.next();
		commented.setId(rs.getInt(1));
	}
	
	
	//TODO getComments from song/playlist
	//OK
	public synchronized TreeSet<Comment> getComments(long id, boolean isSong) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement stmt = null;
		ResultSet rs=null;
		if(isSong){
			stmt=con.prepareStatement("SELECT sc.comment_id, u.user_name, sc.comment_text, sc.upload_date, sc.parent_id FROM songs_comments as sc "
							   + "JOIN users as u "
			                   + "ON sc.user_id=u.user_id "
			                   + "WHERE sc.song_id = ? "
			                   + "AND parent_id IS NULL");
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
		}
		else{
			stmt=con.prepareStatement("SELECT pc.comment_id, u.user_name, pc.comment_text, pc.upload_date, pc.parent_id FROM playlists_comments as pc "
					           + "JOIN users as u "
	                           + "ON pc.user_id=u.user_id "
	                           + "WHERE pc.playlist_id = ? "
	                           + "AND pc.parent_id IS NULL");
	        stmt.setLong(1, id);
	        rs = stmt.executeQuery();
		}
		
		HashMap<Long, Comment> mainComments = new HashMap<>();
		
		while (rs.next()) {
			mainComments.put(rs.getLong(1), new Comment(rs.getLong(1), UserDao.getInstance().getUser(rs.getString(2)), 
					rs.getString(3), rs.getTimestamp(4).toLocalDateTime(), null, new TreeSet())); 
		}
		
		if(isSong){
			stmt = con.prepareStatement("SELECT sc.comment_id, u.user_name, sc.comment_text, sc.upload_date, sc.parent_id FROM songs_comments as sc "
					                  + "JOIN users as u "
					                  + "ON sc.user_id=u.user_id "
					                  + "WHERE sc.song_id = ? "
					                  + "AND parent_id IS NOT NULL");
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
		}
		else{
			stmt = con.prepareStatement("SELECT pc.comment_id, u.user_name, pc.comment_text, pc.upload_date, pc.parent_id FROM playlists_comments as pc "
	                                  + "JOIN users as u "
	                                  + "ON pc.user_id=u.user_id "
	                                  + "WHERE pc.playlist_id = ? "
	                                  + "AND pc.parent_id IS NOT NULL");
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
		}
		
		while (rs.next()) {
			mainComments.get(rs.getLong(5)).addSubcomment(new Comment(rs.getLong(1), UserDao.getInstance().getUser(rs.getString(2)), 
					rs.getString(3), rs.getTimestamp(4).toLocalDateTime(), mainComments.get(rs.getLong(5)), new TreeSet()));
		}

		
		TreeSet<Comment> set = new TreeSet<Comment>();
		for (Long key : mainComments.keySet()) {
		     set.add(mainComments.get(key));
		}
		
		return set;
	}
	
	public synchronized void deleteComments(long id, boolean isSong) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement("DELETE FROM ? WHERE ? = ?");
		stmt.setString(1, isSong ? "songs_comments" : "playlists_comments");
		stmt.setString(2, isSong ? "song_id" : "playlist_id");
		stmt.setLong(3, id);
		
		stmt.execute();
	}
}
