package com.ip.sales.repository;

import com.ip.sales.domain.SalesDet;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SalesDet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SalesDetRepository extends JpaRepository<SalesDet, Long> {

}
