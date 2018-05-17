package com.ip.marketing.web.rest;

import com.ip.marketing.MarketingApp;

import com.ip.marketing.domain.MarketingDet;
import com.ip.marketing.repository.MarketingDetRepository;
import com.ip.marketing.service.MarketingDetService;
import com.ip.marketing.service.dto.MarketingDetDTO;
import com.ip.marketing.service.mapper.MarketingDetMapper;
import com.ip.marketing.web.rest.errors.ExceptionTranslator;

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

import static com.ip.marketing.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MarketingDetResource REST controller.
 *
 * @see MarketingDetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MarketingApp.class)
public class MarketingDetResourceIntTest {

    private static final String DEFAULT_PRODUCT_CODE = "AAAAAA";
    private static final String UPDATED_PRODUCT_CODE = "BBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private MarketingDetRepository marketingDetRepository;

    @Autowired
    private MarketingDetMapper marketingDetMapper;

    @Autowired
    private MarketingDetService marketingDetService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMarketingDetMockMvc;

    private MarketingDet marketingDet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MarketingDetResource marketingDetResource = new MarketingDetResource(marketingDetService);
        this.restMarketingDetMockMvc = MockMvcBuilders.standaloneSetup(marketingDetResource)
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
    public static MarketingDet createEntity(EntityManager em) {
        MarketingDet marketingDet = new MarketingDet()
            .productCODE(DEFAULT_PRODUCT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return marketingDet;
    }

    @Before
    public void initTest() {
        marketingDet = createEntity(em);
    }

    @Test
    @Transactional
    public void createMarketingDet() throws Exception {
        int databaseSizeBeforeCreate = marketingDetRepository.findAll().size();

        // Create the MarketingDet
        MarketingDetDTO marketingDetDTO = marketingDetMapper.toDto(marketingDet);
        restMarketingDetMockMvc.perform(post("/api/marketing-dets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketingDetDTO)))
            .andExpect(status().isCreated());

        // Validate the MarketingDet in the database
        List<MarketingDet> marketingDetList = marketingDetRepository.findAll();
        assertThat(marketingDetList).hasSize(databaseSizeBeforeCreate + 1);
        MarketingDet testMarketingDet = marketingDetList.get(marketingDetList.size() - 1);
        assertThat(testMarketingDet.getProductCODE()).isEqualTo(DEFAULT_PRODUCT_CODE);
        assertThat(testMarketingDet.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMarketingDet.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createMarketingDetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = marketingDetRepository.findAll().size();

        // Create the MarketingDet with an existing ID
        marketingDet.setId(1L);
        MarketingDetDTO marketingDetDTO = marketingDetMapper.toDto(marketingDet);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarketingDetMockMvc.perform(post("/api/marketing-dets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketingDetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MarketingDet in the database
        List<MarketingDet> marketingDetList = marketingDetRepository.findAll();
        assertThat(marketingDetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkProductCODEIsRequired() throws Exception {
        int databaseSizeBeforeTest = marketingDetRepository.findAll().size();
        // set the field null
        marketingDet.setProductCODE(null);

        // Create the MarketingDet, which fails.
        MarketingDetDTO marketingDetDTO = marketingDetMapper.toDto(marketingDet);

        restMarketingDetMockMvc.perform(post("/api/marketing-dets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketingDetDTO)))
            .andExpect(status().isBadRequest());

        List<MarketingDet> marketingDetList = marketingDetRepository.findAll();
        assertThat(marketingDetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = marketingDetRepository.findAll().size();
        // set the field null
        marketingDet.setName(null);

        // Create the MarketingDet, which fails.
        MarketingDetDTO marketingDetDTO = marketingDetMapper.toDto(marketingDet);

        restMarketingDetMockMvc.perform(post("/api/marketing-dets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketingDetDTO)))
            .andExpect(status().isBadRequest());

        List<MarketingDet> marketingDetList = marketingDetRepository.findAll();
        assertThat(marketingDetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = marketingDetRepository.findAll().size();
        // set the field null
        marketingDet.setDescription(null);

        // Create the MarketingDet, which fails.
        MarketingDetDTO marketingDetDTO = marketingDetMapper.toDto(marketingDet);

        restMarketingDetMockMvc.perform(post("/api/marketing-dets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketingDetDTO)))
            .andExpect(status().isBadRequest());

        List<MarketingDet> marketingDetList = marketingDetRepository.findAll();
        assertThat(marketingDetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMarketingDets() throws Exception {
        // Initialize the database
        marketingDetRepository.saveAndFlush(marketingDet);

        // Get all the marketingDetList
        restMarketingDetMockMvc.perform(get("/api/marketing-dets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketingDet.getId().intValue())))
            .andExpect(jsonPath("$.[*].productCODE").value(hasItem(DEFAULT_PRODUCT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getMarketingDet() throws Exception {
        // Initialize the database
        marketingDetRepository.saveAndFlush(marketingDet);

        // Get the marketingDet
        restMarketingDetMockMvc.perform(get("/api/marketing-dets/{id}", marketingDet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(marketingDet.getId().intValue()))
            .andExpect(jsonPath("$.productCODE").value(DEFAULT_PRODUCT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMarketingDet() throws Exception {
        // Get the marketingDet
        restMarketingDetMockMvc.perform(get("/api/marketing-dets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMarketingDet() throws Exception {
        // Initialize the database
        marketingDetRepository.saveAndFlush(marketingDet);
        int databaseSizeBeforeUpdate = marketingDetRepository.findAll().size();

        // Update the marketingDet
        MarketingDet updatedMarketingDet = marketingDetRepository.findOne(marketingDet.getId());
        // Disconnect from session so that the updates on updatedMarketingDet are not directly saved in db
        em.detach(updatedMarketingDet);
        updatedMarketingDet
            .productCODE(UPDATED_PRODUCT_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        MarketingDetDTO marketingDetDTO = marketingDetMapper.toDto(updatedMarketingDet);

        restMarketingDetMockMvc.perform(put("/api/marketing-dets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketingDetDTO)))
            .andExpect(status().isOk());

        // Validate the MarketingDet in the database
        List<MarketingDet> marketingDetList = marketingDetRepository.findAll();
        assertThat(marketingDetList).hasSize(databaseSizeBeforeUpdate);
        MarketingDet testMarketingDet = marketingDetList.get(marketingDetList.size() - 1);
        assertThat(testMarketingDet.getProductCODE()).isEqualTo(UPDATED_PRODUCT_CODE);
        assertThat(testMarketingDet.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMarketingDet.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingMarketingDet() throws Exception {
        int databaseSizeBeforeUpdate = marketingDetRepository.findAll().size();

        // Create the MarketingDet
        MarketingDetDTO marketingDetDTO = marketingDetMapper.toDto(marketingDet);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMarketingDetMockMvc.perform(put("/api/marketing-dets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketingDetDTO)))
            .andExpect(status().isCreated());

        // Validate the MarketingDet in the database
        List<MarketingDet> marketingDetList = marketingDetRepository.findAll();
        assertThat(marketingDetList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMarketingDet() throws Exception {
        // Initialize the database
        marketingDetRepository.saveAndFlush(marketingDet);
        int databaseSizeBeforeDelete = marketingDetRepository.findAll().size();

        // Get the marketingDet
        restMarketingDetMockMvc.perform(delete("/api/marketing-dets/{id}", marketingDet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MarketingDet> marketingDetList = marketingDetRepository.findAll();
        assertThat(marketingDetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketingDet.class);
        MarketingDet marketingDet1 = new MarketingDet();
        marketingDet1.setId(1L);
        MarketingDet marketingDet2 = new MarketingDet();
        marketingDet2.setId(marketingDet1.getId());
        assertThat(marketingDet1).isEqualTo(marketingDet2);
        marketingDet2.setId(2L);
        assertThat(marketingDet1).isNotEqualTo(marketingDet2);
        marketingDet1.setId(null);
        assertThat(marketingDet1).isNotEqualTo(marketingDet2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketingDetDTO.class);
        MarketingDetDTO marketingDetDTO1 = new MarketingDetDTO();
        marketingDetDTO1.setId(1L);
        MarketingDetDTO marketingDetDTO2 = new MarketingDetDTO();
        assertThat(marketingDetDTO1).isNotEqualTo(marketingDetDTO2);
        marketingDetDTO2.setId(marketingDetDTO1.getId());
        assertThat(marketingDetDTO1).isEqualTo(marketingDetDTO2);
        marketingDetDTO2.setId(2L);
        assertThat(marketingDetDTO1).isNotEqualTo(marketingDetDTO2);
        marketingDetDTO1.setId(null);
        assertThat(marketingDetDTO1).isNotEqualTo(marketingDetDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(marketingDetMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(marketingDetMapper.fromId(null)).isNull();
    }
}
