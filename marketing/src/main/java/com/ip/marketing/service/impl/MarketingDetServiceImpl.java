package com.ip.marketing.service.impl;

import com.ip.marketing.service.MarketingDetService;
import com.ip.marketing.domain.MarketingDet;
import com.ip.marketing.repository.MarketingDetRepository;
import com.ip.marketing.service.dto.MarketingDetDTO;
import com.ip.marketing.service.mapper.MarketingDetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing MarketingDet.
 */
@Service
@Transactional
public class MarketingDetServiceImpl implements MarketingDetService {

    private final Logger log = LoggerFactory.getLogger(MarketingDetServiceImpl.class);

    private final MarketingDetRepository marketingDetRepository;

    private final MarketingDetMapper marketingDetMapper;

    public MarketingDetServiceImpl(MarketingDetRepository marketingDetRepository, MarketingDetMapper marketingDetMapper) {
        this.marketingDetRepository = marketingDetRepository;
        this.marketingDetMapper = marketingDetMapper;
    }

    /**
     * Save a marketingDet.
     *
     * @param marketingDetDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MarketingDetDTO save(MarketingDetDTO marketingDetDTO) {
        log.debug("Request to save MarketingDet : {}", marketingDetDTO);
        MarketingDet marketingDet = marketingDetMapper.toEntity(marketingDetDTO);
        marketingDet = marketingDetRepository.save(marketingDet);
        return marketingDetMapper.toDto(marketingDet);
    }

    /**
     * Get all the marketingDets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MarketingDetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MarketingDets");
        return marketingDetRepository.findAll(pageable)
            .map(marketingDetMapper::toDto);
    }

    /**
     * Get one marketingDet by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MarketingDetDTO findOne(Long id) {
        log.debug("Request to get MarketingDet : {}", id);
        MarketingDet marketingDet = marketingDetRepository.findOne(id);
        return marketingDetMapper.toDto(marketingDet);
    }

    /**
     * Delete the marketingDet by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MarketingDet : {}", id);
        marketingDetRepository.delete(id);
    }
}
