package ebs.management.Service;

import ebs.management.Entity.Bill;
import ebs.management.Repository.BillRepository;
import ebs.management.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Bill addBill(Bill bill) {
        System.out.println("Checking for meter_no: " + bill.getMeterNo());

        // 1. Check if customer exists with the given meter_no
        boolean exists = customerRepository.existsByMeterNo(bill.getMeterNo());
        if (!exists) {
            throw new IllegalArgumentException("Customer with meter_no " + bill.getMeterNo() + " does not exist.");
        }

        // 2. Check if bill already exists for this meter_no and month
        boolean duplicate = billRepository.findByMeterNoAndMonth(bill.getMeterNo(), bill.getMonth()).isPresent();
        if (duplicate) {
            throw new IllegalArgumentException("Bill for meter_no " + bill.getMeterNo() + " and month " + bill.getMonth() + " already exists.");
        }

        // 3. Save new bill
        return billRepository.save(bill);
    }


    // 🆕 Get all bills
    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    // 🆕 Get bills by meter number
    public List<Bill> getBillsByMeterNo(int meterNo) {
        if (!customerRepository.existsByMeterNo(meterNo)) {
            throw new IllegalArgumentException("Customer with meter_no " + meterNo + " does not exist.");
        }
        return billRepository.findByMeterNo(meterNo);
    }
}
