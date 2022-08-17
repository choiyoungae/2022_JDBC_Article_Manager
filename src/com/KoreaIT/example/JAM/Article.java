package com.KoreaIT.example.JAM;

import java.time.LocalDateTime;
import java.util.Map;

public class Article extends Object {
	
	public int id;
	public LocalDateTime regDate;
	public LocalDateTime updateDate;
	public String title;
	public String body;
	public String writer;
	
	public Article(int id, LocalDateTime regDate, LocalDateTime updateDate, String title, String body, String writer) {
		this.id = id;
		this.regDate = regDate;
		this.updateDate = updateDate;
		this.title = title;
		this.body = body;
		this.writer = writer;
	}

	public Article(Map<String, Object> articleMap) {
		this.id = (int)articleMap.get("id");
		this.title = (String)articleMap.get("title");
		this.body = (String)articleMap.get("body");
		this.regDate = (LocalDateTime)articleMap.get("regDate");
		this.updateDate = (LocalDateTime)articleMap.get("updateDate");
		this.writer = (String)articleMap.get("writer");
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", regDate=" + regDate + ", updateDate=" + updateDate + ", title=" + title
				+ ", body=" + body + "]";
	}
	
	
}
