package ro.unibuc.fmi.ge.dto.maritime_notice;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@Builder
public class MaritimeNoticeListItemDto {
    private Long id;
    private Instant estimatedArrivalTime;
    private String agentName;
    private String shipName;
    private String portName;
    private Integer documentStatus;
    private Integer maritimeCallStatus;
}
