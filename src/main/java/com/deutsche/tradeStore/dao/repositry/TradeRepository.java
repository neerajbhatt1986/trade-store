package com.deutsche.tradeStore.dao.repositry;

import com.deutsche.tradeStore.dao.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Integer> {

    @Query("select t from Trade t")
    Stream<Trade> getTrades();
    @Query(value = "select * from trade t where t.trade_id=:tradeId order by t.trade_version desc limit 1", nativeQuery = true)
    Optional<Trade> findLatestTradeByTradeId(String tradeId);


    Trade findByTradeId(String tradeId);
}
