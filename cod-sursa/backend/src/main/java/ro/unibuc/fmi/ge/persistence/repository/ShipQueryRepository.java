package ro.unibuc.fmi.ge.persistence.repository;

import ro.unibuc.fmi.ge.dto.ShipDto;

import java.util.List;

public interface ShipQueryRepository {
    List<ShipDto> getAll();
}
