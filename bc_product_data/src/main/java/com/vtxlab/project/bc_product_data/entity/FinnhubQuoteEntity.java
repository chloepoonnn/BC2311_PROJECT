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
@Table(name = "texternal_stock_finnhub_quote")
public class FinnhubQuoteEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String stockCode;
  private double currPrice;
  private double priceChg;
  private double priceChgPct;
  private double priceDayHigh;
  private double priceDayLow;
  private double pricePrevOpen;
  private double pricePrevClose;
}
