package ebs.management.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "login")
public class Login {

    @Id
    private int meter_no;  // Primary Key
    private String username;
    private String name;
    private String password;

    // Constructors
    public Login() {
    }

    public Login(int meter_no, String username, String name, String password) {
        this.meter_no = meter_no;
        this.username = username;
        this.name = name;
        this.password = password;
    }

    // Getters and Setters
    public int getMeter_no() {
        return meter_no;
    }

    public void setMeter_no(int meter_no) {
        this.meter_no = meter_no;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
