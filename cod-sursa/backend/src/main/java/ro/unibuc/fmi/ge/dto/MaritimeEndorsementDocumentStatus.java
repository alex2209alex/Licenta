package ro.unibuc.fmi.ge.dto;

import java.util.HashMap;
import java.util.Map;

public enum MaritimeEndorsementDocumentStatus {
    AWAITING(0),
    ANR_APPROVED(1),
    ANR_APMC_APPROVED(2),
    REJECTED(3),
    CANCELED(4);

    private static final Map<Integer, MaritimeEndorsementDocumentStatus> BY_ID = new HashMap<>();

    static {
        for (MaritimeEndorsementDocumentStatus e : values()) {
            BY_ID.put(e.id, e);
        }
    }

    public final Integer id;

    MaritimeEndorsementDocumentStatus(Integer id) {
        this.id = id;
    }

    public static MaritimeEndorsementDocumentStatus valueOfId(Integer id) {
        return BY_ID.get(id);
    }
}
