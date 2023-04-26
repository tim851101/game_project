package webapp.member.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class WishlistDTO {

    // 移除收藏or加入購物車使用
    private Integer pdNo;
    private String pdName;
    private Integer price;
    private Integer pdStock;
    private Integer picNo;

}
