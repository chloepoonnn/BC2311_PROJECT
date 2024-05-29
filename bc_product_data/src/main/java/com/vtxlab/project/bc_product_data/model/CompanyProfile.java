package com.vtxlab.project.bc_product_data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyProfile {
  private String syscode;
  private String message;
  private Data data;

  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  @Builder
  public static class Data {
    private String country;
    private String currency;
    private String estimateCurrency;
    private String exchange;
    private String finnhubIndustry;
    private String ipo;
    private String logo;
    private Long marketCapitalization;
    private String name;
    private String phone;
    private Long shareOutstanding;
    private String ticker;
    private String weburl;
  }
}
