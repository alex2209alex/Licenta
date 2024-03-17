package ro.unibuc.fmi.ge.service.maritime_notice.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.unibuc.fmi.ge.dto.MaritimeCallStatus;
import ro.unibuc.fmi.ge.dto.MaritimeNoticeDocumentStatus;
import ro.unibuc.fmi.ge.exceptions.ForbiddenException;
import ro.unibuc.fmi.ge.exceptions.NotFoundException;
import ro.unibuc.fmi.ge.persistence.entity.MaritimeNotice;
import ro.unibuc.fmi.ge.persistence.repository.MaritimeNoticeRepository;
import ro.unibuc.fmi.ge.service.maritime_notice.MaritimeNoticeCancellationService;
import ro.unibuc.fmi.ge.shared.UserHelper;

@Service
@Transactional
public class MaritimeNoticeCancellationServiceImpl implements MaritimeNoticeCancellationService {
    private final MaritimeNoticeRepository maritimeNoticeRepository;
    private final UserHelper userHelper;

    public MaritimeNoticeCancellationServiceImpl(MaritimeNoticeRepository maritimeNoticeRepository, UserHelper userHelper) {
        this.maritimeNoticeRepository = maritimeNoticeRepository;
        this.userHelper = userHelper;
    }

    @Override
    public void cancel(Long idMaritimeNotice) {
        MaritimeNotice maritimeNotice = maritimeNoticeRepository.findById(idMaritimeNotice).orElseThrow(NotFoundException::new);
        validateMaritimeNoticeOnCancel(maritimeNotice);
        maritimeNotice.setRejectionReason(null);
        maritimeNotice.setDocumentStatus(MaritimeNoticeDocumentStatus.CANCELLED);
        maritimeNotice.getMaritimeCall().setStatus(MaritimeCallStatus.CANCELLED);
    }

    private void validateMaritimeNoticeOnCancel(MaritimeNotice maritimeNotice) {
        Long userCompanyId = userHelper.getUserCompanyId();
        if (!maritimeNotice.getAgent().getId().equals(userCompanyId)) {
            throw new ForbiddenException();
        }
        if (MaritimeNoticeDocumentStatus.CANCELLED.equals(maritimeNotice.getDocumentStatus())) {
            throw new ForbiddenException();
        }
        if (!maritimeNotice.getMaritimeCall().getStatus().equals(MaritimeCallStatus.PLANNED)) {
            throw new ForbiddenException();
        }
    }
}
