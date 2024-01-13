package ro.unibuc.fmi.ge.persistence.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import ro.unibuc.fmi.ge.dto.GenericDto;
import ro.unibuc.fmi.ge.persistence.entity.Port;
import ro.unibuc.fmi.ge.persistence.entity.Port_;
import ro.unibuc.fmi.ge.persistence.repository.PortQueryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PortQueryJpaRepository implements PortQueryRepository {
    private final EntityManager em;

    public PortQueryJpaRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<GenericDto> getAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<Port> port = cq.from(Port.class);
        cq.multiselect(
                port.get(Port_.id).alias(Port_.ID),
                port.get(Port_.name).alias(Port_.NAME)
        );
        cq.orderBy(
                cb.asc(cb.lower(port.get(Port_.name)))
        );
        TypedQuery<Tuple> query = em.createQuery(cq);
        List<Tuple> items = query.getResultList();
        return items.stream()
                .map(t -> GenericDto.builder()
                        .id(t.get(Port_.ID, Long.class))
                        .label(t.get(Port_.NAME, String.class))
                        .build())
                .collect(Collectors.toList());
    }
}
