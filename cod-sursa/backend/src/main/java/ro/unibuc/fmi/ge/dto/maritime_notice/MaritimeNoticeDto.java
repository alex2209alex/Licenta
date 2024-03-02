package ro.unibuc.fmi.ge.dto.maritime_notice;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ro.unibuc.fmi.ge.dto.GenericDto;
import ro.unibuc.fmi.ge.dto.ShipDto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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
    private Integer status;
    private String rejectionReason;
    private List<@Valid DeclaredCargoDto> cargos;

    public void addDeclaredCargo(DeclaredCargoDto dto) {
        if (cargos == null) {
            cargos = new ArrayList<>();
        }
        cargos.add(dto);
    }
}
