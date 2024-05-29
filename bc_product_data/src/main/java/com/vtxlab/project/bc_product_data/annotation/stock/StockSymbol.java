package com.vtxlab.project.bc_product_data.annotation.stock;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.vtxlab.project.bc_product_data.service.ValidListService;
import lombok.Getter;

@Getter
public class StockSymbol {

  private final ValidListService validListService;

  @Autowired
  public StockSymbol(ValidListService validListService) {
    this.validListService = validListService;
  }

  public List<String> getStockIds() {
    return validListService.getStockList().getStockList();
  }

}
