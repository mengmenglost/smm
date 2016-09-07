package org.mm.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbOperator {
	public DbOperator(String url, String user, String password) {
		this.url = url;
		this.user = user;
		this.password = password;
	}

	private String url;
	private String user;
	private String password;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Map<String, Object>> getList(String sql, Object... params)
			throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection();
			statement = buildStatement(sql, connection, params);
			resultSet = statement.executeQuery();
			ResultSetMetaData metaData = resultSet.getMetaData();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			int count = metaData.getColumnCount();
			while (resultSet.next()) {
				Map<String, Object> map = resultSet2Map(resultSet, metaData,
						count);
				list.add(map);
			}
			return list;
		} finally {
			close(resultSet);
			close(statement);
			close(connection);
		}

	}

	public Map<String, Object> getOne(String sql, Object... params)
			throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection();
			statement = buildStatement(sql, connection, params);
			resultSet = statement.executeQuery();
			ResultSetMetaData metaData = resultSet.getMetaData();
			int count = metaData.getColumnCount();
			while (resultSet.next()) {
				Map<String, Object> map = resultSet2Map(resultSet, metaData,
						count);
				return map;
			}
			return null;
		} finally {
			close(resultSet);
			close(statement);
			close(connection);
		}
	}

	public int execute(String sql, Object... params) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection();
			statement = buildStatement(sql, connection, params);
			int result = statement.executeUpdate();
			return result;
		} finally {
			close(resultSet);
			close(statement);
			close(connection);
		}
	}

	private Map<String, Object> resultSet2Map(ResultSet resultSet,
			ResultSetMetaData metaData, int count) throws SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		for (int columnIndex = 1; columnIndex < count; columnIndex++) {
			String key = metaData.getColumnName(columnIndex);
			Object value = resultSet.getObject(columnIndex);
			map.put(key, value);
		}
		return map;
	}

	private void close(PreparedStatement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private void close(ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void close(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private Connection getConnection() throws SQLException {
		Connection connection = DriverManager
				.getConnection(url, user, password);
		return connection;
	}

	private PreparedStatement buildStatement(String sql, Connection connection,
			Object... params) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sql);
		for (int i = 0; i < params.length; i++) {
			statement.setObject(i, params[i]);
		}
		return statement;
	}

}
