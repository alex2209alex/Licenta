package ro.unibuc.fmi.ge.resource.maritime_notice;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.unibuc.fmi.ge.dto.maritime_notice.MaritimeNoticeListItemDto;
import ro.unibuc.fmi.ge.dto.maritime_notice.MaritimeNoticeSearchDto;
import ro.unibuc.fmi.ge.service.maritime_notice.MaritimeNoticeFinderService;

import java.util.List;

@RestController
@RequestMapping("/avizari-maritime")
public class MaritimeNoticeController {
    private final MaritimeNoticeFinderService visualisationService;

    public MaritimeNoticeController(MaritimeNoticeFinderService visualisationService) {
        this.visualisationService = visualisationService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_AGENT_NAVA')")
    public List<MaritimeNoticeListItemDto> search(MaritimeNoticeSearchDto searchDto) {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return visualisationService.search(searchDto);
    }
}
