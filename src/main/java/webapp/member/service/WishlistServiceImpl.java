package webapp.member.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import webapp.member.dto.CollectionDTO;
import webapp.member.dto.WishlistDTO;
import webapp.member.pojo.Collection;
import webapp.member.pojo.Members;
import webapp.member.repository.WishlistRepository;
import webapp.product.pojo.Product;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistServiceImpl implements WishlistService{


    final WishlistRepository wishlistRepository;

    final ModelMapper modelMapper;

    public WishlistServiceImpl(WishlistRepository wishlistRepository,ModelMapper modelMapper) {
        this.wishlistRepository=wishlistRepository;
        this.modelMapper = modelMapper;
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
    }

    private WishlistDTO wishlistDTO;
    private Members members;
    private Product products;

    @Override
    public List<CollectionDTO> getAllByMemNo(Integer memNo) {
        return wishlistRepository.findAllByMemNo(memNo)
                .stream()
                .map(this::EntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public String saveByMemNoAndPdNo(Integer memNo,Integer pdNo) {
        CollectionDTO collection=new CollectionDTO();
        collection.setMemNo(memNo);
        collection.setPdNo(pdNo);
        if(!wishlistRepository.existsByPdNoAndMemNo(pdNo,memNo)){
            wishlistRepository.save(modelMapper.map(collection, Collection.class));
            return new Gson().toJson(Collections.singletonMap("message", "商品收藏成功"));
        }else {
            return new Gson().toJson(Collections.singletonMap("message", "商品已收藏"));
        }
    }

    @Override
    public String deleteByMemNoAndPdNo(Integer memNo,Integer pdNo) {
        CollectionDTO collection=new CollectionDTO();
        collection.setMemNo(memNo);
        collection.setPdNo(pdNo);
//        wishlistRepository.delete(collection);
        if (wishlistRepository.findByPdNoAndMemNo(pdNo,memNo)!=null){
            wishlistRepository.delete(modelMapper.map(collection, Collection.class));
            return new Gson().toJson(Collections.singletonMap("message", "取消收藏成功"));
        }
        return new Gson().toJson(Collections.singletonMap("message", "取消收藏失敗"));
    }

    @Override
    public List<WishlistDTO> findWishlistByMemNo(Integer memNo) {
        return wishlistRepository.findListByMemNo(memNo);
    }

    private CollectionDTO EntityToDTO(Collection collection) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        CollectionDTO collectionDTO = new CollectionDTO();
        collectionDTO = modelMapper.map(collection, CollectionDTO.class);
        return collectionDTO;
    }
}
