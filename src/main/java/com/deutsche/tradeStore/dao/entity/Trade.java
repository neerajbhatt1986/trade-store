package com.deutsche.tradeStore.dao.entity;

import com.deutsche.tradeStore.dto.DateUtils;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Trade {
    @Id @GeneratedValue
    private Integer id;
    private String tradeId;
    private Integer tradeVersion;
    private String counterPartyId;
    private String bookingId;
    private Date maturityDate;
    private Date createdDate;

    private transient boolean expired;

    public Trade() {
    }

    public Trade(String tradeId, Integer tradeVersion, String counterPartyId, String bookingId, Date maturityDate) {
        this.tradeId = tradeId;
        this.tradeVersion = tradeVersion;
        this.counterPartyId = counterPartyId;
        this.bookingId = bookingId;
        this.maturityDate = maturityDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Date getCreatedDate() {
        return createdDate;
    }


    public boolean isExpired() {
        return DateUtils.parseDate(this.maturityDate).getTime() < DateUtils.parseDate(new Date()).getTime();
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @PrePersist
    protected void onCreate() {
        this.createdDate = new Date();
    }

}
