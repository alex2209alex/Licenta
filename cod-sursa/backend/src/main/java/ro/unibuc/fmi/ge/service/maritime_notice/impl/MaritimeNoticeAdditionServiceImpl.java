package ro.unibuc.fmi.ge.service.maritime_notice.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ro.unibuc.fmi.ge.dto.MaritimeCallStatus;
import ro.unibuc.fmi.ge.dto.MaritimeNoticeDocumentStatus;
import ro.unibuc.fmi.ge.dto.maritime_notice.MaritimeNoticeDto;
import ro.unibuc.fmi.ge.persistence.entity.*;
import ro.unibuc.fmi.ge.persistence.repository.*;
import ro.unibuc.fmi.ge.service.maritime_notice.MaritimeNoticeAdditionService;

import java.time.LocalDateTime;

@Service
@Transactional
public class MaritimeNoticeAdditionServiceImpl implements MaritimeNoticeAdditionService {
    private final MaritimeCallRepository maritimeCallRepository;
    private final MaritimeNoticeRepository maritimeNoticeRepository;
    private final DeclaredCargoRepository declaredCargoRepository;
    private final PortRepository portRepository;
    private final ShipRepository shipRepository;
    private final CompanyRepository companyRepository;

    public MaritimeNoticeAdditionServiceImpl(MaritimeCallRepository maritimeCallRepository, MaritimeNoticeRepository maritimeNoticeRepository, DeclaredCargoRepository declaredCargoRepository, PortRepository portRepository, ShipRepository shipRepository, CompanyRepository companyRepository) {
        this.maritimeCallRepository = maritimeCallRepository;
        this.maritimeNoticeRepository = maritimeNoticeRepository;
        this.declaredCargoRepository = declaredCargoRepository;
        this.portRepository = portRepository;
        this.shipRepository = shipRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public void add(MaritimeNoticeDto dto) {
        MaritimeCall maritimeCall = createMaritimeCall(dto);
        createMaritimeNotice(dto, maritimeCall);
    }

    private void createMaritimeNotice(MaritimeNoticeDto dto, MaritimeCall maritimeCall) {
        Company agent = companyRepository.getReferenceById(dto.getAgent().getId());
        MaritimeNotice maritimeNotice = new MaritimeNotice();
        maritimeNotice.setMaritimeCall(maritimeCall);
        maritimeNotice.setAgent(agent);
        maritimeNotice.setCreationTime(LocalDateTime.now());
        maritimeNotice.setDocumentStatus(MaritimeNoticeDocumentStatus.AWAITING);
        maritimeNotice.setEstimatedArrivalDateTime(dto.getEstimatedArrivalDateTime());
        maritimeNoticeRepository.save(maritimeNotice);
    }

    private MaritimeCall createMaritimeCall(MaritimeNoticeDto dto) {
        Port port = portRepository.getReferenceById(dto.getPort().getId());
        Ship ship = shipRepository.getReferenceById(dto.getShip().getId());
        MaritimeCall maritimeCall = new MaritimeCall();
        maritimeCall.setPort(port);
        maritimeCall.setShip(ship);
        maritimeCall.setStatus(MaritimeCallStatus.PLANNED);
        return maritimeCallRepository.save(maritimeCall);
    }
}
