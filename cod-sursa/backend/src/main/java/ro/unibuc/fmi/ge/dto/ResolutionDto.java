package ro.unibuc.fmi.ge.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResolutionDto {
    @NotNull
    private Integer resolution;
    private String rejectionReason;

    public Resolution getResolutionEnum() {
        return Resolution.valueOfId(resolution);
    }
}
