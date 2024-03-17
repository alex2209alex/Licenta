package ro.unibuc.fmi.ge.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ro.unibuc.fmi.ge.dto.MaritimeNoticeDocumentStatus;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "avizare_maritima")
@Getter
@Setter
public class MaritimeNotice {
    @Id
    @SequenceGenerator(name = "avizare_maritima_seq", sequenceName = "avizare_maritima_seq")
    @GeneratedValue(generator = "avizare_maritima_seq")
    private Long id;

    @Column(name = "data_ora_estimare_sosire")
    private Instant estimatedArrivalDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fk_agent")
    private Company agent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fk_escala")
    private MaritimeCall maritimeCall;

    @Column(name = "data_creare")
    private Instant creationTime;

    @Column(name = "stare_document")
    private MaritimeNoticeDocumentStatus documentStatus;

    @Column(name = "motiv_respingere")
    private String rejectionReason;

    @OneToMany(mappedBy = "maritimeNotice")
    private Set<DeclaredCargo> declaredCargos;

    public void addDeclaredCargo(DeclaredCargo declaredCargo) {
        if (declaredCargos == null) {
            declaredCargos = new HashSet<>();
        }
        declaredCargos.add(declaredCargo);
        declaredCargo.setMaritimeNotice(this);
    }

    public void removeDeclaredCargo(DeclaredCargo declaredCargo) {
        if (declaredCargos == null) {
            declaredCargos = new HashSet<>();
        }
        declaredCargos.remove(declaredCargo);
        declaredCargo.setMaritimeNotice(null);
    }

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
        MaritimeNotice other = (MaritimeNotice) obj;
        return id != null && id.equals(other.getId());
    }
}
