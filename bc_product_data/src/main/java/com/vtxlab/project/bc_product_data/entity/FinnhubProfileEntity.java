package com.vtxlab.project.bc_product_data.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@Table(name = "texternal_stock_finnhub_profile2")
public class FinnhubProfileEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private Timestamp quoteDate;
  private String stockCode;
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
