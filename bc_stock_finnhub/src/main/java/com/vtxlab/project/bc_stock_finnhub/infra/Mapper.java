package com.vtxlab.project.bc_stock_finnhub.infra;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.vtxlab.project.bc_stock_finnhub.model.CompanyProfile;
import com.vtxlab.project.bc_stock_finnhub.model.Quote;
import com.vtxlab.project.bc_stock_finnhub.model.StockDTO;

@Component
public class Mapper {

  @Autowired
  ModelMapper modelMapper;

  public StockDTO map(Quote quote, CompanyProfile companyProfile) {
    return StockDTO.builder()//
        .name(companyProfile.getName())//
        .ticker(companyProfile.getTicker())//
        .currentPrice(quote.getC()) //
        .change(quote.getD()) //
        .percentChange(quote.getD()) //
        .highPrice(quote.getH()) //
        .lowPrice(quote.getL()) //
        .openPrice(quote.getO()) //
        .closePrice(quote.getPc()) //
        .build();
  }
}
