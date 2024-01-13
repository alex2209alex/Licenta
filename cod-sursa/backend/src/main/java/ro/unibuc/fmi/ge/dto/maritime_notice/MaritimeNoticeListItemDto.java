package ro.unibuc.fmi.ge.dto.maritime_notice;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class MaritimeNoticeListItemDto {
    private String estimatedArrivalTime;

    private String agentName;

    private String shipName;

    private Integer documentStatus;
}
