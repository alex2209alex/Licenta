package ro.unibuc.fmi.ge.dto.maritime_notice;

import lombok.Getter;
import lombok.Setter;
import ro.unibuc.fmi.ge.dto.MaritimeNoticeDocumentStatus;

@Setter
@Getter
public class MaritimeNoticeSearchDto {
    private Integer documentStatus;
    private Long companyId;
    private Boolean isAuthority;

    public MaritimeNoticeDocumentStatus getDocumentStatus() {
        if (documentStatus != null) {
            return MaritimeNoticeDocumentStatus.valueOfId(documentStatus);
        }
        return null;
    }
}
