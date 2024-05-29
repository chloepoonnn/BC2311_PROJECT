package com.vtxlab.project.bc_stock_finnhub.service;

import java.util.List;
import com.vtxlab.project.bc_stock_finnhub.model.CompanyProfile;
import com.vtxlab.project.bc_stock_finnhub.model.Quote;
import com.vtxlab.project.bc_stock_finnhub.model.StockDTO;

public interface FinnhubService {

  Quote getQuote(String symbol);

  CompanyProfile getProfile(String symbol);

  StockDTO getStock(String symbol);

  List<String> getStockList();
}
