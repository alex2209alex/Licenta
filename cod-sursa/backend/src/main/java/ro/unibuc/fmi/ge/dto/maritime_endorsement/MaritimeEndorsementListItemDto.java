package ro.unibuc.fmi.ge.dto.maritime_endorsement;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class MaritimeEndorsementListItemDto {
    private String estimatedArrivalTime;

    private String agentName;

    private String shipName;

    private Integer documentStatus;
}
