package ro.unibuc.fmi.ge.resource.cargo;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.unibuc.fmi.ge.dto.GenericDto;
import ro.unibuc.fmi.ge.service.cargo.CargoFinderService;

import java.util.List;

@RestController
@RequestMapping("/marfuri")
public class CargoController {
    private final CargoFinderService finderService;

    public CargoController(CargoFinderService finderService) {
        this.finderService = finderService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<GenericDto> getAll() {
        return finderService.findAll();
    }
}
