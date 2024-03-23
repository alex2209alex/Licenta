package ro.unibuc.fmi.ge.service.company.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.unibuc.fmi.ge.dto.CompanySearchDto;
import ro.unibuc.fmi.ge.dto.GenericDto;
import ro.unibuc.fmi.ge.persistence.repository.CompanyQueryRepository;
import ro.unibuc.fmi.ge.service.company.CompanyFinderService;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CompanyFinderServiceImpl implements CompanyFinderService {
    private final CompanyQueryRepository queryRepository;

    public CompanyFinderServiceImpl(CompanyQueryRepository queryRepository) {
        this.queryRepository = queryRepository;
    }

    @Override
    public List<GenericDto> search(CompanySearchDto searchDto) {
        return queryRepository.search(searchDto);
    }
}
