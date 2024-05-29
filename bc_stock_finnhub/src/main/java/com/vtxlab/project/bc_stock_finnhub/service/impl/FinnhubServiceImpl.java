package com.vtxlab.project.bc_stock_finnhub.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.vtxlab.project.bc_stock_finnhub.infra.Mapper;
import com.vtxlab.project.bc_stock_finnhub.model.CompanyProfile;
import com.vtxlab.project.bc_stock_finnhub.model.Quote;
import com.vtxlab.project.bc_stock_finnhub.model.StockDTO;
import com.vtxlab.project.bc_stock_finnhub.model.Symbol;
import com.vtxlab.project.bc_stock_finnhub.service.FinnhubService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FinnhubServiceImpl implements FinnhubService {

  private final RestTemplate restTemplate;
  private final UriComponentsBuilder finnhubQuoteUriBuilder;
  private final UriComponentsBuilder finnhubCompanyProfileUriBuilder;
  private final UriComponentsBuilder finnhubSymbolUriBuilder;
  private final Mapper mapper;

  @Autowired
  public FinnhubServiceImpl(RestTemplate restTemplate,
      @Qualifier("finnhubQuoteUriBuilder") UriComponentsBuilder finnhubQuoteUriBuilder,
      @Qualifier("finnhubCompanyProfileUriBuilder") UriComponentsBuilder finnhubCompanyProfileUriBuilder,
      @Qualifier("finnhubSymbolUriBuilder") UriComponentsBuilder finnhubSymbolUriBuilder,
      Mapper mapper) {
    this.restTemplate = restTemplate;
    this.finnhubQuoteUriBuilder = finnhubQuoteUriBuilder;
    this.finnhubCompanyProfileUriBuilder = finnhubCompanyProfileUriBuilder;
    this.finnhubSymbolUriBuilder = finnhubSymbolUriBuilder;
    this.mapper = mapper;
  }

  @Override
  public Quote getQuote(String symbol) {
    Quote quote = getQuoteFromApi(symbol);
    return quote;
  }

  @Override
  public CompanyProfile getProfile(String symbol) {
    CompanyProfile companyProfile = getProfileFromApi(symbol);
    return companyProfile;
  }

  public Quote getQuoteFromApi(String symbol) {
    UriComponentsBuilder builder = UriComponentsBuilder
        .fromUriString(finnhubQuoteUriBuilder.toUriString());
    builder.replaceQueryParam("symbol", symbol);
    log.info("getQuoteFromApi Service :" + builder.toUriString());
    return restTemplate.getForObject(builder.toUriString(), Quote.class);
  }

  public CompanyProfile getProfileFromApi(String symbol) {
    UriComponentsBuilder builder = UriComponentsBuilder
        .fromUriString(finnhubCompanyProfileUriBuilder.toUriString());
    builder.replaceQueryParam("symbol", symbol);
    log.info("getProfileFromApi Service :" + builder.toUriString());
    return restTemplate.getForObject(builder.toUriString(),
        CompanyProfile.class);
  }

  @Override
  public StockDTO getStock(String symbol) {
    Quote quote = this.getQuote(symbol);
    CompanyProfile profile = this.getProfile(symbol);
    return mapper.map(quote, profile);
  }

  @Override
  public List<String> getStockList() {
    UriComponentsBuilder builder = UriComponentsBuilder
        .fromUriString(finnhubSymbolUriBuilder.toUriString());
    log.info("getStockList Service :" + builder.toUriString());
    Symbol[] symbols =
        restTemplate.getForObject(builder.toUriString(), Symbol[].class);
    return Arrays.asList(symbols).stream()//
        .filter(e -> e.getType().equals("Common Stock")
            && e.getMic().equals("XNAS"))//
        .map(Symbol::getSymbol)//
        .collect(Collectors.toList());
  }

}
