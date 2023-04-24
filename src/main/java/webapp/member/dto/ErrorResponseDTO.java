package webapp.member.dto;

import lombok.Data;

import java.util.List;
@Data
public class ErrorResponseDTO {
    private String status;
    private List<String> errors;

    public ErrorResponseDTO(String status, List<String> errors) {
        this.status = status;
        this.errors = errors;
    }

    // getters and setters
}