package webapp.event.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import webapp.event.dto.EventOrdDTO;
import webapp.event.dto.memofeventDTO;
import webapp.event.pojo.EventOrdVO;
import webapp.event.service.EventOrdservice;


@RestController
@RequestMapping("/eventord")
@CrossOrigin(origins = "*")
public class EventOrdcontroller {

    @Autowired
    private EventOrdservice EventOrdservice;

    @GetMapping("/eve")
    //測試
    public String test(){
        return "aaa";
    }

    @GetMapping("/ls")  //查詢所有賽事(不分會員)
    @ResponseBody
    public List<EventOrdDTO> getAlleventOrd(){
        return EventOrdservice.getAllEventOrd();
    }
    @PostMapping("byeventformem")
    @ResponseBody
    public List<memofeventDTO> byeventformem(Integer memno){
        return EventOrdservice.findeventotmem(memno);
    }
    @PostMapping("byeventls") //查詢報名賽事的所有會員(傳入賽事編號)
    @ResponseBody
    public List<EventOrdDTO> getAllmembyevent( Integer Eventno){
        return EventOrdservice.getAllmembyevent(Eventno);
    }
    @PostMapping("updatevent")
    @ResponseBody
    //更新報到狀態(傳入會員編號，賽事編號，當前報到狀態)未報到執行後變成已報到，已報到執行後變成未報到
     public Boolean updatecheck( Integer memno,Integer eventno,Boolean check){
        EventOrdVO update =new EventOrdVO();
       return EventOrdservice.updatecheck(memno,eventno,check);
    }
    @PostMapping("updatepay")
    @ResponseBody
    //更新訂單狀態(傳入會員編號，賽事編號，當前報到狀態)未付款執行後變成完成訂單，完成訂單執行後變成取消訂單
    public Boolean paycheck( Integer memno,Integer eventno,Boolean check){
        EventOrdVO update =new EventOrdVO();
        return EventOrdservice.updatepay(memno,eventno,check);
    }
    @PostMapping("insert")
    //新增賽事訂單(賽事編號，會員編號，會員狀態，會員名稱，會員地址，會員Email，會員電話，會員狀態)
    public void insert(@RequestBody EventOrdVO eventOrdVO) {

        EventOrdservice.insertEventOrd(eventOrdVO);
    }

}
