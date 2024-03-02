package ro.unibuc.fmi.ge.service.maritime_notice;

import ro.unibuc.fmi.ge.dto.maritime_notice.MaritimeNoticeDto;
import ro.unibuc.fmi.ge.dto.maritime_notice.MaritimeNoticeListItemDto;
import ro.unibuc.fmi.ge.dto.maritime_notice.MaritimeNoticeSearchDto;

import java.util.List;
import java.util.Optional;

public interface MaritimeNoticeFinderService {
    List<MaritimeNoticeListItemDto> search(MaritimeNoticeSearchDto searchDto);

    Optional<MaritimeNoticeDto> findForAuthority(Long idMaritimeNotice);

    Optional<MaritimeNoticeDto> findForAgent(Long idMaritimeNotice, Long idAgent);
}
