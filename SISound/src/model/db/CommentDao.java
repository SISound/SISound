package model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.TreeSet;

import model.Actionable;
import model.Actions;
import model.Comment;
import model.User;


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
		PreparedStatement stmt = con.prepareStatement("INSERT INTO ? (user_id, comment_text, upload_date, song_id, parent_id) "
				                                  + "VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, commented.isSong() ? "songs_comments" : "playlists_comments");
		stmt.setLong(2, comment.getUser().getUserID());
		stmt.setString(3, comment.getText());
		stmt.setTimestamp(4, Timestamp.valueOf(comment.getDate()));
		stmt.setLong(5, commented.getId());
		stmt.setLong(6, comment.getParentComment().getCommentId());
		ResultSet rs = stmt.executeQuery();
		rs.next();
		commented.setId(rs.getInt(1));
	}
	
	
	//TODO getComments from song/playlist
	public synchronized TreeSet<Comment> comments(long id, boolean isSong) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement("SELECT (comment_id, user_id, comment_text, upload_date, parent_id FROM ? WHERE ? = ? AND parent_id = null");//TODO check query
		stmt.setString(1, isSong ? "songs_comments" : "playlists_comments");
		stmt.setString(2, isSong ? "song_id" : "playlist_id");
		stmt.setLong(3, id);
		
		HashMap<Long, Comment> mainComments = new HashMap<>();
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			mainComments.put(rs.getLong(1), new Comment(rs.getLong(1), UserDao.getInstance().getUser(rs.getLong(2)), rs.getString(3), rs.getTimestamp(4).toLocalDateTime(), null, new TreeSet()); //TODO get user by id
		}
		
		//TODO add subcomments
	}
	
}
