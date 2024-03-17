package ro.unibuc.fmi.ge.service.maritime_notice.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.unibuc.fmi.ge.dto.MaritimeCallStatus;
import ro.unibuc.fmi.ge.dto.MaritimeNoticeDocumentStatus;
import ro.unibuc.fmi.ge.dto.maritime_notice.DeclaredCargoDto;
import ro.unibuc.fmi.ge.dto.maritime_notice.MaritimeNoticeDto;
import ro.unibuc.fmi.ge.exceptions.BadRequestException;
import ro.unibuc.fmi.ge.exceptions.ForbiddenException;
import ro.unibuc.fmi.ge.exceptions.NotFoundException;
import ro.unibuc.fmi.ge.persistence.entity.*;
import ro.unibuc.fmi.ge.persistence.repository.*;
import ro.unibuc.fmi.ge.service.maritime_notice.MaritimeNoticeModificationService;
import ro.unibuc.fmi.ge.shared.UserHelper;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class MaritimeNoticeModificationServiceImpl implements MaritimeNoticeModificationService {
    private final MaritimeNoticeRepository maritimeNoticeRepository;
    private final PortRepository portRepository;
    private final ShipRepository shipRepository;
    private final DeclaredCargoRepository declaredCargoRepository;
    private final CargoRepository cargoRepository;
    private final UserHelper userHelper;

    public MaritimeNoticeModificationServiceImpl(MaritimeNoticeRepository maritimeNoticeRepository,
                                                 PortRepository portRepository,
                                                 ShipRepository shipRepository,
                                                 DeclaredCargoRepository declaredCargoRepository,
                                                 CargoRepository cargoRepository, UserHelper userHelper) {
        this.maritimeNoticeRepository = maritimeNoticeRepository;
        this.portRepository = portRepository;
        this.shipRepository = shipRepository;
        this.declaredCargoRepository = declaredCargoRepository;
        this.cargoRepository = cargoRepository;
        this.userHelper = userHelper;
    }

    @Override
    public void update(MaritimeNoticeDto maritimeNoticeDto) {
        Long id = maritimeNoticeDto.getId();
        MaritimeNotice maritimeNotice = maritimeNoticeRepository.findById(id).orElseThrow(NotFoundException::new);
        validateMaritimeNoticeOnUpdate(maritimeNoticeDto, maritimeNotice);
        updateMaritimeNotice(maritimeNoticeDto, maritimeNotice);
        updateMaritimeCall(maritimeNoticeDto, maritimeNotice);
        updateDeclaredCargo(maritimeNoticeDto, maritimeNotice);
    }

    private void validateMaritimeNoticeOnUpdate(MaritimeNoticeDto maritimeNoticeDto, MaritimeNotice maritimeNotice) {
        Long userCompanyId = userHelper.getUserCompanyId();
        if (!maritimeNotice.getAgent().getId().equals(userCompanyId)) {
            throw new ForbiddenException();
        }
        if (!MaritimeNoticeDocumentStatus.REJECTED.equals(maritimeNotice.getDocumentStatus())) {
            throw new ForbiddenException();
        }
        if (maritimeNoticeDto.getEstimatedArrivalDateTime().isBefore(Instant.now())) {
            throw new BadRequestException();
        }
    }

    private void updateDeclaredCargo(MaritimeNoticeDto maritimeNoticeDto, MaritimeNotice maritimeNotice) {
        declaredCargoRepository.deleteAllByMaritimeNoticeId(maritimeNotice.getId());
        addDeclaredCargos(maritimeNoticeDto.getCargos(), maritimeNotice);
    }

    private void addDeclaredCargos(List<DeclaredCargoDto> cargos, MaritimeNotice maritimeNotice) {
        if (cargos != null && !cargos.isEmpty()) {
            for (DeclaredCargoDto dto : cargos) {
                DeclaredCargo declaredCargo = new DeclaredCargo();
                declaredCargo.setMaritimeNotice(maritimeNotice);
                declaredCargo.setQuantity(dto.getQuantity());
                declaredCargo.setCargo(cargoRepository.getReferenceById(dto.getCargo().getId()));
                declaredCargoRepository.save(declaredCargo);
            }
        }
    }

    private void updateMaritimeNotice(MaritimeNoticeDto maritimeNoticeDto, MaritimeNotice maritimeNotice) {
        maritimeNotice.setDocumentStatus(MaritimeNoticeDocumentStatus.AWAITING);
        maritimeNotice.setCreationTime(Instant.now());
        maritimeNotice.setRejectionReason(null);
        maritimeNotice.setEstimatedArrivalDateTime(maritimeNoticeDto.getEstimatedArrivalDateTime());
    }

    private void updateMaritimeCall(MaritimeNoticeDto maritimeNoticeDto, MaritimeNotice maritimeNotice) {
        MaritimeCall maritimeCall = maritimeNotice.getMaritimeCall();
        Port port = portRepository.getReferenceById(maritimeNoticeDto.getPort().getId());
        Ship ship = shipRepository.getReferenceById(maritimeNoticeDto.getShip().getId());
        maritimeCall.setPort(port);
        maritimeCall.setShip(ship);
        maritimeCall.setStatus(MaritimeCallStatus.PLANNED);
    }
}
