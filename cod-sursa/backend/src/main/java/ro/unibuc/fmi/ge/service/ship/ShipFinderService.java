package ro.unibuc.fmi.ge.service.ship;

import ro.unibuc.fmi.ge.dto.ShipDto;

import java.util.List;

public interface ShipFinderService {
    List<ShipDto> findAll();
}
