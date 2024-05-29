package com.vtxlab.project.bc_product_data.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IdCheckValidator implements ConstraintValidator<IdCheck, Integer> {

  @Override
  public boolean isValid(Integer value, ConstraintValidatorContext context) {
    return value > 0;
  }
}
