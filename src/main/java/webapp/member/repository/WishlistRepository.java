package webapp.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import webapp.member.dto.WishlistDTO;
import webapp.member.pojo.Collection;

import java.util.List;

//@SqlResultSetMapping(
//        name = "WishlistDTOMapping",
//        classes = @ConstructorResult(
//                targetClass = WishlistDTO.class,
//                columns = {
//                        @ColumnResult(name = "PD_PIC", type = Blob.class),
//                        @ColumnResult(name = "PD_NAME", type = String.class),
//                        @ColumnResult(name = "PD_PRICE", type = Integer.class),
//                        @ColumnResult(name = "PD_STOCK", type = Integer.class),
//                        @ColumnResult(name = "PD_NO", type = Integer.class)
//                }
//        )
//)
@Repository
public interface WishlistRepository extends JpaRepository<Collection,Integer> {

    List<Collection> findAllByMemNo(Integer memNo);

    Boolean existsByPdNoAndMemNo(Integer pdNo,Integer memNo);
    Collection findByPdNoAndMemNo(Integer pdNo,Integer memNo);

//    @Modifying
    @Query(value =
            "SELECT new webapp.member.dto.WishlistDTO(pd.pdNo, pd.pdName,pd.pdPrice, pd.pdStock,"+
            "(SELECT pp.pdPic FROM ProductPic pp "+
            "WHERE pp.pdNO = pd.pdNo ORDER BY pp.picNo ASC LIMIT 1) AS pdPic) "+
            "FROM Product pd JOIN Collection cl ON cl.pdNo = pd.pdNo "+
            "WHERE cl.memNo= :memNO AND pd.pdStatus = 1 "
    )
    List<WishlistDTO> findListByMemNo(@Param("memNO") Integer memNo);

}
