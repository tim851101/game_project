package webapp.booking.service;

import core.service.BasicService;
import java.time.LocalDate;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import webapp.booking.dto.ReservationPriceDTO;
import webapp.booking.pojo.ReservationPrice;
import webapp.booking.repository.PriceRepository;

@Service
public class PriceService
    extends BasicService<PriceRepository, ReservationPrice, ReservationPriceDTO> {
    // TODO: sanity checker

    final ModelMapper modelMapper;
    final PriceRepository repository;

    public PriceService(ModelMapper modelMapper, PriceRepository repository) {
        super(modelMapper, repository);
        this.modelMapper = modelMapper;
        this.repository = repository;
    }

    public List<ReservationPriceDTO> findAllPrice(){
        return getAllDTO();
    }

    public Boolean insertAllPrice(List<ReservationPriceDTO> dtoList) {
        try {
            insertAllDTO(dtoList);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Integer findPriceByDate(LocalDate date) {
        return repository.findPriceByDate(date);
    }
}
