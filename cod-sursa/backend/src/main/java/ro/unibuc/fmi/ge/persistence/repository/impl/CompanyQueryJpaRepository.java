package ro.unibuc.fmi.ge.persistence.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import ro.unibuc.fmi.ge.dto.CompanySearchDto;
import ro.unibuc.fmi.ge.dto.GenericDto;
import ro.unibuc.fmi.ge.persistence.entity.*;
import ro.unibuc.fmi.ge.persistence.repository.CompanyQueryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyQueryJpaRepository implements CompanyQueryRepository {
    private final EntityManager em;

    public CompanyQueryJpaRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<GenericDto> search(CompanySearchDto searchDto) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<Company> company = cq.from(Company.class);
        cq.multiselect(
                company.get(Company_.id).alias(Company_.ID),
                company.get(Company_.name).alias(Company_.NAME)
        );
        cq.orderBy(
                cb.asc(cb.lower(company.get(Company_.name)))
        );
        cq.where(buildWhere(searchDto, cb, company));
        TypedQuery<Tuple> query = em.createQuery(cq);
        List<Tuple> items = query.getResultList();
        return items.stream()
                .map(t -> GenericDto.builder()
                        .id(t.get(Company_.ID, Long.class))
                        .label(t.get(Company_.NAME, String.class))
                        .build())
                .collect(Collectors.toList());
    }

    private Predicate[] buildWhere(CompanySearchDto searchDto, CriteriaBuilder cb, Root<Company> company) {
        List<Predicate> predicates = new ArrayList<>();
        if (searchDto.getCompanyType() != null) {
            predicates.add(cb.and(cb.equal(company.get(Company_.type), searchDto.getCompanyType())));
        }
        return predicates.toArray(new Predicate[0]);
    }
}
