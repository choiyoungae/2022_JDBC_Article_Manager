package com.KoreaIT.example.JAM.dao;

import java.util.List;
import java.util.Map;

import com.KoreaIT.example.JAM.container.Container;
import com.KoreaIT.example.JAM.util.DBUtil;
import com.KoreaIT.example.JAM.util.SecSql;

public class ArticleDao {

	public ArticleDao() {
	}

	public int doWrite(String title, String body) {

		SecSql sql = new SecSql();
		
		sql.append("INSERT INTO article ");
		sql.append("SET regDate = NOW(), ");
		sql.append("updateDate = NOW(), ");
		sql.append("title = ?, ", title);
		sql.append("`body` = ?", body);
		
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

	public List<Map<String, Object>> showList() {
		SecSql sql = new SecSql();
		
		sql.append("SELECT * ");
		sql.append("FROM article ");
		sql.append("ORDER BY id DESC");
		
		return DBUtil.selectRows(Container.conn, sql);
	}
	
	
}
