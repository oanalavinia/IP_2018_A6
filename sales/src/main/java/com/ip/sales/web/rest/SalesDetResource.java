package com.ip.sales.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ip.sales.service.SalesDetService;
import com.ip.sales.web.rest.errors.BadRequestAlertException;
import com.ip.sales.web.rest.util.HeaderUtil;
import com.ip.sales.web.rest.util.PaginationUtil;
import com.ip.sales.service.dto.SalesDetDTO;
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
 * REST controller for managing SalesDet.
 */
@RestController
@RequestMapping("/api")
public class SalesDetResource {

    private final Logger log = LoggerFactory.getLogger(SalesDetResource.class);

    private static final String ENTITY_NAME = "salesDet";

    private final SalesDetService salesDetService;

    public SalesDetResource(SalesDetService salesDetService) {
        this.salesDetService = salesDetService;
    }

    /**
     * POST  /sales-dets : Create a new salesDet.
     *
     * @param salesDetDTO the salesDetDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new salesDetDTO, or with status 400 (Bad Request) if the salesDet has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sales-dets")
    @Timed
    public ResponseEntity<SalesDetDTO> createSalesDet(@Valid @RequestBody SalesDetDTO salesDetDTO) throws URISyntaxException {
        log.debug("REST request to save SalesDet : {}", salesDetDTO);
        if (salesDetDTO.getId() != null) {
            throw new BadRequestAlertException("A new salesDet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SalesDetDTO result = salesDetService.save(salesDetDTO);
        return ResponseEntity.created(new URI("/api/sales-dets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sales-dets : Updates an existing salesDet.
     *
     * @param salesDetDTO the salesDetDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated salesDetDTO,
     * or with status 400 (Bad Request) if the salesDetDTO is not valid,
     * or with status 500 (Internal Server Error) if the salesDetDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sales-dets")
    @Timed
    public ResponseEntity<SalesDetDTO> updateSalesDet(@Valid @RequestBody SalesDetDTO salesDetDTO) throws URISyntaxException {
        log.debug("REST request to update SalesDet : {}", salesDetDTO);
        if (salesDetDTO.getId() == null) {
            return createSalesDet(salesDetDTO);
        }
        SalesDetDTO result = salesDetService.save(salesDetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, salesDetDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sales-dets : get all the salesDets.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of salesDets in body
     */
    @GetMapping("/sales-dets")
    @Timed
    public ResponseEntity<List<SalesDetDTO>> getAllSalesDets(Pageable pageable) {
        log.debug("REST request to get a page of SalesDets");
        Page<SalesDetDTO> page = salesDetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sales-dets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sales-dets/:id : get the "id" salesDet.
     *
     * @param id the id of the salesDetDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the salesDetDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sales-dets/{id}")
    @Timed
    public ResponseEntity<SalesDetDTO> getSalesDet(@PathVariable Long id) {
        log.debug("REST request to get SalesDet : {}", id);
        SalesDetDTO salesDetDTO = salesDetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(salesDetDTO));
    }

    /**
     * DELETE  /sales-dets/:id : delete the "id" salesDet.
     *
     * @param id the id of the salesDetDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sales-dets/{id}")
    @Timed
    public ResponseEntity<Void> deleteSalesDet(@PathVariable Long id) {
        log.debug("REST request to delete SalesDet : {}", id);
        salesDetService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
