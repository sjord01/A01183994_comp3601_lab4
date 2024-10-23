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
                System.out.println("SELECT * FROM S456_Customers WHERE id = " + id);
                System.out.printf("Customer [id=%s, firstName=%s, lastName=%s, street=%s, city=%s, postalCode=%s,\n" +
                                  "phone=%s, emailAddress=%s, joinedDate=%s]\n\n",
                    customer.getId(), customer.getFirstName(), customer.getLastName(),
                    customer.getStreetName(), customer.getCity(), customer.getPostalCode(),
                    customer.getPhone(), customer.getEmail(), customer.getJoinDate());
            }
        }
    }
}