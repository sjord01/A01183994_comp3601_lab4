package a01183994.database.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01183994.data.Customer;
import a01183994.database.Database;
import a01183994.database.DbConstants;

public class CustomerDao extends Dao {
    public static final String TABLE_NAME = DbConstants.CUSTOMERS_TABLE_NAME;
    private static final Logger LOG = LogManager.getLogger();

    public CustomerDao(Database database) {
        super(database, TABLE_NAME);
    }

    public enum Fields {
    	ID("id", "VARCHAR", 20, 1),
        FIRST_NAME("firstName", "VARCHAR", 50, 2),
        LAST_NAME("lastName", "VARCHAR", 50, 3),
        STREET_NAME("street", "VARCHAR", 100, 4),
        CITY("city", "VARCHAR", 50, 5),
        POSTAL_CODE("postalCode", "VARCHAR", 10, 6),
        PHONE("phone", "VARCHAR", 20, 7),
        EMAIL("emailAddress", "VARCHAR", 100, 8),
        JOINED_DATE("joinedDate", "DATE", -1, 9);

        private final String name;
        private final String type;
        private final int length;
        private final int column;

        Fields(String name, String type, int length, int column) {
            this.name = name;
            this.type = type;
            this.length = length;
            this.column = column;
        }

        public String getName() { return name; }
        public String getType() { return type; }
        public int getLength() { return length; }
    }

    @Override
    public void create() throws SQLException {
        if (!tableExists()) {
            String sql = String.format(
                "CREATE TABLE %s (" +
                "%s %s(%d), " +
                "%s %s(%d), " +
                "%s %s(%d), " +
                "%s %s(%d), " +
                "%s %s(%d), " +
                "%s %s(%d), " +
                "%s %s(%d), " +
                "%s %s(%d), " +
                "%s %s, " +
                "PRIMARY KEY (%s))",
                TABLE_NAME,
                Fields.ID.getName(), Fields.ID.getType(), Fields.ID.getLength(),
                Fields.FIRST_NAME.getName(), Fields.FIRST_NAME.getType(), Fields.FIRST_NAME.getLength(),
                Fields.LAST_NAME.getName(), Fields.LAST_NAME.getType(), Fields.LAST_NAME.getLength(),
                Fields.STREET_NAME.getName(), Fields.STREET_NAME.getType(), Fields.STREET_NAME.getLength(),
                Fields.CITY.getName(), Fields.CITY.getType(), Fields.CITY.getLength(),
                Fields.POSTAL_CODE.getName(), Fields.POSTAL_CODE.getType(), Fields.POSTAL_CODE.getLength(),
                Fields.PHONE.getName(), Fields.PHONE.getType(), Fields.PHONE.getLength(),
                Fields.EMAIL.getName(), Fields.EMAIL.getType(), Fields.EMAIL.getLength(),
                Fields.JOINED_DATE.getName(), Fields.JOINED_DATE.getType(),
                Fields.ID.getName()
            );
            super.create(sql);
            LOG.info("Table " + TABLE_NAME + " created successfully.");
        } else {
            LOG.info("Table " + TABLE_NAME + " already exists.");
        }
    }

    private boolean tableExists() throws SQLException {
        Connection connection = database.getConnection();
        ResultSet resultSet = connection.getMetaData().getTables(null, null, TABLE_NAME, null);
        boolean exists = resultSet.next();
        resultSet.close();
        return exists;
    }

    public void add(Customer customer) throws SQLException {
        String sql = String.format(
            "INSERT INTO %s VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
            TABLE_NAME,
            customer.getId(),
            customer.getFirstName(),
            customer.getLastName(),
            customer.getStreetName(),
            customer.getCity(),
            customer.getPostalCode(),
            customer.getPhone(),
            customer.getEmail(),
            customer.getJoinDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        );
        executeUpdate(sql);
    }

    public Customer getCustomer(String id) throws SQLException {
        Customer customer = null;
        String sql = String.format("SELECT * FROM %s WHERE %s = '%s'", TABLE_NAME, Fields.ID.getName(), id);
        try (Connection connection = database.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                customer = new Customer.Builder(
                    resultSet.getString(Fields.ID.getName()),
                    resultSet.getString(Fields.PHONE.getName())
                )
                .setFirstName(resultSet.getString(Fields.FIRST_NAME.getName()))
                .setLastName(resultSet.getString(Fields.LAST_NAME.getName()))
                .setStreetName(resultSet.getString(Fields.STREET_NAME.getName()))
                .setCity(resultSet.getString(Fields.CITY.getName()))
                .setPostalCode(resultSet.getString(Fields.POSTAL_CODE.getName()))
                .setEmail(resultSet.getString(Fields.EMAIL.getName()))
                .setJoinDate(resultSet.getDate(Fields.JOINED_DATE.getName()).toLocalDate().minusMonths(1).toString())
                .build();
            }
        }
        return customer;
    }
    public void update(Customer customer) throws SQLException {
        String sql = String.format(
            "UPDATE %s SET %s='%s', %s='%s', %s='%s', %s='%s', %s='%s', %s='%s', %s='%s', %s='%s' WHERE %s='%s'",
            TABLE_NAME,
            Fields.PHONE.getName(), customer.getPhone(),
            Fields.FIRST_NAME.getName(), customer.getFirstName(),
            Fields.LAST_NAME.getName(), customer.getLastName(),
            Fields.STREET_NAME.getName(), customer.getStreetName(),
            Fields.CITY.getName(), customer.getCity(),
            Fields.POSTAL_CODE.getName(), customer.getPostalCode(),
            Fields.EMAIL.getName(), customer.getEmail(),
            Fields.JOINED_DATE.getName(), customer.getJoinDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
            Fields.ID.getName(), customer.getId()
        );
        executeUpdate(sql);
    }

    public void delete(Customer customer) throws SQLException {
        String sql = String.format("DELETE FROM %s WHERE %s='%s'", TABLE_NAME, Fields.ID.getName(), customer.getId());
        executeUpdate(sql);
    }

    public List<String> getIds() throws SQLException {
        List<String> ids = new ArrayList<>();
        String sql = String.format("SELECT %s FROM %s", Fields.ID.getName(), TABLE_NAME);
        try (Connection connection = database.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                ids.add(resultSet.getString(Fields.ID.getName()));
            }
        }
        return ids;
    }
    
    public void cleanup() throws SQLException {
        LOG.info("Cleaning up CustomerDao resources.");
        try (Connection connection = database.getConnection();
             Statement statement = connection.createStatement()) {
            String sql = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
            LOG.debug(sql);
            statement.executeUpdate(sql);
            LOG.info("Table " + TABLE_NAME + " dropped successfully.");
        } catch (SQLException e) {
            LOG.error("Error during CustomerDao cleanup: " + e.getMessage());
            throw e;
        }
    }
}