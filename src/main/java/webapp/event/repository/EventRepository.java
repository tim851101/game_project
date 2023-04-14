package webapp.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import webapp.event.pojo.Event;



@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Event e set e.eventWinner1 = :eventWinner1, e.eventWinner2 = :eventWinner2 , e.eventWinner3 = :eventWinner3, e.eventStatus = :eventStatus WHERE e.eventNo = :eventNo")
    void updateEventWinners(@Param("eventWinner1") String eventWinner1, @Param("eventWinner2") String eventWinner2, @Param("eventWinner3") String eventWinner3, @Param("eventStatus") Byte eventStatus, @Param("eventNo") Integer eventNo);



    @Modifying
    @Transactional
    @Query("UPDATE Event e SET e.signupNum = e.signupNum + :count WHERE e.eventNo = :eventNo")
    void updateEventSignupNum(@Param("count") Integer count, @Param("eventNo") Integer eventNo);



    @Query("SELECT e.signupNum FROM Event e WHERE e.eventNo = :eventNo")
    Integer getEventSignupNum(@Param("eventNo") Integer eventNo);

    @Query("SELECT e.eventLimit FROM Event e WHERE e.eventNo = :eventNo")
    Integer getEventLimit(@Param("eventNo") Integer eventNo);

    @Modifying
    @Transactional
    @Query("UPDATE Event e SET e.eventStatus = :eventStatus, e.eventLimit = :eventLimit, e.signupNum = :signupNum WHERE e.eventNo = :eventNo")
    void setEventStatus(@Param("eventStatus") Byte eventStatus, @Param("eventLimit") Integer eventLimit, @Param("signupNum") Integer signupNum, @Param("eventNo") Integer eventNo);
}
