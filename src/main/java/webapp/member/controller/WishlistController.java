package webapp.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import webapp.member.dto.CollectionDTO;
import webapp.member.dto.WishlistDTO;
import webapp.member.service.WishlistService;

import java.util.List;

@Controller
@RequestMapping("/wish")
public class WishlistController {

    @Autowired
    private WishlistService wishlistServiceImpl;

    @GetMapping("/list-all")
    @ResponseBody
    public List<CollectionDTO> getAllByMemNode(@RequestParam Integer memNo){
        return wishlistServiceImpl.getAllByMemNo(memNo);
    }

    @GetMapping("/myList")
    @ResponseBody
    public List<WishlistDTO> findListByMemNo(Integer memNo){
        System.out.println("start....");
        System.out.println(wishlistServiceImpl.findWishlistByMemNo(memNo));
        return wishlistServiceImpl.findWishlistByMemNo(memNo);
    }

    @PostMapping("/delete-one")
    @ResponseBody
    public String deleteWishlistByPdNo(@RequestParam Integer memNo, @RequestParam Integer pdNo){
        return wishlistServiceImpl.deleteByMemNoAndPdNo(memNo,pdNo);
    }

    @PostMapping("/add-one")
    @ResponseBody
    public String addWishlistByPdNo(@RequestBody CollectionDTO collectionDTO){
        return wishlistServiceImpl.saveByMemNoAndPdNo(collectionDTO.getMemNo(), collectionDTO.getPdNo());
    }
}
