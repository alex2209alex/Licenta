package ro.unibuc.fmi.ge.persistence.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import ro.unibuc.fmi.ge.dto.GenericDto;
import ro.unibuc.fmi.ge.dto.MaritimeNoticeDocumentStatus;
import ro.unibuc.fmi.ge.dto.ShipDto;
import ro.unibuc.fmi.ge.dto.maritime_notice.DeclaredCargoDto;
import ro.unibuc.fmi.ge.dto.maritime_notice.MaritimeNoticeDto;
import ro.unibuc.fmi.ge.dto.maritime_notice.MaritimeNoticeListItemDto;
import ro.unibuc.fmi.ge.dto.maritime_notice.MaritimeNoticeSearchDto;
import ro.unibuc.fmi.ge.persistence.entity.*;
import ro.unibuc.fmi.ge.persistence.repository.MaritimeNoticeQueryRepository;
import ro.unibuc.fmi.ge.shared.SearchConstants;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Repository
public class MaritimeNoticeQueryJpaRepository implements MaritimeNoticeQueryRepository {
    private static final String PORT_ID = "port_id";
    private static final String PORT_NAME = "port_name";
    private static final String SHIP_ID = "ship_id";
    private static final String SHIP_NAME = "ship_name";
    private static final String COMPANY_ID = "company_id";
    private static final String COMPANY_NAME = "company_name";
    private static final String CARGO_ID = "cargo_id";
    private static final String CARGO_NAME = "cargo_name";
    private static final String DECLARED_CARGO_ID = "declared_cargo_id";
    private final EntityManager em;

    public MaritimeNoticeQueryJpaRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<MaritimeNoticeListItemDto> search(MaritimeNoticeSearchDto searchDto) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<MaritimeNotice> maritimeNotice = cq.from(MaritimeNotice.class);
        Join<MaritimeNotice, MaritimeCall> maritimeCallJoin = maritimeNotice.join(MaritimeNotice_.maritimeCall);
        Join<MaritimeNotice, Company> companyJoin = maritimeNotice.join(MaritimeNotice_.agent);
        Join<MaritimeCall, Port> portJoin = maritimeCallJoin.join(MaritimeCall_.port);
        Join<MaritimeCall, Ship> shipJoin = maritimeCallJoin.join(MaritimeCall_.ship);
        cq.multiselect(
                maritimeNotice.get(MaritimeNotice_.documentStatus).alias(MaritimeNotice_.DOCUMENT_STATUS),
                maritimeNotice.get(MaritimeNotice_.estimatedArrivalDateTime).alias(MaritimeNotice_.ESTIMATED_ARRIVAL_DATE_TIME),
                maritimeNotice.get(MaritimeNotice_.id).alias(MaritimeNotice_.ID),
                portJoin.get(Port_.name).alias(PORT_NAME),
                shipJoin.get(Ship_.name).alias(SHIP_NAME),
                companyJoin.get(Company_.name).alias(COMPANY_NAME)
        );
        cq.orderBy(
                cb.desc(maritimeNotice.get(MaritimeNotice_.estimatedArrivalDateTime))
        );
        cq.where(buildWhere(searchDto, cb, companyJoin, maritimeNotice));
        final TypedQuery<Tuple> typedQuery = em.createQuery(cq);
        return typedQuery.setMaxResults(SearchConstants.SEARCH_MAX_RESULTS)
                .getResultStream()
                .map(tuple -> MaritimeNoticeListItemDto.builder()
                        .agentName(tuple.get(COMPANY_NAME, String.class))
                        .shipName(tuple.get(SHIP_NAME, String.class))
                        .estimatedArrivalTime(tuple.get(MaritimeNotice_.ESTIMATED_ARRIVAL_DATE_TIME, Instant.class))
                        .portName(tuple.get(PORT_NAME, String.class))
                        .documentStatus(tuple.get(MaritimeNotice_.DOCUMENT_STATUS, MaritimeNoticeDocumentStatus.class).id)
                        .id(tuple.get(MaritimeNotice_.ID, Long.class))
                        .build())
                .toList();
    }

    @Override
    public Optional<MaritimeNoticeDto> findById(Long idMaritimeNotice) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<MaritimeNotice> maritimeNotice = cq.from(MaritimeNotice.class);
        Join<MaritimeNotice, DeclaredCargo> declaredCargoJoin = maritimeNotice.join(MaritimeNotice_.declaredCargos, JoinType.LEFT);
        Join<MaritimeNotice, MaritimeCall> maritimeCallJoin = maritimeNotice.join(MaritimeNotice_.maritimeCall);
        Join<MaritimeNotice, Company> companyJoin = maritimeNotice.join(MaritimeNotice_.agent);
        Join<MaritimeCall, Port> portJoin = maritimeCallJoin.join(MaritimeCall_.port);
        Join<MaritimeCall, Ship> shipJoin = maritimeCallJoin.join(MaritimeCall_.ship);
        Join<DeclaredCargo, Cargo> cargoJoin = declaredCargoJoin.join(DeclaredCargo_.cargo, JoinType.LEFT);
        cq.multiselect(
                maritimeNotice.get(MaritimeNotice_.id).alias(MaritimeNotice_.ID),
                maritimeNotice.get(MaritimeNotice_.estimatedArrivalDateTime).alias(MaritimeNotice_.ESTIMATED_ARRIVAL_DATE_TIME),
                maritimeNotice.get(MaritimeNotice_.documentStatus).alias(MaritimeNotice_.DOCUMENT_STATUS),
                maritimeNotice.get(MaritimeNotice_.rejectionReason).alias(MaritimeNotice_.REJECTION_REASON),
                portJoin.get(Port_.id).alias(PORT_ID),
                portJoin.get(Port_.name).alias(PORT_NAME),
                shipJoin.get(Ship_.id).alias(SHIP_ID),
                shipJoin.get(Ship_.name).alias(SHIP_NAME),
                shipJoin.get(Ship_.imoNumber).alias(Ship_.IMO_NUMBER),
                companyJoin.get(Company_.id).alias(COMPANY_ID),
                companyJoin.get(Company_.name).alias(COMPANY_NAME),
                cargoJoin.get(Cargo_.id).alias(CARGO_ID),
                cargoJoin.get(Cargo_.name).alias(CARGO_NAME),
                declaredCargoJoin.get(DeclaredCargo_.id).alias(DECLARED_CARGO_ID),
                declaredCargoJoin.get(DeclaredCargo_.quantity).alias(DeclaredCargo_.QUANTITY)
        );
        cq.orderBy(
                cb.asc(maritimeNotice.get(MaritimeNotice_.id))
        );
        cq.where(cb.and(cb.equal(maritimeNotice.get(MaritimeNotice_.id), idMaritimeNotice)));
        final TypedQuery<Tuple> typedQuery = em.createQuery(cq);
        List<Tuple> tuples = typedQuery.getResultList();
        return fromTuplesToDto(tuples);
    }

    @Override
    public Optional<MaritimeNoticeDto> findForAgent(Long idMaritimeNotice, Long idAgent) {
        Optional<MaritimeNoticeDto> maritimeNoticeDtoOptional = findById(idMaritimeNotice);
        if (maritimeNoticeDtoOptional.isPresent()) {
            MaritimeNoticeDto maritimeNoticeDto = maritimeNoticeDtoOptional.get();
            if (maritimeNoticeDto.getAgent().getId().equals(idAgent)) {
                return maritimeNoticeDtoOptional;
            }
        }
        return Optional.empty();
    }

    private Optional<MaritimeNoticeDto> fromTuplesToDto(List<Tuple> tuples) {
        if(tuples.isEmpty()) {
            return Optional.empty();
        }
        MaritimeNoticeDto maritimeNoticeDto = fromTupleToDto(tuples.get(0));
        for (Tuple t : tuples) {
            Long idDeclaredCargo = t.get(DECLARED_CARGO_ID, Long.class);
            if (idDeclaredCargo != null) {
                DeclaredCargoDto declaredCargoDto = new DeclaredCargoDto();
                declaredCargoDto.setId(idDeclaredCargo);
                declaredCargoDto.setQuantity(t.get(DeclaredCargo_.QUANTITY, BigDecimal.class));
                declaredCargoDto.setCargo(
                        GenericDto.builder()
                                .id(t.get(CARGO_ID, Long.class))
                                .label(t.get(CARGO_NAME, String.class))
                                .build()
                );
                maritimeNoticeDto.addDeclaredCargo(declaredCargoDto);
            }
        }
        return Optional.of(maritimeNoticeDto);
    }

    private MaritimeNoticeDto fromTupleToDto(Tuple t) {
        MaritimeNoticeDto maritimeNoticeDto = new MaritimeNoticeDto();
        maritimeNoticeDto.setId(t.get(MaritimeNotice_.ID, Long.class));
        maritimeNoticeDto.setShip(
                ShipDto.builder()
                        .id(t.get(SHIP_ID, Long.class))
                        .label(t.get(SHIP_NAME, String.class))
                        .imoNumber(t.get(Ship_.IMO_NUMBER, String.class))
                        .build()
        );
        maritimeNoticeDto.setAgent(
                GenericDto.builder()
                        .id(t.get(COMPANY_ID, Long.class))
                        .label(t.get(COMPANY_NAME, String.class))
                        .build()
        );
        maritimeNoticeDto.setPort(
                GenericDto.builder()
                        .id(t.get(PORT_ID, Long.class))
                        .label(t.get(PORT_NAME, String.class))
                        .build()
        );
        maritimeNoticeDto.setEstimatedArrivalDateTime(
                t.get(MaritimeNotice_.ESTIMATED_ARRIVAL_DATE_TIME, Instant.class)
        );
        maritimeNoticeDto.setStatus(
                t.get(MaritimeNotice_.DOCUMENT_STATUS, MaritimeNoticeDocumentStatus.class).id
        );
        maritimeNoticeDto.setRejectionReason(
                t.get(MaritimeNotice_.REJECTION_REASON, String.class)
        );
        return maritimeNoticeDto;
    }

    private Predicate[] buildWhere(
            MaritimeNoticeSearchDto searchDto,
            CriteriaBuilder cb,
            Join<MaritimeNotice, Company> companyJoin,
            Root<MaritimeNotice> maritimeNotice) {
        List<Predicate> predicates = new ArrayList<>();
        if (Boolean.FALSE.equals(searchDto.getIsAuthority())) {
            predicates.add(cb.and(cb.equal(companyJoin.get(Company_.id), searchDto.getCompanyId())));
        }
        if (searchDto.getDocumentStatus() != null) {
            predicates.add(cb.and(cb.equal(maritimeNotice.get(MaritimeNotice_.documentStatus), searchDto.getDocumentStatus())));
        }
        return predicates.toArray(new Predicate[0]);
    }
}
