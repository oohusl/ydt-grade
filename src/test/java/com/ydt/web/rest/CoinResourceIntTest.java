package com.ydt.web.rest;

import com.ydt.YdtApp;

import com.ydt.domain.Coin;
import com.ydt.repository.CoinRepository;
import com.ydt.service.CoinService;
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

import com.ydt.domain.enumeration.Fact;
import com.ydt.domain.enumeration.State;
import com.ydt.domain.enumeration.State;
import com.ydt.domain.enumeration.OrderState;
/**
 * Test class for the CoinResource REST controller.
 *
 * @see CoinResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = YdtApp.class)
public class CoinResourceIntTest {

    private static final String DEFAULT_CERT_NO = "AAAAAAAAAA";
    private static final String UPDATED_CERT_NO = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_NO = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_YEAR = "BBBBBBBBBB";

    private static final Integer DEFAULT_DENOM = 1;
    private static final Integer UPDATED_DENOM = 2;

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PACKAGE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PACKAGE_TYPE = "BBBBBBBBBB";

    private static final Fact DEFAULT_GRADING_RESULT = Fact.OK;
    private static final Fact UPDATED_GRADING_RESULT = Fact.NOT;

    private static final State DEFAULT_BLOCK_CHAIN_FLAG = State.YES;
    private static final State UPDATED_BLOCK_CHAIN_FLAG = State.NO;

    private static final BigDecimal DEFAULT_DECLARED_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_DECLARED_VALUE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_HANDING_FEE = new BigDecimal(1);
    private static final BigDecimal UPDATED_HANDING_FEE = new BigDecimal(2);

    private static final State DEFAULT_PACKAGE_FLAG = State.YES;
    private static final State UPDATED_PACKAGE_FLAG = State.NO;

    private static final OrderState DEFAULT_STATE = OrderState.NEW;
    private static final OrderState UPDATED_STATE = OrderState.RECEIVED;

    private static final String DEFAULT_FRONT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_FRONT_IMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_BACK_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_BACK_IMAGE = "BBBBBBBBBB";

    @Autowired
    private CoinRepository coinRepository;

    @Autowired
    private CoinService coinService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCoinMockMvc;

    private Coin coin;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CoinResource coinResource = new CoinResource(coinService);
        this.restCoinMockMvc = MockMvcBuilders.standaloneSetup(coinResource)
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
    public static Coin createEntity(EntityManager em) {
        Coin coin = new Coin()
            .certNo(DEFAULT_CERT_NO)
            .typeNo(DEFAULT_TYPE_NO)
            .year(DEFAULT_YEAR)
            .denom(DEFAULT_DENOM)
            .country(DEFAULT_COUNTRY)
            .name(DEFAULT_NAME)
            .packageType(DEFAULT_PACKAGE_TYPE)
            .gradingResult(DEFAULT_GRADING_RESULT)
            .blockChainFlag(DEFAULT_BLOCK_CHAIN_FLAG)
            .declaredValue(DEFAULT_DECLARED_VALUE)
            .handingFee(DEFAULT_HANDING_FEE)
            .packageFlag(DEFAULT_PACKAGE_FLAG)
            .state(DEFAULT_STATE)
            .frontImage(DEFAULT_FRONT_IMAGE)
            .backImage(DEFAULT_BACK_IMAGE);
        return coin;
    }

    @Before
    public void initTest() {
        coin = createEntity(em);
    }

    @Test
    @Transactional
    public void createCoin() throws Exception {
        int databaseSizeBeforeCreate = coinRepository.findAll().size();

        // Create the Coin
        restCoinMockMvc.perform(post("/api/coins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coin)))
            .andExpect(status().isCreated());

        // Validate the Coin in the database
        List<Coin> coinList = coinRepository.findAll();
        assertThat(coinList).hasSize(databaseSizeBeforeCreate + 1);
        Coin testCoin = coinList.get(coinList.size() - 1);
        assertThat(testCoin.getCertNo()).isEqualTo(DEFAULT_CERT_NO);
        assertThat(testCoin.getTypeNo()).isEqualTo(DEFAULT_TYPE_NO);
        assertThat(testCoin.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testCoin.getDenom()).isEqualTo(DEFAULT_DENOM);
        assertThat(testCoin.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testCoin.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCoin.getPackageType()).isEqualTo(DEFAULT_PACKAGE_TYPE);
        assertThat(testCoin.getGradingResult()).isEqualTo(DEFAULT_GRADING_RESULT);
        assertThat(testCoin.getBlockChainFlag()).isEqualTo(DEFAULT_BLOCK_CHAIN_FLAG);
        assertThat(testCoin.getDeclaredValue()).isEqualTo(DEFAULT_DECLARED_VALUE);
        assertThat(testCoin.getHandingFee()).isEqualTo(DEFAULT_HANDING_FEE);
        assertThat(testCoin.getPackageFlag()).isEqualTo(DEFAULT_PACKAGE_FLAG);
        assertThat(testCoin.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testCoin.getFrontImage()).isEqualTo(DEFAULT_FRONT_IMAGE);
        assertThat(testCoin.getBackImage()).isEqualTo(DEFAULT_BACK_IMAGE);
    }

    @Test
    @Transactional
    public void createCoinWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = coinRepository.findAll().size();

        // Create the Coin with an existing ID
        coin.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoinMockMvc.perform(post("/api/coins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coin)))
            .andExpect(status().isBadRequest());

        // Validate the Coin in the database
        List<Coin> coinList = coinRepository.findAll();
        assertThat(coinList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCoins() throws Exception {
        // Initialize the database
        coinRepository.saveAndFlush(coin);

        // Get all the coinList
        restCoinMockMvc.perform(get("/api/coins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coin.getId().intValue())))
            .andExpect(jsonPath("$.[*].certNo").value(hasItem(DEFAULT_CERT_NO.toString())))
            .andExpect(jsonPath("$.[*].typeNo").value(hasItem(DEFAULT_TYPE_NO.toString())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR.toString())))
            .andExpect(jsonPath("$.[*].denom").value(hasItem(DEFAULT_DENOM)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].packageType").value(hasItem(DEFAULT_PACKAGE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].gradingResult").value(hasItem(DEFAULT_GRADING_RESULT.toString())))
            .andExpect(jsonPath("$.[*].blockChainFlag").value(hasItem(DEFAULT_BLOCK_CHAIN_FLAG.toString())))
            .andExpect(jsonPath("$.[*].declaredValue").value(hasItem(DEFAULT_DECLARED_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].handingFee").value(hasItem(DEFAULT_HANDING_FEE.intValue())))
            .andExpect(jsonPath("$.[*].packageFlag").value(hasItem(DEFAULT_PACKAGE_FLAG.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].frontImage").value(hasItem(DEFAULT_FRONT_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].backImage").value(hasItem(DEFAULT_BACK_IMAGE.toString())));
    }

    @Test
    @Transactional
    public void getCoin() throws Exception {
        // Initialize the database
        coinRepository.saveAndFlush(coin);

        // Get the coin
        restCoinMockMvc.perform(get("/api/coins/{id}", coin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(coin.getId().intValue()))
            .andExpect(jsonPath("$.certNo").value(DEFAULT_CERT_NO.toString()))
            .andExpect(jsonPath("$.typeNo").value(DEFAULT_TYPE_NO.toString()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR.toString()))
            .andExpect(jsonPath("$.denom").value(DEFAULT_DENOM))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.packageType").value(DEFAULT_PACKAGE_TYPE.toString()))
            .andExpect(jsonPath("$.gradingResult").value(DEFAULT_GRADING_RESULT.toString()))
            .andExpect(jsonPath("$.blockChainFlag").value(DEFAULT_BLOCK_CHAIN_FLAG.toString()))
            .andExpect(jsonPath("$.declaredValue").value(DEFAULT_DECLARED_VALUE.intValue()))
            .andExpect(jsonPath("$.handingFee").value(DEFAULT_HANDING_FEE.intValue()))
            .andExpect(jsonPath("$.packageFlag").value(DEFAULT_PACKAGE_FLAG.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.frontImage").value(DEFAULT_FRONT_IMAGE.toString()))
            .andExpect(jsonPath("$.backImage").value(DEFAULT_BACK_IMAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCoin() throws Exception {
        // Get the coin
        restCoinMockMvc.perform(get("/api/coins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCoin() throws Exception {
        // Initialize the database
        coinService.save(coin);

        int databaseSizeBeforeUpdate = coinRepository.findAll().size();

        // Update the coin
        Coin updatedCoin = coinRepository.findOne(coin.getId());
        // Disconnect from session so that the updates on updatedCoin are not directly saved in db
        em.detach(updatedCoin);
        updatedCoin
            .certNo(UPDATED_CERT_NO)
            .typeNo(UPDATED_TYPE_NO)
            .year(UPDATED_YEAR)
            .denom(UPDATED_DENOM)
            .country(UPDATED_COUNTRY)
            .name(UPDATED_NAME)
            .packageType(UPDATED_PACKAGE_TYPE)
            .gradingResult(UPDATED_GRADING_RESULT)
            .blockChainFlag(UPDATED_BLOCK_CHAIN_FLAG)
            .declaredValue(UPDATED_DECLARED_VALUE)
            .handingFee(UPDATED_HANDING_FEE)
            .packageFlag(UPDATED_PACKAGE_FLAG)
            .state(UPDATED_STATE)
            .frontImage(UPDATED_FRONT_IMAGE)
            .backImage(UPDATED_BACK_IMAGE);

        restCoinMockMvc.perform(put("/api/coins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCoin)))
            .andExpect(status().isOk());

        // Validate the Coin in the database
        List<Coin> coinList = coinRepository.findAll();
        assertThat(coinList).hasSize(databaseSizeBeforeUpdate);
        Coin testCoin = coinList.get(coinList.size() - 1);
        assertThat(testCoin.getCertNo()).isEqualTo(UPDATED_CERT_NO);
        assertThat(testCoin.getTypeNo()).isEqualTo(UPDATED_TYPE_NO);
        assertThat(testCoin.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testCoin.getDenom()).isEqualTo(UPDATED_DENOM);
        assertThat(testCoin.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testCoin.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCoin.getPackageType()).isEqualTo(UPDATED_PACKAGE_TYPE);
        assertThat(testCoin.getGradingResult()).isEqualTo(UPDATED_GRADING_RESULT);
        assertThat(testCoin.getBlockChainFlag()).isEqualTo(UPDATED_BLOCK_CHAIN_FLAG);
        assertThat(testCoin.getDeclaredValue()).isEqualTo(UPDATED_DECLARED_VALUE);
        assertThat(testCoin.getHandingFee()).isEqualTo(UPDATED_HANDING_FEE);
        assertThat(testCoin.getPackageFlag()).isEqualTo(UPDATED_PACKAGE_FLAG);
        assertThat(testCoin.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testCoin.getFrontImage()).isEqualTo(UPDATED_FRONT_IMAGE);
        assertThat(testCoin.getBackImage()).isEqualTo(UPDATED_BACK_IMAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingCoin() throws Exception {
        int databaseSizeBeforeUpdate = coinRepository.findAll().size();

        // Create the Coin

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCoinMockMvc.perform(put("/api/coins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coin)))
            .andExpect(status().isCreated());

        // Validate the Coin in the database
        List<Coin> coinList = coinRepository.findAll();
        assertThat(coinList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCoin() throws Exception {
        // Initialize the database
        coinService.save(coin);

        int databaseSizeBeforeDelete = coinRepository.findAll().size();

        // Get the coin
        restCoinMockMvc.perform(delete("/api/coins/{id}", coin.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Coin> coinList = coinRepository.findAll();
        assertThat(coinList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Coin.class);
        Coin coin1 = new Coin();
        coin1.setId(1L);
        Coin coin2 = new Coin();
        coin2.setId(coin1.getId());
        assertThat(coin1).isEqualTo(coin2);
        coin2.setId(2L);
        assertThat(coin1).isNotEqualTo(coin2);
        coin1.setId(null);
        assertThat(coin1).isNotEqualTo(coin2);
    }
}
