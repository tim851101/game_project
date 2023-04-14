package webapp.employee.pojo;

import java.util.Arrays;
import java.util.Objects;

public enum RoleEnum {
    /**
     * Wrong ID or Desc all go to Guest. Read-Only
     */
    ADMIN(1, "ADMIN"),
    USER(2, "MANAGER"),
    GUEST(3, "ENTRY");

    private Integer id;
    private String description;

    RoleEnum(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public static RoleEnum fromId(Integer id) {
        return Arrays.stream(RoleEnum.values())
            .filter(role -> Objects.equals(role.getId(), id))
            .findFirst()
            .orElse(RoleEnum.GUEST);
    }

    public static RoleEnum fromDescription(String description) {
        return Arrays.stream(RoleEnum.values())
            .filter(role -> Objects.equals(role.getDescription(), description))
            .findFirst()
            .orElse(RoleEnum.GUEST);
    }
}
