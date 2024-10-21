package a01183994.database.dao;

import a01183994.data.Customer;
import a01183994.database.Database;

import java.sql.SQLException;
import java.util.List;

public class CustomerDaoTester {
    private CustomerDao customerDao;

    public CustomerDaoTester(Database database) {
        this.customerDao = new CustomerDao(database);
    }

    public void test() throws SQLException {
        List<String> customerIds = customerDao.getIds();
        System.out.println("Loaded " + customerIds.size() + " customer IDs from the database");
        System.out.println("Customer IDs: " + customerIds);
        System.out.println();

        for (String id : customerIds) {
            System.out.println(id);
            Customer customer = customerDao.getCustomer(id);
            if (customer != null) {
                System.out.println("SELECT * FROM " + CustomerDao.TABLE_NAME + " WHERE id = " + id);
                System.out.println(customer);
                System.out.println();
            }
        }
    }
}