package com.ip.warehouse.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ip.warehouse.service.WarehouseDetService;
import com.ip.warehouse.web.rest.errors.BadRequestAlertException;
import com.ip.warehouse.web.rest.util.HeaderUtil;
import com.ip.warehouse.web.rest.util.PaginationUtil;
import com.ip.warehouse.service.dto.WarehouseDetDTO;
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
 * REST controller for managing WarehouseDet.
 */
@RestController
@RequestMapping("/api")
public class WarehouseDetResource {

    private final Logger log = LoggerFactory.getLogger(WarehouseDetResource.class);

    private static final String ENTITY_NAME = "warehouseDet";

    private final WarehouseDetService warehouseDetService;

    public WarehouseDetResource(WarehouseDetService warehouseDetService) {
        this.warehouseDetService = warehouseDetService;
    }

    /**
     * POST  /warehouse-dets : Create a new warehouseDet.
     *
     * @param warehouseDetDTO the warehouseDetDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new warehouseDetDTO, or with status 400 (Bad Request) if the warehouseDet has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/warehouse-dets")
    @Timed
    public ResponseEntity<WarehouseDetDTO> createWarehouseDet(@Valid @RequestBody WarehouseDetDTO warehouseDetDTO) throws URISyntaxException {
        log.debug("REST request to save WarehouseDet : {}", warehouseDetDTO);
        if (warehouseDetDTO.getId() != null) {
            throw new BadRequestAlertException("A new warehouseDet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WarehouseDetDTO result = warehouseDetService.save(warehouseDetDTO);
        return ResponseEntity.created(new URI("/api/warehouse-dets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /warehouse-dets : Updates an existing warehouseDet.
     *
     * @param warehouseDetDTO the warehouseDetDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated warehouseDetDTO,
     * or with status 400 (Bad Request) if the warehouseDetDTO is not valid,
     * or with status 500 (Internal Server Error) if the warehouseDetDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/warehouse-dets")
    @Timed
    public ResponseEntity<WarehouseDetDTO> updateWarehouseDet(@Valid @RequestBody WarehouseDetDTO warehouseDetDTO) throws URISyntaxException {
        log.debug("REST request to update WarehouseDet : {}", warehouseDetDTO);
        if (warehouseDetDTO.getId() == null) {
            return createWarehouseDet(warehouseDetDTO);
        }
        WarehouseDetDTO result = warehouseDetService.save(warehouseDetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, warehouseDetDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /warehouse-dets : get all the warehouseDets.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of warehouseDets in body
     */
    @GetMapping("/warehouse-dets")
    @Timed
    public ResponseEntity<List<WarehouseDetDTO>> getAllWarehouseDets(Pageable pageable) {
        log.debug("REST request to get a page of WarehouseDets");
        Page<WarehouseDetDTO> page = warehouseDetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/warehouse-dets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /warehouse-dets/:id : get the "id" warehouseDet.
     *
     * @param id the id of the warehouseDetDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the warehouseDetDTO, or with status 404 (Not Found)
     */
    @GetMapping("/warehouse-dets/{id}")
    @Timed
    public ResponseEntity<WarehouseDetDTO> getWarehouseDet(@PathVariable Long id) {
        log.debug("REST request to get WarehouseDet : {}", id);
        WarehouseDetDTO warehouseDetDTO = warehouseDetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(warehouseDetDTO));
    }

    /**
     * DELETE  /warehouse-dets/:id : delete the "id" warehouseDet.
     *
     * @param id the id of the warehouseDetDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/warehouse-dets/{id}")
    @Timed
    public ResponseEntity<Void> deleteWarehouseDet(@PathVariable Long id) {
        log.debug("REST request to delete WarehouseDet : {}", id);
        warehouseDetService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
