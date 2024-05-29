package com.vtxlab.project.bc_product_data.annotation.coin;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.vtxlab.project.bc_product_data.service.ValidListService;
import lombok.Getter;

@Getter
public class CoinSymbol {
  private final ValidListService validListService;

  @Autowired
  public CoinSymbol(ValidListService validListService) {
    this.validListService = validListService;
  }

  public List<String> getCoinIds() {
    return validListService.getCoinList().getCoinList();
  }

}
