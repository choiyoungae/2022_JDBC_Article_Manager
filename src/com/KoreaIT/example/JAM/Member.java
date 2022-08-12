package com.KoreaIT.example.JAM;

import java.time.LocalDateTime;

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
	
	
	
}
