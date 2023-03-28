package webapp.employee.service;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webapp.employee.pojo.Role;
import webapp.employee.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {

    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    public List<String> findAllRoles() {
        return roleRepository
            .findAll()
            .stream()
            .map(this::role2String)
            .collect(Collectors.toList());
    }

    private String role2String(Role role){
        return modelMapper.map(role.getRoleName(), String.class);
    }
}
