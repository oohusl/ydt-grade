package com.ydt.service;

import com.ydt.domain.ImageDepot;
import com.ydt.repository.ImageDepotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing ImageDepot.
 */
@Service
@Transactional
public class ImageDepotService {

    private final Logger log = LoggerFactory.getLogger(ImageDepotService.class);

    private final ImageDepotRepository imageDepotRepository;

    public ImageDepotService(ImageDepotRepository imageDepotRepository) {
        this.imageDepotRepository = imageDepotRepository;
    }

    /**
     * Save a imageDepot.
     *
     * @param imageDepot the entity to save
     * @return the persisted entity
     */
    public ImageDepot save(ImageDepot imageDepot) {
        log.debug("Request to save ImageDepot : {}", imageDepot);
        return imageDepotRepository.save(imageDepot);
    }

    /**
     * Get all the imageDepots.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ImageDepot> findAll() {
        log.debug("Request to get all ImageDepots");
        return imageDepotRepository.findAll();
    }

    /**
     * Get one imageDepot by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ImageDepot findOne(Long id) {
        log.debug("Request to get ImageDepot : {}", id);
        return imageDepotRepository.findOne(id);
    }

    /**
     * Delete the imageDepot by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ImageDepot : {}", id);
        imageDepotRepository.delete(id);
    }
}
