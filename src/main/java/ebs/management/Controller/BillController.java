package ebs.management.Controller;

import ebs.management.Entity.Bill;
import ebs.management.Service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BillController {

    @Autowired
    private BillService billService;

    @PostMapping("/admin/add-bill")
    public ResponseEntity<Bill> addBill(@RequestBody Bill bill) {
        Bill savedBill = billService.addBill(bill);
        return ResponseEntity.ok(savedBill);
    }

    // 🆕 View all bills
    @GetMapping("/admin/viewAll-bills")
    public List<Bill> getAllBills() {
        return billService.getAllBills();
    }

    // 🆕 Get bills by meter number
    @GetMapping("/admin/bill-meter/{meterNo}")
    public List<Bill> getBillsByMeterNo(@PathVariable int meterNo) {
        return billService.getBillsByMeterNo(meterNo);
    }
}
