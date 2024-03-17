package ro.unibuc.fmi.ge.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ro.unibuc.fmi.ge.dto.ShipMovementStatus;
import ro.unibuc.fmi.ge.dto.ShipMovementType;

import java.time.Instant;

@Entity
@Table(name = "manevra")
@Getter
@Setter
public class ShipMovement {
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fk_escala")
    private MaritimeCall maritimeCall;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fk_locatie_plecare")
    private Location startLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fk_locatie_sosire")
    private Location endLocation;

    @Column(name = "data_ora_start")
    private Instant startMovementTime;

    @Column(name = "data_ora_final")
    private Instant endMovementTime;

    @Column(name = "tip_manevra")
    private ShipMovementType type;

    @Column(name = "stare_manevra")
    private ShipMovementStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private PilotageBulletin pilotageBulletin;

    @Override
    public int hashCode() {
        return 13;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ShipMovement other = (ShipMovement) obj;
        return id != null && id.equals(other.getId());
    }
}
