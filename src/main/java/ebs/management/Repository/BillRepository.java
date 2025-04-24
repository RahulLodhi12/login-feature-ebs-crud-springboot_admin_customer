package ebs.management.Repository;

import ebs.management.Entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findByMeterNo(int meterNo);

    // 🆕 Check if a bill already exists for a specific meter number and month
    Optional<Bill> findByMeterNoAndMonth(int meterNo, String month);

}
