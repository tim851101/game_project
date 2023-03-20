package webapp.member.dto;

import lombok.Data;

@Data
public class PermissionDTO {

    private Integer permissionsNo;
    private Integer duration;
    private Integer lowerLimit;
    private Integer upperLimit;
}
