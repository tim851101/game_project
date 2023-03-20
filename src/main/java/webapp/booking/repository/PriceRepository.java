package webapp.booking.repository;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import webapp.booking.pojo.ReservationPrice;

@Repository
public interface PriceRepository extends JpaRepository<ReservationPrice, Integer> {
    @Query("SELECT rp.reservationPrice " +
        "FROM ReservationPrice rp " +
        "WHERE :date BETWEEN rp.timeStart AND rp.timeEnd")
    Integer findPriceByDate(@Param("date") LocalDate date);
}
