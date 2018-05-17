package com.ip.shipping.service;

import com.ip.shipping.service.dto.ShippingDetDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ShippingDet.
 */
public interface ShippingDetService {

    /**
     * Save a shippingDet.
     *
     * @param shippingDetDTO the entity to save
     * @return the persisted entity
     */
    ShippingDetDTO save(ShippingDetDTO shippingDetDTO);

    /**
     * Get all the shippingDets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ShippingDetDTO> findAll(Pageable pageable);

    /**
     * Get the "id" shippingDet.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ShippingDetDTO findOne(Long id);

    /**
     * Delete the "id" shippingDet.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
