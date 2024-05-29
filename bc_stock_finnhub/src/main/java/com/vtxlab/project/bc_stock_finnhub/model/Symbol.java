package com.vtxlab.project.bc_stock_finnhub.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Symbol {
  @JsonProperty("currency")
  private String currency;
  @JsonProperty("description")
  private String description;
  @JsonProperty("displaySymbol")
  private String displaySymbol;
  @JsonProperty("figi")
  private String figi;
  @JsonProperty("isin")
  private String isin;
  @JsonProperty("mic")
  private String mic;
  @JsonProperty("shareClassFIGI")
  private String shareClassFIGI;
  @JsonProperty("symbol")
  private String symbol;
  @JsonProperty("symbol2")
  private String symbol2;
  @JsonProperty("type")
  private String type;
}
