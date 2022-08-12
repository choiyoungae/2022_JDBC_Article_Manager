package com.KoreaIT.example.JAM.controller;

import java.sql.Connection;
import java.util.Scanner;

import com.KoreaIT.example.JAM.util.DBUtil;
import com.KoreaIT.example.JAM.util.SecSql;

public class MemberController {
	
	private Connection conn;
	private Scanner sc;

	public void doJoin(String cmd) {
		
		System.out.println("== 회원가입 ==");
		
		String loginId = null;
		String loginPw = null;
		String name = null;
		
		while(true) {
			System.out.printf("아이디 : ");
			loginId = sc.nextLine().trim();
			
			if(loginId.length() == 0) {
				System.out.println("아이디를 입력해주세요.");
				continue;
			}
			
			// 중복 검사
			SecSql sql = new SecSql();
			
			sql.append("SELECT * FROM `member` ");
			sql.append("WHERE loginId = ?", loginId);
			
			if( DBUtil.selectRow(conn, sql).isEmpty() == false ) {
				System.out.println("중복된 아이디입니다. 다시 시도해주세요.");
				continue;
			}
			
			break;
		}
		
		while(true) {				
			System.out.printf("비밀번호 : ");
			loginPw = sc.nextLine().trim();
			
			if(loginPw.length() == 0) {
				System.out.println("비밀번호를 입력해주세요.");
				continue;
			}
			
			System.out.printf("비밀번호 확인 : ");
			String loginPwCheck = sc.nextLine().trim();
			
			if(loginPw.equals(loginPwCheck) == false) {
				System.out.println("비밀번호를 다시 확인해주세요.");
				continue;
			}
			
			break;
		}
		
		while(true) {
			System.out.printf("이름 : ");
			name = sc.nextLine().trim();
			
			if(name.length() == 0) {
				System.out.println("이름을 입력해주세요.");
				continue;
			}
			
			break;
		}
		
		SecSql sql = new SecSql();
		
		sql.append("INSERT INTO `member` ");
		sql.append("SET regDate = NOW(), ");
		sql.append("updateDate = NOW(), ");
		sql.append("loginId = ?, ", loginId);
		sql.append("loginPw = ?, ", loginPw);
		sql.append("`name` = ?", name);
		
		DBUtil.insert(conn, sql);

		System.out.printf("%s님 반갑습니다.\n", name);
	}

	public void setConn(Connection conn) {
		this.conn = conn;
		
	}

	public void setSc(Scanner sc) {
		this.sc = sc;
	}

}
