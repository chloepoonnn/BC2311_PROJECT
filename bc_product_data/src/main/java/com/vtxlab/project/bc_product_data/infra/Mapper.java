package com.vtxlab.project.bc_product_data.infra;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.vtxlab.project.bc_product_data.entity.CoingeckoEntity;
import com.vtxlab.project.bc_product_data.entity.FinnhubProfileEntity;
import com.vtxlab.project.bc_product_data.entity.FinnhubQuoteEntity;
import com.vtxlab.project.bc_product_data.model.CoingeckoDTO;
import com.vtxlab.project.bc_product_data.model.CompanyProfile;
import com.vtxlab.project.bc_product_data.model.StockDTO;
import com.vtxlab.project.bc_product_data.model.response.CompanyProfileResponseDTO;
import com.vtxlab.project.bc_product_data.model.response.QuoteResponseDTO;

@Component
public class Mapper {

  @Autowired
  ModelMapper modelMapper;

  public CoingeckoEntity map(CoingeckoDTO coingeckoDTO) {
    return CoingeckoEntity.builder()//
        .coinId(coingeckoDTO.getId())//
        .symbol(coingeckoDTO.getSymbol())//
        .name(coingeckoDTO.getName())//
        .image(coingeckoDTO.getImage())//
        .totalVolume(coingeckoDTO.getTotalVolume())//
        .currentPrice(coingeckoDTO.getCurrentPrice())//
        .marketCap(coingeckoDTO.getMarketCap())//
        .marketCapRank(coingeckoDTO.getMarketCapRank())//
        .priceChangePercentage24h(coingeckoDTO.getPriceChangePercentage24h()) //
        .build();
  }

  public FinnhubQuoteEntity map(String stockCode, QuoteResponseDTO quote) {
    return FinnhubQuoteEntity.builder()//
        .stockCode(stockCode)//
        .currPrice(quote.getC())//
        .priceChg(quote.getH())//
        .priceChgPct(quote.getDp())//
        .priceDayHigh(quote.getH())//
        .priceDayLow(quote.getL())//
        .pricePrevOpen(quote.getO())//
        .pricePrevClose(quote.getPc())//
        .build();
  }

  public CompanyProfileResponseDTO map(CompanyProfile companyProfile) {
    return CompanyProfileResponseDTO.builder()//
        .country(companyProfile.getData().getCountry())//
        .currency(companyProfile.getData().getCurrency())//
        .estimateCurrency(companyProfile.getData().getEstimateCurrency())//
        .exchange(companyProfile.getData().getExchange())//
        .finnhubIndustry(companyProfile.getData().getFinnhubIndustry())//
        .ipo(companyProfile.getData().getIpo())//
        .logo(companyProfile.getData().getLogo())//
        .marketCapitalization(
            companyProfile.getData().getMarketCapitalization())//
        .name(companyProfile.getData().getName())//
        .phone(companyProfile.getData().getPhone())//
        .shareOutstanding(companyProfile.getData().getShareOutstanding())//
        .ticker(companyProfile.getData().getTicker())//
        .weburl(companyProfile.getData().getWeburl())//
        .build();
  }

  public FinnhubProfileEntity map(CompanyProfileResponseDTO data) {
    return FinnhubProfileEntity.builder()//
        .quoteDate(Timestamp.valueOf(LocalDateTime.now()))//
        .country(data.getCountry())//
        .currency(data.getCurrency())//
        .estimateCurrency(data.getEstimateCurrency())//
        .exchange(data.getExchange())//
        .finnhubIndustry(data.getFinnhubIndustry())//
        .ipo(data.getIpo())//
        .logo(data.getLogo())//
        .marketCapitalization(data.getMarketCapitalization())//
        .name(data.getName())//
        .phone(data.getPhone())//
        .shareOutstanding(data.getShareOutstanding())//
        .ticker(data.getTicker())//
        .weburl(data.getWeburl())//
        .build();
  }

  public StockDTO map(CompanyProfileResponseDTO profile,
      QuoteResponseDTO quote) {
    return StockDTO.builder()//
        .name(profile.getName())//
        .ticker(profile.getTicker())//
        .currentPrice(quote.getC())//
        .change(quote.getH())//
        .percentChange(quote.getDp())//
        .highPriceOfTheDay(quote.getH())//
        .lowPriceOfTheDay(quote.getL())//
        .openPriceOfTheDay(quote.getO())//
        .previousClosePrice(quote.getPc())//
        .build();
  }
}

