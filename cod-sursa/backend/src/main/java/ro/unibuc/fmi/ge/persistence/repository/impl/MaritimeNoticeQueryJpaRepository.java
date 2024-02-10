package ro.unibuc.fmi.ge.persistence.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import ro.unibuc.fmi.ge.dto.MaritimeNoticeDocumentStatus;
import ro.unibuc.fmi.ge.dto.maritime_notice.MaritimeNoticeListItemDto;
import ro.unibuc.fmi.ge.dto.maritime_notice.MaritimeNoticeSearchDto;
import ro.unibuc.fmi.ge.persistence.entity.*;
import ro.unibuc.fmi.ge.persistence.repository.MaritimeNoticeQueryRepository;
import ro.unibuc.fmi.ge.shared.SearchConstants;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MaritimeNoticeQueryJpaRepository implements MaritimeNoticeQueryRepository {
    private static final String PORT_NAME = "port_name";
    private static final String SHIP_NAME = "ship_name";
    private static final String COMPANY_NAME = "company_name";
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
