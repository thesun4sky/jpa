package me.whitebear.jpa.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import me.whitebear.jpa.jdbc.dao.AccountDAO;
import me.whitebear.jpa.jdbc.vo.AccountVO;

public class JDBCTest {
	String url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";
	String username = "sa";
	String password = null;

	@BeforeEach
	void setUp() {

		try (Connection connection = DriverManager.getConnection(url, username, password)) {
			String createSql = "CREATE TABLE ACCOUNT (id SERIAL PRIMARY KEY, username varchar(255), password varchar(255))";
			try (PreparedStatement statement = connection.prepareStatement(createSql)) {
				statement.execute();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Test
	@DisplayName("JDBC 테이블 생성/추가/조회 실습")
	void jdbcTest() throws SQLException {
		// when
		// when
		try (Connection connection = DriverManager.getConnection(url, username, password)) {
			System.out.println("Connection created: " + connection);

			String insertSql = "INSERT INTO ACCOUNT (id, username, password) VALUES ((SELECT coalesce(MAX(ID), 0) + 1 FROM ACCOUNT A), 'user1', 'pass1')";
			try (PreparedStatement statement = connection.prepareStatement(insertSql)) {
				statement.execute();
			}

			// then
			String selectSql = "SELECT * FROM ACCOUNT";
			try (PreparedStatement statement = connection.prepareStatement(selectSql)) {
				var rs = statement.executeQuery();
				while (rs.next()) {
					System.out.printf("%d, %s, %s", rs.getInt("id"), rs.getString("username"),
						rs.getString("password"));
				}
			}
		}
	}

	@Test
	@DisplayName("JDBC DAO 삽입/조회 실습")
	void jdbcDAOInsertSelectTest() throws SQLException {
		// given
		AccountDAO accountDAO = new AccountDAO();

		// when
		var id = accountDAO.insertAccount(new AccountVO("new user", "new password"));

		// then
		var account = accountDAO.selectAccount(id);
		assert account.getUsername().equals("new user");
	}

}
