package ro.unibuc.fmi.ge.dto.maritime_notice;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ro.unibuc.fmi.ge.dto.GenericDto;

import java.math.BigDecimal;

@Getter
@Setter
public class DeclaredCargoDto {
    private Long id;
    @NotNull
    private @Valid GenericDto cargo;
    @NotNull
    private BigDecimal quantity;
}
