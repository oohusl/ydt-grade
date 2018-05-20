package com.ydt.web.rest;

import com.ydt.YdtApp;

import com.ydt.domain.UserAddress;
import com.ydt.repository.UserAddressRepository;
import com.ydt.service.UserAddressService;
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
import java.util.List;

import static com.ydt.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserAddressResource REST controller.
 *
 * @see UserAddressResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = YdtApp.class)
public class UserAddressResourceIntTest {

    private static final String DEFAULT_RECEIVER = "AAAAAAAAAA";
    private static final String UPDATED_RECEIVER = "BBBBBBBBBB";

    private static final String DEFAULT_RECEIVER_TEL = "AAAAAAAAAA";
    private static final String UPDATED_RECEIVER_TEL = "BBBBBBBBBB";

    private static final String DEFAULT_RECEIVER_CITY = "AAAAAAAAAA";
    private static final String UPDATED_RECEIVER_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_RECEIVER_ADDR = "AAAAAAAAAA";
    private static final String UPDATED_RECEIVER_ADDR = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SELECTED = false;
    private static final Boolean UPDATED_SELECTED = true;

    @Autowired
    private UserAddressRepository userAddressRepository;

    @Autowired
    private UserAddressService userAddressService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserAddressMockMvc;

    private UserAddress userAddress;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserAddressResource userAddressResource = new UserAddressResource(userAddressService);
        this.restUserAddressMockMvc = MockMvcBuilders.standaloneSetup(userAddressResource)
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
    public static UserAddress createEntity(EntityManager em) {
        UserAddress userAddress = new UserAddress()
            .receiver(DEFAULT_RECEIVER)
            .receiverTel(DEFAULT_RECEIVER_TEL)
            .receiverCity(DEFAULT_RECEIVER_CITY)
            .receiverAddr(DEFAULT_RECEIVER_ADDR)
            .selected(DEFAULT_SELECTED);
        return userAddress;
    }

    @Before
    public void initTest() {
        userAddress = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserAddress() throws Exception {
        int databaseSizeBeforeCreate = userAddressRepository.findAll().size();

        // Create the UserAddress
        restUserAddressMockMvc.perform(post("/api/user-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAddress)))
            .andExpect(status().isCreated());

        // Validate the UserAddress in the database
        List<UserAddress> userAddressList = userAddressRepository.findAll();
        assertThat(userAddressList).hasSize(databaseSizeBeforeCreate + 1);
        UserAddress testUserAddress = userAddressList.get(userAddressList.size() - 1);
        assertThat(testUserAddress.getReceiver()).isEqualTo(DEFAULT_RECEIVER);
        assertThat(testUserAddress.getReceiverTel()).isEqualTo(DEFAULT_RECEIVER_TEL);
        assertThat(testUserAddress.getReceiverCity()).isEqualTo(DEFAULT_RECEIVER_CITY);
        assertThat(testUserAddress.getReceiverAddr()).isEqualTo(DEFAULT_RECEIVER_ADDR);
        assertThat(testUserAddress.isSelected()).isEqualTo(DEFAULT_SELECTED);
    }

    @Test
    @Transactional
    public void createUserAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userAddressRepository.findAll().size();

        // Create the UserAddress with an existing ID
        userAddress.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserAddressMockMvc.perform(post("/api/user-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAddress)))
            .andExpect(status().isBadRequest());

        // Validate the UserAddress in the database
        List<UserAddress> userAddressList = userAddressRepository.findAll();
        assertThat(userAddressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserAddresses() throws Exception {
        // Initialize the database
        userAddressRepository.saveAndFlush(userAddress);

        // Get all the userAddressList
        restUserAddressMockMvc.perform(get("/api/user-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].receiver").value(hasItem(DEFAULT_RECEIVER.toString())))
            .andExpect(jsonPath("$.[*].receiverTel").value(hasItem(DEFAULT_RECEIVER_TEL.toString())))
            .andExpect(jsonPath("$.[*].receiverCity").value(hasItem(DEFAULT_RECEIVER_CITY.toString())))
            .andExpect(jsonPath("$.[*].receiverAddr").value(hasItem(DEFAULT_RECEIVER_ADDR.toString())))
            .andExpect(jsonPath("$.[*].selected").value(hasItem(DEFAULT_SELECTED.booleanValue())));
    }

    @Test
    @Transactional
    public void getUserAddress() throws Exception {
        // Initialize the database
        userAddressRepository.saveAndFlush(userAddress);

        // Get the userAddress
        restUserAddressMockMvc.perform(get("/api/user-addresses/{id}", userAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userAddress.getId().intValue()))
            .andExpect(jsonPath("$.receiver").value(DEFAULT_RECEIVER.toString()))
            .andExpect(jsonPath("$.receiverTel").value(DEFAULT_RECEIVER_TEL.toString()))
            .andExpect(jsonPath("$.receiverCity").value(DEFAULT_RECEIVER_CITY.toString()))
            .andExpect(jsonPath("$.receiverAddr").value(DEFAULT_RECEIVER_ADDR.toString()))
            .andExpect(jsonPath("$.selected").value(DEFAULT_SELECTED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUserAddress() throws Exception {
        // Get the userAddress
        restUserAddressMockMvc.perform(get("/api/user-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserAddress() throws Exception {
        // Initialize the database
        userAddressService.save(userAddress);

        int databaseSizeBeforeUpdate = userAddressRepository.findAll().size();

        // Update the userAddress
        UserAddress updatedUserAddress = userAddressRepository.findOne(userAddress.getId());
        // Disconnect from session so that the updates on updatedUserAddress are not directly saved in db
        em.detach(updatedUserAddress);
        updatedUserAddress
            .receiver(UPDATED_RECEIVER)
            .receiverTel(UPDATED_RECEIVER_TEL)
            .receiverCity(UPDATED_RECEIVER_CITY)
            .receiverAddr(UPDATED_RECEIVER_ADDR)
            .selected(UPDATED_SELECTED);

        restUserAddressMockMvc.perform(put("/api/user-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserAddress)))
            .andExpect(status().isOk());

        // Validate the UserAddress in the database
        List<UserAddress> userAddressList = userAddressRepository.findAll();
        assertThat(userAddressList).hasSize(databaseSizeBeforeUpdate);
        UserAddress testUserAddress = userAddressList.get(userAddressList.size() - 1);
        assertThat(testUserAddress.getReceiver()).isEqualTo(UPDATED_RECEIVER);
        assertThat(testUserAddress.getReceiverTel()).isEqualTo(UPDATED_RECEIVER_TEL);
        assertThat(testUserAddress.getReceiverCity()).isEqualTo(UPDATED_RECEIVER_CITY);
        assertThat(testUserAddress.getReceiverAddr()).isEqualTo(UPDATED_RECEIVER_ADDR);
        assertThat(testUserAddress.isSelected()).isEqualTo(UPDATED_SELECTED);
    }

    @Test
    @Transactional
    public void updateNonExistingUserAddress() throws Exception {
        int databaseSizeBeforeUpdate = userAddressRepository.findAll().size();

        // Create the UserAddress

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserAddressMockMvc.perform(put("/api/user-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAddress)))
            .andExpect(status().isCreated());

        // Validate the UserAddress in the database
        List<UserAddress> userAddressList = userAddressRepository.findAll();
        assertThat(userAddressList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserAddress() throws Exception {
        // Initialize the database
        userAddressService.save(userAddress);

        int databaseSizeBeforeDelete = userAddressRepository.findAll().size();

        // Get the userAddress
        restUserAddressMockMvc.perform(delete("/api/user-addresses/{id}", userAddress.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserAddress> userAddressList = userAddressRepository.findAll();
        assertThat(userAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAddress.class);
        UserAddress userAddress1 = new UserAddress();
        userAddress1.setId(1L);
        UserAddress userAddress2 = new UserAddress();
        userAddress2.setId(userAddress1.getId());
        assertThat(userAddress1).isEqualTo(userAddress2);
        userAddress2.setId(2L);
        assertThat(userAddress1).isNotEqualTo(userAddress2);
        userAddress1.setId(null);
        assertThat(userAddress1).isNotEqualTo(userAddress2);
    }
}
