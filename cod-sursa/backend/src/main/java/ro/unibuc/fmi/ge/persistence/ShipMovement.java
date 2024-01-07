package ro.unibuc.fmi.ge.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "manevra")
@Getter
@Setter
public class ShipMovement {
    @Id
    @SequenceGenerator(name = "manevra_seq", sequenceName = "manevra_seq")
    @GeneratedValue(generator = "manevra_seq")
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
    private LocalDateTime beginingTime;

    @Column(name = "data_ora_final")
    private LocalDateTime finishTime;

    @Column(name = "tip_manevra")
    private Integer type;

    @Column(name = "stare_manevra")
    private Integer status;


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
