package com.vtxlab.project.bc_stock_finnhub.config;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriComponentsBuilder;
import com.vtxlab.project.bc_stock_finnhub.infra.ApiUtil;
import com.vtxlab.project.bc_stock_finnhub.infra.UriScheme;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class FinnhubUriBuilderConfig {

  @Value("${api.finnhub.token}")
  private String finnhubToken;

  @Value("${api.finnhub.domain}")
  private String domain;

  @Value("${api.finnhub.path}")
  private String path;

  @Value("${api.finnhub.quote.endpoint}")
  private String quoteEndpoint;

  @Value("${api.finnhub.profile2.endpoint}")
  private String profileEndpoint;

  @Value("${api.finnhub.symbol.endpoint}")
  private String symbolEndpoint;

  @Value("${api.finnhub.symbol.exchange}")
  private String exchange;

  @Bean
  UriComponentsBuilder finnhubQuoteUriBuilder() {
    return createUriBuilder(quoteEndpoint);
  }

  @Bean
  UriComponentsBuilder finnhubCompanyProfileUriBuilder() {
    return createUriBuilder(profileEndpoint);
  }

  @Bean
  UriComponentsBuilder finnhubSymbolUriBuilder() {
    return createUriBuilder(symbolEndpoint).queryParam("exchange", exchange);
  }

  private UriComponentsBuilder createUriBuilder(String endpoint) {
    UriComponentsBuilder builder =
        ApiUtil.uriBuilder(UriScheme.HTTPS, domain, path, endpoint);
    builder.queryParam("token", finnhubToken);
    return builder;
  }

}
