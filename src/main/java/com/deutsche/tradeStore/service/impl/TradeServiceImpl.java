package com.deutsche.tradeStore.service.impl;

import com.deutsche.tradeStore.dao.entity.Trade;
import com.deutsche.tradeStore.dto.DateUtils;
import com.deutsche.tradeStore.dto.TradeRequestDTO;
import com.deutsche.tradeStore.dao.repositry.TradeRepository;
import com.deutsche.tradeStore.dto.TradeResponseDTO;
import com.deutsche.tradeStore.exception.AppInternalException;
import com.deutsche.tradeStore.service.TradeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TradeServiceImpl implements TradeService {
    @Autowired
    private TradeRepository tradeRepository;


    @Override
    @Transactional
    public List<Trade> getTrades() {
        return tradeRepository.getTrades().collect(Collectors.toList());
    }

    public Trade saveTrade(Trade trade){
        if (DateUtils.parseDate(trade.getMaturityDate()).getTime() <  DateUtils.parseDate(new Date()).getTime())
            throw new AppInternalException("Cannot add Trade with past Maturity Date.");

        Trade exitingTrade = tradeRepository.findLatestTradeByTradeId(trade.getTradeId()).orElse(null);

        if (exitingTrade != null) {
            if (exitingTrade.getTradeVersion() > trade.getTradeVersion())
                throw new AppInternalException("Can not add Trade with lower version.");

            if(exitingTrade.getTradeVersion().equals(trade.getTradeVersion()) ){
                trade.setId(exitingTrade.getId());
                trade.setCreatedDate(exitingTrade.getCreatedDate());
            }
        }
        return tradeRepository.save(trade);
    }
}
