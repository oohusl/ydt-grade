package com.ydt.web.rest;

import com.ydt.YdtApp;

import com.ydt.domain.ImageDepot;
import com.ydt.repository.ImageDepotRepository;
import com.ydt.service.ImageDepotService;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.ydt.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ImageDepotResource REST controller.
 *
 * @see ImageDepotResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = YdtApp.class)
public class ImageDepotResourceIntTest {

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_CREATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ImageDepotRepository imageDepotRepository;

    @Autowired
    private ImageDepotService imageDepotService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restImageDepotMockMvc;

    private ImageDepot imageDepot;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ImageDepotResource imageDepotResource = new ImageDepotResource(imageDepotService);
        this.restImageDepotMockMvc = MockMvcBuilders.standaloneSetup(imageDepotResource)
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
    public static ImageDepot createEntity(EntityManager em) {
        ImageDepot imageDepot = new ImageDepot()
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .createDate(DEFAULT_CREATE_DATE);
        return imageDepot;
    }

    @Before
    public void initTest() {
        imageDepot = createEntity(em);
    }

    @Test
    @Transactional
    public void createImageDepot() throws Exception {
        int databaseSizeBeforeCreate = imageDepotRepository.findAll().size();

        // Create the ImageDepot
        restImageDepotMockMvc.perform(post("/api/image-depots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imageDepot)))
            .andExpect(status().isCreated());

        // Validate the ImageDepot in the database
        List<ImageDepot> imageDepotList = imageDepotRepository.findAll();
        assertThat(imageDepotList).hasSize(databaseSizeBeforeCreate + 1);
        ImageDepot testImageDepot = imageDepotList.get(imageDepotList.size() - 1);
        assertThat(testImageDepot.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testImageDepot.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testImageDepot.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createImageDepotWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = imageDepotRepository.findAll().size();

        // Create the ImageDepot with an existing ID
        imageDepot.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImageDepotMockMvc.perform(post("/api/image-depots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imageDepot)))
            .andExpect(status().isBadRequest());

        // Validate the ImageDepot in the database
        List<ImageDepot> imageDepotList = imageDepotRepository.findAll();
        assertThat(imageDepotList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllImageDepots() throws Exception {
        // Initialize the database
        imageDepotRepository.saveAndFlush(imageDepot);

        // Get all the imageDepotList
        restImageDepotMockMvc.perform(get("/api/image-depots?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(imageDepot.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())));
    }

    @Test
    @Transactional
    public void getImageDepot() throws Exception {
        // Initialize the database
        imageDepotRepository.saveAndFlush(imageDepot);

        // Get the imageDepot
        restImageDepotMockMvc.perform(get("/api/image-depots/{id}", imageDepot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(imageDepot.getId().intValue()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingImageDepot() throws Exception {
        // Get the imageDepot
        restImageDepotMockMvc.perform(get("/api/image-depots/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImageDepot() throws Exception {
        // Initialize the database
        imageDepotService.save(imageDepot);

        int databaseSizeBeforeUpdate = imageDepotRepository.findAll().size();

        // Update the imageDepot
        ImageDepot updatedImageDepot = imageDepotRepository.findOne(imageDepot.getId());
        // Disconnect from session so that the updates on updatedImageDepot are not directly saved in db
        em.detach(updatedImageDepot);
        updatedImageDepot
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .createDate(UPDATED_CREATE_DATE);

        restImageDepotMockMvc.perform(put("/api/image-depots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedImageDepot)))
            .andExpect(status().isOk());

        // Validate the ImageDepot in the database
        List<ImageDepot> imageDepotList = imageDepotRepository.findAll();
        assertThat(imageDepotList).hasSize(databaseSizeBeforeUpdate);
        ImageDepot testImageDepot = imageDepotList.get(imageDepotList.size() - 1);
        assertThat(testImageDepot.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testImageDepot.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testImageDepot.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingImageDepot() throws Exception {
        int databaseSizeBeforeUpdate = imageDepotRepository.findAll().size();

        // Create the ImageDepot

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restImageDepotMockMvc.perform(put("/api/image-depots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imageDepot)))
            .andExpect(status().isCreated());

        // Validate the ImageDepot in the database
        List<ImageDepot> imageDepotList = imageDepotRepository.findAll();
        assertThat(imageDepotList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteImageDepot() throws Exception {
        // Initialize the database
        imageDepotService.save(imageDepot);

        int databaseSizeBeforeDelete = imageDepotRepository.findAll().size();

        // Get the imageDepot
        restImageDepotMockMvc.perform(delete("/api/image-depots/{id}", imageDepot.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ImageDepot> imageDepotList = imageDepotRepository.findAll();
        assertThat(imageDepotList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImageDepot.class);
        ImageDepot imageDepot1 = new ImageDepot();
        imageDepot1.setId(1L);
        ImageDepot imageDepot2 = new ImageDepot();
        imageDepot2.setId(imageDepot1.getId());
        assertThat(imageDepot1).isEqualTo(imageDepot2);
        imageDepot2.setId(2L);
        assertThat(imageDepot1).isNotEqualTo(imageDepot2);
        imageDepot1.setId(null);
        assertThat(imageDepot1).isNotEqualTo(imageDepot2);
    }
}
