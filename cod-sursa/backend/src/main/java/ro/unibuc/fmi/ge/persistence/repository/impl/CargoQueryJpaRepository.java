package ro.unibuc.fmi.ge.persistence.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import ro.unibuc.fmi.ge.dto.GenericDto;
import ro.unibuc.fmi.ge.persistence.entity.Cargo;
import ro.unibuc.fmi.ge.persistence.entity.Cargo_;
import ro.unibuc.fmi.ge.persistence.repository.CargoQueryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CargoQueryJpaRepository implements CargoQueryRepository {
    private final EntityManager em;

    public CargoQueryJpaRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<GenericDto> getAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<Cargo> cargo = cq.from(Cargo.class);
        cq.multiselect(
                cargo.get(Cargo_.id).alias(Cargo_.ID),
                cargo.get(Cargo_.name).alias(Cargo_.NAME)
        );
        cq.orderBy(
                cb.asc(cb.lower(cargo.get(Cargo_.name)))
        );
        TypedQuery<Tuple> query = em.createQuery(cq);
        List<Tuple> items = query.getResultList();
        return items.stream()
                .map(t -> GenericDto.builder()
                        .id(t.get(Cargo_.ID, Long.class))
                        .label(t.get(Cargo_.NAME, String.class))
                        .build())
                .collect(Collectors.toList());
    }
}
