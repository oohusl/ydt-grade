package com.ydt.service;

import com.ydt.domain.Ordr;
import com.ydt.repository.OrdrRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Ordr.
 */
@Service
@Transactional
public class OrdrService {

    private final Logger log = LoggerFactory.getLogger(OrdrService.class);

    private final OrdrRepository ordrRepository;

    public OrdrService(OrdrRepository ordrRepository) {
        this.ordrRepository = ordrRepository;
    }

    /**
     * Save a ordr.
     *
     * @param ordr the entity to save
     * @return the persisted entity
     */
    public Ordr save(Ordr ordr) {
        log.debug("Request to save Ordr : {}", ordr);
        return ordrRepository.save(ordr);
    }

    /**
     * Get all the ordrs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Ordr> findAll(Pageable pageable) {
        log.debug("Request to get all Ordrs");
        return ordrRepository.findAll(pageable);
    }

    /**
     * Get one ordr by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Ordr findOne(Long id) {
        log.debug("Request to get Ordr : {}", id);
        return ordrRepository.findOne(id);
    }

    /**
     * Delete the ordr by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Ordr : {}", id);
        ordrRepository.delete(id);
    }
}
