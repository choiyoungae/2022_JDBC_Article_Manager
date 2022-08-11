package com.KoreaIT.example.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.KoreaIT.example.JAM.util.DBUtil;
import com.KoreaIT.example.JAM.util.SecSql;

public class App {
	public void run() {
		Scanner sc = new Scanner(System.in);
		
		
		while(true) {
			System.out.printf("명령어) ");
			String cmd = sc.nextLine().trim();
			
			// DB 연결
			Connection conn = null;
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				
			} catch (ClassNotFoundException e) {
				System.out.println("예외 : MySql 드라이버 클래스가 없습니다.");
				System.out.println("프로그램을 종료합니다.");
				break;
				
			}
			
			String url = "jdbc:mysql://127.0.0.1:3306/article_manager?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

			try {
				conn = DriverManager.getConnection(url, "root", "");
				
				int actionResult = doAction(conn, sc, cmd);
				
				if(actionResult == -1) {
					break;
				}
			}
			catch (SQLException e) {
				System.out.println("***에러: " + e);
				break;
				
			} finally {
				try {
					if (conn != null && !conn.isClosed()) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
	private int doAction(Connection conn, Scanner sc, String cmd) {
		if(cmd.equals("exit")) {
			System.out.println("프로그램을 종료합니다.");
			return -1;
		}
		
		if(cmd.equals("article write")) {
			
			System.out.println("== 게시물 작성 ==");
			System.out.printf("제목 : ");
			String title = sc.nextLine();
			System.out.printf("내용 : ");
			String body = sc.nextLine();
			
			SecSql sql = new SecSql();
			
			sql.append("INSERT INTO article ");
			sql.append("SET regDate = NOW()");
			sql.append(", updateDate = NOW()");
			sql.append(", title = ?", title);
			sql.append(", `body` = ?", body);
			
			int id = DBUtil.insert(conn, sql);

			System.out.printf("%d번 글이 작성되었습니다.\n", id);
			
//			PreparedStatement pstmt = null;
//
//			try {
//				String sql = "INSERT INTO article ";
//				sql += "SET regDate = NOW()";
//				sql += ", updateDate = NOW()";
//				sql += ", title = CONCAT('제목',RAND())";
//				sql += ", `body` = CONCAT('내용',RAND())";
//				
//				pstmt = conn.prepareStatement(sql);
//				
//				int affectedRows = pstmt.executeUpdate();
//				
//				System.out.println("affectedRows : " + affectedRows);
//
//			} catch (SQLException e) {
//				System.out.println("에러: " + e);
//			} finally {
//				try {
//					if (pstmt != null && !pstmt.isClosed()) {
//						pstmt.close();
//					}
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
			
			
		} else if(cmd.equals("article list")) {
			
			System.out.println("== 게시글 리스트 ==");
			
			List<Article> articles = new ArrayList<>();
			
			SecSql sql = new SecSql();
			
			sql.append("SELECT * ");
			sql.append("FROM article ");
			sql.append("ORDER BY id DESC");
			
			List<Map<String, Object>> articlesListMap = DBUtil.selectRows(conn, sql);
			
			for(Map<String, Object> articleMap : articlesListMap) {
				articles.add(new Article(articleMap));
			}
			
//			PreparedStatement pstmt = null;
//			ResultSet rs = null;
//
//			try {
//				String sql = "SELECT * ";
//				sql += "FROM article ";
//				sql += "ORDER BY id DESC";
//				
//				pstmt = conn.prepareStatement(sql);
//				rs = pstmt.executeQuery();
//				
//				while (rs.next()) {
//					int id = rs.getInt("id");
//					String regDate = rs.getString("regDate");
//					String updateDate = rs.getString("updateDate");
//					String title = rs.getString("title");
//					String body = rs.getString("body");
//					
//					Article article = new Article(id, regDate, updateDate, title, body);
//					articles.add(article);
//				}
//
//			} catch (SQLException e) {
//				System.out.println("***에러: " + e);
//				
//			} finally {
//				try {
//					if (pstmt != null && !pstmt.isClosed()) {
//						pstmt.close();
//					}
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//				try {
//					if (rs != null && !rs.isClosed()) {
//						rs.close();
//					}
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
			
			if(articles.size() == 0) {
				System.out.println("게시글이 없습니다.");
				return 0;
			}
			
			System.out.println("  번호  |  제목  ");
			for(Article article : articles) {
				System.out.printf("  %d  |  %s\n", article.id, article.title);
			}
			
		} else if(cmd.startsWith("article modify")) {
			int id = Integer.parseInt(cmd.split(" ")[2]);

			System.out.printf("== %d번 게시물 수정 ==\n", id);
			System.out.printf("제목 : ");
			String newTitle = sc.nextLine();
			System.out.printf("내용 : ");
			String newBody = sc.nextLine();
			
			SecSql sql = new SecSql();

			sql.append("UPDATE article");
			sql.append(" SET updateDate = NOW()");
			sql.append(", title = ?", newTitle);
			sql.append(", `body` = ?", newBody);
			sql.append(" WHERE id = ?", id);

			DBUtil.update(conn, sql);
			
			System.out.printf("%d번 글이 수정되었습니다.\n", id);
			
//			PreparedStatement pstmt = null;
//			ResultSet rs = null;
//
//			try {
//				String sql = "UPDATE article ";
//				sql += "SET title = CONCAT('" + newTitle + "'), ";
//				sql += "`body` = CONCAT('" + newBody + "'), ";
//				sql += "updateDate = NOW() ";
//				sql += "WHERE id = " + id;
//				
//				pstmt = conn.prepareStatement(sql);
//				pstmt.executeUpdate();
//
//			} catch (SQLException e) {
//				System.out.println("***에러: " + e);
//			} finally {
//				try {
//					if (pstmt != null && !pstmt.isClosed()) {
//						pstmt.close();
//					}
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//				try {
//					if (rs != null && !rs.isClosed()) {
//						rs.close();
//					}
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
			
		}
		
		return 0;
	}

	
}
