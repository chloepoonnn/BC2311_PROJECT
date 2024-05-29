package com.vtxlab.project.bc_stock_finnhub.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StockDTO {
  private String name;

  private String ticker;

  @JsonProperty("Current_Price")
  private double currentPrice;
  @JsonProperty("Change")
  private double change;
  @JsonProperty("Percent_Change")
  private double percentChange;
  @JsonProperty("High_Price_Of_The_Day")
  private double highPrice;
  @JsonProperty("Low_Price_Of_The_Day")
  private double lowPrice;
  @JsonProperty("Open_Price_Of_The_Day")
  private double openPrice;
  @JsonProperty("Previous_Close_Price")
  private double closePrice;

}
