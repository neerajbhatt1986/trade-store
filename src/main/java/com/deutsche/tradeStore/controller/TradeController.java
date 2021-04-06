package com.deutsche.tradeStore.controller;

import com.deutsche.tradeStore.dao.entity.Trade;
import com.deutsche.tradeStore.dto.TradeRequestDTO;
import com.deutsche.tradeStore.dto.TradeResponseDTO;
import com.deutsche.tradeStore.service.TradeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TradeController {

    @Autowired
    private TradeService tradeService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("trades")
    List<TradeResponseDTO> getTrades(){
        return tradeService.getTrades().stream().map(t -> modelMapper.map(t, TradeResponseDTO.class)).collect(Collectors.toList());
    }

    @PostMapping("trade/save")
    TradeResponseDTO getTrades(@Valid @RequestBody TradeRequestDTO tradeDTO){
        Trade trade = modelMapper.map(tradeDTO, Trade.class);
        return modelMapper.map(tradeService.saveTrade(trade), TradeResponseDTO.class);
    }
}
