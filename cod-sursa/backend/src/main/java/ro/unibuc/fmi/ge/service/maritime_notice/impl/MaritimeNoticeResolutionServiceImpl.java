package ro.unibuc.fmi.ge.service.maritime_notice.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.unibuc.fmi.ge.dto.MaritimeNoticeDocumentStatus;
import ro.unibuc.fmi.ge.dto.Resolution;
import ro.unibuc.fmi.ge.dto.ResolutionDto;
import ro.unibuc.fmi.ge.exceptions.ForbiddenException;
import ro.unibuc.fmi.ge.exceptions.NotFoundException;
import ro.unibuc.fmi.ge.persistence.entity.MaritimeNotice;
import ro.unibuc.fmi.ge.persistence.repository.MaritimeNoticeRepository;
import ro.unibuc.fmi.ge.service.maritime_notice.MaritimeNoticeResolutionService;
import ro.unibuc.fmi.ge.shared.UserHelper;

@Service
@Transactional
public class MaritimeNoticeResolutionServiceImpl implements MaritimeNoticeResolutionService {
    private final MaritimeNoticeRepository maritimeNoticeRepository;
    private final UserHelper userHelper;

    public MaritimeNoticeResolutionServiceImpl(MaritimeNoticeRepository maritimeNoticeRepository, UserHelper userHelper) {
        this.maritimeNoticeRepository = maritimeNoticeRepository;
        this.userHelper = userHelper;
    }


    @Override
    public void resolveMaritimeNotice(ResolutionDto dto, Long idMaritimeNotice) {
        MaritimeNotice maritimeNotice = maritimeNoticeRepository.findById(idMaritimeNotice).orElseThrow(NotFoundException::new);

        if (Boolean.TRUE.equals(userHelper.isANRAuthority()) && !MaritimeNoticeDocumentStatus.AWAITING.equals(maritimeNotice.getDocumentStatus())) {
            throw new ForbiddenException();
        }

        if (Boolean.TRUE.equals(userHelper.isAPMCAuthority()) && !MaritimeNoticeDocumentStatus.ANR_APPROVED.equals(maritimeNotice.getDocumentStatus())) {
            throw new ForbiddenException();
        }

        if (Resolution.REJECTED.equals(dto.getResolutionEnum())) {
            maritimeNotice.setDocumentStatus(MaritimeNoticeDocumentStatus.REJECTED);
            maritimeNotice.setRejectionReason(dto.getRejectionReason());
        }

        if(Resolution.APPROVED.equals(dto.getResolutionEnum())) {
            if (Boolean.TRUE.equals(userHelper.isANRAuthority())) {
                maritimeNotice.setDocumentStatus(MaritimeNoticeDocumentStatus.ANR_APPROVED);
            }
            if (Boolean.TRUE.equals(userHelper.isAPMCAuthority())) {
                maritimeNotice.setDocumentStatus(MaritimeNoticeDocumentStatus.ANR_APMC_APPROVED);
            }
        }
    }
}
