package ro.unibuc.fmi.ge.dto.maritime_endorsement;

import lombok.Getter;
import lombok.Setter;
import ro.unibuc.fmi.ge.dto.MaritimeEndorsementDocumentStatus;

@Setter
@Getter
public class MaritimeEndorsementSearchDto {
    private Integer documentStatus;

    public MaritimeEndorsementDocumentStatus getDocumentStatus() {
        if (documentStatus != null) {
            return MaritimeEndorsementDocumentStatus.valueOfId(documentStatus);
        }
        return null;
    }
}
