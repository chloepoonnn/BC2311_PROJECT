package com.vtxlab.project.bc_product_data.model.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoinListRequestDTO {
  String syscode;
  String message;
  List<String> data;
}
