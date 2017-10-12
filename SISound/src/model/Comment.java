package model;

import java.time.LocalDateTime;
import java.util.TreeSet;

public abstract class Comment implements Comparable<Comment>{

	private int commentId;
	private User user;
	private String text;
	private LocalDateTime date;
	private TreeSet<Comment> subcoments;
	private Comment parentComment;
	
	public Comment(User user, String text, LocalDateTime date, Comment parentComment) {
		this.user = user;
		this.text = text;
		this.date = date;
		this.subcoments = new TreeSet<>();
		this.parentComment = parentComment;
		if(parentComment != null) {
			parentComment.subcoments.add(this);
		}
	}
	
	public Comment(int commentId, User user, String text, LocalDateTime date, Comment parentComment, TreeSet<Comment> subComments) {
		this(user, text, date, parentComment);
		this.commentId = commentId;
		this.subcoments = subComments;
	}

	public int getCommentId() {
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
}
