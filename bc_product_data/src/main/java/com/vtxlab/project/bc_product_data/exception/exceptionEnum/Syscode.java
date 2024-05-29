package com.vtxlab.project.bc_product_data.exception.exceptionEnum;

import lombok.Getter;

@Getter
public enum Syscode {
  OK("000000", "OK"), //
  INVALID_INPUT("9", "Invalid input"), //
  INVALID_OPERATION("10", "Invalid operation"), //
  INVALID_COIN_SYMBOL("11", "Invalid coin symbol"), //
  // Api error
  API_ERROR("100", "API error"), //
  COINGECKO_SERVICE_UNAVAILABLE("900000", "Coingecko service is unavailable");
  //

  private String syscode;
  private String message;

  private Syscode(String syscode, String message) {
    this.syscode = syscode;
    this.message = message;
  }

  public static Syscode fromCode(Syscode syscode) {
    for (Syscode c : Syscode.values()) {
      if (c.getSyscode().equals(syscode.getSyscode())) {
        return c;
      }
    }
    return null;
  }

}
