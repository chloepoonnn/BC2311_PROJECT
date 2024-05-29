package com.vtxlab.project.bc_product_data.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "texternal_crypto_coingecko_market", schema = "bc2311")
public class CoingeckoEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String coinId;
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
