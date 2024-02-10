package ro.unibuc.fmi.ge.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.fmi.ge.persistence.entity.Cargo;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {
}
