package com.vtxlab.project.bc_product_data.exception;

import com.vtxlab.project.bc_product_data.exception.exceptionEnum.Syscode;

public class InvalidCoinSymbolException extends BusinessRuntimeException {

  public InvalidCoinSymbolException(Syscode Syscode) {
    super(Syscode);
  }

}
