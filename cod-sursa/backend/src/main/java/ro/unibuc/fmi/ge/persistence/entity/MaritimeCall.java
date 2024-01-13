package ro.unibuc.fmi.ge.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "escala")
@Getter
@Setter
public class MaritimeCall {
    @Id
    @SequenceGenerator(name = "escala_seq", sequenceName = "escala_seq")
    @GeneratedValue(generator = "escala_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fk_nava")
    private Ship ship;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fk_port")
    private Port port;

    @Column(name = "stare_escala")
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
        MaritimeCall other = (MaritimeCall) obj;
        return id != null && id.equals(other.getId());
    }
}
