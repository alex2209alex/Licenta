package ro.unibuc.fmi.ge.resource.company;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.unibuc.fmi.ge.dto.CompanySearchDto;
import ro.unibuc.fmi.ge.dto.GenericDto;
import ro.unibuc.fmi.ge.service.company.CompanyFinderService;

import java.util.List;

@RestController
@RequestMapping("/firme")
public class CompanyController {
    private final CompanyFinderService visualisationService;

    public CompanyController(CompanyFinderService visualisationService) {
        this.visualisationService = visualisationService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<GenericDto> search(CompanySearchDto searchDto) {
        return visualisationService.search(searchDto);
    }
}
