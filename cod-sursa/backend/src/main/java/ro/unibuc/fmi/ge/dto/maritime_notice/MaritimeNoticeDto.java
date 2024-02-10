package ro.unibuc.fmi.ge.dto.maritime_notice;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ro.unibuc.fmi.ge.dto.GenericDto;
import ro.unibuc.fmi.ge.dto.ShipDto;

import java.time.Instant;

@Getter
@Setter
public class MaritimeNoticeDto {
    private Long id;
    @NotNull
    private Instant estimatedArrivalDateTime;
    @NotNull
    private @Valid ShipDto ship;
    @NotNull
    private @Valid GenericDto port;
    private GenericDto agent;
}
