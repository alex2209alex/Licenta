package ro.unibuc.fmi.ge.service.maritime_notice.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.unibuc.fmi.ge.dto.maritime_notice.MaritimeNoticeDto;
import ro.unibuc.fmi.ge.dto.maritime_notice.MaritimeNoticeListItemDto;
import ro.unibuc.fmi.ge.dto.maritime_notice.MaritimeNoticeSearchDto;
import ro.unibuc.fmi.ge.persistence.repository.MaritimeNoticeQueryRepository;
import ro.unibuc.fmi.ge.service.maritime_notice.MaritimeNoticeFinderService;
import ro.unibuc.fmi.ge.shared.UserHelper;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MaritimeNoticeFinderServiceImpl implements MaritimeNoticeFinderService {
    private final UserHelper userHelper;
    private final MaritimeNoticeQueryRepository repository;

    public MaritimeNoticeFinderServiceImpl(UserHelper userHelper, MaritimeNoticeQueryRepository repository) {
        this.userHelper = userHelper;
        this.repository = repository;
    }

    @Override
    public List<MaritimeNoticeListItemDto> search(MaritimeNoticeSearchDto searchDto) {
        if(Boolean.TRUE.equals(userHelper.isAuthority())) {
            searchDto.setIsAuthority(Boolean.TRUE);
        } else  {
            searchDto.setIsAuthority(Boolean.FALSE);
            searchDto.setCompanyId(userHelper.getUserCompanyId());
        }
        return repository.search(searchDto);
    }

    @Override
    public Optional<MaritimeNoticeDto> findForAuthority(Long idMaritimeNotice) {
        return repository.findById(idMaritimeNotice);
    }

    @Override
    public Optional<MaritimeNoticeDto> findForAgent(Long idMaritimeNotice, Long idAgent) {
        return repository.findForAgent(idMaritimeNotice, idAgent);
    }
}
