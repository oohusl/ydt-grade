package com.ydt.web.rest;

import com.ydt.YdtApp;

import com.ydt.domain.OrdrCoin;
import com.ydt.repository.OrdrCoinRepository;
import com.ydt.service.OrdrCoinService;
import com.ydt.web.rest.errors.ExceptionTranslator;

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
import java.math.BigDecimal;
import java.util.List;

import static com.ydt.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ydt.domain.enumeration.State;
/**
 * Test class for the OrdrCoinResource REST controller.
 *
 * @see OrdrCoinResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = YdtApp.class)
public class OrdrCoinResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final State DEFAULT_PACKAGE_FLAG = State.YES;
    private static final State UPDATED_PACKAGE_FLAG = State.NO;

    private static final BigDecimal DEFAULT_DECLARED_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_DECLARED_VALUE = new BigDecimal(2);

    @Autowired
    private OrdrCoinRepository ordrCoinRepository;

    @Autowired
    private OrdrCoinService ordrCoinService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrdrCoinMockMvc;

    private OrdrCoin ordrCoin;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrdrCoinResource ordrCoinResource = new OrdrCoinResource(ordrCoinService);
        this.restOrdrCoinMockMvc = MockMvcBuilders.standaloneSetup(ordrCoinResource)
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
    public static OrdrCoin createEntity(EntityManager em) {
        OrdrCoin ordrCoin = new OrdrCoin()
            .name(DEFAULT_NAME)
            .quantity(DEFAULT_QUANTITY)
            .packageFlag(DEFAULT_PACKAGE_FLAG)
            .declaredValue(DEFAULT_DECLARED_VALUE);
        return ordrCoin;
    }

    @Before
    public void initTest() {
        ordrCoin = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrdrCoin() throws Exception {
        int databaseSizeBeforeCreate = ordrCoinRepository.findAll().size();

        // Create the OrdrCoin
        restOrdrCoinMockMvc.perform(post("/api/ordr-coins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordrCoin)))
            .andExpect(status().isCreated());

        // Validate the OrdrCoin in the database
        List<OrdrCoin> ordrCoinList = ordrCoinRepository.findAll();
        assertThat(ordrCoinList).hasSize(databaseSizeBeforeCreate + 1);
        OrdrCoin testOrdrCoin = ordrCoinList.get(ordrCoinList.size() - 1);
        assertThat(testOrdrCoin.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrdrCoin.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testOrdrCoin.getPackageFlag()).isEqualTo(DEFAULT_PACKAGE_FLAG);
        assertThat(testOrdrCoin.getDeclaredValue()).isEqualTo(DEFAULT_DECLARED_VALUE);
    }

    @Test
    @Transactional
    public void createOrdrCoinWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ordrCoinRepository.findAll().size();

        // Create the OrdrCoin with an existing ID
        ordrCoin.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdrCoinMockMvc.perform(post("/api/ordr-coins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordrCoin)))
            .andExpect(status().isBadRequest());

        // Validate the OrdrCoin in the database
        List<OrdrCoin> ordrCoinList = ordrCoinRepository.findAll();
        assertThat(ordrCoinList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOrdrCoins() throws Exception {
        // Initialize the database
        ordrCoinRepository.saveAndFlush(ordrCoin);

        // Get all the ordrCoinList
        restOrdrCoinMockMvc.perform(get("/api/ordr-coins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordrCoin.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].packageFlag").value(hasItem(DEFAULT_PACKAGE_FLAG.toString())))
            .andExpect(jsonPath("$.[*].declaredValue").value(hasItem(DEFAULT_DECLARED_VALUE.intValue())));
    }

    @Test
    @Transactional
    public void getOrdrCoin() throws Exception {
        // Initialize the database
        ordrCoinRepository.saveAndFlush(ordrCoin);

        // Get the ordrCoin
        restOrdrCoinMockMvc.perform(get("/api/ordr-coins/{id}", ordrCoin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ordrCoin.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.packageFlag").value(DEFAULT_PACKAGE_FLAG.toString()))
            .andExpect(jsonPath("$.declaredValue").value(DEFAULT_DECLARED_VALUE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOrdrCoin() throws Exception {
        // Get the ordrCoin
        restOrdrCoinMockMvc.perform(get("/api/ordr-coins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrdrCoin() throws Exception {
        // Initialize the database
        ordrCoinService.save(ordrCoin);

        int databaseSizeBeforeUpdate = ordrCoinRepository.findAll().size();

        // Update the ordrCoin
        OrdrCoin updatedOrdrCoin = ordrCoinRepository.findOne(ordrCoin.getId());
        // Disconnect from session so that the updates on updatedOrdrCoin are not directly saved in db
        em.detach(updatedOrdrCoin);
        updatedOrdrCoin
            .name(UPDATED_NAME)
            .quantity(UPDATED_QUANTITY)
            .packageFlag(UPDATED_PACKAGE_FLAG)
            .declaredValue(UPDATED_DECLARED_VALUE);

        restOrdrCoinMockMvc.perform(put("/api/ordr-coins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrdrCoin)))
            .andExpect(status().isOk());

        // Validate the OrdrCoin in the database
        List<OrdrCoin> ordrCoinList = ordrCoinRepository.findAll();
        assertThat(ordrCoinList).hasSize(databaseSizeBeforeUpdate);
        OrdrCoin testOrdrCoin = ordrCoinList.get(ordrCoinList.size() - 1);
        assertThat(testOrdrCoin.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrdrCoin.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testOrdrCoin.getPackageFlag()).isEqualTo(UPDATED_PACKAGE_FLAG);
        assertThat(testOrdrCoin.getDeclaredValue()).isEqualTo(UPDATED_DECLARED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingOrdrCoin() throws Exception {
        int databaseSizeBeforeUpdate = ordrCoinRepository.findAll().size();

        // Create the OrdrCoin

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOrdrCoinMockMvc.perform(put("/api/ordr-coins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordrCoin)))
            .andExpect(status().isCreated());

        // Validate the OrdrCoin in the database
        List<OrdrCoin> ordrCoinList = ordrCoinRepository.findAll();
        assertThat(ordrCoinList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOrdrCoin() throws Exception {
        // Initialize the database
        ordrCoinService.save(ordrCoin);

        int databaseSizeBeforeDelete = ordrCoinRepository.findAll().size();

        // Get the ordrCoin
        restOrdrCoinMockMvc.perform(delete("/api/ordr-coins/{id}", ordrCoin.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OrdrCoin> ordrCoinList = ordrCoinRepository.findAll();
        assertThat(ordrCoinList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdrCoin.class);
        OrdrCoin ordrCoin1 = new OrdrCoin();
        ordrCoin1.setId(1L);
        OrdrCoin ordrCoin2 = new OrdrCoin();
        ordrCoin2.setId(ordrCoin1.getId());
        assertThat(ordrCoin1).isEqualTo(ordrCoin2);
        ordrCoin2.setId(2L);
        assertThat(ordrCoin1).isNotEqualTo(ordrCoin2);
        ordrCoin1.setId(null);
        assertThat(ordrCoin1).isNotEqualTo(ordrCoin2);
    }
}
