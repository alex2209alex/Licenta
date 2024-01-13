package ro.unibuc.fmi.ge.service.maritime_notice.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.unibuc.fmi.ge.dto.MaritimeNoticeDocumentStatus;
import ro.unibuc.fmi.ge.dto.maritime_notice.MaritimeNoticeListItemDto;
import ro.unibuc.fmi.ge.dto.maritime_notice.MaritimeNoticeSearchDto;
import ro.unibuc.fmi.ge.service.maritime_notice.MaritimeNoticeFinderService;

import java.util.List;

@Service
@Transactional
public class MaritimeNoticeFinderServiceImpl implements MaritimeNoticeFinderService {
    @Override
    public List<MaritimeNoticeListItemDto> search(MaritimeNoticeSearchDto searchDto) {
        return buildDummyList();
    }

    private List<MaritimeNoticeListItemDto> buildDummyList() {
        MaritimeNoticeListItemDto listItemDto = MaritimeNoticeListItemDto
                .builder()
                .agentName("Dummy agent")
                .documentStatus(MaritimeNoticeDocumentStatus.AWAITING.id)
                .estimatedArrivalTime("21.01.2024")
                .shipName("Dummy ship")
                .build();
        return List.of(listItemDto, listItemDto, listItemDto, listItemDto, listItemDto);
    }
}
