package com.KoreaIT.example.JAM.controller;

import java.sql.Connection;
import java.util.Scanner;

import com.KoreaIT.example.JAM.service.MemberService;

public class MemberController extends Controller {
	
	private MemberService memberService;
	
	public MemberController(Connection conn, Scanner sc) {
		super(sc);
		memberService = new MemberService(conn);
	}
	
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
			boolean isLoginIdDup = memberService.isLoginIdDup(loginId);
			
			if( isLoginIdDup == true ) {
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
		
		int id = memberService.doJoin(loginId, loginPw, name);
		
		System.out.printf("%s님 반갑습니다.\n", name);
	}

}
