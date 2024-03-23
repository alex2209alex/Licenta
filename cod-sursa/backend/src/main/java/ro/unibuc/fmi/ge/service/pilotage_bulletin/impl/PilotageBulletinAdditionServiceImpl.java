package ro.unibuc.fmi.ge.service.pilotage_bulletin.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.unibuc.fmi.ge.dto.MaritimeCallStatus;
import ro.unibuc.fmi.ge.dto.PilotageBulletinDocumentStatus;
import ro.unibuc.fmi.ge.dto.ShipMovementStatus;
import ro.unibuc.fmi.ge.dto.ShipMovementType;
import ro.unibuc.fmi.ge.dto.pilotage_bulletin.PilotageBulletinDto;
import ro.unibuc.fmi.ge.exceptions.BadRequestException;
import ro.unibuc.fmi.ge.persistence.entity.*;
import ro.unibuc.fmi.ge.persistence.repository.*;
import ro.unibuc.fmi.ge.service.pilotage_bulletin.PilotageBulletinAdditionService;

import java.time.Instant;
import java.util.Objects;

@Service
@Transactional
public class PilotageBulletinAdditionServiceImpl implements PilotageBulletinAdditionService {
    private final MaritimeCallRepository maritimeCallRepository;
    private final CompanyRepository companyRepository;
    private final LocationRepository locationRepository;
    private final ShipMovementRepository shipMovementRepository;
    private final PilotageBulletinRepository pilotageBulletinRepository;


    public PilotageBulletinAdditionServiceImpl(MaritimeCallRepository maritimeCallRepository, CompanyRepository companyRepository, LocationRepository locationRepository, ShipMovementRepository shipMovementRepository, PilotageBulletinRepository pilotageBulletinRepository) {
        this.maritimeCallRepository = maritimeCallRepository;
        this.companyRepository = companyRepository;
        this.locationRepository = locationRepository;
        this.shipMovementRepository = shipMovementRepository;
        this.pilotageBulletinRepository = pilotageBulletinRepository;
    }

    @Override
    public void add(PilotageBulletinDto dto) {
        validatePilotageBulletinDate(dto.getEstimatedStartTime());
        MaritimeCall maritimeCall = maritimeCallRepository.findById(dto.getMaritimeCall().getId()).orElseThrow(BadRequestException::new);
        validateMaritimeCallIsPlanned(maritimeCall);
        PilotageBulletin pilotageBulletin = createPilotageBulletin(dto, maritimeCall);
        createShipMovement(dto, pilotageBulletin, maritimeCall);
    }

    private void createShipMovement(PilotageBulletinDto dto, PilotageBulletin pilotageBulletin, MaritimeCall maritimeCall) {
        Location startLocation = locationRepository.findById(dto.getStartLocation().getId()).orElseThrow(BadRequestException::new);
        Location endLocation = locationRepository.findById(dto.getEndLocation().getId()).orElseThrow(BadRequestException::new);
        ShipMovement shipMovement = new ShipMovement();
        shipMovement.setPilotageBulletin(pilotageBulletin);
        shipMovement.setStatus(ShipMovementStatus.PLANNED);
        validateLocations(startLocation, endLocation, maritimeCall.getPort());
        shipMovement.setStartLocation(startLocation);
        shipMovement.setEndLocation(endLocation);
        shipMovement.setType(ShipMovementType.valueOfId(dto.getShipMovementType()));
        shipMovementRepository.save(shipMovement);
    }

    private void validateLocations(Location startLocation, Location endLocation, Port port) {
        if (!Objects.equals(startLocation.getPort().getId(), endLocation.getPort().getId()) || !Objects.equals(startLocation.getPort().getId(), port.getId())) {
            throw new BadRequestException();
        }
    }


    private PilotageBulletin createPilotageBulletin(PilotageBulletinDto dto, MaritimeCall maritimeCall) {
        Company pilotageCompany = companyRepository.findById(dto.getPilotageCompany().getId()).orElseThrow(BadRequestException::new);
        Company agent = companyRepository.getReferenceById(dto.getAgent().getId());
        PilotageBulletin pilotageBulletin = new PilotageBulletin();
        pilotageBulletin.setPilotageCompany(pilotageCompany);
        pilotageBulletin.setAgent(agent);
        pilotageBulletin.setDocumentStatus(PilotageBulletinDocumentStatus.AWAITING);
        pilotageBulletin.setCreationDate(Instant.now());
        pilotageBulletin.setMaritimeCall(maritimeCall);
        if (dto.getCargoOperatingCompany() != null) {
            Company cargoOperatingCompany = companyRepository.findById(dto.getCargoOperatingCompany().getId()).orElseThrow(BadRequestException::new);
            pilotageBulletin.setCargoOperatingCompany(cargoOperatingCompany);
        }
        pilotageBulletin.setEstimatedStartTime(dto.getEstimatedStartTime());
        return pilotageBulletinRepository.save(pilotageBulletin);
    }

    private void validateMaritimeCallIsPlanned(MaritimeCall maritimeCall) {
        if (!(MaritimeCallStatus.APPROVED.equals(maritimeCall.getStatus())
                || MaritimeCallStatus.COMPLETED.equals(maritimeCall.getStatus()))) {
            throw new BadRequestException();
        }
    }

    private void validatePilotageBulletinDate(Instant estimatedStartTime) {
        if (estimatedStartTime.isBefore(Instant.now())) {
            throw new BadRequestException();
        }
    }
}
