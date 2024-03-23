package ro.unibuc.fmi.ge.resource.pilotage_bulletin;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.fmi.ge.dto.GenericDto;
import ro.unibuc.fmi.ge.dto.pilotage_bulletin.PilotageBulletinDto;
import ro.unibuc.fmi.ge.dto.pilotage_bulletin.PilotageBulletinListItemDto;
import ro.unibuc.fmi.ge.dto.pilotage_bulletin.PilotageBulletinSearchDto;
import ro.unibuc.fmi.ge.service.pilotage_bulletin.PilotageBulletinAdditionService;
import ro.unibuc.fmi.ge.service.pilotage_bulletin.PilotageBulletinFinderService;
import ro.unibuc.fmi.ge.shared.RoleConstants;
import ro.unibuc.fmi.ge.shared.UserHelper;

import java.util.List;

@RestController
@RequestMapping("/buletine-pilotaj")
public class PilotageBulletinController {
    private final PilotageBulletinFinderService visualisationService;
    private final PilotageBulletinAdditionService additionService;
    private final UserHelper userHelper;


    public PilotageBulletinController(PilotageBulletinFinderService visualisationService, PilotageBulletinAdditionService additionService, UserHelper userHelper) {
        this.visualisationService = visualisationService;
        this.additionService = additionService;
        this.userHelper = userHelper;
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

    @PostMapping
    @PreAuthorize("hasAnyRole('" + RoleConstants.ROLE_AGENT_NAVA + "')")
    public void add(@RequestBody @Valid PilotageBulletinDto dto) {
        Long companyId = userHelper.getUserCompanyId();
        dto.setAgent(GenericDto.builder().id(companyId).build());
        additionService.add(dto);
    }
}
