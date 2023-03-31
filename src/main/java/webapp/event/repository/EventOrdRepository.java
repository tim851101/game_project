package webapp.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import webapp.event.pojo.EventOrdVO;

import java.util.List;


@Repository
public interface EventOrdRepository extends JpaRepository<EventOrdVO, Integer> {
    // connection pool + similar to DAO(both support by extends Jpa repository)
    // program new DAO behavior here
    @Modifying
    @Query(value = "SELECT * FROM boardgame.event_ord WHERE EVENT_NO= :eventno", nativeQuery = true)
    List<EventOrdVO> selectmembyevent(@Param("eventno") Integer eventno);
}
