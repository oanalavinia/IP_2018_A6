package com.ip.warehouse.web.rest;

import com.ip.warehouse.WarehouseApp;

import com.ip.warehouse.domain.WarehouseDet;
import com.ip.warehouse.repository.WarehouseDetRepository;
import com.ip.warehouse.service.WarehouseDetService;
import com.ip.warehouse.service.dto.WarehouseDetDTO;
import com.ip.warehouse.service.mapper.WarehouseDetMapper;
import com.ip.warehouse.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.ip.warehouse.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the WarehouseDetResource REST controller.
 *
 * @see WarehouseDetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WarehouseApp.class)
public class WarehouseDetResourceIntTest {

    private static final String DEFAULT_PRODUCT_CODE = "AAAAAA";
    private static final String UPDATED_PRODUCT_CODE = "BBBBBB";

    private static final Integer DEFAULT_STOCK = 1;
    private static final Integer UPDATED_STOCK = 2;

    @Autowired
    private WarehouseDetRepository warehouseDetRepository;

    @Autowired
    private WarehouseDetMapper warehouseDetMapper;

    @Autowired
    private WarehouseDetService warehouseDetService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWarehouseDetMockMvc;

    private WarehouseDet warehouseDet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WarehouseDetResource warehouseDetResource = new WarehouseDetResource(warehouseDetService);
        this.restWarehouseDetMockMvc = MockMvcBuilders.standaloneSetup(warehouseDetResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WarehouseDet createEntity(EntityManager em) {
        WarehouseDet warehouseDet = new WarehouseDet()
            .productCODE(DEFAULT_PRODUCT_CODE)
            .stock(DEFAULT_STOCK);
        return warehouseDet;
    }

    @Before
    public void initTest() {
        warehouseDet = createEntity(em);
    }

    @Test
    @Transactional
    public void createWarehouseDet() throws Exception {
        int databaseSizeBeforeCreate = warehouseDetRepository.findAll().size();

        // Create the WarehouseDet
        WarehouseDetDTO warehouseDetDTO = warehouseDetMapper.toDto(warehouseDet);
        restWarehouseDetMockMvc.perform(post("/api/warehouse-dets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(warehouseDetDTO)))
            .andExpect(status().isCreated());

        // Validate the WarehouseDet in the database
        List<WarehouseDet> warehouseDetList = warehouseDetRepository.findAll();
        assertThat(warehouseDetList).hasSize(databaseSizeBeforeCreate + 1);
        WarehouseDet testWarehouseDet = warehouseDetList.get(warehouseDetList.size() - 1);
        assertThat(testWarehouseDet.getProductCODE()).isEqualTo(DEFAULT_PRODUCT_CODE);
        assertThat(testWarehouseDet.getStock()).isEqualTo(DEFAULT_STOCK);
    }

    @Test
    @Transactional
    public void createWarehouseDetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = warehouseDetRepository.findAll().size();

        // Create the WarehouseDet with an existing ID
        warehouseDet.setId(1L);
        WarehouseDetDTO warehouseDetDTO = warehouseDetMapper.toDto(warehouseDet);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWarehouseDetMockMvc.perform(post("/api/warehouse-dets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(warehouseDetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WarehouseDet in the database
        List<WarehouseDet> warehouseDetList = warehouseDetRepository.findAll();
        assertThat(warehouseDetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkProductCODEIsRequired() throws Exception {
        int databaseSizeBeforeTest = warehouseDetRepository.findAll().size();
        // set the field null
        warehouseDet.setProductCODE(null);

        // Create the WarehouseDet, which fails.
        WarehouseDetDTO warehouseDetDTO = warehouseDetMapper.toDto(warehouseDet);

        restWarehouseDetMockMvc.perform(post("/api/warehouse-dets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(warehouseDetDTO)))
            .andExpect(status().isBadRequest());

        List<WarehouseDet> warehouseDetList = warehouseDetRepository.findAll();
        assertThat(warehouseDetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStockIsRequired() throws Exception {
        int databaseSizeBeforeTest = warehouseDetRepository.findAll().size();
        // set the field null
        warehouseDet.setStock(null);

        // Create the WarehouseDet, which fails.
        WarehouseDetDTO warehouseDetDTO = warehouseDetMapper.toDto(warehouseDet);

        restWarehouseDetMockMvc.perform(post("/api/warehouse-dets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(warehouseDetDTO)))
            .andExpect(status().isBadRequest());

        List<WarehouseDet> warehouseDetList = warehouseDetRepository.findAll();
        assertThat(warehouseDetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWarehouseDets() throws Exception {
        // Initialize the database
        warehouseDetRepository.saveAndFlush(warehouseDet);

        // Get all the warehouseDetList
        restWarehouseDetMockMvc.perform(get("/api/warehouse-dets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(warehouseDet.getId().intValue())))
            .andExpect(jsonPath("$.[*].productCODE").value(hasItem(DEFAULT_PRODUCT_CODE.toString())))
            .andExpect(jsonPath("$.[*].stock").value(hasItem(DEFAULT_STOCK)));
    }

    @Test
    @Transactional
    public void getWarehouseDet() throws Exception {
        // Initialize the database
        warehouseDetRepository.saveAndFlush(warehouseDet);

        // Get the warehouseDet
        restWarehouseDetMockMvc.perform(get("/api/warehouse-dets/{id}", warehouseDet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(warehouseDet.getId().intValue()))
            .andExpect(jsonPath("$.productCODE").value(DEFAULT_PRODUCT_CODE.toString()))
            .andExpect(jsonPath("$.stock").value(DEFAULT_STOCK));
    }

    @Test
    @Transactional
    public void getNonExistingWarehouseDet() throws Exception {
        // Get the warehouseDet
        restWarehouseDetMockMvc.perform(get("/api/warehouse-dets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWarehouseDet() throws Exception {
        // Initialize the database
        warehouseDetRepository.saveAndFlush(warehouseDet);
        int databaseSizeBeforeUpdate = warehouseDetRepository.findAll().size();

        // Update the warehouseDet
        WarehouseDet updatedWarehouseDet = warehouseDetRepository.findOne(warehouseDet.getId());
        // Disconnect from session so that the updates on updatedWarehouseDet are not directly saved in db
        em.detach(updatedWarehouseDet);
        updatedWarehouseDet
            .productCODE(UPDATED_PRODUCT_CODE)
            .stock(UPDATED_STOCK);
        WarehouseDetDTO warehouseDetDTO = warehouseDetMapper.toDto(updatedWarehouseDet);

        restWarehouseDetMockMvc.perform(put("/api/warehouse-dets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(warehouseDetDTO)))
            .andExpect(status().isOk());

        // Validate the WarehouseDet in the database
        List<WarehouseDet> warehouseDetList = warehouseDetRepository.findAll();
        assertThat(warehouseDetList).hasSize(databaseSizeBeforeUpdate);
        WarehouseDet testWarehouseDet = warehouseDetList.get(warehouseDetList.size() - 1);
        assertThat(testWarehouseDet.getProductCODE()).isEqualTo(UPDATED_PRODUCT_CODE);
        assertThat(testWarehouseDet.getStock()).isEqualTo(UPDATED_STOCK);
    }

    @Test
    @Transactional
    public void updateNonExistingWarehouseDet() throws Exception {
        int databaseSizeBeforeUpdate = warehouseDetRepository.findAll().size();

        // Create the WarehouseDet
        WarehouseDetDTO warehouseDetDTO = warehouseDetMapper.toDto(warehouseDet);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWarehouseDetMockMvc.perform(put("/api/warehouse-dets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(warehouseDetDTO)))
            .andExpect(status().isCreated());

        // Validate the WarehouseDet in the database
        List<WarehouseDet> warehouseDetList = warehouseDetRepository.findAll();
        assertThat(warehouseDetList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWarehouseDet() throws Exception {
        // Initialize the database
        warehouseDetRepository.saveAndFlush(warehouseDet);
        int databaseSizeBeforeDelete = warehouseDetRepository.findAll().size();

        // Get the warehouseDet
        restWarehouseDetMockMvc.perform(delete("/api/warehouse-dets/{id}", warehouseDet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WarehouseDet> warehouseDetList = warehouseDetRepository.findAll();
        assertThat(warehouseDetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WarehouseDet.class);
        WarehouseDet warehouseDet1 = new WarehouseDet();
        warehouseDet1.setId(1L);
        WarehouseDet warehouseDet2 = new WarehouseDet();
        warehouseDet2.setId(warehouseDet1.getId());
        assertThat(warehouseDet1).isEqualTo(warehouseDet2);
        warehouseDet2.setId(2L);
        assertThat(warehouseDet1).isNotEqualTo(warehouseDet2);
        warehouseDet1.setId(null);
        assertThat(warehouseDet1).isNotEqualTo(warehouseDet2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WarehouseDetDTO.class);
        WarehouseDetDTO warehouseDetDTO1 = new WarehouseDetDTO();
        warehouseDetDTO1.setId(1L);
        WarehouseDetDTO warehouseDetDTO2 = new WarehouseDetDTO();
        assertThat(warehouseDetDTO1).isNotEqualTo(warehouseDetDTO2);
        warehouseDetDTO2.setId(warehouseDetDTO1.getId());
        assertThat(warehouseDetDTO1).isEqualTo(warehouseDetDTO2);
        warehouseDetDTO2.setId(2L);
        assertThat(warehouseDetDTO1).isNotEqualTo(warehouseDetDTO2);
        warehouseDetDTO1.setId(null);
        assertThat(warehouseDetDTO1).isNotEqualTo(warehouseDetDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(warehouseDetMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(warehouseDetMapper.fromId(null)).isNull();
    }
}
