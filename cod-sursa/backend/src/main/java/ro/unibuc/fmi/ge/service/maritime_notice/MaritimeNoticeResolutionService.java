package ro.unibuc.fmi.ge.service.maritime_notice;

import ro.unibuc.fmi.ge.dto.ResolutionDto;

public interface MaritimeNoticeResolutionService {
    void resolveMaritimeNotice(ResolutionDto dto, Long idMaritimeNotice);
}
