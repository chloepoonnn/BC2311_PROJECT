package com.vtxlab.project.bc_product_data.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class CompanyProfileResponseDTO {
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
