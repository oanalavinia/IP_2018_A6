package com.ip.shipping.repository;

import com.ip.shipping.domain.ShippingDet;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ShippingDet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShippingDetRepository extends JpaRepository<ShippingDet, Long> {

}
