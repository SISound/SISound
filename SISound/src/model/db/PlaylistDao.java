package model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeSet;

import model.Actions;
import model.Comment;
import model.Playlist;
import model.Song;
import model.User;

public class PlaylistDao {

	private static PlaylistDao instance;
	
	private PlaylistDao(){}
	
	public static synchronized PlaylistDao getInstance(){
		if(instance==null){
			instance=new PlaylistDao();
		}
		
		return instance;
	}
	
	public synchronized void createPlaylist(Playlist playlist) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("INSERT INTO playlists (playlist_name, user_id, upload_date, isPrivate) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, playlist.getTitle());
		stmt.setLong(2, playlist.getUser().getUserID());
		stmt.setString(3, playlist.getCreationDate().toString());
		stmt.setBoolean(4, playlist.isPrivate());
		ResultSet rs=stmt.executeQuery();
		rs.next();
		playlist.setId(rs.getLong(1));
	}
	
	public synchronized boolean existPlaylist(Playlist playlist) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("SELECT count(*) FROM ? WHERE ?=?");
		stmt.setString(1, "playlists");
		stmt.setString(2, "playlist_id");
		stmt.setLong(3, playlist.getId());
		ResultSet rs=stmt.executeQuery();
		rs.next();
		int count=rs.getInt(1);
		return count>0;
	}
	
	public synchronized TreeSet<Playlist> getPlaylistsForUser(User u) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("SELECT playlist_id, playlist_name, upload_date, isPrivate FROM playlists WHERE user_id=?");
		stmt.setLong(1, u.getUserID());
		ResultSet rs=stmt.executeQuery();
		TreeSet<Playlist> playlists=new TreeSet<>();
		
		while(rs.next()){
			playlists.add(new Playlist(rs.getLong(1), rs.getString(2), rs.getTimestamp(3).toLocalDateTime(), u, ActionsDao.getInstance().getActions(false, rs.getLong(1)),
					CommentDao.getInstance().getComments(rs.getLong(1), false), rs.getBoolean(4), SongDao.getInstance().getSongsForPlaylist(rs.getLong(1))));
		}
		
		return playlists;
	}
	
	public synchronized Playlist searchPlaylistByName(String playlistName) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("SELECT playlist_id, playlist_name, user_name, upload_date, isPrivate FROM playlists as p JOIN users as u "
				                                  + "ON p.user_id=u.user_id "
				                                  + "WHERE playlist_name=?");
		stmt.setString(1, playlistName);
		ResultSet rs=stmt.executeQuery();
		rs.next();
		Playlist p=new Playlist(rs.getLong(1), rs.getString(2), rs.getTimestamp(3).toLocalDateTime(), UserDao.getInstance().getUser(rs.getString(3)),
				                ActionsDao.getInstance().getActions(false, rs.getLong(1)), CommentDao.getInstance().getComments(rs.getLong(1), false), 
				                rs.getBoolean(5), SongDao.getInstance().getSongsForPlaylist(rs.getLong(1)));
		
		return p;
	}
	
	//deleting playlist
	public synchronized void deletePlaylist(Playlist playlist) throws SQLException {
		Connection con=DBManager.getInstance().getConnection();
		
		ArrayList<HashMap<Actions, HashSet<User>>> likes = new ArrayList<>();
		TreeSet<Comment> comments = CommentDao.getInstance().getComments(playlist.getId(), false);
		HashMap<Actions, HashSet<User>> actions = ActionsDao.getInstance().getActions(false, playlist.getId());
		
		boolean commentsDeleted = false;
		boolean playlistSongsDeleted = false;
		boolean playlistDeleted = false;
		boolean likesDeleted = false;
		boolean dislikesDeleted = false;
		boolean sharesDeleted = false;
		
		try {
			//delete comment-likes

			for (Comment comment : comments) {
				likes.add(ActionsDao.getInstance().getCommentsLikes(comment.getId()));
				ActionsDao.getInstance().deleteCommentLikes(comment.getId());
			}
			
			//delete comments
			commentsDeleted = CommentDao.getInstance().deleteComments(playlist.getId(), false);
			
			//delete likes
			likesDeleted = ActionsDao.getInstance().deleteLikes(false, playlist.getId());
			//delete dislikes
			dislikesDeleted = ActionsDao.getInstance().deleteDislikes(false, playlist.getId());
			//delete shares
			sharesDeleted = ActionsDao.getInstance().deleteShares(false, playlist.getId());
			
			
			//delete from playlist_songs
			PreparedStatement stmt = con.prepareStatement("DELETE FROM playlists_songs WHERE playlist_id = ?");
			stmt.setLong(1, playlist.getId());
			playlistSongsDeleted = stmt.execute();
			
			//delete playlist
			stmt=con.prepareStatement("DELETE FROM playlists WHERE playlist_id = ?");
			stmt.setLong(1, playlist.getId());
			playlistDeleted = stmt.execute();
				
		} catch (SQLException e) {
			//reverse
			//add deleted comment likes
			for (HashMap<Actions, HashSet<User>> map : likes) {
				for (Actions action : map.keySet()) {
					for (User user : map.get(action)) {
						ActionsDao.getInstance().addAction(playlist, action, user);						
					}
				}
			}
			
			//add deleted comments
			if(!commentsDeleted) {
				for (Comment comment : comments) {
					CommentDao.getInstance().insertComment(comment, playlist);
				}
			}
			
			//add deleted likes
			if(!likesDeleted) {
				for (User user : actions.get(Actions.LIKE)) {
					ActionsDao.getInstance().addAction(playlist, Actions.LIKE, user);
				}
			}
			
			//add deleted dislikes
			if(!dislikesDeleted) {
				for (User user : actions.get(Actions.DISLIKE)) {
					ActionsDao.getInstance().addAction(playlist, Actions.DISLIKE, user);
				}
			}
			
			//add deleted shares
			if(!sharesDeleted) {
				for (User user : actions.get(Actions.SHARE)) {
					ActionsDao.getInstance().addAction(playlist, Actions.SHARE, user);
				}
			}
			
			//add deleted playlist_songs
			if(!playlistSongsDeleted) {
				for(Map.Entry<LocalDateTime,Song> entry : playlist.getSongs().entrySet()) {
					  SongDao.getInstance().addSongToPlaylist(entry.getValue().getId(), playlist.getId(), entry.getKey());
				}
			}
			
			//add deleted playlist
			if(!playlistDeleted) {				
				PlaylistDao.getInstance().createPlaylist(playlist);
			}
			
			throw new SQLException();
		}
	}
}
