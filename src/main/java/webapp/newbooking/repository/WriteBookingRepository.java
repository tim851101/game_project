package webapp.newbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import webapp.newbooking.dto.BookingDTO;
import webapp.newbooking.pojo.newBooking;

import java.util.List;

@Repository
public interface WriteBookingRepository extends JpaRepository<newBooking, Integer> {
    // connection pool + similar to DAO(both support by extends Jpa repository)
    // program new DAO behavior here
    @Query(value = "SELECT * FROM boardgame.booking WHERE MEM_NO=:memno ", nativeQuery = true)
    List<newBooking> findbookingbymemno(@Param("memno") Integer memno);
}
