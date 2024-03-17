package ro.unibuc.fmi.ge.persistence.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import ro.unibuc.fmi.ge.dto.pilotage_bulletin.PilotageBulletinListItemDto;
import ro.unibuc.fmi.ge.dto.pilotage_bulletin.PilotageBulletinSearchDto;
import ro.unibuc.fmi.ge.persistence.entity.*;
import ro.unibuc.fmi.ge.persistence.repository.PilotageBulletinQueryRepository;
import ro.unibuc.fmi.ge.shared.SearchConstants;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PilotageBulletinQueryJpaRepository implements PilotageBulletinQueryRepository {
    private static final String AGENT_NAME = "agent_name";
    private static final String PILOTAGE_NAME = "pilotage_name";
    private static final String SHIP_NAME = "ship_name";
    private final EntityManager em;

    public PilotageBulletinQueryJpaRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<PilotageBulletinListItemDto> search(PilotageBulletinSearchDto searchDto) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<ShipMovement> shipMovement = cq.from(ShipMovement.class);
        Join<ShipMovement, PilotageBulletin> pilotageBulletinJoin = shipMovement.join(ShipMovement_.pilotageBulletin);
        Join<ShipMovement, MaritimeCall> maritimeCallJoin = shipMovement.join(ShipMovement_.maritimeCall);
        Join<MaritimeCall, Ship> shipJoin = maritimeCallJoin.join(MaritimeCall_.ship);
        Join<PilotageBulletin, Company> agentJoin = pilotageBulletinJoin.join(PilotageBulletin_.agent);
        Join<PilotageBulletin, Company> pilotageJoin = pilotageBulletinJoin.join(PilotageBulletin_.pilotageCompany);
        Join<ShipMovement, Location> startLocationJoin = shipMovement.join(ShipMovement_.startLocation);
        Join<ShipMovement, Location> endLocationJoin = shipMovement.join(ShipMovement_.endLocation);
        cq.multiselect(
                pilotageBulletinJoin.get(PilotageBulletin_.id).alias(PilotageBulletin_.ID),
                pilotageBulletinJoin.get(PilotageBulletin_.estimatedStartTime).alias(PilotageBulletin_.ESTIMATED_START_TIME),
                agentJoin.get(Company_.name).alias(AGENT_NAME),
                pilotageJoin.get(Company_.name).alias(PILOTAGE_NAME),
                shipJoin.get(Ship_.name).alias(SHIP_NAME),
                startLocationJoin.get(Location_.name).alias(ShipMovement_.START_LOCATION),
                endLocationJoin.get(Location_.name).alias(ShipMovement_.END_LOCATION),
                shipMovement.get(ShipMovement_.startMovementTime).alias(ShipMovement_.START_MOVEMENT_TIME),
                shipMovement.get(ShipMovement_.endMovementTime).alias(ShipMovement_.END_MOVEMENT_TIME),
                pilotageBulletinJoin.get(PilotageBulletin_.documentStatus).alias(PilotageBulletin_.DOCUMENT_STATUS)
        );
        cq.orderBy(
                cb.desc(pilotageBulletinJoin.get(PilotageBulletin_.estimatedStartTime))
        );
        cq.where(buildWhere(searchDto, cb, agentJoin, pilotageJoin, pilotageBulletinJoin));
        final TypedQuery<Tuple> typedQuery = em.createQuery(cq);
        return typedQuery.setMaxResults(SearchConstants.SEARCH_MAX_RESULTS)
                .getResultStream()
                .map(tuple -> PilotageBulletinListItemDto.builder()
                        .id(tuple.get(PilotageBulletin_.ID, Long.class))
                        .estimatedStartTime(tuple.get(PilotageBulletin_.ESTIMATED_START_TIME, Instant.class))
                        .agentName(tuple.get(AGENT_NAME, String.class))
                        .pilotageCompanyName(tuple.get(PILOTAGE_NAME, String.class))
                        .shipName(tuple.get(SHIP_NAME, String.class))
                        .startLocationName(tuple.get(ShipMovement_.START_LOCATION, String.class))
                        .endLocationName(tuple.get(ShipMovement_.END_LOCATION, String.class))
                        .startMovementTime(tuple.get(ShipMovement_.START_MOVEMENT_TIME, Instant.class))
                        .endMovementTime(tuple.get(ShipMovement_.END_MOVEMENT_TIME, Instant.class))
                        .documentStatus(tuple.get(PilotageBulletin_.DOCUMENT_STATUS, Integer.class))
                        .build())
                .toList();
    }

    private Predicate[] buildWhere(
            PilotageBulletinSearchDto searchDto,
            CriteriaBuilder cb,
            Join<PilotageBulletin, Company> agentJoin,
            Join<PilotageBulletin, Company> pilotageJoin,
            Join<ShipMovement, PilotageBulletin> pilotageBulletinJoin) {
        List<Predicate> predicates = new ArrayList<>();
        if (Boolean.TRUE.equals(searchDto.getIsAgent())) {
            predicates.add(cb.and(cb.equal(agentJoin.get(Company_.id), searchDto.getCompanyId())));
        }
        if (Boolean.TRUE.equals(searchDto.getIsPilotage())) {
            predicates.add(cb.and(cb.equal(pilotageJoin.get(Company_.id), searchDto.getCompanyId())));
        }
        if (searchDto.getDocumentStatus() != null) {
            predicates.add(cb.and(cb.equal(pilotageBulletinJoin.get(PilotageBulletin_.documentStatus), searchDto.getDocumentStatus())));
        }
        return predicates.toArray(new Predicate[0]);
    }
}
