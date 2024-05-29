package com.vtxlab.project.bc_product_data.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.vtxlab.project.bc_product_data.repository.coin.CoinListRepo;
import com.vtxlab.project.bc_product_data.repository.stock.StockListRepo;
import com.vtxlab.project.bc_product_data.service.ValidListService;

@Component
public class AppStartRunner implements CommandLineRunner {

  @Autowired
  private ValidListService validListService;

  @Autowired
  private CoinListRepo coinListRepo;

  @Autowired
  private StockListRepo stockListRepo;

  @Override
  public void run(String... args) throws Exception {
    if (coinListRepo.findAll().size() == 0 && //
        stockListRepo.findAll().size() == 0) {
      validListService.saveCoinList();
      validListService.saveStockList();
    }
  }

}
