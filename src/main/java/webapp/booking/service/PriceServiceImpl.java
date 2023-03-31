package webapp.booking.service;

import core.service.BasicService;
import java.time.LocalDate;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.stereotype.Component;
=======
import org.springframework.stereotype.Service;
>>>>>>> 4f56a669f32301d81d89e8be88f0f51552d150ac
import webapp.booking.dto.ReservationPriceDTO;
import webapp.booking.pojo.ReservationPrice;
import webapp.booking.repository.PriceRepository;

<<<<<<< HEAD
@Component
=======
@Service
>>>>>>> 4f56a669f32301d81d89e8be88f0f51552d150ac
public class PriceServiceImpl
    extends BasicService<PriceRepository, ReservationPrice, ReservationPriceDTO>
    implements PriceService {
    // TODO: sanity checker

    final ModelMapper modelMapper;
    final PriceRepository repository;

    @Autowired
    public PriceServiceImpl(ModelMapper modelMapper, PriceRepository repository) {
        super(modelMapper, repository);
        this.modelMapper = modelMapper;
        this.repository = repository;
    }

    public List<ReservationPriceDTO> findAllPrice() {
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
