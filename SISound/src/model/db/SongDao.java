package model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeSet;
import java.sql.Timestamp;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

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
	
	public synchronized void uploadSong(Song song) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("INSERT INTO songs (song_name, upload_date, listenings, user_id, genre_id, song_url) "
				                                  + "VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, song.getTitle());
		stmt.setTimestamp(2, Timestamp.valueOf(song.getUploadDate()));
		stmt.setLong(3, song.getTimesListened());
		stmt.setLong(4, song.getUser().getUserID());
		stmt.setLong(5, song.getGenre().getGenreId());
		stmt.setString(6, song.getUrl());
		ResultSet rs=stmt.executeQuery();
		rs.next();
		song.setId(rs.getInt(1));
	}
	
	//?
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
		PreparedStatement stmt=con.prepareStatement("SELECT s.song_id, s.song_name, s.upload_date, s.listenings, g.genre_title, s.song_url, s.genre_id"
				                                  + "FROM songs as s JOIN music_genres as g "
				                                  + "ON s.genre_id=g.genre_id"
				                                  + "WHERE user_id=?");
		stmt.setLong(1, u.getUserID());
		ResultSet rs=stmt.executeQuery();
		TreeSet<Song> songs=new TreeSet<>();
		//TODO add comments
		while(rs.next()){
			songs.add(new Song(rs.getLong(1), rs.getString(2), rs.getTimestamp(3).toLocalDateTime(), rs.getLong(4), u, rs.getString(6), rs.getString(5), 
					ActionsDao.getInstance().getActions(true, rs.getLong(1)), CommentDao.getInstance().getComments(rs.getLong(1), true)));
		}
		
		return songs;
	}
	
	//TODO searching song by name
	public synchronized void searchSongByName(String songName){
		
	}
	
	//TODO deleting song method
	public synchronized void deleteSong(String songName){
		
	}
}
