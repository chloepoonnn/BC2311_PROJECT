package com.vtxlab.project.bc_product_data.repository.coin;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vtxlab.project.bc_product_data.entity.CoingeckoEntity;

public interface CoingeckoRepo extends JpaRepository<CoingeckoEntity, Integer>{
  
}
