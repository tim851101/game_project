package webapp.others.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;


@Data
public class NewsDTO {

    @NotBlank(message = "消息標題不可為空")
    private String newsTitle;

    @NotBlank(message = "消息標題不可為空")
    private String newsDesc;


    @NotNull(message = "下架時間不可為空")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private Date dueDate;

//    @DateValid
    @NotNull(message = "下架時間不可為空")
    @Min(1)
    private Long seconds;

    public void setSeconds(Long seconds) {
        if (seconds <0) {
            return;
        }
        this.seconds = seconds;
    }
}
