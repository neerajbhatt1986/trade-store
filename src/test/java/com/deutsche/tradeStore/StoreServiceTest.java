package com.deutsche.tradeStore;

import com.deutsche.tradeStore.dao.entity.Trade;
import com.deutsche.tradeStore.dao.repositry.TradeRepository;
import com.deutsche.tradeStore.dto.TradeRequestDTO;
import com.deutsche.tradeStore.dto.TradeResponseDTO;
import com.deutsche.tradeStore.exception.AppInternalException;
import com.deutsche.tradeStore.service.TradeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(TradeStoreApplication.class)
public class StoreServiceTest {

    @Autowired
    private TradeService tradeService;
    @Autowired
    TradeRepository tradeRepository;

    @Test
    public void test(){
        tradeService.getTrades();
    }

    @Test
    public void addTrade(){
        LocalDate maturityDate = LocalDate.now().plusMonths(5);
        Trade t1 = new TradeBuilder().tradeId("T1").tradeVersion(1).bookingId("B1").counterPartyId("CP-1").maturityDate(maturityDate).build();
        Trade trade = tradeService.saveTrade(t1);
        assertThat(trade).isNotNull();
        assertThat(tradeRepository.findByTradeId("T1")).isNotNull();

        assertThat(trade.getTradeId()).isEqualTo("T1");
        assertThat(trade.getTradeVersion()).isEqualTo(1);
        assertThat(trade.getBookingId()).isEqualTo("B1");
        assertThat(trade.getCounterPartyId()).isEqualTo("CP-1");
        assertThat(trade.getMaturityDate()).isEqualTo(TradeBuilder.getDate(maturityDate));
        assertThat(trade.isExpired()).isEqualTo(false);

    }



    @Test
    public void addTrade_with_pastMaturityDate(){

        Trade newTrade = new TradeBuilder().tradeId("T1").tradeVersion(1).bookingId("B1").counterPartyId("CP-2").maturityDate(LocalDate.now().minusDays(2)).build();
        AppInternalException appInternalException = Assertions.assertThrows(AppInternalException.class, () -> tradeService.saveTrade(newTrade));
        assertThat(appInternalException.getMessage()).isEqualTo("Cannot add Trade with past Maturity Date.");

    }

    @Test
    public void addTrade_with_lowerVersionId(){

        String tradeId = "T2";
        Trade existingTrade = new Trade(tradeId, 5, "CP-2", "B2", new Date());
        tradeRepository.save(existingTrade);

        Trade newTrade = new TradeBuilder().tradeId(tradeId).tradeVersion(1).bookingId("B1").counterPartyId("CP-1").maturityDate(LocalDate.now().plusMonths(5)).build();
        AppInternalException appInternalException = Assertions.assertThrows(AppInternalException.class, () -> tradeService.saveTrade(newTrade));
        assertThat(appInternalException.getMessage()).isEqualTo("Can not add Trade with lower version.");
    }

    @Test
    public void addTrade_with_sameVersionId(){

        String tradeId = "T3";
        Trade existingTrade = new Trade(tradeId, 3, "CP-3", "B3", TradeBuilder.getDate(LocalDate.now().plusMonths(2)));
        tradeRepository.save(existingTrade);


        LocalDate localDate = LocalDate.now().plusMonths(3);
        Trade newTrade = new TradeBuilder().tradeId(tradeId).tradeVersion(3).bookingId("B4").counterPartyId("CP-4").maturityDate(localDate).build();
        Trade trade = tradeService.saveTrade(newTrade);

        assertThat(trade.getTradeId()).isEqualTo(tradeId);
        assertThat(trade.getTradeVersion()).isEqualTo(3);
        assertThat(trade.getBookingId()).isEqualTo("B4");
        assertThat(trade.getCounterPartyId()).isEqualTo("CP-4");
        assertThat(trade.getMaturityDate()).isEqualTo(TradeBuilder.getDate(localDate));
        assertThat(trade.isExpired()).isEqualTo(false);

    }

    @Test
    public void validate_expired_trade(){
        String tradeId = "T4";
        LocalDate maturityDate = LocalDate.now().minusMonths(2);
        Trade existingTrade = new Trade(tradeId, 3, "CP-3", "B3", TradeBuilder.getDate(maturityDate));
        tradeRepository.save(existingTrade);


        Trade trade = tradeService.getTrades().stream().filter(t -> t.getTradeId().equals(tradeId)).findFirst().get();
        assertThat(trade.getTradeId()).isEqualTo(tradeId);
        assertThat(trade.getTradeVersion()).isEqualTo(3);
        assertThat(trade.getBookingId()).isEqualTo("B3");
        assertThat(trade.getCounterPartyId()).isEqualTo("CP-3");
        assertThat(trade.isExpired()).isEqualTo(true);

    }


    @Test
    public void addTrade_with_diffrentVersionId(){

        //Pre condition
        String tradeId = "T5";
        Trade existingTrade = new Trade(tradeId, 1, "CP-1", "B1", TradeBuilder.getDate(LocalDate.now().plusMonths(2)));
        tradeRepository.save(existingTrade);
        long beforeTradeCount = tradeRepository.count();

        LocalDate localDate = LocalDate.now().plusMonths(3);
        Trade newTrade = new TradeBuilder().tradeId(tradeId).tradeVersion(2).bookingId("B2").counterPartyId("CP-2").maturityDate(localDate).build();
        Trade trade = tradeService.saveTrade(newTrade);


        assertThat(tradeRepository.count()).isEqualTo(beforeTradeCount+1);

        assertThat(trade.getTradeId()).isEqualTo(tradeId);
        assertThat(trade.getTradeVersion()).isEqualTo(2);
        assertThat(trade.getBookingId()).isEqualTo("B2");
        assertThat(trade.getCounterPartyId()).isEqualTo("CP-2");
        assertThat(trade.getMaturityDate()).isEqualTo(TradeBuilder.getDate(localDate));
        assertThat(trade.isExpired()).isEqualTo(false);

    }

    @Test
    public void addTrade_todayDate(){
        LocalDate maturityDate = LocalDate.now();
        Trade t1 = new TradeBuilder().tradeId("T6").tradeVersion(1).bookingId("B1").counterPartyId("CP-1").maturityDate(maturityDate).build();
        Trade trade = tradeService.saveTrade(t1);
        assertThat(trade).isNotNull();
        assertThat(tradeRepository.findByTradeId("T6")).isNotNull();

        assertThat(trade.getTradeId()).isEqualTo("T6");
        assertThat(trade.getTradeVersion()).isEqualTo(1);
        assertThat(trade.getBookingId()).isEqualTo("B1");
        assertThat(trade.getCounterPartyId()).isEqualTo("CP-1");
        assertThat(trade.getMaturityDate()).isEqualTo(TradeBuilder.getDate(maturityDate));
        assertThat(trade.isExpired()).isEqualTo(false);

    }

}
