package com.ip.warehouse.repository;

import com.ip.warehouse.domain.WarehouseDet;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the WarehouseDet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WarehouseDetRepository extends JpaRepository<WarehouseDet, Long> {

}
