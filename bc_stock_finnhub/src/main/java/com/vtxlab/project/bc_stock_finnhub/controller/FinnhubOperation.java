package com.vtxlab.project.bc_stock_finnhub.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.vtxlab.project.bc_stock_finnhub.exception.ApiResp;
import com.vtxlab.project.bc_stock_finnhub.model.CompanyProfile;
import com.vtxlab.project.bc_stock_finnhub.model.Quote;
import com.vtxlab.project.bc_stock_finnhub.model.StockDTO;

public interface FinnhubOperation {

  @GetMapping("/quote")
  @ResponseStatus(HttpStatus.OK)
  ApiResp<Quote> getQuote(@RequestParam String symbol);

  @GetMapping("/profile2")
  @ResponseStatus(HttpStatus.OK)
  ApiResp<CompanyProfile> getProfile(@RequestParam String symbol);

  @GetMapping("/getStock")
  @ResponseStatus(HttpStatus.OK)
  ApiResp<StockDTO> getStock(@RequestParam String symbol);

  @GetMapping("/stockList")
  @ResponseStatus(HttpStatus.OK)
  ApiResp<List<String>> getStockList();
}
