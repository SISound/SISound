package model;

import java.util.ArrayList;
import java.util.TreeSet;

public class User {

	private long userID;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private String email;
	private City city;
	private Country country;
	private String bio;
	private String profilPicture;
	private String coverPhoto;
	private TreeSet<Song> songs;
	private TreeSet<Playlist> playlists;
	private ArrayList<User> followers;
	
	//constructor for registering user
	public User(String firstName, String lastName, String username, String password, String email, City city,
			Country country) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.email = email;
		this.city = city;
		this.country = country;
		
		this.songs=new TreeSet();
		this.playlists=new TreeSet();
		this.followers=new ArrayList<>();
	}

	//constructor for retrieving from db
	public User(long userID, String firstName, String lastName, String username, String password, String email,
			City city, Country country, String bio, String profilPicture, String coverPhoto, TreeSet<Song> songs,
			TreeSet<Playlist> playlists, ArrayList<User> followers) {
		this(firstName, lastName, username, password, email, city, country);
		this.bio = bio;
		this.profilPicture = profilPicture;
		this.coverPhoto = coverPhoto;
		this.songs = songs;
		this.playlists = playlists;
		this.followers = followers;
	}
	
	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getProfilPicture() {
		return profilPicture;
	}

	public void setProfilPicture(String profilPicture) {
		this.profilPicture = profilPicture;
	}

	public String getCoverPhoto() {
		return coverPhoto;
	}

	public void setCoverPhoto(String coverPhoto) {
		this.coverPhoto = coverPhoto;
	}

	public TreeSet<Song> getSongs() {
		return songs;
	}

	public void setSongs(TreeSet<Song> songs) {
		this.songs = songs;
	}

	public TreeSet<Playlist> getPlaylists() {
		return playlists;
	}

	public void setPlaylists(TreeSet<Playlist> playlists) {
		this.playlists = playlists;
	}

	public ArrayList<User> getFollowers() {
		return followers;
	}

	public void setFollowers(ArrayList<User> followers) {
		this.followers = followers;
	}
	
	
}
