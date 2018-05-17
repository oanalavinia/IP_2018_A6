package com.ip.marketing.service;

import com.ip.marketing.service.dto.MarketingDetDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing MarketingDet.
 */
public interface MarketingDetService {

    /**
     * Save a marketingDet.
     *
     * @param marketingDetDTO the entity to save
     * @return the persisted entity
     */
    MarketingDetDTO save(MarketingDetDTO marketingDetDTO);

    /**
     * Get all the marketingDets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MarketingDetDTO> findAll(Pageable pageable);

    /**
     * Get the "id" marketingDet.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MarketingDetDTO findOne(Long id);

    /**
     * Delete the "id" marketingDet.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
