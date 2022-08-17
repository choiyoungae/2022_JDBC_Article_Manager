package com.KoreaIT.example.JAM.dao;

import java.sql.Connection;
import java.util.Map;

import com.KoreaIT.example.JAM.Member;
import com.KoreaIT.example.JAM.util.DBUtil;
import com.KoreaIT.example.JAM.util.SecSql;

public class MemberDao {
	private Connection conn;
	
	public MemberDao(Connection conn) {
		this.conn = conn;
	}
	
	public boolean isLoginIdDup(String loginId) {
		SecSql sql = new SecSql();
		
		sql.append("SELECT * FROM `member` ");
		sql.append("WHERE loginId = ?", loginId);
		
		return !(DBUtil.selectRow(conn, sql).isEmpty());
	}

	public int doJoin(String loginId, String loginPw, String name) {
		SecSql sql = new SecSql();
		
		sql.append("INSERT INTO `member` ");
		sql.append("SET regDate = NOW(), ");
		sql.append("updateDate = NOW(), ");
		sql.append("loginId = ?, ", loginId);
		sql.append("loginPw = ?, ", loginPw);
		sql.append("`name` = ?", name);
		
		int id = DBUtil.insert(conn, sql);
		
		return id;
	}

	public Member doLogin(String loginId, String loginPw) {
		SecSql sql = new SecSql();
		
		sql.append("SELECT * FROM `member` ");
		sql.append("WHERE loginId = ? ", loginId);
		sql.append("AND loginPw = ?", loginPw);
		
		Map<String, Object> memberMap = DBUtil.selectRow(conn, sql);
		
		if(memberMap.isEmpty()) {
			return null;
		}
		
		return new Member(memberMap);
	}

	public Member getMemberByLoginId(String loginId) {
		SecSql sql = new SecSql();
		
		sql.append("SELECT * FROM `member` ");
		sql.append("WHERE loginId = ? ", loginId);
		
		Map<String, Object> memberMap = DBUtil.selectRow(conn, sql);
		
		if(memberMap.isEmpty()) {
			return null;
		}
		
		return new Member(memberMap);
	}
}


