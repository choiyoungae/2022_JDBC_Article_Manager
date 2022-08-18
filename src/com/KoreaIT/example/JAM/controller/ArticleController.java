package com.KoreaIT.example.JAM.controller;

import java.util.List;

import com.KoreaIT.example.JAM.Article;
import com.KoreaIT.example.JAM.container.Container;
import com.KoreaIT.example.JAM.service.ArticleService;

public class ArticleController extends Controller {

	private ArticleService articleService;
	
	public ArticleController() {
		articleService = Container.articleService;
	}

	public void doWrite() {
		
		if(Container.session.isLogined() == false) {
			System.out.println("로그인이 필요한 기능입니다.");
			return;
		}
		
		System.out.println("== 게시물 작성 ==");
		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();
				
		int writerId = Container.session.loginedMemberId;

		int id = articleService.doWrite(title, body, writerId);

		System.out.printf("%d번 글이 작성되었습니다.\n", id);
	}

	public void showList(String cmd) {
		
		List<Article> articles = null;
		
		if (cmd.equals("article list")) {
			
			System.out.println("== 게시글 리스트 ==");
			
			articles = articleService.getArticles();
			
			
		} else if(cmd.startsWith("article list ")) {
			
			String searchKeyword = cmd.split(" ")[2];
			
			System.out.printf("검색된 키워드 : %s\n", searchKeyword);
			
			System.out.println("== 게시글 리스트 ==");
			
			articles = articleService.getArticlesWithSearchKeyword(searchKeyword);

		}
		
		if(articles.size() == 0) {
			System.out.println("게시글이 없습니다.");
			return;
		}
		
		System.out.println("  번호  |     제목     |  작성자  |  조회수");
		for(Article article : articles) {
			System.out.printf("  %3d  |  %9s  | %6s | %3d\n", article.id, article.title, article.writer, article.hit);
		}
		
	}

	public void doModify(String cmd) {
		
		if(Container.session.isLogined() == false) {
			System.out.println("로그인이 필요한 기능입니다.");
			return;
		}
		
		int id = Integer.parseInt(cmd.split(" ")[2]);
		
		int articlesCount = articleService.searchArticleCount(id);
		
		if(articlesCount == 0) {
			System.out.printf("%d번 게시글이 존재하지 않습니다.\n", id);
			return;
		}
		
		if(articleService.getWriterIdByArticleId(id) != Container.session.loginedMemberId) {
			System.out.println("본인이 작성한 게시글만 변경이 가능합니다.");
			return;
		}

		System.out.printf("== %d번 게시물 수정 ==\n", id);
		System.out.printf("제목 : ");
		String newTitle = sc.nextLine();
		System.out.printf("내용 : ");
		String newBody = sc.nextLine();
		
		articleService.doModify(id, newTitle, newBody);
				
		System.out.printf("%d번 글이 수정되었습니다.\n", id);
	}

	public void doDelete(String cmd) {
		
		if(Container.session.isLogined() == false) {
			System.out.println("로그인이 필요한 기능입니다.");
			return;
		}
		
		int id = Integer.parseInt(cmd.split(" ")[2]);
		
		int articlesCount = articleService.searchArticleCount(id);
		
		if(articlesCount == 0) {
			System.out.printf("%d번 게시글이 존재하지 않습니다.\n", id);
			return;
		}

		if(articleService.getWriterIdByArticleId(id) != Container.session.loginedMemberId) {
			System.out.println("본인이 작성한 게시글만 변경이 가능합니다.");
			return;
		}
		
		articleService.doDelete(id);
		
		System.out.printf("%d번 글이 삭제되었습니다.\n", id);
	}

	public void showDetail(String cmd) {
		int id = Integer.parseInt(cmd.split(" ")[2]);
		
		Article article = articleService.getArticleByArticleId(id);
		
		if (article == null) {
			System.out.printf("%d번 게시글이 존재하지 않습니다.\n", id);
			return;
		}

		articleService.increaseHit(id);
		
		article = articleService.getArticleByArticleId(id);

		System.out.printf("== %d번 게시물 상세보기 ==\n", id);
		
		System.out.printf("번호 : %d\n", article.id);
		System.out.printf("작성자 : %s\n", article.writer);
		System.out.printf("조회수 : %d\n", article.hit);
		System.out.printf("작성날짜 : %s\n", article.regDate);
		System.out.printf("수정날짜 : %s\n", article.updateDate);
		System.out.printf("제목 : %s\n", article.title);
		System.out.printf("내용 : %s\n", article.body);
		
	}

}
