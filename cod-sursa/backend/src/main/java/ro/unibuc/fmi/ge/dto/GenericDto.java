package ro.unibuc.fmi.ge.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class GenericDto {
    private Long id;
    private String label;
}
