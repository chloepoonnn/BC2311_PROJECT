package com.vtxlab.project.bc_product_data.annotation.stock;

import java.util.Objects;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StockSymbolValidator
    implements ConstraintValidator<StockSymbolCheck, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return Objects.nonNull(value);
  }
}
