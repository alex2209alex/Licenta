package ro.unibuc.fmi.ge.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.fmi.ge.persistence.entity.PilotageBulletin;

@Repository
public interface PilotageBulletinRepository extends JpaRepository<PilotageBulletin, Long> {
}
