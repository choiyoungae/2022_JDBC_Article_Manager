package com.KoreaIT.example.JAM.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.KoreaIT.example.JAM.dao.ArticleDao;

public class ArticleService {
	private ArticleDao articleDao;
	
	public ArticleService(Connection conn) {
		articleDao = new ArticleDao(conn);
	}

	public int doWrite(String title, String body) {
		int id = articleDao.doWrite(title, body);
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

	public Map<String, Object> searchArticle(int id) {
		return articleDao.searchArticle(id);
	}

	public List<Map<String, Object>> showList() {
		return articleDao.showList();
	}
}
