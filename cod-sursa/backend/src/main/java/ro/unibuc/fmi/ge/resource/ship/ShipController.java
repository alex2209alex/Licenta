package ro.unibuc.fmi.ge.resource.ship;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.unibuc.fmi.ge.dto.ShipDto;
import ro.unibuc.fmi.ge.service.ship.ShipFinderService;

import java.util.List;

@RestController
@RequestMapping("/nave")
public class ShipController {
    private final ShipFinderService finderService;

    public ShipController(ShipFinderService finderService) {
        this.finderService = finderService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<ShipDto> getAll() {
        return finderService.findAll();
    }
}
