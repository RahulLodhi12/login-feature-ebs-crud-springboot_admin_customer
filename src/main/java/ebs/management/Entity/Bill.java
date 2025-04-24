package ebs.management.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "bill")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int meterNo;
    private String month;
    private int unitsConsumed;
    private double amount;

    // Constructors
    public Bill() {}

    public Bill(int meterNo, String month, int unitsConsumed, double amount) {
        this.meterNo = meterNo;
        this.month = month;
        this.unitsConsumed = unitsConsumed;
        this.amount = amount;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMeterNo() {
        return meterNo;
    }

    public void setMeterNo(int meterNo) {
        this.meterNo = meterNo;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getUnitsConsumed() {
        return unitsConsumed;
    }

    public void setUnitsConsumed(int unitsConsumed) {
        this.unitsConsumed = unitsConsumed;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
