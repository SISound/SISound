package model;

public class Genre {

	private long genreId;
	private String name;
	
	public Genre(long genreId, String name) {
		this.genreId = genreId;
		this.name = name;
	}
	
	public long getGenreId() {
		return genreId;
	}
	
	public String getName() {
		return name;
	}
	
}
