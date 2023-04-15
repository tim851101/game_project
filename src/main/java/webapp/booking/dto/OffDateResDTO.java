package webapp.booking.dto;

import java.util.List;
import lombok.Data;

@Data
public class OffDateResDTO {
    private String id; // vacation type
    private List<String> date;
}
