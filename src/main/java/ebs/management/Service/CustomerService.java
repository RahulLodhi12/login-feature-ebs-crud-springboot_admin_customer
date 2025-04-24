package ebs.management.Service;

import ebs.management.Entity.Customer;
import ebs.management.Entity.Login;
import ebs.management.Repository.CustomerRepository;
import ebs.management.Repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LoginRepository loginRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // ✅ Add a customer (includes login logic)
    public Map<String, String> addCustomer(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);

        String customerName = savedCustomer.getName();
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be null or empty");
        }

        // Generate username and password
        String username = savedCustomer.getName().toLowerCase().replaceAll("\\s+", "") + savedCustomer.getMeterNo();
        String password = generateRandomPassword(savedCustomer.getMeterNo());

        // Save login details
        Login login = new Login(
                savedCustomer.getMeterNo(),
                username,
                savedCustomer.getName(),
                password
        );
        loginRepository.save(login);

        // Return response
        Map<String, String> response = new HashMap<>();
        response.put("message", "Customer added successfully");
        response.put("username", username);
        response.put("password", password);
        return response;
    }

    // Helper: Generate random password
    private String generateRandomPassword(int meterNo) {
        return "pw@" + (meterNo % 10000); // you can improve this logic
    }

    // Delete a customer by meter_no
    public String deleteCustomer(int meterNo) {
        if (customerRepository.existsById(meterNo)) {
            customerRepository.deleteById(meterNo);
            return "Customer with meter no " + meterNo + " deleted successfully.";
        } else {
            return "Customer with meter no " + meterNo + " not found.";
        }
    }

    // Update customer details
    public Customer updateCustomer(int meterNo, Customer customer) {
        Optional<Customer> existing = customerRepository.findById(meterNo);

        if (existing.isPresent()) {
            Customer updated = existing.get();
            updated.setName(customer.getName());
            updated.setAddress(customer.getAddress());
            updated.setCity(customer.getCity());
            updated.setState(customer.getState());
            updated.setEmail(customer.getEmail());
            updated.setPhone(customer.getPhone());

            return customerRepository.save(updated);
        } else {
            return null;
        }
    }

    // Search customer by meter_no
    public Optional<Customer> getCustomerByMeterNo(int meterNo) {
        return customerRepository.findById(meterNo);
    }
}
