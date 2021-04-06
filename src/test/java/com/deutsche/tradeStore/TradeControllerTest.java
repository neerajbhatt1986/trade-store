package com.deutsche.tradeStore;

import com.deutsche.tradeStore.dao.entity.Trade;
import com.deutsche.tradeStore.dto.ErrorResponse;
import com.deutsche.tradeStore.dto.TradeRequestDTO;
import com.deutsche.tradeStore.dto.TradeResponseDTO;
import com.deutsche.tradeStore.service.TradeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TradeControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private TradeService tradeService;

    @Test
    public void getTrades(){
        LocalDate maturityDate = LocalDate.now().plusMonths(3);
        Trade t1 = new TradeBuilder().tradeId("T1").tradeVersion(1).bookingId("B1").counterPartyId("CP-1").maturityDate(maturityDate).createdDate(LocalDate.now()).expired(false).build();
        Mockito.when(tradeService.getTrades()).thenReturn(Arrays.asList(t1));
        ResponseEntity<TradeResponseDTO[]> response = restTemplate.getForEntity("/trades", TradeResponseDTO[].class);
        Assertions.assertThat(response.getBody().length).isEqualTo(1);
        TradeResponseDTO tradeResponseDTO = response.getBody()[0];
        assertThat(tradeResponseDTO.getTradeId()).isEqualTo("T1");
        assertThat(tradeResponseDTO.getTradeVersion()).isEqualTo(1);
        assertThat(tradeResponseDTO.getBookingId()).isEqualTo("B1");
        assertThat(tradeResponseDTO.getCounterPartyId()).isEqualTo("CP-1");
        assertThat(tradeResponseDTO.isExpired()).isEqualTo(false);
    }


    @Test
    public void addTrade(){

        ResponseEntity<ErrorResponse> response = restTemplate.postForEntity("/trade/save", new TradeRequestDTO(), ErrorResponse.class);
        Assertions.assertThat(response.getBody().getErrors().size()).isEqualTo(5);
        Assertions.assertThat(response.getBody().getErrors())
                .contains("Booking id cannot be blank")
                .contains("Trade version cannot be blank.")
                .contains("Maturity Date cannot be blank")
                .contains("Counter Party cannot be blank")
                .contains("Trade Id cannot be blank.");

    }

}
