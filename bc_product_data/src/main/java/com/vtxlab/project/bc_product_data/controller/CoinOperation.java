package com.vtxlab.project.bc_product_data.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

public interface CoinOperation {

  // @GetMapping("/coin")
  ModelAndView getAllCoinData();

  @GetMapping("/coin")
  String getAllCoinData(Model model);
}
