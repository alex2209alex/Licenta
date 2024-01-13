package ro.unibuc.fmi.ge.service.port;

import ro.unibuc.fmi.ge.dto.GenericDto;

import java.util.List;

public interface PortFinderService {
    List<GenericDto> findAll();
}
