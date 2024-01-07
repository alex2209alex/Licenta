package ro.unibuc.fmi.ge.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "buletin_pilotaj")
@Getter
@Setter
public class PilotageBulletin {
    @Id
    @SequenceGenerator(name = "buletin_pilotaj_seq", sequenceName = "buletin_pilotaj_seq")
    @GeneratedValue(generator = "buletin_pilotaj_seq")
    private Long id;

    @Column(name = "data_ora_estimare_start")
    private LocalDateTime estimatedStartTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fk_operator_marfa")
    private Company cargoOperatingCompany;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fk_pilotaj")
    private Company pilotageCompany;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fk_agent")
    private Company agent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fk_escala")
    private MaritimeCall maritimeCall;

    @Column(name = "data_creare")
    private LocalDateTime creationDate;

    @Column(name = "stare_document")
    private Integer status;

    @Column(name = "motiv_respingere")
    private String rejectionReason;

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
        PilotageBulletin other = (PilotageBulletin) obj;
        return id != null && id.equals(other.getId());
    }
}
