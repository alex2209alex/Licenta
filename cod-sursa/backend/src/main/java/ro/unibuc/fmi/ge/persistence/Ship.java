package ro.unibuc.fmi.ge.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "nava")
@Getter
@Setter
public class Ship {
    @Id
    @SequenceGenerator(name = "nava_seq", sequenceName = "nava_seq")
    @GeneratedValue(generator = "nava_seq")
    private Long id;

    @Column(name = "den_nava")
    private String name;

    @Column(name = "numar_imo")
    private String imoNumber;

    @Column(name = "lungime")
    private BigDecimal length;

    @Column(name = "pescaj_maxim")
    private BigDecimal maximumDraft;

    private BigDecimal dwt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fk_tara")
    private Country country;

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
        Ship other = (Ship) obj;
        return id != null && id.equals(other.getId());
    }
}
