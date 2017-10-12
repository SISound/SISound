package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class Song {

	private int songId;
	private String title;
	private LocalDateTime uploadDate;
	private int timesListened;
	private User user;
	private String url;
	private Genres genre;
	private HashMap<Actions, ArrayList<User>> actions;
	private TreeSet<Comment> comments;
	
	//constructor for adding song
	public Song(String title, User user, Genres genre , String url) {
		this.title = title;
		this.genre = genre;
		this.user =  user;
		this.url = url;
		this.timesListened = 0;
		actions = new HashMap<>();
		for (Actions action : Actions.values()) {
			this.actions.put(action, new ArrayList<>());
		}
		comments = new TreeSet<>();
	}

	//constructor for retrieving from db
	public Song(int songId, String title, LocalDateTime uploadDate, int timesListened, User user, String url,
			Genres genre, HashMap<Actions, ArrayList<User>> actions, TreeSet<Comment> comments) {
		this.songId = songId;
		this.title = title;
		this.uploadDate = uploadDate;
		this.timesListened = timesListened;
		this.user = user;
		this.url = url;
		this.genre = genre;
		this.actions = actions;
		this.comments = comments;
	}

	public int getSongId() {
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

	public HashMap<Actions, ArrayList<User>> getActions() {
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
}
