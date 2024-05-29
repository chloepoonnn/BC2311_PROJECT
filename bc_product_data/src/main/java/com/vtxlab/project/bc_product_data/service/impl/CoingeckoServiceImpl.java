package com.vtxlab.project.bc_product_data.service.impl;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.vtxlab.project.bc_product_data.entity.CoingeckoEntity;
import com.vtxlab.project.bc_product_data.model.CoingeckoDTO;
import com.vtxlab.project.bc_product_data.repository.coin.CoingeckoRepo;
import com.vtxlab.project.bc_product_data.service.CoingeckoService;

@Service
public class CoingeckoServiceImpl implements CoingeckoService {

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private CoingeckoRepo coingeckoRepo;

  @Autowired
  @Qualifier("coingeckoUriString")
  private UriComponentsBuilder coingeckoUriString;


  @Autowired
  @Qualifier("coingeckoMarketUriString")
  private UriComponentsBuilder coingeckoMarketUriString;

  @Override
  public List<CoingeckoDTO> getAllCoinData() {
    return Arrays.asList(restTemplate.getForObject(
        coingeckoMarketUriString.build(false).toUriString(),
        CoingeckoDTO[].class));
  }

  @Override
  public CoingeckoDTO getCoinData(String coin) {
    return this.getAllCoinData().stream()//
        .filter(e -> e.getId().equals(coin))//
        .findFirst()//
        .orElse(null);
  }

  @Override
  public boolean saveCoinData(CoingeckoEntity coingeckoEntity) {
    coingeckoRepo.save(coingeckoEntity);
    return true;
  }
}
