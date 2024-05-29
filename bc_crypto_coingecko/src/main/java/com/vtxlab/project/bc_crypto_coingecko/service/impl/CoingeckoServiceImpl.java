package com.vtxlab.project.bc_crypto_coingecko.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.vtxlab.project.bc_crypto_coingecko.infra.Mapper;
import com.vtxlab.project.bc_crypto_coingecko.model.Coingecko;
import com.vtxlab.project.bc_crypto_coingecko.model.CoingeckoDTO;
import com.vtxlab.project.bc_crypto_coingecko.redis.RedisHelper;
import com.vtxlab.project.bc_crypto_coingecko.service.CoingeckoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CoingeckoServiceImpl implements CoingeckoService {

  private final RestTemplate restTemplate;
  private final Mapper mapper;
  private final String coinIds;
  private final UriComponentsBuilder coingeckoUriBuilder;
  private final RedisHelper redisHelper;

  @Autowired
  public CoingeckoServiceImpl(RestTemplate restTemplate, Mapper mapper,
      @Value("${redis-key.crypto.coingecko.coins-markets.currency}") String currency,
      @Value("${redis-key.crypto.coingecko.coins-markets.coin-ids}") String coinIds,
      @Qualifier("coingeckoUriBuilder") UriComponentsBuilder coingeckoUriBuilder,
      RedisHelper redisHelper) {
    this.restTemplate = restTemplate;
    this.mapper = mapper;
    this.redisHelper = redisHelper;
    this.coinIds = coinIds;
    this.coingeckoUriBuilder = coingeckoUriBuilder;
  }

  @Override
  public List<Coingecko> getDataFromApi(String currency, String ids) {
    List<String> inputCoinIdList = Arrays.asList(ids.split(","));
    log.info("getDataFromApi , before redisHelper.lGet");
    List<Coingecko> rawData = redisHelper.lGet("crypto:coingecko:coins-markets",
        0, 1, Coingecko.class);

    List<String> coinIdList = Arrays.asList(coinIds.split(","));
    log.info(coinIdList.size() + " size");
    List<CoingeckoDTO> entities =
        rawData.stream().filter(e -> coinIdList.contains(e.getSymbol()))
            .map(e -> mapper.map(e)).collect(Collectors.toList());
    log.info(entities.get(0) + " first");
    log.info(entities.get(1) + " second");

    log.info("entities : " + entities.size());
    log.info("After saveAll");

    return rawData;
  }

  /// old
  // private void setDataToRedis(List<CoingeckoDTO> entities) {
  // entities.forEach(e -> {
  // log.info("e : " + e);
  // redisUtils.set(
  // "crypto:coingecko:coins-markets:" + currency + ":" + e.getId(), e,
  // 60);
  // });
  // }


  @Override
  public List<Coingecko> getCoinMarket() {
    // log.info("coingeckoUriBuilder : " + coingeckoUriBuilder.toUriString());
    // List<Coingecko> result = Arrays.asList(restTemplate
    // .getForObject(coingeckoUriBuilder.toUriString(), Coingecko[].class));
    // redisHelper.lSet("crypto:coingecko:coins-markets", result, 60);
    log.info("getCoinMarket , before redisHelper.lGet");
    List<Coingecko> result = redisHelper.lGet("crypto:coingecko:coins-markets",
        0, -1, Coingecko.class);
    return result;
  }

  @Override
  public List<String> getCoinList() {
    log.info("getCoinList , before redisHelper.lGet");

    return (List<String>) redisHelper
        .<Coingecko>lGet("crypto:coingecko:coins-markets", 0, -1,
            Coingecko.class)
        .stream().filter(Objects::nonNull)
        .map(coingecko -> ((Coingecko) coingecko).getSymbol())  
        .collect(Collectors.toList());
  }
}  
