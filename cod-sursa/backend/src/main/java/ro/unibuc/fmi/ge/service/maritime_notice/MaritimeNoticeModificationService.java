package ro.unibuc.fmi.ge.service.maritime_notice;

import ro.unibuc.fmi.ge.dto.maritime_notice.MaritimeNoticeDto;

public interface MaritimeNoticeModificationService {
    void update(MaritimeNoticeDto maritimeNoticeDto);
}
