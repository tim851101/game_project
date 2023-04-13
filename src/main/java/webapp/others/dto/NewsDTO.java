package webapp.others.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Date;


@Data
public class NewsDTO {

    private String oldNewsTitle;

    @NotBlank(message = "消息標題不可為空")
    private String newsTitle;

    @NotBlank(message = "消息標題不可為空")
    private String newsDesc;


    @NotNull(message = "下架時間不可為空")
    @Future(message = "日期不能小於今天")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dueDate;

//    @DateValid
//    @NotNull(message = "下架時間不可為空")
//    @Min(1)
//    private Long seconds;
//
//    public void setSeconds(Long seconds) {
//        if (seconds <0) {
//            return;
//        }
//        this.seconds = seconds;
//    }
}
