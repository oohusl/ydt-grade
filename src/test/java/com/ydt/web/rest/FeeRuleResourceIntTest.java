package com.ydt.web.rest;

import com.ydt.YdtApp;

import com.ydt.domain.FeeRule;
import com.ydt.repository.FeeRuleRepository;
import com.ydt.service.FeeRuleService;
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

/**
 * Test class for the FeeRuleResource REST controller.
 *
 * @see FeeRuleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = YdtApp.class)
public class FeeRuleResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_MIN_VALUE = 1;
    private static final Integer UPDATED_MIN_VALUE = 2;

    private static final Integer DEFAULT_MAX_VALUE = 1;
    private static final Integer UPDATED_MAX_VALUE = 2;

    private static final BigDecimal DEFAULT_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_RATE = new BigDecimal(2);

    private static final Integer DEFAULT_FIXED = 1;
    private static final Integer UPDATED_FIXED = 2;

    @Autowired
    private FeeRuleRepository feeRuleRepository;

    @Autowired
    private FeeRuleService feeRuleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFeeRuleMockMvc;

    private FeeRule feeRule;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FeeRuleResource feeRuleResource = new FeeRuleResource(feeRuleService);
        this.restFeeRuleMockMvc = MockMvcBuilders.standaloneSetup(feeRuleResource)
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
    public static FeeRule createEntity(EntityManager em) {
        FeeRule feeRule = new FeeRule()
            .name(DEFAULT_NAME)
            .minValue(DEFAULT_MIN_VALUE)
            .maxValue(DEFAULT_MAX_VALUE)
            .rate(DEFAULT_RATE)
            .fixed(DEFAULT_FIXED);
        return feeRule;
    }

    @Before
    public void initTest() {
        feeRule = createEntity(em);
    }

    @Test
    @Transactional
    public void createFeeRule() throws Exception {
        int databaseSizeBeforeCreate = feeRuleRepository.findAll().size();

        // Create the FeeRule
        restFeeRuleMockMvc.perform(post("/api/fee-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feeRule)))
            .andExpect(status().isCreated());

        // Validate the FeeRule in the database
        List<FeeRule> feeRuleList = feeRuleRepository.findAll();
        assertThat(feeRuleList).hasSize(databaseSizeBeforeCreate + 1);
        FeeRule testFeeRule = feeRuleList.get(feeRuleList.size() - 1);
        assertThat(testFeeRule.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFeeRule.getMinValue()).isEqualTo(DEFAULT_MIN_VALUE);
        assertThat(testFeeRule.getMaxValue()).isEqualTo(DEFAULT_MAX_VALUE);
        assertThat(testFeeRule.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testFeeRule.getFixed()).isEqualTo(DEFAULT_FIXED);
    }

    @Test
    @Transactional
    public void createFeeRuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = feeRuleRepository.findAll().size();

        // Create the FeeRule with an existing ID
        feeRule.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeeRuleMockMvc.perform(post("/api/fee-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feeRule)))
            .andExpect(status().isBadRequest());

        // Validate the FeeRule in the database
        List<FeeRule> feeRuleList = feeRuleRepository.findAll();
        assertThat(feeRuleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFeeRules() throws Exception {
        // Initialize the database
        feeRuleRepository.saveAndFlush(feeRule);

        // Get all the feeRuleList
        restFeeRuleMockMvc.perform(get("/api/fee-rules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feeRule.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].minValue").value(hasItem(DEFAULT_MIN_VALUE)))
            .andExpect(jsonPath("$.[*].maxValue").value(hasItem(DEFAULT_MAX_VALUE)))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.intValue())))
            .andExpect(jsonPath("$.[*].fixed").value(hasItem(DEFAULT_FIXED)));
    }

    @Test
    @Transactional
    public void getFeeRule() throws Exception {
        // Initialize the database
        feeRuleRepository.saveAndFlush(feeRule);

        // Get the feeRule
        restFeeRuleMockMvc.perform(get("/api/fee-rules/{id}", feeRule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(feeRule.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.minValue").value(DEFAULT_MIN_VALUE))
            .andExpect(jsonPath("$.maxValue").value(DEFAULT_MAX_VALUE))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.intValue()))
            .andExpect(jsonPath("$.fixed").value(DEFAULT_FIXED));
    }

    @Test
    @Transactional
    public void getNonExistingFeeRule() throws Exception {
        // Get the feeRule
        restFeeRuleMockMvc.perform(get("/api/fee-rules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFeeRule() throws Exception {
        // Initialize the database
        feeRuleService.save(feeRule);

        int databaseSizeBeforeUpdate = feeRuleRepository.findAll().size();

        // Update the feeRule
        FeeRule updatedFeeRule = feeRuleRepository.findOne(feeRule.getId());
        // Disconnect from session so that the updates on updatedFeeRule are not directly saved in db
        em.detach(updatedFeeRule);
        updatedFeeRule
            .name(UPDATED_NAME)
            .minValue(UPDATED_MIN_VALUE)
            .maxValue(UPDATED_MAX_VALUE)
            .rate(UPDATED_RATE)
            .fixed(UPDATED_FIXED);

        restFeeRuleMockMvc.perform(put("/api/fee-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFeeRule)))
            .andExpect(status().isOk());

        // Validate the FeeRule in the database
        List<FeeRule> feeRuleList = feeRuleRepository.findAll();
        assertThat(feeRuleList).hasSize(databaseSizeBeforeUpdate);
        FeeRule testFeeRule = feeRuleList.get(feeRuleList.size() - 1);
        assertThat(testFeeRule.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFeeRule.getMinValue()).isEqualTo(UPDATED_MIN_VALUE);
        assertThat(testFeeRule.getMaxValue()).isEqualTo(UPDATED_MAX_VALUE);
        assertThat(testFeeRule.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testFeeRule.getFixed()).isEqualTo(UPDATED_FIXED);
    }

    @Test
    @Transactional
    public void updateNonExistingFeeRule() throws Exception {
        int databaseSizeBeforeUpdate = feeRuleRepository.findAll().size();

        // Create the FeeRule

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFeeRuleMockMvc.perform(put("/api/fee-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feeRule)))
            .andExpect(status().isCreated());

        // Validate the FeeRule in the database
        List<FeeRule> feeRuleList = feeRuleRepository.findAll();
        assertThat(feeRuleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFeeRule() throws Exception {
        // Initialize the database
        feeRuleService.save(feeRule);

        int databaseSizeBeforeDelete = feeRuleRepository.findAll().size();

        // Get the feeRule
        restFeeRuleMockMvc.perform(delete("/api/fee-rules/{id}", feeRule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FeeRule> feeRuleList = feeRuleRepository.findAll();
        assertThat(feeRuleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeeRule.class);
        FeeRule feeRule1 = new FeeRule();
        feeRule1.setId(1L);
        FeeRule feeRule2 = new FeeRule();
        feeRule2.setId(feeRule1.getId());
        assertThat(feeRule1).isEqualTo(feeRule2);
        feeRule2.setId(2L);
        assertThat(feeRule1).isNotEqualTo(feeRule2);
        feeRule1.setId(null);
        assertThat(feeRule1).isNotEqualTo(feeRule2);
    }
}
