package ebs.management.Controller;

import ebs.management.Entity.Bill;
import ebs.management.Entity.Customer;
import ebs.management.Entity.Login;
import ebs.management.Repository.BillRepository;
import ebs.management.Repository.LoginRepository;
import ebs.management.Service.CustomerService;
import ebs.management.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private BillRepository billRepository;

    // 📃 Get All Customers
    @GetMapping("/admin/all-customers")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }


    // ➕ Add Customer
    @PostMapping("/admin/add-customer")
    public ResponseEntity<Map<String, String>> addCustomer(@RequestBody Customer customer) {
        Map<String, String> result = customerService.addCustomer(customer);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }


    // 🔄 Update Customer
    @PutMapping("/admin/update/{meterNo}")
    public Customer updateCustomer(@PathVariable int meterNo, @RequestBody Customer customer) {
        return customerService.updateCustomer(meterNo,customer);
    }

    // 🔍 Get Customer by meter_no
    @GetMapping("/admin/{meterNo}")
    public Customer getCustomer(@PathVariable int meterNo) {
        return customerService.getCustomerByMeterNo(meterNo)
                .orElse(null); // or throw a custom exception if needed
    }

    // ❌ Delete Customer by meter_no
    @DeleteMapping("/admin/delete/{meterNo}")
    public String deleteCustomer(@PathVariable int meterNo) {
        return customerService.deleteCustomer(meterNo);
    }

    @Autowired
    private JwtUtil jwtUtil;

    // 📃 Get Bills by Meter Number - Customer
    @GetMapping("/customer/bills/{meterNo}")
    public ResponseEntity<?> getBillsByMeterNo(@PathVariable int meterNo, @RequestHeader("Authorization") String token) {
        // Validate token and extract username
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(403).body("Token missing or invalid");
        }

        String jwtToken = token.substring(7); // Remove 'Bearer ' prefix
        String username = jwtUtil.extractUsername(jwtToken); // Extract username from JWT token

        Optional<Login> login = loginRepository.findByUsername(username);
        if (login.isPresent() && login.get().getMeter_no() == meterNo) {
            // If the customer is authorized to view bills for this meter number
            List<Bill> bills = billRepository.findByMeterNo(meterNo);
            return ResponseEntity.ok(bills);
        } else {
            return ResponseEntity.status(403).body("You are not authorized to view these bills.");
        }
    }

    // 📃 Get Bills by Meter Number - Admin
    @GetMapping("/admin/bills/{meterNo}")
    public ResponseEntity<?> getBillsByMeterNos(@PathVariable int meterNo, @RequestHeader("Authorization") String token) {
        // Validate token and extract username
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(403).body("Token missing or invalid");
        }

        String jwtToken = token.substring(7); // Remove 'Bearer ' prefix
        String username = jwtUtil.extractUsername(jwtToken); // Extract username from JWT token

        Optional<Login> login = loginRepository.findByUsername(username);
        if (login.isPresent() && login.get().getMeter_no() == meterNo) {
            // If the customer is authorized to view bills for this meter number
            List<Bill> bills = billRepository.findByMeterNo(meterNo);
            return ResponseEntity.ok(bills);
        } else {
            return ResponseEntity.status(403).body("You are not authorized to view these bills.");
        }
    }
}
