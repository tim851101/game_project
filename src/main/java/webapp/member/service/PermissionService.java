package webapp.member.service;

import core.service.BasicService;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import webapp.member.dto.PermissionDTO;
import webapp.member.pojo.MemberPermission;
import webapp.member.repository.PermissionRepository;

@Service
public class PermissionService
    extends BasicService<PermissionRepository, MemberPermission, PermissionDTO> {


    final PermissionRepository permissionRepository;
    public PermissionService(ModelMapper modelMapper, PermissionRepository repository) {
        super(modelMapper, repository);
        this.permissionRepository = repository;
    }

    public List<PermissionDTO> findAllInfo(){
        return getAllDTO();
    }

    public Boolean saveAllLevel(List<PermissionDTO> dtoList) {
        try {
            insertAllDTO(dtoList);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Integer findDurationByTimes(Integer times){
        return repository.findDurationByTimes(times);
    }
}
