package com.vtxlab.project.bc_product_data.annotation.coin;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import com.vtxlab.project.bc_product_data.exception.InvalidCoinSymbolException;
import com.vtxlab.project.bc_product_data.model.UserIdRQDTO;
import com.vtxlab.project.bc_product_data.service.ValidListService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CoinSymbolValidator
    implements ConstraintValidator<CoinSymbolCheck, String> {

  private final ValidListService validListService;

  @Autowired
  public CoinSymbolValidator(ValidListService validListService) {
    this.validListService = validListService;
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (!Objects.nonNull(value) || value.isEmpty() || value.isBlank()
        || value == null)
      return false;

    try {
      return validListService.getCoinList().getCoinList().contains(value);
    } catch (InvalidCoinSymbolException e) {
      return false; 
    }


    // false -> Spring Program throw ConstraintViolationException -> GEH -> API Response
  }
}
