package webapp.newbooking.service;

import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webapp.booking.dto.SeatDTO;
import webapp.booking.service.SeatService;
import webapp.newbooking.dto.BookingDTO;
import webapp.newbooking.pojo.newBooking;
import webapp.newbooking.repository.WriteBookingRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {
    @Autowired // DI
    private WriteBookingRepository writeBookingRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<BookingDTO> getAllBooking(){
        modelMapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.LOOSE);
        return writeBookingRepository.findAll()
            .stream()
            .map(this::EntityToDTO)
            .collect(Collectors.toList());
    }
    public List<BookingDTO> getOneBooking(Integer findno){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return writeBookingRepository.findById(findno)
                .stream()
                .map(this::EntityToDTO)
                .collect(Collectors.toList());
    }
    public List<BookingDTO> findbookingbymemno(Integer memno){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return writeBookingRepository.findbookingbymemno(memno)
                .stream()
                .map(this::EntityToDTO)
                .collect(Collectors.toList());
    }
    public BookingDTO insertBooking(@NotNull newBooking openNewBooking){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        System.out.println(openNewBooking.getBookingNo());
        System.out.println(openNewBooking.getBookingDate());
        int find =1;
        if(openNewBooking.getBookingNo()==0){
        while (true){
            try{
        if(writeBookingRepository.existsById(find)){
        System.out.println(find);
        find++;
        }
        else{
            break;}
        }catch(Exception e){
            break;
        }
        }
        openNewBooking.setBookingNo(find);
        }
        BookingDTO bookingDTO=EntityToDTO(openNewBooking);
        writeBookingRepository.save(openNewBooking);
        return null;
    }

    public BookingDTO updateBooking(@NotNull newBooking openNewBooking){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        System.out.println(openNewBooking.getBookingNo());
        System.out.println(openNewBooking.getBookingDate());
//        BookingDTO bookingDTO=EntityToDTO(openNewBooking);
       try{
       writeBookingRepository.save(openNewBooking);
       }catch (Exception e){
           System.out.println("錯誤");
       }
        return null;
    }
    private BookingDTO EntityToDTO(newBooking openNewBooking) {
        modelMapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.LOOSE);
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO = modelMapper.map(openNewBooking, BookingDTO.class);
        return bookingDTO;
    }

    public BookingDTO deleteBooking(Integer deleteno) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        writeBookingRepository.deleteById(deleteno);

        BookingDTO bookingDTO= new  BookingDTO();
        return  bookingDTO;
    }




    public Boolean updatepay(Integer pay, Integer memno) {
        newBooking newBooking=writeBookingRepository.getReferenceById(memno);
        if(pay!=0)
        {
            newBooking.setBookingPaymentStatus(0);
        }
        if (pay==0)
        { long miliseconds = System.currentTimeMillis();
            Date date = new Date(miliseconds);
            newBooking.setBookingPaymentStatus(1);
            newBooking.setBookingFinishDate(date);
        }
        writeBookingRepository.save(newBooking);
        return true;
    }
    public Boolean updatecheck(Integer check, Integer memno) {
        newBooking newBooking=writeBookingRepository.getReferenceById(memno);
        if(check!=0)
        {
            newBooking.setBookingCheckStatus(0);
        }
        if (check==0)
        {
            newBooking.setBookingCheckStatus(1);
        }
        writeBookingRepository.save(newBooking);
        return true;
    }
    @Autowired
    private SeatService seatservice;
    public  Integer minseat(String date, Integer minTime ,Integer maxTime){
        if(minTime>=maxTime)
        {  return -100;}
       List<Integer> Intergerlist= seatservice.findByDate(date);
        int minseat=100;
        for (int i=minTime-1;i<maxTime-1;i++){

    if(minseat>Intergerlist.get(i)){
        minseat=Intergerlist.get(i);
    }
    System.out.println(minseat);
        }
        return minseat;
    }
    public List<SeatDTO> saveseat(String date, Integer minTime ,Integer maxTime,Integer change){
        List<SeatDTO> seat=new ArrayList<>();
        for(int i=minTime;i<=maxTime;i++){
        seat.add(new SeatDTO(date,i,change));
       }
        seatservice.updateSeat(seat);
       return seat;
    }
}
