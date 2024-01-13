package ro.unibuc.fmi.ge.persistence.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import ro.unibuc.fmi.ge.dto.ShipDto;
import ro.unibuc.fmi.ge.persistence.entity.Ship;
import ro.unibuc.fmi.ge.persistence.entity.Ship_;
import ro.unibuc.fmi.ge.persistence.repository.ShipQueryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ShipQueryJpaRepository implements ShipQueryRepository {
    private final EntityManager em;

    public ShipQueryJpaRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<ShipDto> getAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<Ship> ship = cq.from(Ship.class);
        cq.multiselect(
                ship.get(Ship_.id).alias(Ship_.ID),
                ship.get(Ship_.name).alias(Ship_.NAME),
                ship.get(Ship_.imoNumber).alias(Ship_.IMO_NUMBER)
        );
        cq.orderBy(
                cb.asc(cb.lower(ship.get(Ship_.name)))
        );
        TypedQuery<Tuple> query = em.createQuery(cq);
        List<Tuple> items = query.getResultList();
        return items.stream()
                .map(t -> ShipDto.builder()
                        .id(t.get(Ship_.ID, Long.class))
                        .label(t.get(Ship_.NAME, String.class))
                        .imoNumber(t.get(Ship_.IMO_NUMBER, String.class))
                        .build()
                ).collect(Collectors.toList());
    }
}
