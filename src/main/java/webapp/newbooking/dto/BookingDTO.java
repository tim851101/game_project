package webapp.newbooking.dto;

import lombok.Data;

import java.sql.Date;
import java.sql.Time;

@Data
public class BookingDTO {
    private int bookingNo;
    private int memNo;
    private Date bookingDate;
    private Time bookingStartTime;
    private Time bookingEndTime;
    private int bookingPaymentStatus;
    private int bookingCheckStatus;
    private Date bookingFinishDate;
    private int  bookingTotalPrice;
    private int  bookingPeople;

}



