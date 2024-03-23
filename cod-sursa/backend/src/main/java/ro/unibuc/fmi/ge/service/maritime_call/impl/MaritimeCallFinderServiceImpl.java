package ro.unibuc.fmi.ge.service.maritime_call.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.unibuc.fmi.ge.dto.MaritimeCallDto;
import ro.unibuc.fmi.ge.persistence.repository.MaritimeCallQueryRepository;
import ro.unibuc.fmi.ge.service.maritime_call.MaritimeCallFinderService;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MaritimeCallFinderServiceImpl implements MaritimeCallFinderService {

    private final MaritimeCallQueryRepository queryRepository;

    public MaritimeCallFinderServiceImpl(MaritimeCallQueryRepository queryRepository) {
        this.queryRepository = queryRepository;
    }

    @Override
    public List<MaritimeCallDto> findAllThatCanHaveNewPilotageBulletin() {
        return queryRepository.findAllThatCanHaveNewPilotageBulletin();
    }
}
