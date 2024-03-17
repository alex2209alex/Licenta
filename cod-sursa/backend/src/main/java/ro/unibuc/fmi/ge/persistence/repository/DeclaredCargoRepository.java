package ro.unibuc.fmi.ge.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.unibuc.fmi.ge.persistence.entity.DeclaredCargo;

@Repository
public interface DeclaredCargoRepository extends JpaRepository<DeclaredCargo, Long> {
    @Modifying
    @Query("DELETE FROM DeclaredCargo dc WHERE dc.maritimeNotice.id = :idMaritimeNotice")
    void deleteAllByMaritimeNoticeId(@Param("idMaritimeNotice") Long idMaritimeNotice);
}
