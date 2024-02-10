package ro.unibuc.fmi.ge.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.fmi.ge.persistence.entity.ShipMovement;

@Repository
public interface ShipMovementRepository extends JpaRepository<ShipMovement, Long> {
}
