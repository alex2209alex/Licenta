package ro.unibuc.fmi.ge.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "avizare_maritima")
@Getter
@Setter
public class MaritimeEndorsement {
    @Id
    @SequenceGenerator(name = "avizare_maritima_seq", sequenceName = "avizare_maritima_seq")
    @GeneratedValue(generator = "avizare_maritima_seq")
    private Long id;

    @Column(name = "data_ora_estimare_sosire")
    private LocalDateTime estimatedArrivalTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fk_agent")
    private Company agent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fk_escala")
    private MaritimeCall maritimeCall;

    @Column(name = "data_creare")
    private LocalDateTime creationTime;

    @Column(name = "stare_document")
    private Integer documentStatus;

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
        MaritimeEndorsement other = (MaritimeEndorsement) obj;
        return id != null && id.equals(other.getId());
    }
}
