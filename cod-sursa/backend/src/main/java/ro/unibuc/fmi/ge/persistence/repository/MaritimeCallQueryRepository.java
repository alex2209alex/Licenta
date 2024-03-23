package ro.unibuc.fmi.ge.persistence.repository;

import ro.unibuc.fmi.ge.dto.MaritimeCallDto;

import java.util.List;

public interface MaritimeCallQueryRepository {
    List<MaritimeCallDto> findAllThatCanHaveNewPilotageBulletin();
}
