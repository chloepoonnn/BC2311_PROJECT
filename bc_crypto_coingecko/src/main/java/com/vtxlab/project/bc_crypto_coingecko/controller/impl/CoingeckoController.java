package com.vtxlab.project.bc_crypto_coingecko.controller.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vtxlab.project.bc_crypto_coingecko.controller.CoingeckoOperation;
import com.vtxlab.project.bc_crypto_coingecko.exception.ApiResp;
import com.vtxlab.project.bc_crypto_coingecko.exception.exceptionEnum.Syscode;
import com.vtxlab.project.bc_crypto_coingecko.model.CoinMarketRespDto;
import com.vtxlab.project.bc_crypto_coingecko.model.Coingecko;
import com.vtxlab.project.bc_crypto_coingecko.service.CoingeckoService;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/crypto/coingecko/api/v1")
public class CoingeckoController implements CoingeckoOperation {

  @Autowired
  private CoingeckoService coingeckoService;

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  com.vtxlab.project.bc_crypto_coingecko.redis.RedisHelper redisHelper;

  @Override
  public ApiResp<List<Coingecko>> getAllData(String currency, String ids) {
    List<Coingecko> data = coingeckoService.getDataFromApi(currency, ids);
    return ApiResp.<List<Coingecko>>builder()//
        .Syscode(Syscode.OK.getSyscode())//
        .message(Syscode.OK.getMessage())//
        .data(data)//
        .build();//
  }

  @Override
  public ApiResp<List<String>> getCoinList() {
    List<String> data = coingeckoService.getCoinList();
    return ApiResp.<List<String>>builder()//
        .Syscode(Syscode.OK.getSyscode())//
        .message(Syscode.OK.getMessage())//
        .data(data)//
        .build();//
  }

  @Override
  @CrossOrigin
  public List<CoinMarketRespDto> getMarketData() {
    return (List<CoinMarketRespDto>) (List<?>) redisHelper
        .lGet("crypto:coingecko:coins-markets", 0, -1, Coingecko.class).stream()//
        .map(coin -> modelMapper.map(coin, CoinMarketRespDto.class))//
        .collect(Collectors.toList());

  }

  @GetMapping("testRedis")
  public List<Coingecko> getfromRedis()
      throws JsonMappingException, JsonProcessingException {
    objectMapper.registerModule(new JavaTimeModule());
    List<Coingecko> jsonStrings = redisHelper
        .lGet("crypto:coingecko:coins-markets", 0, -1, Coingecko.class);

    return jsonStrings;
  }



}
