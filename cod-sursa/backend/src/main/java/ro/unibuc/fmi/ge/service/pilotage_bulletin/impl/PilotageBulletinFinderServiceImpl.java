package ro.unibuc.fmi.ge.service.pilotage_bulletin.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.unibuc.fmi.ge.dto.pilotage_bulletin.PilotageBulletinListItemDto;
import ro.unibuc.fmi.ge.dto.pilotage_bulletin.PilotageBulletinSearchDto;
import ro.unibuc.fmi.ge.persistence.repository.PilotageBulletinQueryRepository;
import ro.unibuc.fmi.ge.service.pilotage_bulletin.PilotageBulletinFinderService;
import ro.unibuc.fmi.ge.shared.UserHelper;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PilotageBulletinFinderServiceImpl implements PilotageBulletinFinderService {
    private final UserHelper userHelper;
    private final PilotageBulletinQueryRepository repository;

    public PilotageBulletinFinderServiceImpl(UserHelper userHelper, PilotageBulletinQueryRepository repository) {
        this.userHelper = userHelper;
        this.repository = repository;
    }

    @Override
    public List<PilotageBulletinListItemDto> search(PilotageBulletinSearchDto searchDto) {
        if (Boolean.TRUE.equals(userHelper.isAgent())) {
            searchDto.setIsAgent(Boolean.TRUE);
            searchDto.setCompanyId(userHelper.getUserCompanyId());
        } else {
            searchDto.setIsAgent(Boolean.FALSE);
        }
        if (Boolean.TRUE.equals(userHelper.isPilotage())) {
            searchDto.setIsPilotage(Boolean.TRUE);
            searchDto.setCompanyId(userHelper.getUserCompanyId());
        } else {
            searchDto.setIsPilotage(Boolean.FALSE);
        }
        return repository.search(searchDto);
    }
}
