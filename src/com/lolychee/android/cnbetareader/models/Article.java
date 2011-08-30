package com.lolychee.android.cnbetareader.models;

import java.sql.Timestamp;
import java.util.Date;

public class Article {

	private long article_id;

	private String topic;

	private String title;

	private String summary;

	private String content;

	private Timestamp timestamp;

	private int hits;

	public int comments_total;

	private boolean is_read;

	public void setId(long id) {
		if (article_id == 0) {
			article_id = id;
		}
	}

	public long getId() {
		return article_id;
	}

	public void setTopic(String topic) {
		this.topic = topic.trim();
	}

	public String getTopic() {
		return topic;
	}

	public void setTitle(String title) {
		this.title = title.trim();
	}

	public String getTitle() {
		return title;
	}

	public void setSummary(String summary) {
		this.summary = summary.trim();
	}

	public String getSummary() {
		return summary;
	}

	public void setContent(String content) {
		this.content = content.trim();
	}

	public String getContent() {
		return content;
	}

	public void setDate(Date date) {
		this.timestamp = new Timestamp(date.getTime());
	}

	public Date getDate() {
		return new Date(timestamp.getTime());
	}

	public void setHits() {
		this.hits += 1;
	}

	public int getHits() {
		return hits;
	}

	public void setIsRead(boolean flag) {
		this.is_read = flag;
	}

	public boolean getIsRead() {
		return is_read;
	}
}
