package webapp.member.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import webapp.member.dto.PermissionDTO;
import webapp.member.service.PermissionService;

@RestController
@RequestMapping("/mem")
public class PermissionController {
    // TODO: add sanity checker
    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/duration-by-times")
    public Integer findDurationByTimes(@RequestParam Integer times) {
        return permissionService.findDurationByTimes(times);
    }

    @GetMapping("/ls-perm-level")
    public List<PermissionDTO> findAllInfo() {
        return permissionService.findAllInfo();
    }

    @PostMapping("/save-all-perm-level")
    @ResponseBody
    public Boolean saveAllLevel(@RequestBody List<PermissionDTO> dtoList) {
        return permissionService.saveAllLevel(dtoList);
    }
}
