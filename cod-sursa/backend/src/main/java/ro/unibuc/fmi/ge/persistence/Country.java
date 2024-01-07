package ro.unibuc.fmi.ge.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tara")
@Getter
@Setter
public class Country {
    @Id
    @SequenceGenerator(name = "tara_seq", sequenceName = "tara_seq")
    @GeneratedValue(generator = "tara_seq")
    private Long id;

    @Column(name = "cod_tara")
    private String code;

    @Column(name = "den_tara")
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
        Country other = (Country) obj;
        return id != null && id.equals(other.getId());
    }
}
