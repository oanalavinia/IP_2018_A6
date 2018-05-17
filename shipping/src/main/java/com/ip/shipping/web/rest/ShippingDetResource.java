package com.ip.shipping.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ip.shipping.service.ShippingDetService;
import com.ip.shipping.web.rest.errors.BadRequestAlertException;
import com.ip.shipping.web.rest.util.HeaderUtil;
import com.ip.shipping.web.rest.util.PaginationUtil;
import com.ip.shipping.service.dto.ShippingDetDTO;
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
 * REST controller for managing ShippingDet.
 */
@RestController
@RequestMapping("/api")
public class ShippingDetResource {

    private final Logger log = LoggerFactory.getLogger(ShippingDetResource.class);

    private static final String ENTITY_NAME = "shippingDet";

    private final ShippingDetService shippingDetService;

    public ShippingDetResource(ShippingDetService shippingDetService) {
        this.shippingDetService = shippingDetService;
    }

    /**
     * POST  /shipping-dets : Create a new shippingDet.
     *
     * @param shippingDetDTO the shippingDetDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shippingDetDTO, or with status 400 (Bad Request) if the shippingDet has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shipping-dets")
    @Timed
    public ResponseEntity<ShippingDetDTO> createShippingDet(@Valid @RequestBody ShippingDetDTO shippingDetDTO) throws URISyntaxException {
        log.debug("REST request to save ShippingDet : {}", shippingDetDTO);
        if (shippingDetDTO.getId() != null) {
            throw new BadRequestAlertException("A new shippingDet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShippingDetDTO result = shippingDetService.save(shippingDetDTO);
        return ResponseEntity.created(new URI("/api/shipping-dets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shipping-dets : Updates an existing shippingDet.
     *
     * @param shippingDetDTO the shippingDetDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shippingDetDTO,
     * or with status 400 (Bad Request) if the shippingDetDTO is not valid,
     * or with status 500 (Internal Server Error) if the shippingDetDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shipping-dets")
    @Timed
    public ResponseEntity<ShippingDetDTO> updateShippingDet(@Valid @RequestBody ShippingDetDTO shippingDetDTO) throws URISyntaxException {
        log.debug("REST request to update ShippingDet : {}", shippingDetDTO);
        if (shippingDetDTO.getId() == null) {
            return createShippingDet(shippingDetDTO);
        }
        ShippingDetDTO result = shippingDetService.save(shippingDetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shippingDetDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shipping-dets : get all the shippingDets.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of shippingDets in body
     */
    @GetMapping("/shipping-dets")
    @Timed
    public ResponseEntity<List<ShippingDetDTO>> getAllShippingDets(Pageable pageable) {
        log.debug("REST request to get a page of ShippingDets");
        Page<ShippingDetDTO> page = shippingDetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/shipping-dets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /shipping-dets/:id : get the "id" shippingDet.
     *
     * @param id the id of the shippingDetDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shippingDetDTO, or with status 404 (Not Found)
     */
    @GetMapping("/shipping-dets/{id}")
    @Timed
    public ResponseEntity<ShippingDetDTO> getShippingDet(@PathVariable Long id) {
        log.debug("REST request to get ShippingDet : {}", id);
        ShippingDetDTO shippingDetDTO = shippingDetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(shippingDetDTO));
    }

    /**
     * DELETE  /shipping-dets/:id : delete the "id" shippingDet.
     *
     * @param id the id of the shippingDetDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/shipping-dets/{id}")
    @Timed
    public ResponseEntity<Void> deleteShippingDet(@PathVariable Long id) {
        log.debug("REST request to delete ShippingDet : {}", id);
        shippingDetService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
