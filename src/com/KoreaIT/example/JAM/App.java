package com.KoreaIT.example.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
	public void run() {
		Scanner sc = new Scanner(System.in);
		
		int lastArticleId = 0;
		
		while(true) {
			System.out.printf("명령어) ");
			String cmd = sc.nextLine().trim();
			
			if(cmd.equals("exit")) {
				System.out.println("프로그램을 종료합니다.");
				break;
			}
			
			if(cmd.equals("article write")) {
				
				int id = lastArticleId + 1;
				System.out.printf("제목 : ");
				String title = sc.nextLine();
				System.out.printf("내용 : ");
				String body = sc.nextLine();
				
				linkDBMS(title, body);
				
			} else if(cmd.equals("article list")) {
				List<Article> theArticles = selectDBMS();
				
				if(theArticles.size() == 0) {
					System.out.println("게시글이 없습니다.");
					continue;
				}
				
				System.out.println("  번호  |  제목  ");
				for(Article article : theArticles) {
					System.out.printf("  %d  |  %s\n", article.id, article.title);
				}
				
			} else if(cmd.startsWith("article modify")) {
				int id = Integer.parseInt(cmd.split(" ")[2]);

				System.out.printf("== %d번 게시물 수정 ==\n", id);
				System.out.printf("제목 : ");
				String newTitle = sc.nextLine();
				System.out.printf("내용 : ");
				String newBody = sc.nextLine();
				
				modifyDBMS(newTitle, newBody, id);
			}
		}
		
	}
	
	public void linkDBMS(String title, String body) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/article_manager?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

			conn = DriverManager.getConnection(url, "root", "");
			System.out.println("연결 성공!");
			
			String sql = "INSERT INTO article ";
			sql += "SET regDate = NOW()";
			sql += ", updateDate = NOW()";
			sql += ", title = CONCAT('" + title + "')";
			sql += ", `body` = CONCAT('" + body + "')";
			
//			System.out.println(sql);
			pstmt = conn.prepareStatement(sql);
			
			int affectedRows = pstmt.executeUpdate();
			
			System.out.println("affectedRows : " + affectedRows);

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
		} catch (SQLException e) {
			System.out.println("에러: " + e);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<Article> selectDBMS() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<Article> articles = new ArrayList<>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/article_manager?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

			conn = DriverManager.getConnection(url, "root", "");
			System.out.println("연결 성공!");
			
			String sql = "SELECT * ";
			sql += "FROM article ";
			sql += "ORDER BY id DESC";
			
//			System.out.println(sql);
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("id");
				String regDate = rs.getString("regDate");
				String updateDate = rs.getString("updateDate");
				String title = rs.getString("title");
				String body = rs.getString("body");
				
				Article article = new Article(id, regDate, updateDate, title, body);
				articles.add(article);
			}
			

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
		} catch (SQLException e) {
			System.out.println("에러: " + e);
		} finally {
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return articles;
	}
	
	public void modifyDBMS(String newTitle, String newBody, int id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/article_manager?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

			conn = DriverManager.getConnection(url, "root", "");
			System.out.println("연결 성공!");
			
			String sql = "UPDATE article ";
			sql += "SET title = CONCAT('" + newTitle + "'), ";
			sql += "`body` = CONCAT('" + newBody + "'), ";
			sql += "updateDate = NOW() ";
			sql += "WHERE id = " + id;
			
//			System.out.println(sql);
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
		} catch (SQLException e) {
			System.out.println("에러: " + e);
		} finally {
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
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