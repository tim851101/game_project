package webapp.event.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webapp.event.dto.EventDTO;
import webapp.event.service.EventServiceImpl;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/event")
public class EventController {
    private final EventServiceImpl eventServiceImpl;

    @Autowired
    public EventController(EventServiceImpl eventServiceImpl) {

        this.eventServiceImpl = eventServiceImpl;
    }


    // 創建賽事。
    // 參數:eventStatus(輸入0:未完賽), eventName, eventDisc, eventDate, eventStarttime, eventEndtime, eventLimit, signupNum, eventFee, signupStartTime, signupDeadline
    @PostMapping("/save-event")
    @ResponseBody
    public Boolean insertAllEvent(@RequestBody EventDTO eventDTO){
         return eventServiceImpl.insert(eventDTO);
    }

    // 查詢全賽事。
    // 參數:無
    @GetMapping("/ls-event")
    public List<EventDTO> getAllEvent() {
        return eventServiceImpl.getAllEvent();
    }

    // 更新冠亞季軍及賽事狀態完賽(輸入1:已完賽)。
    // 參數: Integer eventNo, String eventWinner1, String eventWinner2, String eventWinner3
    @PostMapping("/updateWinners")
    @ResponseBody
    public Boolean updateWinner(@RequestBody EventDTO eventDTO){
        return eventServiceImpl.updateWinner(eventDTO);
    }

    // 報名人數+1。
    // 參數: Integer eventNo，人數到達上限不在加入，加入回傳true，無法加入回傳false
    @PostMapping("/addSignupNum")
    @ResponseBody
    public Boolean updateSignupNum(@RequestBody EventDTO eventDTO){
        return eventServiceImpl.updateSignupNum(eventDTO);
    }

    // 報名人數-1。
    // 參數: Integer eventNo，人數減到剩0，減掉回傳true，無法在減回傳false
    @PostMapping("/delSignupNum")
    @ResponseBody
    public Boolean cancelSignupNum(@RequestBody EventDTO eventDTO){
        return eventServiceImpl.cancelSignupNum(eventDTO);
    }

    // 查詢該賽事報名上數人數。
    // 參數: Integer eventNo，回傳該場賽事上限人數
    @PostMapping("/selectEventLimit")
    @ResponseBody
    public Integer selectEventLimit(@RequestBody EventDTO eventDTO){
        return eventServiceImpl.selectEventLimit(eventDTO);
    }

    //設定賽事狀態。
    //上限人數、報名人數。參數: Integer eventNo(0:未完賽/1:已完賽/2:取消賽事), Byte eventStatus, Integer eventLimit, Integer signupNum
    @PostMapping("/setStatus")
    @ResponseBody
    public Boolean updateEventStatus(@RequestBody EventDTO eventDTO){
        return eventServiceImpl.updateEventStatus(eventDTO);
    }
}
