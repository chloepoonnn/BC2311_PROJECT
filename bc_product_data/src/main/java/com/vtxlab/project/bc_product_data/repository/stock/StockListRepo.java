package com.vtxlab.project.bc_product_data.repository.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vtxlab.project.bc_product_data.entity.StockList;

public interface StockListRepo extends JpaRepository<StockList, Integer> {

  void deleteByStockCode(String stockCode);

}
