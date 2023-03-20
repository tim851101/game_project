package webapp.booking.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "RESERVATION_PRICE")
public class ReservationPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESERVATION_PRICE_NO")
    private Integer reservationPriceNo;

    @Column(name = "RESERVATION_PRICE", nullable = false)
    private Integer reservationPrice;

    @Column(name = "TIME_START")
    private LocalDate timeStart;

    @Column(name = "TIME_END")
    private LocalDate timeEnd;

}
