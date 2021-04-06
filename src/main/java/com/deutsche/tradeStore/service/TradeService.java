package com.deutsche.tradeStore.service;

import com.deutsche.tradeStore.dao.entity.Trade;
import com.deutsche.tradeStore.dto.TradeRequestDTO;
import com.deutsche.tradeStore.dto.TradeResponseDTO;

import java.util.List;

public interface TradeService {
    List<Trade> getTrades();
    Trade saveTrade(Trade tradeDTO);
}
