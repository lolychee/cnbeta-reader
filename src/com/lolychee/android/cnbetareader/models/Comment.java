package com.lolychee.android.cnbetareader.models;

import java.sql.Timestamp;
import java.util.Date;

public class Comment {
	
	private long comment_id;
	
	private long article_id;
	
	private int floor_id;
	
	private String name;
	
	private String content;
	
	private int vote_up;
	
	private int vote_down;
	
	private Timestamp timestamp;
	
	public void setId(long id) {
		if (comment_id == 0) {
			comment_id = id;
		}
	}
	
	public long getId() {
		return comment_id;
	}
	
	public void setVoteUp(int number) {
			vote_up = number;
	}
	
	public void setVoteUp(boolean flag) {
		if (flag == true) {
			vote_up += 1;
		}
	}
	
	public int getVoteUp() {
		return vote_up;
	}
	
	public void setVoteDown(int number) {
		vote_down = number;
}

	public void setVoteDown(boolean flag) {
		if (flag == true) {
			vote_down += 1;
		}
	}
	
	public int getVoteDown() {
		return vote_down;
	}

	public void setContent(String content) {
		this.content = content.trim();
	}

	public String getContent() {
		return content;
	}

	public void setName(String name) {
		this.name = name.trim();
	}

	public String getName() {
		return name;
	}

	public void setArticleId(int article_id) {
		this.article_id = article_id;
	}

	public long getArticleId() {
		return article_id;
	}

	public void setFloorId(int floor_id) {
		this.floor_id = floor_id;
	}

	public int getFloorId() {
		return floor_id;
	}
	
	public void setDate(Date date) {
		this.timestamp = new Timestamp(date.getTime());
	}

	public Date getDate() {
		return new Date(timestamp.getTime());
	}

}
