package webapp.event.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webapp.event.dto.EventOrdDTO;
import webapp.event.dto.memofeventDTO;
import webapp.event.pojo.EventOrdVO;
import webapp.event.repository.EventOrdRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventOrdservice {
    @Autowired // DI
    private EventOrdRepository EventOrdRepository;
    @Autowired
    private ModelMapper modelMapper;
    private EventOrdDTO EntityToDTO(EventOrdVO openEventOrd) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        EventOrdDTO eventOrdDTO = new EventOrdDTO();
        eventOrdDTO = modelMapper.map(openEventOrd, EventOrdDTO.class);
        return eventOrdDTO;
    }
    public List<EventOrdDTO> getAllEventOrd(){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return EventOrdRepository.findAll()
                .stream()
                .map(this::EntityToDTO)
                .collect(Collectors.toList());
    }
    public List<EventOrdDTO> getAllmembyevent(Integer eventNO){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return EventOrdRepository.selectmembyevent(eventNO)
                .stream()
                .map(this::EntityToDTO)
                .collect(Collectors.toList());
    }
    public Boolean updatecheck(Integer memno,Integer eventno,Boolean check){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        EventOrdVO updateCH;
        updateCH=EventOrdRepository.findByEventnoAndMemNo(eventno,memno);
        if (check){
            updateCH.setMemChecked(0);
            EventOrdRepository.save(updateCH);
        System.out.println(updateCH);
        return false;
        }
        else {
            updateCH.setMemChecked(1);
            EventOrdRepository.save(updateCH);
        System.out.println(updateCH);
        return  true;
        }
    }
    public Boolean updatepay(Integer memno,Integer eventno,Boolean check){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        EventOrdVO updateCH;
        updateCH=EventOrdRepository.findByEventnoAndMemNo(eventno,memno);
        if (check){
            updateCH.setEnevtStatus(2);
            EventOrdRepository.save(updateCH);
            System.out.println(updateCH);
            return false;
        }
        else {
            updateCH.setEnevtStatus(1);
            EventOrdRepository.save(updateCH);
            System.out.println(updateCH);
            return  true;
        }
    }
    public void insertEventOrd(EventOrdVO dto) {
        EventOrdRepository.save(dto);
    }
    public List<memofeventDTO> findeventotmem(Integer memno)
    {
      return  EventOrdRepository.findAllBymemno(memno);
    }
}
