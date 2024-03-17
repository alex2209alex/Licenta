package ro.unibuc.fmi.ge.service.pilotage_bulletin;

import ro.unibuc.fmi.ge.dto.pilotage_bulletin.PilotageBulletinListItemDto;
import ro.unibuc.fmi.ge.dto.pilotage_bulletin.PilotageBulletinSearchDto;

import java.util.List;

public interface PilotageBulletinFinderService {
    List<PilotageBulletinListItemDto> search(PilotageBulletinSearchDto searchDto);
}
