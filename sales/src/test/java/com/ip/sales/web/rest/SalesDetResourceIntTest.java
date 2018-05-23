package com.ip.sales.web.rest;

import com.ip.sales.SalesApp;

import com.ip.sales.domain.SalesDet;
import com.ip.sales.repository.SalesDetRepository;
import com.ip.sales.service.SalesDetService;
import com.ip.sales.service.dto.SalesDetDTO;
import com.ip.sales.service.mapper.SalesDetMapper;
import com.ip.sales.web.rest.errors.ExceptionTranslator;

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

import static com.ip.sales.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SalesDetResource REST controller.
 *
 * @see SalesDetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SalesApp.class)
public class SalesDetResourceIntTest {

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final Long DEFAULT_PRODUCT_ID = 1L;
    private static final Long UPDATED_PRODUCT_ID = 2L;

    @Autowired
    private SalesDetRepository salesDetRepository;

    @Autowired
    private SalesDetMapper salesDetMapper;

    @Autowired
    private SalesDetService salesDetService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSalesDetMockMvc;

    private SalesDet salesDet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SalesDetResource salesDetResource = new SalesDetResource(salesDetService);
        this.restSalesDetMockMvc = MockMvcBuilders.standaloneSetup(salesDetResource)
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
    public static SalesDet createEntity(EntityManager em) {
        SalesDet salesDet = new SalesDet()
            .price(DEFAULT_PRICE)
            .productId(DEFAULT_PRODUCT_ID);
        return salesDet;
    }

    @Before
    public void initTest() {
        salesDet = createEntity(em);
    }

    @Test
    @Transactional
    public void createSalesDet() throws Exception {
        int databaseSizeBeforeCreate = salesDetRepository.findAll().size();

        // Create the SalesDet
        SalesDetDTO salesDetDTO = salesDetMapper.toDto(salesDet);
        restSalesDetMockMvc.perform(post("/api/sales-dets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesDetDTO)))
            .andExpect(status().isCreated());

        // Validate the SalesDet in the database
        List<SalesDet> salesDetList = salesDetRepository.findAll();
        assertThat(salesDetList).hasSize(databaseSizeBeforeCreate + 1);
        SalesDet testSalesDet = salesDetList.get(salesDetList.size() - 1);
        assertThat(testSalesDet.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testSalesDet.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
    }

    @Test
    @Transactional
    public void createSalesDetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = salesDetRepository.findAll().size();

        // Create the SalesDet with an existing ID
        salesDet.setId(1L);
        SalesDetDTO salesDetDTO = salesDetMapper.toDto(salesDet);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalesDetMockMvc.perform(post("/api/sales-dets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesDetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SalesDet in the database
        List<SalesDet> salesDetList = salesDetRepository.findAll();
        assertThat(salesDetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = salesDetRepository.findAll().size();
        // set the field null
        salesDet.setPrice(null);

        // Create the SalesDet, which fails.
        SalesDetDTO salesDetDTO = salesDetMapper.toDto(salesDet);

        restSalesDetMockMvc.perform(post("/api/sales-dets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesDetDTO)))
            .andExpect(status().isBadRequest());

        List<SalesDet> salesDetList = salesDetRepository.findAll();
        assertThat(salesDetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProductIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = salesDetRepository.findAll().size();
        // set the field null
        salesDet.setProductId(null);

        // Create the SalesDet, which fails.
        SalesDetDTO salesDetDTO = salesDetMapper.toDto(salesDet);

        restSalesDetMockMvc.perform(post("/api/sales-dets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesDetDTO)))
            .andExpect(status().isBadRequest());

        List<SalesDet> salesDetList = salesDetRepository.findAll();
        assertThat(salesDetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSalesDets() throws Exception {
        // Initialize the database
        salesDetRepository.saveAndFlush(salesDet);

        // Get all the salesDetList
        restSalesDetMockMvc.perform(get("/api/sales-dets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesDet.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.intValue())));
    }

    @Test
    @Transactional
    public void getSalesDet() throws Exception {
        // Initialize the database
        salesDetRepository.saveAndFlush(salesDet);

        // Get the salesDet
        restSalesDetMockMvc.perform(get("/api/sales-dets/{id}", salesDet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(salesDet.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSalesDet() throws Exception {
        // Get the salesDet
        restSalesDetMockMvc.perform(get("/api/sales-dets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSalesDet() throws Exception {
        // Initialize the database
        salesDetRepository.saveAndFlush(salesDet);
        int databaseSizeBeforeUpdate = salesDetRepository.findAll().size();

        // Update the salesDet
        SalesDet updatedSalesDet = salesDetRepository.findOne(salesDet.getId());
        // Disconnect from session so that the updates on updatedSalesDet are not directly saved in db
        em.detach(updatedSalesDet);
        updatedSalesDet
            .price(UPDATED_PRICE)
            .productId(UPDATED_PRODUCT_ID);
        SalesDetDTO salesDetDTO = salesDetMapper.toDto(updatedSalesDet);

        restSalesDetMockMvc.perform(put("/api/sales-dets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesDetDTO)))
            .andExpect(status().isOk());

        // Validate the SalesDet in the database
        List<SalesDet> salesDetList = salesDetRepository.findAll();
        assertThat(salesDetList).hasSize(databaseSizeBeforeUpdate);
        SalesDet testSalesDet = salesDetList.get(salesDetList.size() - 1);
        assertThat(testSalesDet.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testSalesDet.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingSalesDet() throws Exception {
        int databaseSizeBeforeUpdate = salesDetRepository.findAll().size();

        // Create the SalesDet
        SalesDetDTO salesDetDTO = salesDetMapper.toDto(salesDet);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSalesDetMockMvc.perform(put("/api/sales-dets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesDetDTO)))
            .andExpect(status().isCreated());

        // Validate the SalesDet in the database
        List<SalesDet> salesDetList = salesDetRepository.findAll();
        assertThat(salesDetList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSalesDet() throws Exception {
        // Initialize the database
        salesDetRepository.saveAndFlush(salesDet);
        int databaseSizeBeforeDelete = salesDetRepository.findAll().size();

        // Get the salesDet
        restSalesDetMockMvc.perform(delete("/api/sales-dets/{id}", salesDet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SalesDet> salesDetList = salesDetRepository.findAll();
        assertThat(salesDetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalesDet.class);
        SalesDet salesDet1 = new SalesDet();
        salesDet1.setId(1L);
        SalesDet salesDet2 = new SalesDet();
        salesDet2.setId(salesDet1.getId());
        assertThat(salesDet1).isEqualTo(salesDet2);
        salesDet2.setId(2L);
        assertThat(salesDet1).isNotEqualTo(salesDet2);
        salesDet1.setId(null);
        assertThat(salesDet1).isNotEqualTo(salesDet2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalesDetDTO.class);
        SalesDetDTO salesDetDTO1 = new SalesDetDTO();
        salesDetDTO1.setId(1L);
        SalesDetDTO salesDetDTO2 = new SalesDetDTO();
        assertThat(salesDetDTO1).isNotEqualTo(salesDetDTO2);
        salesDetDTO2.setId(salesDetDTO1.getId());
        assertThat(salesDetDTO1).isEqualTo(salesDetDTO2);
        salesDetDTO2.setId(2L);
        assertThat(salesDetDTO1).isNotEqualTo(salesDetDTO2);
        salesDetDTO1.setId(null);
        assertThat(salesDetDTO1).isNotEqualTo(salesDetDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(salesDetMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(salesDetMapper.fromId(null)).isNull();
    }
}
