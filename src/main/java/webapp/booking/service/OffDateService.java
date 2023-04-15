package webapp.booking.service;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webapp.booking.dto.OffDateReqDTO;
import webapp.booking.dto.OffDateResDTO;
import webapp.booking.pojo.OffDate;
import webapp.booking.repository.OffDateRepository;

@Service
public class OffDateService {
    final private OffDateRepository offDateRepository;

    final private ModelMapper modelMapper;

    @Autowired
    public OffDateService(OffDateRepository offDateRepository, ModelMapper modelMapper) {
        this.offDateRepository = offDateRepository;
        this.modelMapper = modelMapper;
    }

    public Boolean saveAll(List<OffDateResDTO> dtos) {
        try {
            for (OffDateResDTO dto : dtos) {
                offDateRepository.save(modelMapper.map(dto, OffDate.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<OffDateResDTO> findAll(List<OffDateReqDTO> idList) {
        List<OffDateResDTO> offDateDTOS=new ArrayList<>();
        try {
            for (OffDateReqDTO reqDto : idList) {
                OffDateResDTO dto = new OffDateResDTO();
                dto.setId(reqDto.getId());
                dto.setDate(offDateRepository.findById(reqDto.getId()));
                offDateDTOS.add(dto);
            }
            return offDateDTOS;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
