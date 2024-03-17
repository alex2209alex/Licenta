package ro.unibuc.fmi.ge.dto;

import java.util.HashMap;
import java.util.Map;

public enum ShipMovementStatus {
    PLANNED(0),
    FINISHED(1),
    CANCELLED(2);

    private static final Map<Integer, ShipMovementStatus> BY_ID = new HashMap<>();

    static {
        for (ShipMovementStatus e : values()) {
            BY_ID.put(e.id, e);
        }
    }

    public final Integer id;

    ShipMovementStatus(Integer id) {
        this.id = id;
    }

    public static ShipMovementStatus valueOfId(Integer id) {
        return BY_ID.get(id);
    }
}
