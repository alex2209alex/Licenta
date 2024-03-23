package ro.unibuc.fmi.ge.service.maritime_call;

import ro.unibuc.fmi.ge.dto.MaritimeCallDto;

import java.util.List;

public interface MaritimeCallFinderService {
    List<MaritimeCallDto> findAllThatCanHaveNewPilotageBulletin();
}
