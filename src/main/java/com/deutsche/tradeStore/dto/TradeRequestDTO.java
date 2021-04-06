package com.deutsche.tradeStore.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class TradeRequestDTO {
    @NotBlank(message = "Trade Id cannot be blank.")
    private String tradeId;
    @NotNull(message = "Trade version cannot be blank.")
    private Integer tradeVersion;
    @NotBlank(message = "Counter Party cannot be blank")
    private String counterPartyId;
    @NotBlank(message = "Booking id cannot be blank")
    private String bookingId;
    @NotNull(message = "Maturity Date cannot be blank")
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date maturityDate;

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public Integer getTradeVersion() {
        return tradeVersion;
    }

    public void setTradeVersion(Integer tradeVersion) {
        this.tradeVersion = tradeVersion;
    }

    public String getCounterPartyId() {
        return counterPartyId;
    }

    public void setCounterPartyId(String counterPartyId) {
        this.counterPartyId = counterPartyId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public Date getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(Date maturityDate) {
        this.maturityDate = maturityDate;
    }


}
