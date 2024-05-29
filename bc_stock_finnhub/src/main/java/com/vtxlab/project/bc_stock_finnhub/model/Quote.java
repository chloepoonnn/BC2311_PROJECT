package com.vtxlab.project.bc_stock_finnhub.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Quote {
  // @JsonProperty("Current_Price")
  private double c;
  // @JsonProperty("Change")
  private double d;
  // @JsonProperty("Percent_Change")
  private double dp;
  // @JsonProperty("High_Price_Of_The_Day")
  private double h;
  // @JsonProperty("Low_Price_Of_The_Day")
  private double l;
  // @JsonProperty("Open_Price_Of_The_Day")
  private double o;
  // @JsonProperty("Previous_Close_Price")
  private double pc;
  // @JsonProperty("Timestamp")
  private Long t;
}
