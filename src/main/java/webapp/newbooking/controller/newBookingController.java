package webapp.newbooking.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import webapp.newbooking.dto.BookingDTO;
import webapp.newbooking.image.image;
import webapp.newbooking.pojo.newBooking;
import webapp.newbooking.service.BookingService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

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
    @GetMapping(value="oneimage", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getimagebooking(Integer findno) throws IOException {
        OutputStream out = null;
        File file = new File("src/main/resources/static/background/static/image/img1.jpg");
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        return bytes;
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
    @Resource
    private image Toimage;
    @GetMapping("/image")
    public void download(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws ServletException, IOException {
            File file = new File("src/main/resources/static/background/static/image/img1.jpg");
            httpServletRequest.setAttribute(image.ATTRIBUTE_FILE, file);
//        Toimage.handleRequest(httpServletRequest, httpServletResponse);
    }
}
