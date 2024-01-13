package ro.unibuc.fmi.ge.service.port.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.unibuc.fmi.ge.dto.GenericDto;
import ro.unibuc.fmi.ge.persistence.repository.PortQueryRepository;
import ro.unibuc.fmi.ge.service.port.PortFinderService;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PortFinderServiceImpl implements PortFinderService {
    private final PortQueryRepository queryRepository;

    public PortFinderServiceImpl(PortQueryRepository queryRepository) {
        this.queryRepository = queryRepository;
    }

    @Override
    public List<GenericDto> findAll() {
        return queryRepository.getAll();
    }
}
