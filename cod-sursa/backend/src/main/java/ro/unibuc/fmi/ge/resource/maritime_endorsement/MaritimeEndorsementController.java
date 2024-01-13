package ro.unibuc.fmi.ge.resource.maritime_endorsement;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.unibuc.fmi.ge.dto.maritime_endorsement.MaritimeEndorsementListItemDto;
import ro.unibuc.fmi.ge.dto.maritime_endorsement.MaritimeEndorsementSearchDto;
import ro.unibuc.fmi.ge.service.maritime_endorsement.MaritimeEndorsementVisualisationService;

import java.util.List;

@RestController
@RequestMapping("/avizari-maritime")
public class MaritimeEndorsementController {
    private final MaritimeEndorsementVisualisationService visualisationService;

    public MaritimeEndorsementController(MaritimeEndorsementVisualisationService visualisationService) {
        this.visualisationService = visualisationService;
    }

    @GetMapping
    //@PreAuthorize("hasAnyRole('ROLE_AGENT_NAVA')")
    public List<MaritimeEndorsementListItemDto> search(MaritimeEndorsementSearchDto searchDto) {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return visualisationService.search(searchDto);
    }
}
