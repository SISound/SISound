package model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.TreeSet;

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
	
	//TODO deleting playlist
	public synchronized void deletePlaylist(Playlist playlist) throws SQLException {
		Connection con=DBManager.getInstance().getConnection();
		
		try {
			//delete comment-likes
			for (Comment comment : playlist.getComments()) {
				ActionsDao.getInstance().deleteCommentLikes(comment.getId());
			}
			
			//delete comments
			CommentDao.getInstance().deleteComments(playlist.getId(), false);
			
			//delete likes, dislikes, shares
			ActionsDao.getInstance().deleteAllActions(false, playlist.getId());
			
			//delete from playlist_songs
			PreparedStatement stmt = con.prepareStatement("DELETE FROM playlists_songs WHERE playlist_id = ?");
			stmt.setLong(1, playlist.getId());
			stmt.execute();
			
			//delete playlist
			stmt=con.prepareStatement("DELETE FROM playlists WHERE playlist_id = ?");
			stmt.setLong(1, playlist.getId());
			stmt.execute();
				
		} catch (SQLException e) {
			//reverse
			throw new SQLException();
		}
	}
	
	public synchronized void addSongToPlaylist(long userId, String playlistName, Song s) throws SQLException{
		SongDao.getInstance().uploadSong(s);
		
		//getting the ID of the playlist to which we want to add song 
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("SELECT playlist_id FROM playlists WHERE user_id=? AND playlist_name=?");
		stmt.setLong(1, userId);
		stmt.setString(2, playlistName);
		ResultSet rs=stmt.executeQuery();
		rs.next();
		long playlistId=rs.getLong(1);
		
		//adding the song to the playlist 
		stmt=con.prepareStatement("INSERT INTO playlists_songs (playlist_id, song_id, upload_date) VALUES (?, ?, ?)");
		stmt.setLong(1, playlistId);
		stmt.setLong(2, s.getId());
		stmt.setString(3, LocalDateTime.now().toString());
	}
	
	public synchronized void deleteSongFromPlaylist(long userId, String playlistName, String songName) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		
		//getting the ID of the playlist from which we want to delete song 
		PreparedStatement stmt=con.prepareStatement("SELECT playlist_id FROM playlists WHERE playlist_name=? AND user_id=?");
		stmt.setLong(1, userId);
		stmt.setString(2, playlistName);
		ResultSet rs=stmt.executeQuery();
		rs.next();
		long playlistId=rs.getLong(1);
				
		//getting the ID of the song we want to delete
		stmt=con.prepareStatement("SELECT song_id FROM songs WHERE song_name=? AND user_id=?");
		stmt.setString(1, songName);
		stmt.setLong(2, userId);
		rs=stmt.executeQuery();
		rs.next();
		long songId=rs.getLong(1);
		
		//deleting the song from the playlist
		stmt=con.prepareStatement("DELETE FROM playlists_songs WHERE playlist_id=? AND song_id=?");
		stmt.setLong(1, playlistId);
		stmt.setLong(2, songId);
		stmt.executeQuery();
	}
}
