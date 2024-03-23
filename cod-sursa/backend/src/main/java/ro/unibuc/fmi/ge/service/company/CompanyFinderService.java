package ro.unibuc.fmi.ge.service.company;

import ro.unibuc.fmi.ge.dto.CompanySearchDto;
import ro.unibuc.fmi.ge.dto.GenericDto;

import java.util.List;

public interface CompanyFinderService {
    List<GenericDto> search(CompanySearchDto searchDto);
}
