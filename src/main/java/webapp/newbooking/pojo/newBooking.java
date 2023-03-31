package webapp.newbooking.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@Data // Lombok: Gene getter/setter by @getter/@setter
@AllArgsConstructor // Lombok: Auto gene args constructor
@NoArgsConstructor // Lombok: read above
@Entity // JPA: mark this class as entity
@Table(name = "BOOKING")// JPA: specify the table this class mapping to
public class newBooking {

    //    @GeneratedValue(strategy = GenerationType.IDENTITY) // AI
    @Id // JPA: primary key
    @Column(name = "BOOKING_NO")// JPA: map to column
    private int bookingNo;

    @Column(name = "MEM_NO")
    private int memNo;

    @Column(name = "BOOKING_DATE")
    private Date bookingDate;


    @Column(name = "BOOKING_START_TIME")
    private Time bookingStartTime;

    @Column(name = "BOOKING_END_TIME")
    private Time bookingEndTime;

    @Column(name = "BOOKING_PAYMENT_STATUS")
    private int bookingPaymentStatus;

    @Column(name = "BOOKING_CHECK_STATUS")
    private int bookingCheckStatus;


    @Column(name = "BOOKING_FINISH_DATE")
    private Date bookingFinishDate;

    @Column(name = "BOOKING_TOTAL_PRICE")
    private int  bookingTotalPrice;

    @Column(name = "BOOKING_PEOPLE")
    private int  bookingPeople;

}