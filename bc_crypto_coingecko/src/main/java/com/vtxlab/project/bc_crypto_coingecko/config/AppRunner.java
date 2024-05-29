package com.vtxlab.project.bc_crypto_coingecko.config;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.vtxlab.project.bc_crypto_coingecko.infra.Mapper;
import com.vtxlab.project.bc_crypto_coingecko.model.Coingecko;
import com.vtxlab.project.bc_crypto_coingecko.redis.RedisHelper;
import com.vtxlab.project.bc_crypto_coingecko.service.CoingeckoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
// @Component
@Profile("!test") // 表示这个配置或者方法只在非测试环境下生效。也就是说，当你的Spring环境不是"test"时，这个配置或者方法才会被加载和执行。
public class AppRunner implements CommandLineRunner {

  // ready the element to start the sever
  // main testk in AppRunner:
  // 1.Check on configuration - DB or yml
  // 2.Invoke API to get data(i.e. configuration , raw data)

  @Autowired
  RedisHelper redisHelper;

  @Autowired
  RestTemplate restTemplate;

  @Autowired
  CoingeckoService coingeckoService;

  @Autowired
  Mapper mapper;

  @Value("${redis-key.crypto.coingecko.coins-markets.currency}")
  String currency;

  @Value("${redis-key.crypto.coingecko.coins-markets.coin-ids}")
  String coinIds;

  @Autowired
  @Qualifier("coingeckoUriBuilder")
  UriComponentsBuilder coingeckoUriBuilder;


  @Override
  public void run(String... args) throws Exception {
    List<Coingecko> result = Arrays.asList(restTemplate
        .getForObject(coingeckoUriBuilder.toUriString(), Coingecko[].class));
    redisHelper.lSet("crypto:coingecko:coins-markets", result, 59);

  }
}
// @Override
// public List<CoingeckoDTO> getDataFromRedis(String symbol) {
// List<CoingeckoDTO> result = new ArrayList<>();
// String key = "crypto:coingecko:coins-markets:" + currency.toUpperCase()
// + ":" + symbol;
// String json = (String) redisUtils.get(key);
// if (json != null) {
// try {
// CoingeckoDTO target = objectMapper.readValue(json, CoingeckoDTO.class);
// result.add(target);
// } catch (JsonProcessingException e) {
// log.error("Error deserializing JSON to CoingeckoDTO object: {}",
// e.getMessage());
// }
// }
// return result;
// }

// }
// }
