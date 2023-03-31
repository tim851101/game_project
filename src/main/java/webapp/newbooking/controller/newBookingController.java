package webapp.newbooking.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import webapp.newbooking.dto.BookingDTO;
import webapp.newbooking.pojo.newBooking;
import webapp.newbooking.service.BookingService;

@RestController
@RequestMapping("/book")
@CrossOrigin(origins = "*")
public class newBookingController {

    @Autowired
    private BookingService BookingService;

    @GetMapping("/try")
    public String test(){
        return "aaa";
    }

    @GetMapping("/ls")
    public List<BookingDTO> getAllBooking(){
        return BookingService.getAllBooking();
    }

    @GetMapping("one")
    public List<BookingDTO> getOnebooking(Integer findno){
        return BookingService.getOneBooking(findno);
    }
    @PostMapping("addbooking")
    public  BookingDTO insertbooking(@RequestBody newBooking insertbook){
        try{
        System.out.println(insertbook.getBookingNo());
        return BookingService.insertBooking(insertbook);}
        catch (Exception e){
            System.out.println("錯誤");
        }
        return null;
    }
    @PostMapping("updatebooking")
    public  BookingDTO updatetbooking(@RequestBody newBooking insertbook){
        System.out.println(insertbook.getBookingNo());
        return BookingService.updateBooking(insertbook);}
    @PostMapping("deletebook")
    @ResponseBody
    public  BookingDTO deleteBooking(Integer deleteno){return  BookingService.deleteBooking(deleteno);}

    @GetMapping("redirect")
    public RedirectView redirectView(Integer changeno){
        System.out.println(changeno);
        String url;
        if(changeno==null){
         url="http://localhost:63342/CGA106G1/boardgameSpring/static/foreground/user_booking.html";
        }else{
         url="http://localhost:63342/CGA106G1/boardgameSpring/static/foreground/insertBooking.html";
        }
        return new RedirectView(url);
    }

}
