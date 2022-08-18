package com.KoreaIT.example.JAM.session;

import com.KoreaIT.example.JAM.Member;

public class Session {
	private Member member;

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	public boolean isLogined() {
		if(member != null) {
			return true;
		}
		
		return false;
	}
	
	
}
