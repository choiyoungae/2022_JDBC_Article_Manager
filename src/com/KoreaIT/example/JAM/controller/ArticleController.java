package com.KoreaIT.example.JAM.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.KoreaIT.example.JAM.Article;
import com.KoreaIT.example.JAM.container.Container;
import com.KoreaIT.example.JAM.service.ArticleService;

public class ArticleController extends Controller {

	private ArticleService articleService;
	
	public ArticleController() {
		articleService = Container.articleService;
	}

	public void doWrite() {
		System.out.println("== 게시물 작성 ==");
		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();
				
		int id = articleService.doWrite(title, body);

		System.out.printf("%d번 글이 작성되었습니다.\n", id);
	}

	public void showList() {
		System.out.println("== 게시글 리스트 ==");
		
		List<Article> articles = new ArrayList<>();

		List<Map<String, Object>> articlesListMap = articleService.showList();
		
		for(Map<String, Object> articleMap : articlesListMap) {
			articles.add(new Article(articleMap));
		}
		
		if(articles.size() == 0) {
			System.out.println("게시글이 없습니다.");
			return;
		}
		
		System.out.println("  번호  |   제목   |  작성자");
		for(Article article : articles) {
			System.out.printf("  %3d  |  %5s  | %3s\n", article.id, article.title, article.writer);
		}
	}

	public void doModify(String cmd) {
		int id = Integer.parseInt(cmd.split(" ")[2]);

		System.out.printf("== %d번 게시물 수정 ==\n", id);
		System.out.printf("제목 : ");
		String newTitle = sc.nextLine();
		System.out.printf("내용 : ");
		String newBody = sc.nextLine();
		
		articleService.doModify(id, newTitle, newBody);
				
		System.out.printf("%d번 글이 수정되었습니다.\n", id);
	}

	public void doDelete(String cmd) {
		int id = Integer.parseInt(cmd.split(" ")[2]);
		
		int articlesCount = articleService.searchArticleCount(id);
		
		if(articlesCount == 0) {
			System.out.printf("%d번 게시글이 존재하지 않습니다.\n", id);
			return;
		}

		articleService.doDelete(id);
		
		System.out.printf("%d번 글이 삭제되었습니다.\n", id);
	}

	public void showDetail(String cmd) {
		int id = Integer.parseInt(cmd.split(" ")[2]);
		
		Map<String, Object> articleMap = articleService.searchArticle(id);;
		
		if (articleMap.isEmpty()) {
			System.out.printf("%d번 게시글이 존재하지 않습니다.\n", id);
			return;
		}

		System.out.printf("== %d번 게시물 상세보기 ==\n", id);
		
		Article article = new Article(articleMap);
		
		System.out.printf("번호 : %d\n", article.id);
		System.out.printf("작성날짜 : %s\n", article.regDate);
		System.out.printf("수정날짜 : %s\n", article.updateDate);
		System.out.printf("제목 : %s\n", article.title);
		System.out.printf("내용 : %s\n", article.body);
	}

}