package com.vtxlab.project.bc_product_data.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;
import com.vtxlab.project.bc_product_data.exception.ApiResp;
import com.vtxlab.project.bc_product_data.exception.BusinessException;
import com.vtxlab.project.bc_product_data.exception.exceptionEnum.Syscode;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = RestClientException.class)
  @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE) // 503
  public ApiResp<Void> bc2311ExceptionHandler(RestClientException e) {
    return ApiResp.<Void>builder() //
        .syscode(Syscode.COINGECKO_SERVICE_UNAVAILABLE.getSyscode()) //
        .message(e.getMessage())//
        .build();
  }


  @ExceptionHandler(value = BusinessException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ApiResp<Void> bc2311ExceptionHandler(BusinessException e) {
    return ApiResp.<Void>builder() //
        .syscode(e.getSyscode().getSyscode()) //
        .message(e.getMessage())//
        .build();
  }

  @ExceptionHandler(value = RuntimeException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ApiResp<Void> runtimeExceptionHandler(RuntimeException e) {
    return ApiResp.<Void>builder() //
        .syscode(Syscode.INVALID_OPERATION.getSyscode()) //
        .message(e.getMessage())//
        .build();
  }

  @ExceptionHandler(value = Exception.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ApiResp<Void> exceptionHandler(Exception e) {
    return ApiResp.<Void>builder() //
        .syscode(Syscode.INVALID_OPERATION.getSyscode()) //
        .message(e.getMessage())//
        .build();
  }

  private static Syscode getRespCode(Exception e) {

    if (e instanceof IllegalArgumentException) {
      return Syscode.INVALID_INPUT;
    }
    // ...
    // return null;
    return Syscode.INVALID_OPERATION;
  }
}
