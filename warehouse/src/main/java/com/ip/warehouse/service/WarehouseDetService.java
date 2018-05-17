package com.ip.warehouse.service;

import com.ip.warehouse.service.dto.WarehouseDetDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing WarehouseDet.
 */
public interface WarehouseDetService {

    /**
     * Save a warehouseDet.
     *
     * @param warehouseDetDTO the entity to save
     * @return the persisted entity
     */
    WarehouseDetDTO save(WarehouseDetDTO warehouseDetDTO);

    /**
     * Get all the warehouseDets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<WarehouseDetDTO> findAll(Pageable pageable);

    /**
     * Get the "id" warehouseDet.
     *
     * @param id the id of the entity
     * @return the entity
     */
    WarehouseDetDTO findOne(Long id);

    /**
     * Delete the "id" warehouseDet.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
