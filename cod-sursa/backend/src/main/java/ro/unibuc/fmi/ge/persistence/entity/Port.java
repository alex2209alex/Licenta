package ro.unibuc.fmi.ge.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Port {
    @Id
    @SequenceGenerator(name = "port_seq", sequenceName = "port_seq")
    @GeneratedValue(generator = "port_seq")
    private Long id;

    @Column(name = "cod_port")
    private String code;

    @Column(name = "den_port")
    private String name;

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
        Port other = (Port) obj;
        return id != null && id.equals(other.getId());
    }
}
