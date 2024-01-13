package ro.unibuc.fmi.ge.service.ship.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.unibuc.fmi.ge.dto.ShipDto;
import ro.unibuc.fmi.ge.persistence.repository.ShipQueryRepository;
import ro.unibuc.fmi.ge.service.ship.ShipFinderService;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ShipFinderServiceImpl implements ShipFinderService {
    private final ShipQueryRepository queryRepository;

    public ShipFinderServiceImpl(ShipQueryRepository queryRepository) {
        this.queryRepository = queryRepository;
    }

    @Override
    public List<ShipDto> findAll() {
        return queryRepository.getAll();
    }
}
