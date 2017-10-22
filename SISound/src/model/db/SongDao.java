package model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import model.Comment;
import model.Song;
import model.User;

public class SongDao {

	private static SongDao instance;
	
	private SongDao(){}
	
	public static synchronized SongDao getInstance(){
		if(instance==null){
			instance=new SongDao();
		}
		
		return instance;
	}
	
	//OK
	public synchronized void uploadSong(Song song) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		
		PreparedStatement stmt=con.prepareStatement("INSERT INTO songs (song_name, upload_date, listenings, user_id, genre_id, song_url) "
				                                  + "VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, song.getTitle());
		stmt.setTimestamp(2, Timestamp.valueOf(song.getUploadDate()));
		stmt.setLong(3, song.getTimesListened());
		stmt.setLong(4, song.getUser().getUserID());
		stmt.setLong(5, GenresDao.getInstance().getGenreId(song.getGenre()));
		stmt.setString(6, song.getUrl());
		stmt.executeUpdate();
		ResultSet rs=stmt.getGeneratedKeys();
		rs.next();
		song.setId(rs.getInt(1));
	}
	
	public synchronized void addSongToPlaylist(long songId, long playlistId, LocalDateTime time) throws SQLException {
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("INSERT INTO playlists_songs (playlist_id, song_id, upload_date) "
				                                  + "VALUES (?, ?, ?)");
		
		stmt.setLong(1, playlistId);
		stmt.setLong(2, songId);
		stmt.setTimestamp(3, Timestamp.valueOf(time));
		stmt.execute();
	}
	//ok
	public synchronized boolean existSong(Song s) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("SELECT count(*) FROM songs WHERE song_id=?");
		stmt.setLong(1, s.getId());
		ResultSet rs=stmt.executeQuery();
		rs.next();
		int count=rs.getInt(1);
		return count>0;
	}
	
	public synchronized TreeSet<Song> getSongsForUser(User u) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement("SELECT s.song_id, s.song_name, s.upload_date, s.listenings, g.genre_title, s.song_url"
				                                  + "FROM songs as s JOIN music_genres as g "
				                                  + "ON s.genre_id = g.genre_id"
				                                  + "WHERE s.user_id = ?");
		stmt.setLong(1, u.getUserID());
		System.out.println("opala");
		ResultSet rs = stmt.executeQuery();//TODO fix throws exception
		TreeSet<Song> songs = new TreeSet<>();

		while(rs.next()){
			songs.add(new Song(rs.getLong(1), rs.getString(2), rs.getTimestamp(3).toLocalDateTime(), rs.getInt(4), u, rs.getString(6), rs.getString(5), 
					ActionsDao.getInstance().getActions(true, rs.getLong(1)), CommentDao.getInstance().getComments(rs.getLong(1), true)));
		}
		
		return songs;
	}
	
	public synchronized TreeMap<LocalDateTime, Song> getSongsForPlaylist(long playlistId) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("SELECT s.song_id, s.song_name, s.upload_date, s.listenings, u.user_name, mg.genre_title, s.song_url, ps.upload_date "
			                                      + "FROM playlists_songs as ps JOIN songs as s ON ps.song_id = s.song_id "
			                                      + "JOIN users as u ON u.user_id = s.user_id "
			                                      + "JOIN music_genres as mg ON s.genre_id = mg.genre_id "
			                                      + "WHERE ps.playlist_id=?");
		stmt.setLong(1, playlistId);
		ResultSet rs=stmt.executeQuery();
		TreeMap<LocalDateTime, Song> songs=new TreeMap<>();
		while(rs.next()){
			songs.put(rs.getTimestamp(8).toLocalDateTime(), new Song(rs.getLong(1), rs.getString(2), rs.getTimestamp(3).toLocalDateTime(), rs.getInt(4), UserDao.getInstance().getUser(rs.getString(5)),
					  rs.getString(7), rs.getString(6), ActionsDao.getInstance().getActions(true, rs.getLong(1)), CommentDao.getInstance().getComments(rs.getLong(1), true)));
		}
		
		return songs;
	}
	
	//searching song by name
	public synchronized Song searchSongByName(String songName) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("SELECT s.song_id, s.song_name, s.upload_date, s.listenings, u.user_name, mg.genre_title, s.song_url "
				                                  + "FROM songs as s JOIN users as u "
				                                  + "ON s.user_id=u.user_id "
				                                  + "JOIN music_genres as mg ON s.genre_id=mg.genre_id "
				                                  + "WHERE s.song_name=?");
		stmt.setString(1, songName);
		ResultSet rs = stmt.executeQuery();
		rs.next();
		
		return new Song(rs.getLong(1), rs.getString(2), rs.getTimestamp(3).toLocalDateTime(), rs.getInt(4), UserDao.getInstance().getUser(rs.getString(5)),
				        rs.getString(7), rs.getString(6), ActionsDao.getInstance().getActions(true, rs.getLong(1)), CommentDao.getInstance().getComments(rs.getLong(1), true));		
	}
	
	public synchronized HashMap<LocalDateTime, Long> getPlaylistsWithSong(long songId) throws SQLException {
		HashMap<LocalDateTime, Long> res = new HashMap<>();
		
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("SELECT playlist_id, upload_date FROM playlists_songs WHERE song_id = ?");
		stmt.setLong(1, songId);
		
		ResultSet rs = stmt.executeQuery();
		while(rs.next()){
			res.put(rs.getTimestamp(2).toLocalDateTime(), rs.getLong(1));
		}
		
		return res;
	}
	
	public synchronized TreeSet<Song> getAllSongs() throws SQLException{
		TreeSet<Song> songs=new TreeSet<>();
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("SELECT s.song_id, s.song_name, s.upload_date, s.listenings, u.user_name, "
				                                  + "m.genre_title, s.song_url "
				                                  + "FROM songs as s "
				                                  + "JOIN users as u ON s.user_id=u.user_id "
				                                  + "JOIN music_genres as m ON s.genre_id=m.genre_id");
		ResultSet rs=stmt.executeQuery();
		while(rs.next()){
			songs.add(new Song(rs.getLong(1), rs.getString(2), rs.getTimestamp(3).toLocalDateTime(), rs.getInt(4), 
					           UserDao.getInstance().getUser(rs.getString(5)), rs.getString(7), rs.getString(6), 
					           ActionsDao.getInstance().getActions(true, rs.getLong(1)), CommentDao.getInstance().getComments(rs.getLong(1), true)));
		}
		
		return songs;
	}
	
	//deleting song method
	public synchronized void deleteSong(Song song) throws SQLException {
		Connection con=DBManager.getInstance().getConnection();
		con.setAutoCommit(false);
		
		try {
			//delete comment-likes
			for (Comment comment : CommentDao.getInstance().getComments(song.getId(), true)) {
				ActionsDao.getInstance().deleteCommentLikes(comment.getId());
			}
			
			//delete comments
			CommentDao.getInstance().deleteComments(song.getId(), true);
		
			//delete likes
			ActionsDao.getInstance().deleteLikes(true, song.getId());
		
			//delete dislikes
			ActionsDao.getInstance().deleteDislikes(true, song.getId());
			
			//delete shares
			ActionsDao.getInstance().deleteShares(true, song.getId());
			
			//delete from playlist_songs
			PreparedStatement stmt=con.prepareStatement("DELETE FROM playlists_songs WHERE song_id = ?");
			stmt.setLong(1, song.getId());
			stmt.execute();
			
			//delete song
			stmt=con.prepareStatement("DELETE FROM songs WHERE song_id = ?");
			stmt.setLong(1, song.getId());
			stmt.execute();
			
			con.commit();
		} catch (SQLException e) {
			//reverse
			con.rollback();
			throw new SQLException();
	
		} finally {
			con.setAutoCommit(true);
		}
	}
}
