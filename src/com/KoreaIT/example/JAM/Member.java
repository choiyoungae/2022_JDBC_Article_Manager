package com.KoreaIT.example.JAM;

import java.time.LocalDateTime;
import java.util.Map;

public class Member {
	public int id;
	public String name;
	public String loginId;
	public String loginPw;
	public LocalDateTime regDate;
	public LocalDateTime updateDate;
	
	
	public Member(int id, String name, String loginId, String loginPw, LocalDateTime regDate,
			LocalDateTime updateDate) {
		this.id = id;
		this.name = name;
		this.loginId = loginId;
		this.loginPw = loginPw;
		this.regDate = regDate;
		this.updateDate = updateDate;
	}
	
	public Member(Map<String, Object> memberMap) {
		this.id = (int)memberMap.get("id");
		this.name = (String)memberMap.get("name");
		this.loginId = (String)memberMap.get("loginId");
		this.loginPw = (String)memberMap.get("loginPw");
		this.regDate = (LocalDateTime)memberMap.get("regDate");
		this.updateDate = (LocalDateTime)memberMap.get("updateDate");
	}
	
}
