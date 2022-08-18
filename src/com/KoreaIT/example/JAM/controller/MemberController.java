package com.KoreaIT.example.JAM.controller;

import com.KoreaIT.example.JAM.Member;
import com.KoreaIT.example.JAM.container.Container;
import com.KoreaIT.example.JAM.service.MemberService;

public class MemberController extends Controller {
	
	private MemberService memberService;
	
	public MemberController() {
		memberService = Container.memberService;
	}
	
	public void doJoin() {
		
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
		
		memberService.doJoin(loginId, loginPw, name);
		
		System.out.printf("%s님 반갑습니다.\n", name);
	}

	public void doLogin() {
//		if(Container.session.isLogined() == true) {
//			System.out.println("이미 로그인 상태입니다.");
//			return;
//		}
//		
//		System.out.println("== 로그인 ==");
//		System.out.printf("아이디 : ");
//		String loginId = sc.nextLine();
//		System.out.printf("비밀번호 : ");
//		String loginPw = sc.nextLine();
//		
//		Member member = memberService.doLogin(loginId, loginPw);
//		boolean isLoginIdDup = memberService.isLoginIdDup(loginId);
//
//		if(isLoginIdDup == false) {
//			System.out.println("존재하지 않는 아이디입니다.");
//			return;
//		}
//		
//		if(member == null) {
//			System.out.println("비밀번호가 틀렸습니다.");
//			
//		} else {
//			System.out.printf("%s님 반갑습니다.\n", member.name);
//			Container.session.setMember(member);
//		}
		
		String loginId = null;
		String loginPw = null;

		System.out.println("== 로그인 ==");

		int loginIdTryMaxCount = 3;
		int loginIdTryCount = 0;

		while (true) {
			if (loginIdTryCount >= loginIdTryMaxCount) {
				System.out.println("아이디를 확인하고 다시 시도해주세요.");
				return;
			}

			System.out.printf("아이디 : ");
			loginId = sc.nextLine().trim();
			if (loginId.length() == 0) {
				loginIdTryCount++;
				System.out.println("아이디를 입력해주세요");
				continue;
			}

			boolean isLoginIdDup = memberService.isLoginIdDup(loginId);

			if (isLoginIdDup == false) {
				loginIdTryCount++;
				System.out.printf("%s는(은) 존재하지 않는 아이디입니다.\n", loginId);
				continue;
			}

			break;
		}

		Member member = memberService.getMemberByLoginId(loginId);

		int loginPwTryMaxCount = 3;
		int loginPwTryCount = 0;

		while (true) {
			if (loginPwTryCount >= loginPwTryMaxCount) {
				System.out.println("비밀번호를 확인하고 다시 시도해주세요.");
				return;
			}

			System.out.printf("비밀번호 : ");
			loginPw = sc.nextLine().trim();

			if (loginPw.length() == 0) {
				loginPwTryCount++;
				System.out.println("비밀번호를 입력해주세요");
				continue;
			}

			if (member.loginPw.equals(loginPw) == false) {
				loginPwTryCount++;
				System.out.println("비밀번호가 일치하지 않습니다.");
				continue;
			}

			System.out.printf("%s님 환영합니다.\n", member.name);

			Container.session.login(member);

			break;
		}
		
	}

	public void showProfile() {
		
		if (Container.session.isLogined() == false) {
			System.out.println("로그인 상태가 아닙니다.");
		} else {
			System.out.println(Container.session.loginedMember.name);
		}
		
	}

	public void doLogout() {
		if (Container.session.isLogined() == false) {
			System.out.println("로그인 상태가 아닙니다.");
		} else {
			Container.session.logout();
			System.out.println("로그아웃 되었습니다.");
		}
	}

}
