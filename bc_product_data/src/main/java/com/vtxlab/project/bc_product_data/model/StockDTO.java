package com.vtxlab.project.bc_product_data.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Builder
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
    private double highPriceOfTheDay;
    @JsonProperty("Low_Price_Of_The_Day")
    private double lowPriceOfTheDay;
    @JsonProperty("Open_Price_Of_The_Day")
    private double openPriceOfTheDay;
    @JsonProperty("Previous_Close_Price")
    private double previousClosePrice;
  }
