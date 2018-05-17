package com.ip.warehouse.service.impl;

import com.ip.warehouse.service.WarehouseDetService;
import com.ip.warehouse.domain.WarehouseDet;
import com.ip.warehouse.repository.WarehouseDetRepository;
import com.ip.warehouse.service.dto.WarehouseDetDTO;
import com.ip.warehouse.service.mapper.WarehouseDetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing WarehouseDet.
 */
@Service
@Transactional
public class WarehouseDetServiceImpl implements WarehouseDetService {

    private final Logger log = LoggerFactory.getLogger(WarehouseDetServiceImpl.class);

    private final WarehouseDetRepository warehouseDetRepository;

    private final WarehouseDetMapper warehouseDetMapper;

    public WarehouseDetServiceImpl(WarehouseDetRepository warehouseDetRepository, WarehouseDetMapper warehouseDetMapper) {
        this.warehouseDetRepository = warehouseDetRepository;
        this.warehouseDetMapper = warehouseDetMapper;
    }

    /**
     * Save a warehouseDet.
     *
     * @param warehouseDetDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public WarehouseDetDTO save(WarehouseDetDTO warehouseDetDTO) {
        log.debug("Request to save WarehouseDet : {}", warehouseDetDTO);
        WarehouseDet warehouseDet = warehouseDetMapper.toEntity(warehouseDetDTO);
        warehouseDet = warehouseDetRepository.save(warehouseDet);
        return warehouseDetMapper.toDto(warehouseDet);
    }

    /**
     * Get all the warehouseDets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WarehouseDetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WarehouseDets");
        return warehouseDetRepository.findAll(pageable)
            .map(warehouseDetMapper::toDto);
    }

    /**
     * Get one warehouseDet by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public WarehouseDetDTO findOne(Long id) {
        log.debug("Request to get WarehouseDet : {}", id);
        WarehouseDet warehouseDet = warehouseDetRepository.findOne(id);
        return warehouseDetMapper.toDto(warehouseDet);
    }

    /**
     * Delete the warehouseDet by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WarehouseDet : {}", id);
        warehouseDetRepository.delete(id);
    }
}
