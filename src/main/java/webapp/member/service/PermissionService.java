package webapp.member.service;

import core.service.IntrinsicService;
import java.util.List;
import org.springframework.stereotype.Service;
import webapp.member.dto.PermissionDTO;

public interface PermissionService extends IntrinsicService<PermissionDTO> {

    List<PermissionDTO> findAllInfo();
    Boolean saveAllLevel(List<PermissionDTO> dtoList);
    Integer findDurationByTimes(Integer times);
}
