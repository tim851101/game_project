package webapp.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webapp.event.pojo.EventOrdVO;


@Repository
public interface EventOrdRepository extends JpaRepository<EventOrdVO, Integer> {
    // connection pool + similar to DAO(both support by extends Jpa repository)
    // program new DAO behavior here
}
