package ro.unibuc.fmi.ge.resource.maritime_notice;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.fmi.ge.dto.GenericDto;
import ro.unibuc.fmi.ge.dto.ResolutionDto;
import ro.unibuc.fmi.ge.dto.maritime_notice.MaritimeNoticeDto;
import ro.unibuc.fmi.ge.dto.maritime_notice.MaritimeNoticeListItemDto;
import ro.unibuc.fmi.ge.dto.maritime_notice.MaritimeNoticeSearchDto;
import ro.unibuc.fmi.ge.exceptions.NotFoundException;
import ro.unibuc.fmi.ge.service.maritime_notice.MaritimeNoticeAdditionService;
import ro.unibuc.fmi.ge.service.maritime_notice.MaritimeNoticeFinderService;
import ro.unibuc.fmi.ge.service.maritime_notice.MaritimeNoticeModificationService;
import ro.unibuc.fmi.ge.service.maritime_notice.MaritimeNoticeResolutionService;
import ro.unibuc.fmi.ge.shared.RoleConstants;
import ro.unibuc.fmi.ge.shared.UserHelper;

import java.util.List;

@RestController
@RequestMapping("/avizari-maritime")
public class MaritimeNoticeController {
    private final MaritimeNoticeFinderService visualisationService;
    private final MaritimeNoticeAdditionService additionService;
    private final MaritimeNoticeModificationService modificationService;
    private final MaritimeNoticeResolutionService maritimeNoticeResolutionService;
    private final UserHelper userHelper;

    public MaritimeNoticeController(
            MaritimeNoticeFinderService visualisationService,
            MaritimeNoticeAdditionService additionService,
            MaritimeNoticeModificationService modificationService, MaritimeNoticeResolutionService maritimeNoticeResolutionService,
            UserHelper userHelper) {
        this.visualisationService = visualisationService;
        this.additionService = additionService;
        this.modificationService = modificationService;
        this.maritimeNoticeResolutionService = maritimeNoticeResolutionService;
        this.userHelper = userHelper;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole("
            + "'" + RoleConstants.ROLE_AGENT_NAVA + "'"
            + ",'" + RoleConstants.ROLE_DISPECER_APMC + "'"
            + ",'" + RoleConstants.ROLE_DISPECER_ANR + "'"
            + ")")
    public List<MaritimeNoticeListItemDto> search(MaritimeNoticeSearchDto searchDto) {
        return visualisationService.search(searchDto);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('" + RoleConstants.ROLE_AGENT_NAVA + "')")
    public void add(@RequestBody @Valid MaritimeNoticeDto dto) {
        Long companyId = userHelper.getUserCompanyId();
        dto.setAgent(GenericDto.builder().id(companyId).build());
        additionService.add(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('" + RoleConstants.ROLE_AGENT_NAVA + "')")
    public void update(@PathVariable Long id, @RequestBody @Valid MaritimeNoticeDto dto) {
        dto.setId(id);
        modificationService.update(dto);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole("
            + "'" + RoleConstants.ROLE_AGENT_NAVA + "'"
            + ",'" + RoleConstants.ROLE_DISPECER_APMC + "'"
            + ",'" + RoleConstants.ROLE_DISPECER_ANR + "'"
            + ")")
    public MaritimeNoticeDto findById(@PathVariable Long id) {
        if (Boolean.TRUE.equals(userHelper.isAuthority())) {
            return visualisationService.findForAuthority(id).orElseThrow(NotFoundException::new);
        }
        return visualisationService.findForAgent(id, userHelper.getUserCompanyId()).orElseThrow(NotFoundException::new);
    }

    @PostMapping("/resolve/{id}")
    @PreAuthorize("hasAnyRole("
            + "'" + RoleConstants.ROLE_DISPECER_ANR + "'"
            + ",'" + RoleConstants.ROLE_DISPECER_APMC + "'"
            + ")")
    public void resolveMaritimeNotice(@PathVariable Long id, @RequestBody @Valid ResolutionDto dto) {
        maritimeNoticeResolutionService.resolveMaritimeNotice(dto, id);
    }
}
