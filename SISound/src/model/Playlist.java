package model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

public class Playlist implements Comparable<Playlist>, Actionable {

	private long playlistID;
	private String title;
	private LocalDateTime creationDate;
	private User user;
	private HashMap<Actions, HashSet<User>> actions;
	private TreeSet<Comment> comments;
	private boolean isPrivate;
	private TreeSet<Song> songs;
	
	
	public Playlist(String title, LocalDateTime creationDate, User user, boolean isPrivate) {
		this.title = title;
		this.creationDate = creationDate;
		this.user = user;
		this.isPrivate = isPrivate;
		this.comments = new TreeSet<>();
		this.actions = new HashMap<>();
		for (Actions action : Actions.values()) {
			this.actions.put(action, new HashSet());
		}
		this.songs = new TreeSet<>();
	}


	public Playlist(long playlistID, String title, LocalDateTime creationDate, User user, HashMap<Actions, 
			HashSet<User>> actions, TreeSet<Comment> comments, boolean isPrivate, TreeSet<Song> songs) {
		this(title, creationDate, user, isPrivate);
		this.playlistID = playlistID;
		this.actions = actions;
		this.comments = comments;
		this.songs = songs;
	}

	@Override
	public long getId() {
		return playlistID;
	}


	public String getTitle() {
		return title;
	}


	public LocalDateTime getCreationDate() {
		return creationDate;
	}


	public User getUser() {
		return user;
	}


	public HashMap<Actions, HashSet<User>> getActions() {
		return actions;
	}


	public TreeSet<Comment> getComments() {
		return comments;
	}


	public boolean isPrivate() {
		return isPrivate;
	}


	public TreeSet<Song> getSongs() {
		return songs;
	}
	
	@Override
	public void setId(long playlistID) {
		this.playlistID = playlistID;
	}
	
	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void addComment(Comment comment) {
		this.comments.add(comment);
	}
	
	public void addSong(Song song) {
		this.songs.add(song);
	}
	
	public void addAction(Actions action, User user) {
		this.actions.get(action).add(user);
	}
	
	@Override
	public int compareTo(Playlist o) {
		return this.creationDate.compareTo(o.getCreationDate());
	}
	
	@Override
	public boolean isSong() {
		return false;
	}
}
