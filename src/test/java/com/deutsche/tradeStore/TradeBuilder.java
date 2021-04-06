package com.deutsche.tradeStore;

import com.deutsche.tradeStore.dao.entity.Trade;
import com.deutsche.tradeStore.dto.TradeRequestDTO;
import com.deutsche.tradeStore.dto.TradeResponseDTO;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class TradeBuilder {
    private String tradeId;
    private Integer tradeVersion;
    private String counterPartyId;
    private String bookingId;
    private LocalDate maturity;
    private boolean expired;
    private LocalDate createdDate;

    public String getTradeId() {
        return tradeId;
    }

    public Integer getTradeVersion() {
        return tradeVersion;
    }

    public String getCounterPartyId() {
        return counterPartyId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public LocalDate getMaturity() {
        return maturity;
    }

    public TradeBuilder tradeId(String tradeId) {
        this.tradeId = tradeId;
        return this;
    }

    public TradeBuilder tradeVersion(Integer tradeVersion) {
        this.tradeVersion = tradeVersion;
        return this;
    }

    public TradeBuilder counterPartyId(String counterPartyId) {
        this.counterPartyId = counterPartyId;
        return this;
    }

    public TradeBuilder bookingId(String bookingId) {
        this.bookingId = bookingId;
        return this;
    }

    public TradeBuilder maturityDate(LocalDate maturityDate) {
        this.maturity = maturityDate;
        return this;
    }

    public TradeBuilder createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public TradeBuilder expired(boolean expired) {
        this.expired = expired;
        return this;
    }


    public Trade build(){
        Trade trade = new Trade();
        trade.setTradeId(this.getTradeId());
        trade.setBookingId(this.getBookingId());
        trade.setCounterPartyId(this.getCounterPartyId());
        trade.setTradeVersion(this.getTradeVersion());
        trade.setMaturityDate(getDate(this.maturity));
        return trade;

    }


    public static Date getDate(LocalDate localDate){
        ZoneId defaultZoneId = ZoneId.systemDefault();
        return Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
    }
}
