package ro.unibuc.fmi.ge.service.cargo.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.unibuc.fmi.ge.dto.GenericDto;
import ro.unibuc.fmi.ge.persistence.repository.CargoQueryRepository;
import ro.unibuc.fmi.ge.service.cargo.CargoFinderService;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CargoFinderServiceImpl implements CargoFinderService {
    private final CargoQueryRepository queryRepository;

    public CargoFinderServiceImpl(CargoQueryRepository queryRepository) {
        this.queryRepository = queryRepository;
    }

    @Override
    public List<GenericDto> findAll() {
        return queryRepository.getAll();
    }
}
