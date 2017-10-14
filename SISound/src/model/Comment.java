package model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

public class Comment implements Comparable<Comment>, Actionable{

	private long commentId;
	private User user;
	private String text;
	private LocalDateTime date;
	private TreeSet<Comment> subcoments;
	private Comment parentComment;
	private HashMap<Actions, HashSet<User>> likes;
	
	public Comment(User user, String text, LocalDateTime date, Comment parentComment) {
		this.user = user;
		this.text = text;
		this.date = date;
		this.subcoments = new TreeSet<>();
		this.parentComment = parentComment;
		if(parentComment != null) {
			parentComment.subcoments.add(this);
		}
		this.likes=new HashMap<>();
		this.likes.put(Actions.LIKE, new HashSet<>());
	}
	
	public Comment(long commentId, User user, String text, LocalDateTime date, Comment parentComment, TreeSet<Comment> subComments) {
		this(user, text, date, parentComment);
		this.commentId = commentId;
		this.subcoments = subComments;
	}

	public long getCommentId() {
		return commentId;
	}

	public User getUser() {
		return user;
	}

	public String getText() {
		return text;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public TreeSet<Comment> getSubcoments() {
		return subcoments;
	}

	public Comment getParentComment() {
		return parentComment;
	}

	public void addSubcomment(Comment comment) {
		this.subcoments.add(comment);
	}
	
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public int compareTo(Comment o) {
		return this.date.compareTo(o.getDate());
	}

	@Override
	public boolean isSong() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isComment() {
		return false;
	}

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setId(long id) {
		// TODO Auto-generated method stub
		
	}
	
	public void setLikes(HashMap<Actions, HashSet<User>> likes) {
		this.likes = likes;
	}
}
