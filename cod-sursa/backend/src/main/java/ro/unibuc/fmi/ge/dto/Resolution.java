package ro.unibuc.fmi.ge.dto;

import java.util.HashMap;
import java.util.Map;

public enum Resolution {
    REJECTED(0),
    APPROVED(1);

    private static final Map<Integer, Resolution> BY_ID = new HashMap<>();

    static {
        for (Resolution e : values()) {
            BY_ID.put(e.id, e);
        }
    }

    public final Integer id;

    Resolution(Integer id) {
        this.id = id;
    }

    public static Resolution valueOfId(Integer id) {
        return BY_ID.get(id);
    }
}
