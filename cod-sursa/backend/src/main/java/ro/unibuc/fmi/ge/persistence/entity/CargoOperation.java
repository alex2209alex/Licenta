package ro.unibuc.fmi.ge.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "operare_marfa")
@Getter
@Setter
public class CargoOperation {
    @Id
    @SequenceGenerator(name = "operare_marfa_seq", sequenceName = "operare_marfa_seq")
    @GeneratedValue(generator = "operare_marfa_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fk_escala")
    private MaritimeCall maritimeCall;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fk_locatie")
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fk_marfa")
    private Cargo cargo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fk_firma_operare")
    private Company operator;

    @Column(name="tip_operatie")
    private Integer type;

    @Column(name="cantitate_tone")
    private BigDecimal quantity;


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
        CargoOperation other = (CargoOperation) obj;
        return id != null && id.equals(other.getId());
    }
}
