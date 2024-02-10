package ro.unibuc.fmi.ge.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.fmi.ge.persistence.entity.MaritimeNotice;

@Repository
public interface MaritimeNoticeRepository extends JpaRepository<MaritimeNotice, Long> {
}
