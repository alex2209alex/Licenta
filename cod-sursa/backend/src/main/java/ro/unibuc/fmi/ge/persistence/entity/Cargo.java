package ro.unibuc.fmi.ge.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "marfa")
@Getter
@Setter
public class Cargo {
    @Id
    @SequenceGenerator(name = "marfa_seq", sequenceName = "marfa_seq")
    @GeneratedValue(generator = "marfa_seq")
    private Long id;

    @Column(name = "den_marfa")
    private String name;

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
        Cargo other = (Cargo) obj;
        return id != null && id.equals(other.getId());
    }
}
