package com.ip.salesapp.web.rest;

import com.ip.salesapp.SalesappApp;

import com.ip.salesapp.domain.Sales;
import com.ip.salesapp.repository.SalesRepository;
import com.ip.salesapp.service.SalesService;
import com.ip.salesapp.service.dto.SalesDTO;
import com.ip.salesapp.service.mapper.SalesMapper;
import com.ip.salesapp.web.rest.errors.ExceptionTranslator;

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

import static com.ip.salesapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SalesResource REST controller.
 *
 * @see SalesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SalesappApp.class)
public class SalesResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRICE = 0;
    private static final Integer UPDATED_PRICE = 1;

    @Autowired
    private SalesRepository salesRepository;

    @Autowired
    private SalesMapper salesMapper;

    @Autowired
    private SalesService salesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSalesMockMvc;

    private Sales sales;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SalesResource salesResource = new SalesResource(salesService);
        this.restSalesMockMvc = MockMvcBuilders.standaloneSetup(salesResource)
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
    public static Sales createEntity(EntityManager em) {
        Sales sales = new Sales()
            .name(DEFAULT_NAME)
            .price(DEFAULT_PRICE);
        return sales;
    }

    @Before
    public void initTest() {
        sales = createEntity(em);
    }

    @Test
    @Transactional
    public void createSales() throws Exception {
        int databaseSizeBeforeCreate = salesRepository.findAll().size();

        // Create the Sales
        SalesDTO salesDTO = salesMapper.toDto(sales);
        restSalesMockMvc.perform(post("/api/sales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesDTO)))
            .andExpect(status().isCreated());

        // Validate the Sales in the database
        List<Sales> salesList = salesRepository.findAll();
        assertThat(salesList).hasSize(databaseSizeBeforeCreate + 1);
        Sales testSales = salesList.get(salesList.size() - 1);
        assertThat(testSales.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSales.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createSalesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = salesRepository.findAll().size();

        // Create the Sales with an existing ID
        sales.setId(1L);
        SalesDTO salesDTO = salesMapper.toDto(sales);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalesMockMvc.perform(post("/api/sales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sales in the database
        List<Sales> salesList = salesRepository.findAll();
        assertThat(salesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = salesRepository.findAll().size();
        // set the field null
        sales.setName(null);

        // Create the Sales, which fails.
        SalesDTO salesDTO = salesMapper.toDto(sales);

        restSalesMockMvc.perform(post("/api/sales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesDTO)))
            .andExpect(status().isBadRequest());

        List<Sales> salesList = salesRepository.findAll();
        assertThat(salesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = salesRepository.findAll().size();
        // set the field null
        sales.setPrice(null);

        // Create the Sales, which fails.
        SalesDTO salesDTO = salesMapper.toDto(sales);

        restSalesMockMvc.perform(post("/api/sales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesDTO)))
            .andExpect(status().isBadRequest());

        List<Sales> salesList = salesRepository.findAll();
        assertThat(salesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSales() throws Exception {
        // Initialize the database
        salesRepository.saveAndFlush(sales);

        // Get all the salesList
        restSalesMockMvc.perform(get("/api/sales?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sales.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)));
    }

    @Test
    @Transactional
    public void getSales() throws Exception {
        // Initialize the database
        salesRepository.saveAndFlush(sales);

        // Get the sales
        restSalesMockMvc.perform(get("/api/sales/{id}", sales.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sales.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE));
    }

    @Test
    @Transactional
    public void getNonExistingSales() throws Exception {
        // Get the sales
        restSalesMockMvc.perform(get("/api/sales/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSales() throws Exception {
        // Initialize the database
        salesRepository.saveAndFlush(sales);
        int databaseSizeBeforeUpdate = salesRepository.findAll().size();

        // Update the sales
        Sales updatedSales = salesRepository.findOne(sales.getId());
        // Disconnect from session so that the updates on updatedSales are not directly saved in db
        em.detach(updatedSales);
        updatedSales
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE);
        SalesDTO salesDTO = salesMapper.toDto(updatedSales);

        restSalesMockMvc.perform(put("/api/sales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesDTO)))
            .andExpect(status().isOk());

        // Validate the Sales in the database
        List<Sales> salesList = salesRepository.findAll();
        assertThat(salesList).hasSize(databaseSizeBeforeUpdate);
        Sales testSales = salesList.get(salesList.size() - 1);
        assertThat(testSales.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSales.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingSales() throws Exception {
        int databaseSizeBeforeUpdate = salesRepository.findAll().size();

        // Create the Sales
        SalesDTO salesDTO = salesMapper.toDto(sales);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSalesMockMvc.perform(put("/api/sales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesDTO)))
            .andExpect(status().isCreated());

        // Validate the Sales in the database
        List<Sales> salesList = salesRepository.findAll();
        assertThat(salesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSales() throws Exception {
        // Initialize the database
        salesRepository.saveAndFlush(sales);
        int databaseSizeBeforeDelete = salesRepository.findAll().size();

        // Get the sales
        restSalesMockMvc.perform(delete("/api/sales/{id}", sales.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Sales> salesList = salesRepository.findAll();
        assertThat(salesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sales.class);
        Sales sales1 = new Sales();
        sales1.setId(1L);
        Sales sales2 = new Sales();
        sales2.setId(sales1.getId());
        assertThat(sales1).isEqualTo(sales2);
        sales2.setId(2L);
        assertThat(sales1).isNotEqualTo(sales2);
        sales1.setId(null);
        assertThat(sales1).isNotEqualTo(sales2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalesDTO.class);
        SalesDTO salesDTO1 = new SalesDTO();
        salesDTO1.setId(1L);
        SalesDTO salesDTO2 = new SalesDTO();
        assertThat(salesDTO1).isNotEqualTo(salesDTO2);
        salesDTO2.setId(salesDTO1.getId());
        assertThat(salesDTO1).isEqualTo(salesDTO2);
        salesDTO2.setId(2L);
        assertThat(salesDTO1).isNotEqualTo(salesDTO2);
        salesDTO1.setId(null);
        assertThat(salesDTO1).isNotEqualTo(salesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(salesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(salesMapper.fromId(null)).isNull();
    }
}
