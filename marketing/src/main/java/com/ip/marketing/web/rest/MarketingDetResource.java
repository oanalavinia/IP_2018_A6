package com.ip.marketing.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ip.marketing.service.MarketingDetService;
import com.ip.marketing.web.rest.errors.BadRequestAlertException;
import com.ip.marketing.web.rest.util.HeaderUtil;
import com.ip.marketing.web.rest.util.PaginationUtil;
import com.ip.marketing.service.dto.MarketingDetDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MarketingDet.
 */
@RestController
@RequestMapping("/api")
public class MarketingDetResource {

    private final Logger log = LoggerFactory.getLogger(MarketingDetResource.class);

    private static final String ENTITY_NAME = "marketingDet";

    private final MarketingDetService marketingDetService;

    public MarketingDetResource(MarketingDetService marketingDetService) {
        this.marketingDetService = marketingDetService;
    }

    /**
     * POST  /marketing-dets : Create a new marketingDet.
     *
     * @param marketingDetDTO the marketingDetDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new marketingDetDTO, or with status 400 (Bad Request) if the marketingDet has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/marketing-dets")
    @Timed
    public ResponseEntity<MarketingDetDTO> createMarketingDet(@Valid @RequestBody MarketingDetDTO marketingDetDTO) throws URISyntaxException {
        log.debug("REST request to save MarketingDet : {}", marketingDetDTO);
        if (marketingDetDTO.getId() != null) {
            throw new BadRequestAlertException("A new marketingDet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MarketingDetDTO result = marketingDetService.save(marketingDetDTO);
        return ResponseEntity.created(new URI("/api/marketing-dets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /marketing-dets : Updates an existing marketingDet.
     *
     * @param marketingDetDTO the marketingDetDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated marketingDetDTO,
     * or with status 400 (Bad Request) if the marketingDetDTO is not valid,
     * or with status 500 (Internal Server Error) if the marketingDetDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/marketing-dets")
    @Timed
    public ResponseEntity<MarketingDetDTO> updateMarketingDet(@Valid @RequestBody MarketingDetDTO marketingDetDTO) throws URISyntaxException {
        log.debug("REST request to update MarketingDet : {}", marketingDetDTO);
        if (marketingDetDTO.getId() == null) {
            return createMarketingDet(marketingDetDTO);
        }
        MarketingDetDTO result = marketingDetService.save(marketingDetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, marketingDetDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /marketing-dets : get all the marketingDets.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of marketingDets in body
     */
    @GetMapping("/marketing-dets")
    @Timed
    public ResponseEntity<List<MarketingDetDTO>> getAllMarketingDets(Pageable pageable) {
        log.debug("REST request to get a page of MarketingDets");
        Page<MarketingDetDTO> page = marketingDetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/marketing-dets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /marketing-dets/:id : get the "id" marketingDet.
     *
     * @param id the id of the marketingDetDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the marketingDetDTO, or with status 404 (Not Found)
     */
    @GetMapping("/marketing-dets/{id}")
    @Timed
    public ResponseEntity<MarketingDetDTO> getMarketingDet(@PathVariable Long id) {
        log.debug("REST request to get MarketingDet : {}", id);
        MarketingDetDTO marketingDetDTO = marketingDetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(marketingDetDTO));
    }

    /**
     * DELETE  /marketing-dets/:id : delete the "id" marketingDet.
     *
     * @param id the id of the marketingDetDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/marketing-dets/{id}")
    @Timed
    public ResponseEntity<Void> deleteMarketingDet(@PathVariable Long id) {
        log.debug("REST request to delete MarketingDet : {}", id);
        marketingDetService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
