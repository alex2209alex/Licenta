package ro.unibuc.fmi.ge.persistence.repository;

import ro.unibuc.fmi.ge.dto.GenericDto;

import java.util.List;

public interface PortQueryRepository {
    List<GenericDto> getAll();
}
