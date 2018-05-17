package com.ip.shipping.service.impl;

import com.ip.shipping.service.ShippingDetService;
import com.ip.shipping.domain.ShippingDet;
import com.ip.shipping.repository.ShippingDetRepository;
import com.ip.shipping.service.dto.ShippingDetDTO;
import com.ip.shipping.service.mapper.ShippingDetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ShippingDet.
 */
@Service
@Transactional
public class ShippingDetServiceImpl implements ShippingDetService {

    private final Logger log = LoggerFactory.getLogger(ShippingDetServiceImpl.class);

    private final ShippingDetRepository shippingDetRepository;

    private final ShippingDetMapper shippingDetMapper;

    public ShippingDetServiceImpl(ShippingDetRepository shippingDetRepository, ShippingDetMapper shippingDetMapper) {
        this.shippingDetRepository = shippingDetRepository;
        this.shippingDetMapper = shippingDetMapper;
    }

    /**
     * Save a shippingDet.
     *
     * @param shippingDetDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ShippingDetDTO save(ShippingDetDTO shippingDetDTO) {
        log.debug("Request to save ShippingDet : {}", shippingDetDTO);
        ShippingDet shippingDet = shippingDetMapper.toEntity(shippingDetDTO);
        shippingDet = shippingDetRepository.save(shippingDet);
        return shippingDetMapper.toDto(shippingDet);
    }

    /**
     * Get all the shippingDets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ShippingDetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ShippingDets");
        return shippingDetRepository.findAll(pageable)
            .map(shippingDetMapper::toDto);
    }

    /**
     * Get one shippingDet by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ShippingDetDTO findOne(Long id) {
        log.debug("Request to get ShippingDet : {}", id);
        ShippingDet shippingDet = shippingDetRepository.findOne(id);
        return shippingDetMapper.toDto(shippingDet);
    }

    /**
     * Delete the shippingDet by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShippingDet : {}", id);
        shippingDetRepository.delete(id);
    }
}
