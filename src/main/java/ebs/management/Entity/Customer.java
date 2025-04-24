package ebs.management.Entity;

import jakarta.persistence.*;
import java.util.Random;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @Column(name = "meter_no")
    private Integer meterNo;

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "address", length = 50)
    private String address;

    @Column(name = "city", length = 30)
    private String city;

    @Column(name = "state", length = 30)
    private String state;

    @Column(name = "email", length = 40)
    private String email;

    @Column(name = "phone")
    private Long phone;

    public Customer() {
        this.meterNo = generateMeterNo();
    }

    // Custom logic to generate 6-digit meter number
    private int generateMeterNo() {
        Random random = new Random();
        return 100000 + random.nextInt(900000); // generates between 100000 to 999999
    }

    // Getters and Setters
    public Integer getMeterNo() {
        return meterNo;
    }

    public void setMeterNo(Integer meterNo) {
        this.meterNo = meterNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }
}
