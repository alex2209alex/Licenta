package ro.unibuc.fmi.ge.service.maritime_endorsement.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ro.unibuc.fmi.ge.dto.MaritimeEndorsementDocumentStatus;
import ro.unibuc.fmi.ge.dto.maritime_endorsement.MaritimeEndorsementListItemDto;
import ro.unibuc.fmi.ge.dto.maritime_endorsement.MaritimeEndorsementSearchDto;
import ro.unibuc.fmi.ge.service.maritime_endorsement.MaritimeEndorsementVisualisationService;

import java.util.List;

@Service
@Transactional
public class MaritimeEndorsementVisualisationServiceImpl implements MaritimeEndorsementVisualisationService {
    @Override
    public List<MaritimeEndorsementListItemDto> search(MaritimeEndorsementSearchDto searchDto) {
        return buildDummyList();
    }

    private List<MaritimeEndorsementListItemDto> buildDummyList() {
        MaritimeEndorsementListItemDto listItemDto = MaritimeEndorsementListItemDto
                .builder()
                .agentName("Dummy agent")
                .documentStatus(MaritimeEndorsementDocumentStatus.AWAITING.id)
                .estimatedArrivalTime("21.01.2024")
                .shipName("Dummy ship")
                .build();
        return List.of(listItemDto);
    }
}
