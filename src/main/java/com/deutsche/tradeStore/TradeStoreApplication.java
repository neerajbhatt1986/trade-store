package com.deutsche.tradeStore;

import com.deutsche.tradeStore.dao.entity.Trade;
import com.deutsche.tradeStore.dao.repositry.TradeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@SpringBootApplication
public class TradeStoreApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TradeStoreApplication.class, args);
	}

	@Autowired
	TradeRepository tradeRepository;

	@Bean
	ModelMapper modelMapper(){
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		return modelMapper;
	}

	@Override
	public void run(String... args) throws Exception {

		if(tradeRepository.count() == 0){
			tradeRepository.save(new Trade("T1", 1, "CP-1", "B1", getDate(LocalDate.now().plusMonths(6))));
			tradeRepository.save(new Trade("T2", 2, "CP-2", "B1", getDate(LocalDate.now().plusYears(1))));
			Trade trade = new Trade("T2", 1, "CP-1", "B1", getDate(LocalDate.now().plusYears(1).minusDays(30)));
			trade.setCreatedDate(getDate(LocalDate.now().minusMonths(6)));
			tradeRepository.save(trade);
			tradeRepository.save(new Trade("T3", 3, "CP-3", "B2", getDate(LocalDate.now().minusYears(2))));
		}
	}

	private Date getDate(LocalDate localDate){
		ZoneId defaultZoneId = ZoneId.systemDefault();
		return Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
	}
}
