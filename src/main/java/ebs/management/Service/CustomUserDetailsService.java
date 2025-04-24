package ebs.management.Service;

import ebs.management.Entity.Customer;
import ebs.management.Entity.Login;
import ebs.management.Repository.CustomerRepository;
import ebs.management.Repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LoginRepository loginRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Handle hardcoded admin
        if (username.equals("admin")) {
            return new User(
                    "admin",
                    "{noop}password123",
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );
        }

        // Handle customer from DB
        Optional<Login> loginOpt = loginRepository.findByUsername(username);

        if (loginOpt.isPresent()) {
            Login login = loginOpt.get(); // Get the Login object safely

            return new User(
                    login.getUsername(),
                    "{noop}"+login.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_CUSTOMER"))
            );
        } else {
            // If login is not found, return null or throw an exception depending on your use case
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
