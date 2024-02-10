package ro.unibuc.fmi.ge.resource.port;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.unibuc.fmi.ge.dto.GenericDto;
import ro.unibuc.fmi.ge.service.port.PortFinderService;

import java.util.List;

@RestController
@RequestMapping("/porturi")
public class PortController {
    private final PortFinderService finderService;

    public PortController(PortFinderService finderService) {
        this.finderService = finderService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<GenericDto> getAll() {
        return finderService.findAll();
    }
}
