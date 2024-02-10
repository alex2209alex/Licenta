package ro.unibuc.fmi.ge.persistence.repository;

import ro.unibuc.fmi.ge.dto.maritime_notice.MaritimeNoticeListItemDto;
import ro.unibuc.fmi.ge.dto.maritime_notice.MaritimeNoticeSearchDto;

import java.util.List;

public interface MaritimeNoticeQueryRepository {
    List<MaritimeNoticeListItemDto> search(MaritimeNoticeSearchDto searchDto);
}