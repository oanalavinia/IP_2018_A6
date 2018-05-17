package com.ip.shipping.web.rest;

import com.ip.shipping.ShippingApp;

import com.ip.shipping.domain.ShippingDet;
import com.ip.shipping.repository.ShippingDetRepository;
import com.ip.shipping.service.ShippingDetService;
import com.ip.shipping.service.dto.ShippingDetDTO;
import com.ip.shipping.service.mapper.ShippingDetMapper;
import com.ip.shipping.web.rest.errors.ExceptionTranslator;

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

import static com.ip.shipping.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ShippingDetResource REST controller.
 *
 * @see ShippingDetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShippingApp.class)
public class ShippingDetResourceIntTest {

    private static final String DEFAULT_PRODUCT_CODE = "AAAAAA";
    private static final String UPDATED_PRODUCT_CODE = "BBBBBB";

    private static final Double DEFAULT_SHIP_COST = 1D;
    private static final Double UPDATED_SHIP_COST = 2D;

    @Autowired
    private ShippingDetRepository shippingDetRepository;

    @Autowired
    private ShippingDetMapper shippingDetMapper;

    @Autowired
    private ShippingDetService shippingDetService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restShippingDetMockMvc;

    private ShippingDet shippingDet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ShippingDetResource shippingDetResource = new ShippingDetResource(shippingDetService);
        this.restShippingDetMockMvc = MockMvcBuilders.standaloneSetup(shippingDetResource)
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
    public static ShippingDet createEntity(EntityManager em) {
        ShippingDet shippingDet = new ShippingDet()
            .productCODE(DEFAULT_PRODUCT_CODE)
            .shipCost(DEFAULT_SHIP_COST);
        return shippingDet;
    }

    @Before
    public void initTest() {
        shippingDet = createEntity(em);
    }

    @Test
    @Transactional
    public void createShippingDet() throws Exception {
        int databaseSizeBeforeCreate = shippingDetRepository.findAll().size();

        // Create the ShippingDet
        ShippingDetDTO shippingDetDTO = shippingDetMapper.toDto(shippingDet);
        restShippingDetMockMvc.perform(post("/api/shipping-dets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shippingDetDTO)))
            .andExpect(status().isCreated());

        // Validate the ShippingDet in the database
        List<ShippingDet> shippingDetList = shippingDetRepository.findAll();
        assertThat(shippingDetList).hasSize(databaseSizeBeforeCreate + 1);
        ShippingDet testShippingDet = shippingDetList.get(shippingDetList.size() - 1);
        assertThat(testShippingDet.getProductCODE()).isEqualTo(DEFAULT_PRODUCT_CODE);
        assertThat(testShippingDet.getShipCost()).isEqualTo(DEFAULT_SHIP_COST);
    }

    @Test
    @Transactional
    public void createShippingDetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shippingDetRepository.findAll().size();

        // Create the ShippingDet with an existing ID
        shippingDet.setId(1L);
        ShippingDetDTO shippingDetDTO = shippingDetMapper.toDto(shippingDet);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShippingDetMockMvc.perform(post("/api/shipping-dets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shippingDetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShippingDet in the database
        List<ShippingDet> shippingDetList = shippingDetRepository.findAll();
        assertThat(shippingDetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkProductCODEIsRequired() throws Exception {
        int databaseSizeBeforeTest = shippingDetRepository.findAll().size();
        // set the field null
        shippingDet.setProductCODE(null);

        // Create the ShippingDet, which fails.
        ShippingDetDTO shippingDetDTO = shippingDetMapper.toDto(shippingDet);

        restShippingDetMockMvc.perform(post("/api/shipping-dets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shippingDetDTO)))
            .andExpect(status().isBadRequest());

        List<ShippingDet> shippingDetList = shippingDetRepository.findAll();
        assertThat(shippingDetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShipCostIsRequired() throws Exception {
        int databaseSizeBeforeTest = shippingDetRepository.findAll().size();
        // set the field null
        shippingDet.setShipCost(null);

        // Create the ShippingDet, which fails.
        ShippingDetDTO shippingDetDTO = shippingDetMapper.toDto(shippingDet);

        restShippingDetMockMvc.perform(post("/api/shipping-dets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shippingDetDTO)))
            .andExpect(status().isBadRequest());

        List<ShippingDet> shippingDetList = shippingDetRepository.findAll();
        assertThat(shippingDetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllShippingDets() throws Exception {
        // Initialize the database
        shippingDetRepository.saveAndFlush(shippingDet);

        // Get all the shippingDetList
        restShippingDetMockMvc.perform(get("/api/shipping-dets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shippingDet.getId().intValue())))
            .andExpect(jsonPath("$.[*].productCODE").value(hasItem(DEFAULT_PRODUCT_CODE.toString())))
            .andExpect(jsonPath("$.[*].shipCost").value(hasItem(DEFAULT_SHIP_COST.doubleValue())));
    }

    @Test
    @Transactional
    public void getShippingDet() throws Exception {
        // Initialize the database
        shippingDetRepository.saveAndFlush(shippingDet);

        // Get the shippingDet
        restShippingDetMockMvc.perform(get("/api/shipping-dets/{id}", shippingDet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shippingDet.getId().intValue()))
            .andExpect(jsonPath("$.productCODE").value(DEFAULT_PRODUCT_CODE.toString()))
            .andExpect(jsonPath("$.shipCost").value(DEFAULT_SHIP_COST.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingShippingDet() throws Exception {
        // Get the shippingDet
        restShippingDetMockMvc.perform(get("/api/shipping-dets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShippingDet() throws Exception {
        // Initialize the database
        shippingDetRepository.saveAndFlush(shippingDet);
        int databaseSizeBeforeUpdate = shippingDetRepository.findAll().size();

        // Update the shippingDet
        ShippingDet updatedShippingDet = shippingDetRepository.findOne(shippingDet.getId());
        // Disconnect from session so that the updates on updatedShippingDet are not directly saved in db
        em.detach(updatedShippingDet);
        updatedShippingDet
            .productCODE(UPDATED_PRODUCT_CODE)
            .shipCost(UPDATED_SHIP_COST);
        ShippingDetDTO shippingDetDTO = shippingDetMapper.toDto(updatedShippingDet);

        restShippingDetMockMvc.perform(put("/api/shipping-dets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shippingDetDTO)))
            .andExpect(status().isOk());

        // Validate the ShippingDet in the database
        List<ShippingDet> shippingDetList = shippingDetRepository.findAll();
        assertThat(shippingDetList).hasSize(databaseSizeBeforeUpdate);
        ShippingDet testShippingDet = shippingDetList.get(shippingDetList.size() - 1);
        assertThat(testShippingDet.getProductCODE()).isEqualTo(UPDATED_PRODUCT_CODE);
        assertThat(testShippingDet.getShipCost()).isEqualTo(UPDATED_SHIP_COST);
    }

    @Test
    @Transactional
    public void updateNonExistingShippingDet() throws Exception {
        int databaseSizeBeforeUpdate = shippingDetRepository.findAll().size();

        // Create the ShippingDet
        ShippingDetDTO shippingDetDTO = shippingDetMapper.toDto(shippingDet);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restShippingDetMockMvc.perform(put("/api/shipping-dets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shippingDetDTO)))
            .andExpect(status().isCreated());

        // Validate the ShippingDet in the database
        List<ShippingDet> shippingDetList = shippingDetRepository.findAll();
        assertThat(shippingDetList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteShippingDet() throws Exception {
        // Initialize the database
        shippingDetRepository.saveAndFlush(shippingDet);
        int databaseSizeBeforeDelete = shippingDetRepository.findAll().size();

        // Get the shippingDet
        restShippingDetMockMvc.perform(delete("/api/shipping-dets/{id}", shippingDet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ShippingDet> shippingDetList = shippingDetRepository.findAll();
        assertThat(shippingDetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShippingDet.class);
        ShippingDet shippingDet1 = new ShippingDet();
        shippingDet1.setId(1L);
        ShippingDet shippingDet2 = new ShippingDet();
        shippingDet2.setId(shippingDet1.getId());
        assertThat(shippingDet1).isEqualTo(shippingDet2);
        shippingDet2.setId(2L);
        assertThat(shippingDet1).isNotEqualTo(shippingDet2);
        shippingDet1.setId(null);
        assertThat(shippingDet1).isNotEqualTo(shippingDet2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShippingDetDTO.class);
        ShippingDetDTO shippingDetDTO1 = new ShippingDetDTO();
        shippingDetDTO1.setId(1L);
        ShippingDetDTO shippingDetDTO2 = new ShippingDetDTO();
        assertThat(shippingDetDTO1).isNotEqualTo(shippingDetDTO2);
        shippingDetDTO2.setId(shippingDetDTO1.getId());
        assertThat(shippingDetDTO1).isEqualTo(shippingDetDTO2);
        shippingDetDTO2.setId(2L);
        assertThat(shippingDetDTO1).isNotEqualTo(shippingDetDTO2);
        shippingDetDTO1.setId(null);
        assertThat(shippingDetDTO1).isNotEqualTo(shippingDetDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(shippingDetMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(shippingDetMapper.fromId(null)).isNull();
    }
}
