package ro.unibuc.fmi.ge.dto;

import java.util.HashMap;
import java.util.Map;

public enum PilotageBulletinDocumentStatus {
    AWAITING(0),
    ANR_APPROVED(1),
    ANR_APMC_APPROVED(2),
    REALISED_BY_PILOTAGE_COMPANY(3),
    REJECTED(4),
    CANCELLED(5);

    private static final Map<Integer, PilotageBulletinDocumentStatus> BY_ID = new HashMap<>();

    static {
        for (PilotageBulletinDocumentStatus e : values()) {
            BY_ID.put(e.id, e);
        }
    }

    public final Integer id;

    PilotageBulletinDocumentStatus(Integer id) {
        this.id = id;
    }

    public static PilotageBulletinDocumentStatus valueOfId(Integer id) {
        return BY_ID.get(id);
    }
}
