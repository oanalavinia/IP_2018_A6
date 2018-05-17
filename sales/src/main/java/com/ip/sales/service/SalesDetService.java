package com.ip.sales.service;

import com.ip.sales.service.dto.SalesDetDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing SalesDet.
 */
public interface SalesDetService {

    /**
     * Save a salesDet.
     *
     * @param salesDetDTO the entity to save
     * @return the persisted entity
     */
    SalesDetDTO save(SalesDetDTO salesDetDTO);

    /**
     * Get all the salesDets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SalesDetDTO> findAll(Pageable pageable);

    /**
     * Get the "id" salesDet.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SalesDetDTO findOne(Long id);

    /**
     * Delete the "id" salesDet.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
