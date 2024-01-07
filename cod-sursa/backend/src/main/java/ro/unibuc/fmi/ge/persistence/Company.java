package ro.unibuc.fmi.ge.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "firma")
@Getter
@Setter
public class Company {
    @Id
    @SequenceGenerator(name = "firma_seq", sequenceName = "firma_seq")
    @GeneratedValue(generator = "firma_seq")
    private Long id;

    @Column(name = "den_firma")
    private String name;

    @Column(name = "tip_firma")
    private Integer type;

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
        Company other = (Company) obj;
        return id != null && id.equals(other.getId());
    }
}
