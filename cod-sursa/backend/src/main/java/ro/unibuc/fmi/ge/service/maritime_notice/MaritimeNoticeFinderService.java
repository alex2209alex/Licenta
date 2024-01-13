package ro.unibuc.fmi.ge.service.maritime_notice;

import ro.unibuc.fmi.ge.dto.maritime_notice.MaritimeNoticeListItemDto;
import ro.unibuc.fmi.ge.dto.maritime_notice.MaritimeNoticeSearchDto;

import java.util.List;

public interface MaritimeNoticeFinderService {
    List<MaritimeNoticeListItemDto> search(MaritimeNoticeSearchDto searchDto);
}
