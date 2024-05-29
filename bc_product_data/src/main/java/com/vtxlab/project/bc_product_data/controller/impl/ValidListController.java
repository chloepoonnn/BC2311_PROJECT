package com.vtxlab.project.bc_product_data.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vtxlab.project.bc_product_data.controller.ValidListOperation;
import com.vtxlab.project.bc_product_data.model.response.CoinListResponseDTO;
import com.vtxlab.project.bc_product_data.model.response.StockListResponseDTO;
import com.vtxlab.project.bc_product_data.service.ValidListService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/admin")
public class ValidListController implements ValidListOperation {

  @Autowired
  private ValidListService validListService;

  @Override
  public CoinListResponseDTO getCoinList() {
    return validListService.getCoinList();
  }

  @Override
  public StockListResponseDTO getStockList() {
    return validListService.getStockList();
  }

  @Override
  public boolean deleteCoin(String coinId) {
    return validListService.deleteCoin(coinId);
  }

  @Override
  public boolean deleteStock(String stockId) {
    return validListService.deleteStock(stockId);
  }
}