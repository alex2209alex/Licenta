package ro.unibuc.fmi.ge.dto;

import java.util.HashMap;
import java.util.Map;

public enum MaritimeCallStatus {
    PLANNED(0),
    COMPLETED(1),
    CANCELLED(2),
    ARCHIVED(3),
    APPROVED(4);

    private static final Map<Integer, MaritimeCallStatus> BY_ID = new HashMap<>();

    static {
        for (MaritimeCallStatus e : values()) {
            BY_ID.put(e.id, e);
        }
    }

    public final Integer id;

    MaritimeCallStatus(Integer id) {
        this.id = id;
    }

    public static MaritimeCallStatus valueOfId(Integer id) {
        return BY_ID.get(id);
    }
}
