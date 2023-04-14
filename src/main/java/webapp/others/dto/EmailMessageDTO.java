package webapp.others.dto;

import lombok.Data;

@Data
public class EmailMessageDTO {
    private String name;
    private String from;
    private String subject;
    private String text;
}
