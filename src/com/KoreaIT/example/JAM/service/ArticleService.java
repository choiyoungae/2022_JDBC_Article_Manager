package com.KoreaIT.example.JAM.service;

import java.util.List;
import java.util.Map;

import com.KoreaIT.example.JAM.Member;
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

	public Map<String, Object> searchArticle(int id) {
		return articleDao.searchArticle(id);
	}

	public List<Map<String, Object>> showList() {
		return articleDao.showList();
	}

	public Member getMemberByWriterId(int writerId) {
		return articleDao.getMemberByWriterId(writerId);
	}

	public int getWriterIdByArticleId(int id) {
		return articleDao.getWriterIdByArticleId(id);
	}
}
