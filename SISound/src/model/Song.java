package model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.TreeSet;

import java.util.HashSet;


public class Song implements Comparable<Song>, Actionable {

	private long songId;
	private String title;
	private LocalDateTime uploadDate;
	private long timesListened;
	private User user;
	private String url;
	private String genre;
	private HashMap<Actions, HashSet<User>> actions;
	private TreeSet<Comment> comments;
	
	//constructor for adding song
	public Song(String title, User user, String genre , String url, LocalDateTime uploadDate) {
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
	public Song(long songId, String title, LocalDateTime uploadDate, long timesListened, User user, String url,
			String genre, HashMap<Actions, HashSet<User>> actions, TreeSet<Comment> comments) {
		this(title, user, genre, url, uploadDate);
		this.songId = songId;
		this.timesListened = timesListened;
		this.actions = actions;
		this.comments = comments;
	}
	
	public void addComment(Comment c){
		this.comments.add(c);
	}
	
	@Override
	public long getId() {
		return songId;
	}

	public String getTitle() {
		return title;
	}

	public LocalDateTime getUploadDate() {
		return uploadDate;
	}

	public long getTimesListened() {
		return timesListened;
	}

	public User getUser() {
		return user;
	}

	public String getUrl() {
		return url;
	}

	public String getGenre() {
		return genre;
	}

	public HashMap<Actions, HashSet<User>> getActions() {
		return actions;
	}

	public TreeSet<Comment> getComments() {
		return comments;
	}

	@Override
	public void setId(long songId) {
		this.songId = songId;
	}
	
	public void setTimesListened(int timesListened) {
		this.timesListened = timesListened;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	int getLikesCount(){
		return this.actions.get(Actions.LIKE).size();
	}

	@Override
	public int compareTo(Song o) {
		return this.uploadDate.compareTo(o.uploadDate);
	}

	public void addAction(Actions action, User user) {
		this.actions.get(action).add(user);
	}
	
	@Override
	public boolean isSong() {
		return true;
	}

	@Override
	public boolean isComment() {
		return false;
	}


}
