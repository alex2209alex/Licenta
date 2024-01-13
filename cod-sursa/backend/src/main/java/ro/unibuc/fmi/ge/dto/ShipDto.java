package ro.unibuc.fmi.ge.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class ShipDto extends GenericDto {
    private String imoNumber;

    @Override
    public String getLabel() {
        return super.getLabel() + ", " + imoNumber;
    }
}
