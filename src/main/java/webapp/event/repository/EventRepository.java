package webapp.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webapp.event.pojo.Event;





@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

}
