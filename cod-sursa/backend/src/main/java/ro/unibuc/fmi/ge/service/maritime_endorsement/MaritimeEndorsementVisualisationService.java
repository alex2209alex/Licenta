package ro.unibuc.fmi.ge.service.maritime_endorsement;

import ro.unibuc.fmi.ge.dto.maritime_endorsement.MaritimeEndorsementListItemDto;
import ro.unibuc.fmi.ge.dto.maritime_endorsement.MaritimeEndorsementSearchDto;

import java.util.List;

public interface MaritimeEndorsementVisualisationService {
    List<MaritimeEndorsementListItemDto> search(MaritimeEndorsementSearchDto searchDto);
}
