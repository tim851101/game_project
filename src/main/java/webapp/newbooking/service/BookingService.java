package webapp.newbooking.service;

import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webapp.newbooking.dto.BookingDTO;
import webapp.newbooking.pojo.newBooking;
import webapp.newbooking.repository.WriteBookingRepository;

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
        while (true){try{
        if(writeBookingRepository.existsById(find)){
        System.out.println(find);
        find++;
        }
        else{
            find+=1;
            break;}
        }catch(Exception e){
            break;
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
}
