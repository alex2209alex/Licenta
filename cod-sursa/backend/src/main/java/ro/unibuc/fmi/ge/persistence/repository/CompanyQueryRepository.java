package ro.unibuc.fmi.ge.persistence.repository;

import ro.unibuc.fmi.ge.dto.CompanySearchDto;
import ro.unibuc.fmi.ge.dto.GenericDto;

import java.util.List;

public interface CompanyQueryRepository {
    List<GenericDto> search(CompanySearchDto searchDto);
}
