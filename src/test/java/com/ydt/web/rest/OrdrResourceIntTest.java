package com.ydt.web.rest;

import com.ydt.YdtApp;

import com.ydt.domain.Ordr;
import com.ydt.repository.OrdrRepository;
import com.ydt.service.OrdrService;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.ydt.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ydt.domain.enumeration.OrderState;
/**
 * Test class for the OrdrResource REST controller.
 *
 * @see OrdrResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = YdtApp.class)
public class OrdrResourceIntTest {

    private static final String DEFAULT_RECEIVER = "AAAAAAAAAA";
    private static final String UPDATED_RECEIVER = "BBBBBBBBBB";

    private static final String DEFAULT_RECEIVER_TEL = "AAAAAAAAAA";
    private static final String UPDATED_RECEIVER_TEL = "BBBBBBBBBB";

    private static final String DEFAULT_RECEIVER_ADDR = "AAAAAAAAAA";
    private static final String UPDATED_RECEIVER_ADDR = "BBBBBBBBBB";

    private static final Integer DEFAULT_DELIVERY_TYPE = 1;
    private static final Integer UPDATED_DELIVERY_TYPE = 2;

    private static final BigDecimal DEFAULT_INSURED_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_INSURED_PRICE = new BigDecimal(2);

    private static final Integer DEFAULT_TOTAL_QUANTITY = 1;
    private static final Integer UPDATED_TOTAL_QUANTITY = 2;

    private static final BigDecimal DEFAULT_HANDING_FEE = new BigDecimal(1);
    private static final BigDecimal UPDATED_HANDING_FEE = new BigDecimal(2);

    private static final Instant DEFAULT_CREATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_RECEIVE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RECEIVE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_RECEIVE_NO = "AAAAAAAAAA";
    private static final String UPDATED_RECEIVE_NO = "BBBBBBBBBB";

    private static final Instant DEFAULT_BACK_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BACK_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_BACK_NO = "AAAAAAAAAA";
    private static final String UPDATED_BACK_NO = "BBBBBBBBBB";

    private static final OrderState DEFAULT_STATE = OrderState.NEW;
    private static final OrderState UPDATED_STATE = OrderState.RECEIVED;

    @Autowired
    private OrdrRepository ordrRepository;

    @Autowired
    private OrdrService ordrService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrdrMockMvc;

    private Ordr ordr;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrdrResource ordrResource = new OrdrResource(ordrService);
        this.restOrdrMockMvc = MockMvcBuilders.standaloneSetup(ordrResource)
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
    public static Ordr createEntity(EntityManager em) {
        Ordr ordr = new Ordr()
            .receiver(DEFAULT_RECEIVER)
            .receiverTel(DEFAULT_RECEIVER_TEL)
            .receiverAddr(DEFAULT_RECEIVER_ADDR)
            .deliveryType(DEFAULT_DELIVERY_TYPE)
            .insuredPrice(DEFAULT_INSURED_PRICE)
            .totalQuantity(DEFAULT_TOTAL_QUANTITY)
            .handingFee(DEFAULT_HANDING_FEE)
            .createDate(DEFAULT_CREATE_DATE)
            .receiveDate(DEFAULT_RECEIVE_DATE)
            .receiveNo(DEFAULT_RECEIVE_NO)
            .backDate(DEFAULT_BACK_DATE)
            .backNo(DEFAULT_BACK_NO)
            .state(DEFAULT_STATE);
        return ordr;
    }

    @Before
    public void initTest() {
        ordr = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrdr() throws Exception {
        int databaseSizeBeforeCreate = ordrRepository.findAll().size();

        // Create the Ordr
        restOrdrMockMvc.perform(post("/api/ordrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordr)))
            .andExpect(status().isCreated());

        // Validate the Ordr in the database
        List<Ordr> ordrList = ordrRepository.findAll();
        assertThat(ordrList).hasSize(databaseSizeBeforeCreate + 1);
        Ordr testOrdr = ordrList.get(ordrList.size() - 1);
        assertThat(testOrdr.getReceiver()).isEqualTo(DEFAULT_RECEIVER);
        assertThat(testOrdr.getReceiverTel()).isEqualTo(DEFAULT_RECEIVER_TEL);
        assertThat(testOrdr.getReceiverAddr()).isEqualTo(DEFAULT_RECEIVER_ADDR);
        assertThat(testOrdr.getDeliveryType()).isEqualTo(DEFAULT_DELIVERY_TYPE);
        assertThat(testOrdr.getInsuredPrice()).isEqualTo(DEFAULT_INSURED_PRICE);
        assertThat(testOrdr.getTotalQuantity()).isEqualTo(DEFAULT_TOTAL_QUANTITY);
        assertThat(testOrdr.getHandingFee()).isEqualTo(DEFAULT_HANDING_FEE);
        assertThat(testOrdr.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testOrdr.getReceiveDate()).isEqualTo(DEFAULT_RECEIVE_DATE);
        assertThat(testOrdr.getReceiveNo()).isEqualTo(DEFAULT_RECEIVE_NO);
        assertThat(testOrdr.getBackDate()).isEqualTo(DEFAULT_BACK_DATE);
        assertThat(testOrdr.getBackNo()).isEqualTo(DEFAULT_BACK_NO);
        assertThat(testOrdr.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    @Transactional
    public void createOrdrWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ordrRepository.findAll().size();

        // Create the Ordr with an existing ID
        ordr.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdrMockMvc.perform(post("/api/ordrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordr)))
            .andExpect(status().isBadRequest());

        // Validate the Ordr in the database
        List<Ordr> ordrList = ordrRepository.findAll();
        assertThat(ordrList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOrdrs() throws Exception {
        // Initialize the database
        ordrRepository.saveAndFlush(ordr);

        // Get all the ordrList
        restOrdrMockMvc.perform(get("/api/ordrs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordr.getId().intValue())))
            .andExpect(jsonPath("$.[*].receiver").value(hasItem(DEFAULT_RECEIVER.toString())))
            .andExpect(jsonPath("$.[*].receiverTel").value(hasItem(DEFAULT_RECEIVER_TEL.toString())))
            .andExpect(jsonPath("$.[*].receiverAddr").value(hasItem(DEFAULT_RECEIVER_ADDR.toString())))
            .andExpect(jsonPath("$.[*].deliveryType").value(hasItem(DEFAULT_DELIVERY_TYPE)))
            .andExpect(jsonPath("$.[*].insuredPrice").value(hasItem(DEFAULT_INSURED_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].totalQuantity").value(hasItem(DEFAULT_TOTAL_QUANTITY)))
            .andExpect(jsonPath("$.[*].handingFee").value(hasItem(DEFAULT_HANDING_FEE.intValue())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].receiveDate").value(hasItem(DEFAULT_RECEIVE_DATE.toString())))
            .andExpect(jsonPath("$.[*].receiveNo").value(hasItem(DEFAULT_RECEIVE_NO.toString())))
            .andExpect(jsonPath("$.[*].backDate").value(hasItem(DEFAULT_BACK_DATE.toString())))
            .andExpect(jsonPath("$.[*].backNo").value(hasItem(DEFAULT_BACK_NO.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())));
    }

    @Test
    @Transactional
    public void getOrdr() throws Exception {
        // Initialize the database
        ordrRepository.saveAndFlush(ordr);

        // Get the ordr
        restOrdrMockMvc.perform(get("/api/ordrs/{id}", ordr.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ordr.getId().intValue()))
            .andExpect(jsonPath("$.receiver").value(DEFAULT_RECEIVER.toString()))
            .andExpect(jsonPath("$.receiverTel").value(DEFAULT_RECEIVER_TEL.toString()))
            .andExpect(jsonPath("$.receiverAddr").value(DEFAULT_RECEIVER_ADDR.toString()))
            .andExpect(jsonPath("$.deliveryType").value(DEFAULT_DELIVERY_TYPE))
            .andExpect(jsonPath("$.insuredPrice").value(DEFAULT_INSURED_PRICE.intValue()))
            .andExpect(jsonPath("$.totalQuantity").value(DEFAULT_TOTAL_QUANTITY))
            .andExpect(jsonPath("$.handingFee").value(DEFAULT_HANDING_FEE.intValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.receiveDate").value(DEFAULT_RECEIVE_DATE.toString()))
            .andExpect(jsonPath("$.receiveNo").value(DEFAULT_RECEIVE_NO.toString()))
            .andExpect(jsonPath("$.backDate").value(DEFAULT_BACK_DATE.toString()))
            .andExpect(jsonPath("$.backNo").value(DEFAULT_BACK_NO.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrdr() throws Exception {
        // Get the ordr
        restOrdrMockMvc.perform(get("/api/ordrs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrdr() throws Exception {
        // Initialize the database
        ordrService.save(ordr);

        int databaseSizeBeforeUpdate = ordrRepository.findAll().size();

        // Update the ordr
        Ordr updatedOrdr = ordrRepository.findOne(ordr.getId());
        // Disconnect from session so that the updates on updatedOrdr are not directly saved in db
        em.detach(updatedOrdr);
        updatedOrdr
            .receiver(UPDATED_RECEIVER)
            .receiverTel(UPDATED_RECEIVER_TEL)
            .receiverAddr(UPDATED_RECEIVER_ADDR)
            .deliveryType(UPDATED_DELIVERY_TYPE)
            .insuredPrice(UPDATED_INSURED_PRICE)
            .totalQuantity(UPDATED_TOTAL_QUANTITY)
            .handingFee(UPDATED_HANDING_FEE)
            .createDate(UPDATED_CREATE_DATE)
            .receiveDate(UPDATED_RECEIVE_DATE)
            .receiveNo(UPDATED_RECEIVE_NO)
            .backDate(UPDATED_BACK_DATE)
            .backNo(UPDATED_BACK_NO)
            .state(UPDATED_STATE);

        restOrdrMockMvc.perform(put("/api/ordrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrdr)))
            .andExpect(status().isOk());

        // Validate the Ordr in the database
        List<Ordr> ordrList = ordrRepository.findAll();
        assertThat(ordrList).hasSize(databaseSizeBeforeUpdate);
        Ordr testOrdr = ordrList.get(ordrList.size() - 1);
        assertThat(testOrdr.getReceiver()).isEqualTo(UPDATED_RECEIVER);
        assertThat(testOrdr.getReceiverTel()).isEqualTo(UPDATED_RECEIVER_TEL);
        assertThat(testOrdr.getReceiverAddr()).isEqualTo(UPDATED_RECEIVER_ADDR);
        assertThat(testOrdr.getDeliveryType()).isEqualTo(UPDATED_DELIVERY_TYPE);
        assertThat(testOrdr.getInsuredPrice()).isEqualTo(UPDATED_INSURED_PRICE);
        assertThat(testOrdr.getTotalQuantity()).isEqualTo(UPDATED_TOTAL_QUANTITY);
        assertThat(testOrdr.getHandingFee()).isEqualTo(UPDATED_HANDING_FEE);
        assertThat(testOrdr.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testOrdr.getReceiveDate()).isEqualTo(UPDATED_RECEIVE_DATE);
        assertThat(testOrdr.getReceiveNo()).isEqualTo(UPDATED_RECEIVE_NO);
        assertThat(testOrdr.getBackDate()).isEqualTo(UPDATED_BACK_DATE);
        assertThat(testOrdr.getBackNo()).isEqualTo(UPDATED_BACK_NO);
        assertThat(testOrdr.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    public void updateNonExistingOrdr() throws Exception {
        int databaseSizeBeforeUpdate = ordrRepository.findAll().size();

        // Create the Ordr

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOrdrMockMvc.perform(put("/api/ordrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordr)))
            .andExpect(status().isCreated());

        // Validate the Ordr in the database
        List<Ordr> ordrList = ordrRepository.findAll();
        assertThat(ordrList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOrdr() throws Exception {
        // Initialize the database
        ordrService.save(ordr);

        int databaseSizeBeforeDelete = ordrRepository.findAll().size();

        // Get the ordr
        restOrdrMockMvc.perform(delete("/api/ordrs/{id}", ordr.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ordr> ordrList = ordrRepository.findAll();
        assertThat(ordrList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ordr.class);
        Ordr ordr1 = new Ordr();
        ordr1.setId(1L);
        Ordr ordr2 = new Ordr();
        ordr2.setId(ordr1.getId());
        assertThat(ordr1).isEqualTo(ordr2);
        ordr2.setId(2L);
        assertThat(ordr1).isNotEqualTo(ordr2);
        ordr1.setId(null);
        assertThat(ordr1).isNotEqualTo(ordr2);
    }
}
