package com.KoreaIT.example.JAM.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.KoreaIT.example.JAM.Article;
import com.KoreaIT.example.JAM.container.Container;
import com.KoreaIT.example.JAM.util.DBUtil;
import com.KoreaIT.example.JAM.util.SecSql;

public class ArticleDao {

	public ArticleDao() {
	}

	public int doWrite(String title, String body, int writerId) {

		SecSql sql = new SecSql();
		
		sql.append("INSERT INTO article ");
		sql.append("SET regDate = NOW(), ");
		sql.append("updateDate = NOW(), ");
		sql.append("title = ?, ", title);
		sql.append("`body` = ?, ", body);
		sql.append("writerId = ?, ", writerId);
		sql.append("hit = 0");
		
		int id = DBUtil.insert(Container.conn, sql);
		
		return id;
	}

	public void doModify(int id, String newTitle, String newBody) {

		SecSql sql = new SecSql();

		sql.append("UPDATE article");
		sql.append(" SET updateDate = NOW(), ");
		sql.append("title = ?, ", newTitle);
		sql.append("`body` = ?", newBody);
		sql.append(" WHERE id = ?", id);

		DBUtil.update(Container.conn, sql);

	}

	public int searchArticleCount(int id) {
		SecSql sql = new SecSql();
		sql.append("SELECT COUNT(*) FROM article ");
		sql.append("WHERE id = ?", id);
		
		int articlesCount = DBUtil.selectRowIntValue(Container.conn, sql);
		
		return articlesCount;
	}

	public void doDelete(int id) {
		SecSql sql = new SecSql();
		sql.append("DELETE FROM article ");
		sql.append("WHERE id = ?", id);
		
		DBUtil.delete(Container.conn, sql);
	}

	public Map<String, Object> searchArticle(int id) {
		SecSql sql = new SecSql();

		sql.append("SELECT * FROM article ");
		sql.append("WHERE id = ?", id);
		
		return DBUtil.selectRow(Container.conn, sql);
	}

	public int getWriterIdByArticleId(int id) {
		SecSql sql = new SecSql();
		
		sql.append("SELECT writerId ");
		sql.append("FROM article ");
		sql.append("WHERE id = ?", id);
		
		return DBUtil.selectRowIntValue(Container.conn, sql);
	}
	
	public Article getArticleByArticleId(int id) {
		SecSql sql = new SecSql();
		
		sql.append("SELECT A.*, M.name AS writer ");
		sql.append("FROM article AS A ");
		sql.append("INNER JOIN member AS M ");
		sql.append("ON A.writerId = M.id ");
		sql.append("WHERE A.id = ?", id);
		
		Map<String, Object> articleMap = DBUtil.selectRow(Container.conn, sql);
		
		if(articleMap.isEmpty()) {
			return null;
		}
		
		return new Article(articleMap);
	}
	
	public void increaseHit(int id) {
		SecSql sql = new SecSql();
		
		sql.append("UPDATE article ");
		sql.append("SET hit = hit + 1 ");
		sql.append("WHERE id = ?", id);
		
		DBUtil.update(Container.conn, sql);
	}

	public List<Article> getForPrintArticles(Map<String, Object> args) {
		SecSql sql = new SecSql();
		
		String searchKeyword = "";
		
		if(args.containsKey("searchKeyword")) {
			searchKeyword = (String)args.get("searchKeyword");
		}
		
		int limitFrom = -1;
		int limitTake = -1;
		
		if(args.containsKey("limitFrom")) {
			limitFrom = (int)args.get("limitFrom");
		}
		if(args.containsKey("limitTake")) {
			limitTake = (int)args.get("limitTake");
		}
		
		sql.append("SELECT A.*, M.name AS writer ");
		sql.append("FROM article AS A ");
		sql.append("INNER JOIN member AS M ");
		sql.append("ON A.writerId = M.id ");
		if(searchKeyword.length() > 0) {
			sql.append("WHERE A.title LIKE CONCAT('%', ?, '%')", searchKeyword);
		}
		sql.append("ORDER BY A.id DESC");
		
		if(limitFrom != -1) {
			sql.append("LIMIT ?, ?", limitFrom, limitTake);
		}
		
		List<Map<String, Object>> articlesListMap = DBUtil.selectRows(Container.conn, sql);
		
		List<Article> articles = new ArrayList<>();
		for (Map<String, Object> articleMap : articlesListMap) {
			articles.add(new Article(articleMap));
		}
		return articles;
	}
}
