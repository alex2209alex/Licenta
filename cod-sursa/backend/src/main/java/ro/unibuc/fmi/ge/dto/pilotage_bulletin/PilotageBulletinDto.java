package ro.unibuc.fmi.ge.dto.pilotage_bulletin;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ro.unibuc.fmi.ge.dto.GenericDto;
import ro.unibuc.fmi.ge.dto.MaritimeCallDto;

import java.time.Instant;

@Getter
@Setter
public class PilotageBulletinDto {
    private Long id;
    @NotNull
    private Instant estimatedStartTime;
    private GenericDto cargoOperatingCompany;
    @NotNull
    private @Valid GenericDto pilotageCompany;
    private GenericDto agent;
    @NotNull
    private MaritimeCallDto maritimeCall;
    @NotNull
    private @Valid GenericDto startLocation;
    @NotNull
    private @Valid GenericDto endLocation;
    @NotNull
    private Integer shipMovementType;
}
