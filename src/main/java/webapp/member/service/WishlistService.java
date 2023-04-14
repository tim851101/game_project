package webapp.member.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import webapp.member.dto.CollectionDTO;
import webapp.member.dto.WishlistDTO;

import java.util.List;

@Component
public interface WishlistService {

    List<CollectionDTO>getAllByMemNo(Integer memNo);

    @Transactional
    String saveByMemNoAndPdNo(Integer memNo,Integer pdNo);

    @Transactional
    String deleteByMemNoAndPdNo(Integer memNo,Integer pdNo);

    List<WishlistDTO> findWishlistByMemNo(Integer memNo);
}
