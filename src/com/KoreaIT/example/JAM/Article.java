package com.KoreaIT.example.JAM;

import java.time.LocalDateTime;
import java.util.Map;

public class Article extends Object {
	
	public int id;
	public LocalDateTime regDate;
	public LocalDateTime updateDate;
	public String title;
	public String body;
	public int writerId;
	public String writer;
	
	public Article(Map<String, Object> articleMap) {
		this.id = (int)articleMap.get("id");
		this.title = (String)articleMap.get("title");
		this.body = (String)articleMap.get("body");
		this.regDate = (LocalDateTime)articleMap.get("regDate");
		this.updateDate = (LocalDateTime)articleMap.get("updateDate");
		this.writerId = (int)articleMap.get("writerId");
		
		if (articleMap.get("writer") != null) {
			this.writer = (String)articleMap.get("writer");
		}
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", regDate=" + regDate + ", updateDate=" + updateDate + ", writerId=" + writerId
				+ ", title=" + title + ", body=" + body + "]";
	}
	
	
}
