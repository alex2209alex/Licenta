package ro.unibuc.fmi.ge.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CompanySearchDto {
    private Integer companyType;

    public CompanyType getCompanyType() {
        if (companyType != null) {
            return CompanyType.valueOfId(companyType);
        }
        return null;
    }
}
