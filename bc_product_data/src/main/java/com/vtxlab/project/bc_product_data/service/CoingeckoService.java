package com.vtxlab.project.bc_product_data.service;

import java.util.List;
import com.vtxlab.project.bc_product_data.entity.CoingeckoEntity;
import com.vtxlab.project.bc_product_data.model.CoingeckoDTO;

public interface CoingeckoService {
  List<CoingeckoDTO> getAllCoinData();

  CoingeckoDTO getCoinData(String coin);

  boolean saveCoinData(CoingeckoEntity coingeckoEntity);
}

