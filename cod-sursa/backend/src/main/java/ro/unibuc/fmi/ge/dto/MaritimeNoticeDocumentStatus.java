package ro.unibuc.fmi.ge.dto;

import java.util.HashMap;
import java.util.Map;

public enum MaritimeNoticeDocumentStatus {
    AWAITING(0),
    ANR_APPROVED(1),
    ANR_APMC_APPROVED(2),
    REJECTED(3),
    CANCELED(4);

    private static final Map<Integer, MaritimeNoticeDocumentStatus> BY_ID = new HashMap<>();

    static {
        for (MaritimeNoticeDocumentStatus e : values()) {
            BY_ID.put(e.id, e);
        }
    }

    public final Integer id;

    MaritimeNoticeDocumentStatus(Integer id) {
        this.id = id;
    }

    public static MaritimeNoticeDocumentStatus valueOfId(Integer id) {
        return BY_ID.get(id);
    }
}
