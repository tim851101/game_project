package webapp.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import webapp.member.dto.WishlistDTO;
import webapp.member.pojo.Collection;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Collection,Integer> {

    List<Collection> findAllByMemNo(Integer memNo);

    Boolean existsByPdNoAndMemNo(Integer pdNo,Integer memNo);
    Collection findByPdNoAndMemNo(Integer pdNo,Integer memNo);


    @Query(value =
        "SELECT new webapp.member.dto.WishlistDTO(" +
                "pd.pdNo, " +
                "pd.pdName, " +
                "pd.pdPrice, " +
                "pd.pdStock, " +
                "(SELECT pp.picNo FROM ProductPic pp WHERE pp.pdNo = pd.pdNo ORDER BY pp.picNo ASC LIMIT 1) AS picNo " +
                ") " +
                "FROM Product pd " +
                "JOIN Collection cl ON cl.pdNo = pd.pdNo " +
                "WHERE cl.memNo = :memNo AND pd.pdStatus = 1"
    )
    List<WishlistDTO> findListByMemNo(@Param("memNo") Integer memNo);

}
