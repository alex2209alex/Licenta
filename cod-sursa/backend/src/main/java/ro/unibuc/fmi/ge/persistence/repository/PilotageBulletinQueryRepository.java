package ro.unibuc.fmi.ge.persistence.repository;

import ro.unibuc.fmi.ge.dto.pilotage_bulletin.PilotageBulletinListItemDto;
import ro.unibuc.fmi.ge.dto.pilotage_bulletin.PilotageBulletinSearchDto;

import java.util.List;

public interface PilotageBulletinQueryRepository {
    List<PilotageBulletinListItemDto> search(PilotageBulletinSearchDto searchDto);
}
