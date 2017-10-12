package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

public class Song implements Comparable<Song> {

	private long songId;
	private String title;
	private LocalDateTime uploadDate;
	private int timesListened;
	private User user;
	private String url;
	private Genres genre;
	private HashMap<Actions, HashSet<User>> actions;
	private TreeSet<Comment> comments;
	
	//constructor for adding song
	public Song(String title, User user, Genres genre , String url, LocalDateTime uploadDate) {
		this.title = title;
		this.genre = genre;
		this.user =  user;
		this.url = url;
		this.uploadDate = uploadDate;
		this.timesListened = 0;
		this.actions = new HashMap<>();
		for (Actions action : Actions.values()) {
			this.actions.put(action, new HashSet<>());
		}
		this.comments = new TreeSet<>();
	}

	//constructor for retrieving from db
	public Song(long songId, String title, LocalDateTime uploadDate, int timesListened, User user, String url,
			Genres genre, HashMap<Actions, HashSet<User>> actions, TreeSet<Comment> comments) {
		this(title, user, genre, url, uploadDate);
		this.songId = songId;
		this.timesListened = timesListened;
		this.actions = actions;
		this.comments = comments;
	}

	public long getSongId() {
		return songId;
	}

	public String getTitle() {
		return title;
	}

	public LocalDateTime getUploadDate() {
		return uploadDate;
	}

	public int getTimesListened() {
		return timesListened;
	}

	public User getUser() {
		return user;
	}

	public String getUrl() {
		return url;
	}

	public Genres getGenre() {
		return genre;
	}

	public HashMap<Actions, HashSet<User>> getActions() {
		return actions;
	}

	public TreeSet<Comment> getComments() {
		return comments;
	}

	public void setSongId(int songId) {
		this.songId = songId;
	}
	
	public void setTimesListened(int timesListened) {
		this.timesListened = timesListened;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setGenre(Genres genre) {
		this.genre = genre;
	}
	
	public void addAction(Actions action, User user) {
		this.actions.get(action).add(user);
	}
	
	@Override
	public int compareTo(Song o) {
		return this.uploadDate.compareTo(o.getUploadDate());
	}
}
