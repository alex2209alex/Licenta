package ro.unibuc.fmi.ge.resource.maritime_call;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.unibuc.fmi.ge.dto.MaritimeCallDto;
import ro.unibuc.fmi.ge.service.maritime_call.MaritimeCallFinderService;

import java.util.List;

@RestController
@RequestMapping("/escale")
public class MaritimeCallController {
    private final MaritimeCallFinderService visualisationService;

    public MaritimeCallController(MaritimeCallFinderService visualisationService) {
        this.visualisationService = visualisationService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<MaritimeCallDto> findAllThatCanHaveNewPilotageBulletin() {
        return visualisationService.findAllThatCanHaveNewPilotageBulletin();
    }
}
