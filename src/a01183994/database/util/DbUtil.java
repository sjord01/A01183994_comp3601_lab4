package a01183994.database.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DbUtil {

	public static boolean tableExists(Connection connection, String tableName) throws SQLException {
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		ResultSet resultSet = null;
		String rsTableName = null;

		try {
			resultSet = databaseMetaData.getTables(connection.getCatalog(), "%", "%", null);
			while (resultSet.next()) {
				rsTableName = resultSet.getString("TABLE_NAME");
				if (rsTableName.equalsIgnoreCase(tableName)) {
					return true;
				}
			}
		} finally {
			resultSet.close();
		}

		return false;
	}

	public static int executeUpdate(Statement statement, String sql) throws SQLException {
		System.out.println("Ready to executeUpdate: " + sql);
		int count = statement.executeUpdate(sql);

		return count;
	}

	public static ResultSet executeQuery(Statement statement, String sql) throws SQLException {
		System.out.println("Ready to query: " + sql);
		ResultSet resultSet = statement.executeQuery(sql);

		return resultSet;
	}

	public static boolean execute(PreparedStatement statement, Object... args) throws SQLException {
		System.out.print(statement.toString() + " values: ");
		boolean result = false;
		int i = 1;
		for (Object object : args) {
			if (object == null) {
				statement.setObject(i, null);
			} else if (object instanceof String) {
				statement.setString(i, object.toString());
			} else if (object instanceof Boolean) {
				statement.setBoolean(i, (Boolean) object);
			} else if (object instanceof Integer) {
				statement.setInt(i, (Integer) object);
			} else if (object instanceof Long) {
				statement.setLong(i, (Long) object);
			} else if (object instanceof Float) {
				statement.setFloat(i, (Float) object);
			} else if (object instanceof Double) {
				statement.setDouble(i, (Double) object);
			} else if (object instanceof Byte) {
				statement.setByte(i, (Byte) object);
			} else if (object instanceof Timestamp) {
				statement.setTimestamp(i, (Timestamp) object);
			} else if (object instanceof LocalDateTime) {
				statement.setTimestamp(i, Timestamp.valueOf((LocalDateTime) object));
			} else {
				statement.setString(i, object.toString());
			}

			if (object != null) {
				System.out.print(object.toString());
			} else {
				System.out.print("null");
			}

			System.out.print(" ");
			i++;
		}

		result = statement.execute();
		System.out.println();

		return result;
	}

	public static ResultSet executeQuery(Connection connection, String preparedStatementString, Object... args) throws SQLException {
		System.out.print(preparedStatementString + " values: ");
		ResultSet result = null;
		PreparedStatement statement = null;
		statement = connection.prepareStatement(preparedStatementString);
		int i = 1;
		for (Object object : args) {
			if (object == null) {
				statement.setObject(i, null);
			} else if (object instanceof String) {
				statement.setString(i, object.toString());
			} else if (object instanceof Boolean) {
				statement.setBoolean(i, (Boolean) object);
			} else if (object instanceof Integer) {
				statement.setInt(i, (Integer) object);
			} else if (object instanceof Long) {
				statement.setLong(i, (Long) object);
			} else if (object instanceof Float) {
				statement.setFloat(i, (Float) object);
			} else if (object instanceof Double) {
				statement.setDouble(i, (Double) object);
			} else if (object instanceof Byte) {
				statement.setByte(i, (Byte) object);
			} else if (object instanceof Timestamp) {
				statement.setTimestamp(i, (Timestamp) object);
			} else if (object instanceof LocalDateTime) {
				statement.setTimestamp(i, Timestamp.valueOf((LocalDateTime) object));
			} else {
				statement.setString(i, object.toString());
			}

			if (object != null) {
				System.out.print(object.toString());
			} else {
				System.out.print("null");
			}

			System.out.print(" ");
			i++;
		}

		result = statement.executeQuery();
		System.out.println();

		return result;
	}

	public static void close(Statement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
			System.out.println("Failed to close statment" + e);
		}
	}

}
