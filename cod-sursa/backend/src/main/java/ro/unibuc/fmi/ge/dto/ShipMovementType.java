package ro.unibuc.fmi.ge.dto;

import java.util.HashMap;
import java.util.Map;

public enum ShipMovementType {
    ENTERING(0),
    MOVEMENT(1),
    EXITING(2);

    private static final Map<Integer, ShipMovementType> BY_ID = new HashMap<>();

    static {
        for (ShipMovementType e : values()) {
            BY_ID.put(e.id, e);
        }
    }

    public final Integer id;

    ShipMovementType(Integer id) {
        this.id = id;
    }

    public static ShipMovementType valueOfId(Integer id) {
        return BY_ID.get(id);
    }
}
