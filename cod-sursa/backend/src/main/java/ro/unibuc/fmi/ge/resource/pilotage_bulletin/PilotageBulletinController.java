package ro.unibuc.fmi.ge.resource.pilotage_bulletin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.unibuc.fmi.ge.dto.pilotage_bulletin.PilotageBulletinListItemDto;
import ro.unibuc.fmi.ge.dto.pilotage_bulletin.PilotageBulletinSearchDto;
import ro.unibuc.fmi.ge.service.pilotage_bulletin.PilotageBulletinFinderService;
import ro.unibuc.fmi.ge.shared.RoleConstants;

import java.util.List;

@RestController
@RequestMapping("/buletine-pilotaj")
public class PilotageBulletinController {
    private final PilotageBulletinFinderService visualisationService;

    public PilotageBulletinController(PilotageBulletinFinderService visualisationService) {
        this.visualisationService = visualisationService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole("
            + "'" + RoleConstants.ROLE_AGENT_NAVA + "'"
            + ",'" + RoleConstants.ROLE_DISPECER_APMC + "'"
            + ",'" + RoleConstants.ROLE_DISPECER_ANR + "'"
            + ",'" + RoleConstants.ROLE_DISPECER_PILOTAJ + "'"
            + ")")
    public List<PilotageBulletinListItemDto> search(PilotageBulletinSearchDto searchDto) {
        return visualisationService.search(searchDto);
    }
}
