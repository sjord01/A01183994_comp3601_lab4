package a01183994.database.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01183994.database.Database;


public abstract class Dao {
    protected final Database database;
    protected final String tableName;
    private static final Logger LOG = LogManager.getLogger();

    protected Dao(Database database, String tableName) {
        this.database = database;
        this.tableName = tableName;
    }

    public abstract void create() throws SQLException;
    
    protected void create(String sql) throws SQLException {
		Statement statement = null;
		try {
			Connection connection = database.getConnection();
			statement = connection.createStatement();
			LOG.debug(sql);
			statement.executeUpdate(sql);
		} finally {
			close(statement);
		}
	}

    protected void executeUpdate(String sql) throws SQLException {
        try (Connection connection = database.getConnection();
             Statement statement = connection.createStatement()) {
            LOG.debug(sql);
            statement.executeUpdate(sql);
        }
    }

    public void drop() throws SQLException {
		Statement statement = null;
		try {
			Connection connection = database.getConnection();
			statement = connection.createStatement();
			String sql = "drop table " + tableName;
			LOG.debug(sql);
			statement.executeUpdate(sql);
		} finally {
			close(statement);
		}
	}
    
    protected void close(Statement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

