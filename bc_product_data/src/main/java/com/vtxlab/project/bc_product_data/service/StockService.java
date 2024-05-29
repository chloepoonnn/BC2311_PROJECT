package com.vtxlab.project.bc_product_data.service;

import com.vtxlab.project.bc_product_data.model.CompanyProfile;
import com.vtxlab.project.bc_product_data.model.response.CompanyProfileResponseDTO;
import com.vtxlab.project.bc_product_data.model.response.QuoteResponseDTO;

public interface StockService {
  QuoteResponseDTO getQuote(String symbol);

  CompanyProfileResponseDTO getProfile(String symbol);

  boolean saveQuote(String symbol);
  
  boolean saveProfile(String symbol);


}
