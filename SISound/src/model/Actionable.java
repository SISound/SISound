package model;

public interface Actionable {
	
	boolean isSong();
	boolean isComment();
	long getId();
	void setId(long id);
}
