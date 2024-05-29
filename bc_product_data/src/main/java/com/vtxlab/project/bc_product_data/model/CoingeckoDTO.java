package com.vtxlab.project.bc_product_data.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CoingeckoDTO {
  private String id;
  private String symbol;
  private String name;
  private String image;
  private long totalVolume;
  private double currentPrice;
  private Long marketCap;
  private long marketCapRank;
  @JsonProperty("price_change_percentage_24h")
  private double priceChangePercentage24h;
}
