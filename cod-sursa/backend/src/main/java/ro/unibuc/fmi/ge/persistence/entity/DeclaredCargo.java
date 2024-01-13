package ro.unibuc.fmi.ge.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "marfa_declarata")
@Getter
@Setter
public class DeclaredCargo {
    @Id
    @SequenceGenerator(name = "marfa_declarata_seq", sequenceName = "marfa_declarata_seq")
    @GeneratedValue(generator = "marfa_declarata_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fk_am")
    private MaritimeEndorsement maritimeEndorsement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fk_marfa")
    private Cargo cargo;

    @Column(name = "cantitate_tone")
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
        DeclaredCargo other = (DeclaredCargo) obj;
        return id != null && id.equals(other.getId());
    }
}
