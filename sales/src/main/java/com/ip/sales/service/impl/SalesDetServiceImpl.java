package com.ip.sales.service.impl;

import com.ip.sales.service.SalesDetService;
import com.ip.sales.domain.SalesDet;
import com.ip.sales.repository.SalesDetRepository;
import com.ip.sales.service.dto.SalesDetDTO;
import com.ip.sales.service.mapper.SalesDetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing SalesDet.
 */
@Service
@Transactional
public class SalesDetServiceImpl implements SalesDetService {

    private final Logger log = LoggerFactory.getLogger(SalesDetServiceImpl.class);

    private final SalesDetRepository salesDetRepository;

    private final SalesDetMapper salesDetMapper;

    public SalesDetServiceImpl(SalesDetRepository salesDetRepository, SalesDetMapper salesDetMapper) {
        this.salesDetRepository = salesDetRepository;
        this.salesDetMapper = salesDetMapper;
    }

    /**
     * Save a salesDet.
     *
     * @param salesDetDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SalesDetDTO save(SalesDetDTO salesDetDTO) {
        log.debug("Request to save SalesDet : {}", salesDetDTO);
        SalesDet salesDet = salesDetMapper.toEntity(salesDetDTO);
        salesDet = salesDetRepository.save(salesDet);
        return salesDetMapper.toDto(salesDet);
    }

    /**
     * Get all the salesDets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SalesDetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SalesDets");
        return salesDetRepository.findAll(pageable)
            .map(salesDetMapper::toDto);
    }

    /**
     * Get one salesDet by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SalesDetDTO findOne(Long id) {
        log.debug("Request to get SalesDet : {}", id);
        SalesDet salesDet = salesDetRepository.findOne(id);
        return salesDetMapper.toDto(salesDet);
    }

    /**
     * Delete the salesDet by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SalesDet : {}", id);
        salesDetRepository.delete(id);
    }
}
