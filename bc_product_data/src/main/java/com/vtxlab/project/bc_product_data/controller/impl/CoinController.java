package com.vtxlab.project.bc_product_data.controller.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.vtxlab.project.bc_product_data.controller.CoinOperation;
import com.vtxlab.project.bc_product_data.model.CoingeckoDTO;
import com.vtxlab.project.bc_product_data.service.CoingeckoService;

@RestController
@RequestMapping("/api/v1")
public class CoinController implements CoinOperation {

  @Autowired
  private CoingeckoService coingeckoService;

  @Override
  public ModelAndView getAllCoinData() {
    ModelAndView modelAndView = new ModelAndView("coin");
    List<CoingeckoDTO> result = coingeckoService.getAllCoinData();
    modelAndView.addObject("coin", result);
    return modelAndView;
  }

  @Override
  public String getAllCoinData(Model model) {
    List<CoingeckoDTO> result = coingeckoService.getAllCoinData();
    model.addAttribute("coin", result);
    return "coin";
  }
}
