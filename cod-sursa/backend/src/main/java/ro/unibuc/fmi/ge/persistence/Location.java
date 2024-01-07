package ro.unibuc.fmi.ge.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "locatie")
@Getter
@Setter
public class Location {
    @Id
    @SequenceGenerator(name = "locatie_seq", sequenceName = "locatie_seq")
    @GeneratedValue(generator = "locatie_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fk_port")
    private Port port;

    @Column(name = "den_locatie")
    private String name;

    @Column(name = "adancime")
    private BigDecimal depth;

    @Column(name = "lungime")
    private BigDecimal length;

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
        Location other = (Location) obj;
        return id != null && id.equals(other.getId());
    }
}
