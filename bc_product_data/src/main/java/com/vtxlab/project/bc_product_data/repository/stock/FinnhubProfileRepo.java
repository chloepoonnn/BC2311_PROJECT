package com.vtxlab.project.bc_product_data.repository.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vtxlab.project.bc_product_data.entity.FinnhubProfileEntity;

public interface FinnhubProfileRepo
    extends JpaRepository<FinnhubProfileEntity, Integer> {

}
