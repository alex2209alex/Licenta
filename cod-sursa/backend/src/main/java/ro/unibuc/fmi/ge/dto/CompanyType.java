package ro.unibuc.fmi.ge.dto;

import java.util.HashMap;
import java.util.Map;

public enum CompanyType {
    AGENT(0),
    CARGO_OPERATOR(1),
    AUTHORITY(2),
    PILOTAGE(3);

    private static final Map<Integer, CompanyType> BY_ID = new HashMap<>();

    static {
        for (CompanyType e : values()) {
            BY_ID.put(e.id, e);
        }
    }

    public final Integer id;

    CompanyType(Integer id) {
        this.id = id;
    }

    public static CompanyType valueOfId(Integer id) {
        return BY_ID.get(id);
    }
}
