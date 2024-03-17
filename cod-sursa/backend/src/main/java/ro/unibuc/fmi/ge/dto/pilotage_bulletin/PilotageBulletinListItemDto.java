package ro.unibuc.fmi.ge.dto.pilotage_bulletin;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@Builder
public class PilotageBulletinListItemDto {
    private Long id;
    private Instant estimatedStartTime;
    private String agentName;
    private String pilotageCompanyName;
    private String shipName;
    private String startLocationName;
    private String endLocationName;
    private Instant startMovementTime;
    private Instant endMovementTime;
    private Integer documentStatus;
}
