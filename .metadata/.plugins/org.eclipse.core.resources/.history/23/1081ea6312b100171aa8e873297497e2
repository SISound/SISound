package model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeSet;

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
	
	public synchronized TreeSet<Playlist> getPlaylistForUser(User u) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("SELECT s.song_id, s.song_name, s.upload_date, s.listenings, g.genre_title, s.song_url"
				                                  + "FROM songs as s JOIN music_genres as g "
				                                  + "ON s.genre_id=g.genre_id"
				                                  + "WHERE user_id=?");
		stmt.setLong(1, u.getUserID());
		ResultSet rs=stmt.executeQuery();
		TreeSet<Song> songs=new TreeSet<>();
		//TODO add comments
		while(rs.next()){
			songs.add(new Song(rs.getLong(1), rs.getString(2), rs.getDate(3), rs.getLong(4), u, rs.getString(6), rs.getString(5), ActionsDao.getInstance().getActions(true, rs.getLong(1)), comments));
		}
	}
	
	//TODO search playlist by name
	public synchronized void searchPlaylistByName(String playlistName){
		
	}
	
	//TODO deleting playlist
	public synchronized void deletePlaylist(String playlistName){
		
	}
}
