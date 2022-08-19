package com.KoreaIT.example.JAM.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.KoreaIT.example.JAM.Article;
import com.KoreaIT.example.JAM.container.Container;
import com.KoreaIT.example.JAM.dao.ArticleDao;

public class ArticleService {
	private ArticleDao articleDao;
	
	public ArticleService() {
		articleDao = Container.articleDao;
	}

	public int doWrite(String title, String body, int writerId) {
		int id = articleDao.doWrite(title, body, writerId);
		return id;
	}

	public void doModify(int id, String newTitle, String newBody) {
		articleDao.doModify(id, newTitle, newBody);
	}

	public int searchArticleCount(int id) {
		int articlesCount = articleDao.searchArticleCount(id);
		
		return articlesCount;
	}

	public void doDelete(int id) {
		articleDao.doDelete(id);
	}

	public int getWriterIdByArticleId(int id) {
		return articleDao.getWriterIdByArticleId(id);
	}

	public Article getArticleByArticleId(int id) {
		return articleDao.getArticleByArticleId(id);
	}

	public void increaseHit(int id) {
		articleDao.increaseHit(id);
	}

	public List<Article> getForPrintArticles(int page, int itemsInAPage, String searchKeyword) {
		int limitFrom = (page - 1) * itemsInAPage;
		int limitTake = itemsInAPage;
		
		// 압축 파일처럼 데이터 보내기
		Map<String, Object> args = new HashMap<>();
		args.put("page", page);
		args.put("limitFrom", limitFrom);
		args.put("limitTake", limitTake);
		args.put("searchKeyword", searchKeyword);
		
		return articleDao.getForPrintArticles(args);
	}
	
}
