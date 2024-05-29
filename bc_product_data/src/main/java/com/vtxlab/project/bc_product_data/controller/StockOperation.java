package com.vtxlab.project.bc_product_data.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.vtxlab.project.bc_product_data.annotation.stock.StockSymbolCheck;
import com.vtxlab.project.bc_product_data.model.StockDTO;

public interface StockOperation {
  @GetMapping("/stock")
  StockDTO getStock(@StockSymbolCheck @RequestParam String symbol);

}
