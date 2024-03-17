package ro.unibuc.fmi.ge.dto.pilotage_bulletin;

import lombok.Getter;
import lombok.Setter;
import ro.unibuc.fmi.ge.dto.PilotageBulletinDocumentStatus;

@Setter
@Getter
public class PilotageBulletinSearchDto {
    private Integer documentStatus;
    private Long companyId;
    private Boolean isAgent;
    private Boolean isPilotage;

    public PilotageBulletinDocumentStatus getDocumentStatus() {
        if (documentStatus != null) {
            return PilotageBulletinDocumentStatus.valueOfId(documentStatus);
        }
        return null;
    }
}
