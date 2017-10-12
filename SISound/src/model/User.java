package model;

import java.util.ArrayList;
import java.util.TreeSet;

public class User {

	private int userID;
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
	
	public User(){
		//TODO add comparators to treesets
		this.songs=new TreeSet();
		this.playlists=new TreeSet();
		this.followers=new ArrayList<>();
	}

}
