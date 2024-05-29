package com.vtxlab.project.bc_product_data.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.vtxlab.project.bc_product_data.infra.Mapper;
import com.vtxlab.project.bc_product_data.model.response.CoinListResponseDTO;
import com.vtxlab.project.bc_product_data.model.response.StockListResponseDTO;
import com.vtxlab.project.bc_product_data.service.CoingeckoService;
import com.vtxlab.project.bc_product_data.service.StockService;
import com.vtxlab.project.bc_product_data.service.impl.ValidListServiceImpl;

@Component
@EnableScheduling
public class ScheduledConfig {

  @Autowired
  private ValidListServiceImpl validListService;

  @Autowired
  private CoingeckoService coingeckoService;

  @Autowired
  private StockService stockService;

  @Autowired
  private Mapper mapper;

  @Scheduled(fixedRate = 60000)
  public void saveCoinGecko() {
    CoinListResponseDTO coinList = validListService.getCoinList();
    coinList.getCoinList().stream()//
        .limit(10)//
        .map(e -> coingeckoService.getCoinData(e))//
        .map(e -> mapper.map(e))//
        .forEach(coingeckoService::saveCoinData);
  }


  // @Scheduled(fixedDelay = 3000)
  @Scheduled(fixedRate = 60000)
  public void saveStockQuote() {
    StockListResponseDTO stockList = validListService.getStockList();
    stockList.getStockList().stream()//
        .limit(10)//
        .forEach(stockService::saveQuote);
    stockList.getStockList().stream()//
        .limit(10)//
        .forEach(stockService::saveProfile);
  }


  // @Scheduled(cron = "*/5 * * * * ?")
  @Async
  public void scheduleTaskUsingCronExpression() throws InterruptedException {
    System.out.println("Cron Task - " + System.currentTimeMillis() / 1000);
    // Thread.sleep(10000);
    System.out.println("End Cron ");
  }

  // @Scheduled(cron = "10 * * * * ?")
  @Async
  public void cronTask() throws InterruptedException {
    System.out.println("Cron Task - " + System.currentTimeMillis() / 1000);
    // Thread.sleep(10000);
    System.out.println("End Cron ");
  }


}
