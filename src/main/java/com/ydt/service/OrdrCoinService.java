package com.ydt.service;

import com.ydt.domain.OrdrCoin;
import com.ydt.repository.OrdrCoinRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing OrdrCoin.
 */
@Service
@Transactional
public class OrdrCoinService {

    private final Logger log = LoggerFactory.getLogger(OrdrCoinService.class);

    private final OrdrCoinRepository ordrCoinRepository;

    public OrdrCoinService(OrdrCoinRepository ordrCoinRepository) {
        this.ordrCoinRepository = ordrCoinRepository;
    }

    /**
     * Save a ordrCoin.
     *
     * @param ordrCoin the entity to save
     * @return the persisted entity
     */
    public OrdrCoin save(OrdrCoin ordrCoin) {
        log.debug("Request to save OrdrCoin : {}", ordrCoin);
        return ordrCoinRepository.save(ordrCoin);
    }

    /**
     * Get all the ordrCoins.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<OrdrCoin> findAll() {
        log.debug("Request to get all OrdrCoins");
        return ordrCoinRepository.findAll();
    }

    /**
     * Get one ordrCoin by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public OrdrCoin findOne(Long id) {
        log.debug("Request to get OrdrCoin : {}", id);
        return ordrCoinRepository.findOne(id);
    }

    /**
     * Delete the ordrCoin by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete OrdrCoin : {}", id);
        ordrCoinRepository.delete(id);
    }
}
