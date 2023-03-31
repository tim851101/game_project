package webapp.newbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webapp.newbooking.pojo.newBooking;

@Repository
public interface WriteBookingRepository extends JpaRepository<newBooking, Integer> {
    // connection pool + similar to DAO(both support by extends Jpa repository)
    // program new DAO behavior here
}
