package com.vtxlab.project.bc_product_data.service;

import com.vtxlab.project.bc_product_data.model.response.CoinListResponseDTO;
import com.vtxlab.project.bc_product_data.model.response.StockListResponseDTO;

public interface ValidListService {

  boolean saveCoinList();

  boolean saveStockList();

  boolean deleteCoin(String coinId);

  boolean deleteStock(String stockId);

  CoinListResponseDTO getCoinList();

  StockListResponseDTO getStockList();

  boolean deleteAll();
}
