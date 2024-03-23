package ro.unibuc.fmi.ge.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@SuperBuilder
@Jacksonized
public class MaritimeCallDto extends GenericDto {
    private Integer maritimeCallStatus;
}
