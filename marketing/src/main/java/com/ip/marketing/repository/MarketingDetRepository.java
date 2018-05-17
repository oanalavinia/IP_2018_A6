package com.ip.marketing.repository;

import com.ip.marketing.domain.MarketingDet;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MarketingDet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarketingDetRepository extends JpaRepository<MarketingDet, Long> {

}
