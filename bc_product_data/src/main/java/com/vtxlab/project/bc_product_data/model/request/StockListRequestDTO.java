package com.vtxlab.project.bc_product_data.model.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockListRequestDTO {
  String syscode;
  String message;
  List<String> data;
}
