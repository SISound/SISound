package model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import model.Actionable;
import model.Actions;
import model.User;

public class ActionsDao {

	private static ActionsDao instance;
	
	private ActionsDao(){}
	
	public static synchronized ActionsDao getInstance(){
		if(instance == null){
			instance = new ActionsDao();
		}
		
		return instance;
	}
	
	public synchronized void addAction(Actionable a, Actions action, User user) throws SQLException {
		
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement("INSERT INTO ? (?, user_id) " + "VALUES (?, ?)");
		switch (action) {
		case LIKE:
			if(a.isSong()) {
				stmt.setString(1, "songs_likes");
				stmt.setString(2, "song_id");
				
			} else {
				stmt.setString(1, "playlists_likes");
				stmt.setString(2, "playlist_id");
			}
						
			break;
		case DISLIKE:
			if(a.isSong()) {
				stmt.setString(1, "songs_dislikes");
				stmt.setString(2, "song_id");
				
			} else {
				stmt.setString(1, "playlists_dislikes");
				stmt.setString(2, "playlist_id");
			}
			
			break;
		case SHARE:
			if(a.isSong()) {
				stmt.setString(1, "songs_shares");
				stmt.setString(2, "song_id");
				
			} else {
				stmt.setString(1, "playlists_shares");
				stmt.setString(2, "playlist_id");
			}
			
			break;
		}
		
		stmt.setLong(3, a.getId());
		stmt.setLong(4, user.getUserID());
		
		stmt.execute();
	}
	
	public synchronized HashMap<Actions, HashSet<User>> getActions(boolean isSong, long id) throws SQLException {
		
		//creating the result map
		HashMap<Actions, HashSet<User>> actions;
		actions = new HashMap<>();
		for (Actions act : Actions.values()) {
			actions.put(act, new HashSet());
		}
		
		Connection con = DBManager.getInstance().getConnection();

		//getting likes
		PreparedStatement stmt = con.prepareStatement("SELECT ?.username FROM ? JOIN ? "
				                                    + "ON ?=?"
				                                    + "WHERE ?=?");
		stmt.setString(1, "u");
		stmt.setString(2, isSong ? "songs_likes as sl" : "playlists_likes as pl");
		stmt.setString(3, "users as u");
		stmt.setString(4, isSong ? "sl.user_id" : "pl.user_id");
		stmt.setString(5, "u.user_id");
		stmt.setString(6, isSong ? "song_id" : "playlist_id");
		stmt.setLong(7, id);
		
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			actions.get(Actions.LIKE).add(UserDao.getInstance().getUser(rs.getString(1)));
		}
		
		//getting dislikes
		stmt = con.prepareStatement("SELECT ?.username FROM ? JOIN ? "
				                  + "ON ?=? "
				                  + "WHERE ?=?");
		stmt.setString(1, "u");
		stmt.setString(2, isSong ? "songs_dislikes as sd" : "playlists_dislikes as pd");
		stmt.setString(3, " users as u");
		stmt.setString(4, isSong ? "sd.user_id" : "pd.user_id");
		stmt.setString(5, "u.user_id");
		stmt.setString(6, isSong ? "song_id" : "playlist_id");
		stmt.setLong(7, id);
		rs = stmt.executeQuery();
		
		while (rs.next()) {
			actions.get(Actions.DISLIKE).add(UserDao.getInstance().getUser(rs.getString(1)));
		}
		
		//getting shares
		stmt = con.prepareStatement("SELECT ?.username FROM ? JOIN ? "
                                  + "ON ?=? "
                                  + "WHERE ?=?");		
		stmt.setString(1, "u");
		stmt.setString(2, isSong ? "songs_shares as ss" : "playlists_shares as ps");
		stmt.setString(3, " users as u");
		stmt.setString(4, isSong ? "sd.user_id" : "pd.user_id");
		stmt.setString(5, "u.user_id");
		stmt.setString(6, isSong ? "song_id" : "playlist_id");
		stmt.setLong(7, id);
		rs = stmt.executeQuery();
		
		while (rs.next()) {
			actions.get(Actions.SHARE).add(UserDao.getInstance().getUser(rs.getString(1))); 
		}
		
		return actions;
	}
	
}
