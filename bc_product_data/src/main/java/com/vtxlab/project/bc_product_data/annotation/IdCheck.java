package com.vtxlab.project.bc_product_data.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IdCheckValidator.class)
public @interface IdCheck {
  public String message() default "Id must be greater than 0";

  public Class<?>[] groups() default {};

  public Class<? extends Payload>[] payload() default {};
}
