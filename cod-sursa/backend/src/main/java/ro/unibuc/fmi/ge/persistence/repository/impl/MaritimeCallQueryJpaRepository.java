package ro.unibuc.fmi.ge.persistence.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import ro.unibuc.fmi.ge.dto.MaritimeCallDto;
import ro.unibuc.fmi.ge.dto.MaritimeCallStatus;
import ro.unibuc.fmi.ge.persistence.entity.MaritimeCall;
import ro.unibuc.fmi.ge.persistence.entity.MaritimeCall_;
import ro.unibuc.fmi.ge.persistence.entity.Ship;
import ro.unibuc.fmi.ge.persistence.entity.Ship_;
import ro.unibuc.fmi.ge.persistence.repository.MaritimeCallQueryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MaritimeCallQueryJpaRepository implements MaritimeCallQueryRepository {
    private final EntityManager em;

    public MaritimeCallQueryJpaRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<MaritimeCallDto> findAllThatCanHaveNewPilotageBulletin() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<MaritimeCall> maritimeCall = cq.from(MaritimeCall.class);
        Join<MaritimeCall, Ship> shipJoin = maritimeCall.join(MaritimeCall_.ship);
        cq.multiselect(
                maritimeCall.get(MaritimeCall_.id).alias(MaritimeCall_.ID),
                maritimeCall.get(MaritimeCall_.status).alias(MaritimeCall_.STATUS),
                shipJoin.get(Ship_.name).alias(Ship_.NAME)
        );
        cq.orderBy(
                cb.asc(cb.lower(shipJoin.get(Ship_.name)))
        );
        cq.where(cb.and(cb.or(
                cb.equal(maritimeCall.get(MaritimeCall_.status), MaritimeCallStatus.APPROVED),
                cb.equal(maritimeCall.get(MaritimeCall_.status), MaritimeCallStatus.COMPLETED)
        )));
        TypedQuery<Tuple> query = em.createQuery(cq);
        List<Tuple> items = query.getResultList();
        return items.stream()
                .map(t -> MaritimeCallDto.builder()
                        .id(t.get(MaritimeCall_.ID, Long.class))
                        .label(t.get(Ship_.NAME, String.class))
                        .maritimeCallStatus(t.get(MaritimeCall_.STATUS, MaritimeCallStatus.class).id)
                        .build())
                .collect(Collectors.toList());
    }
}
