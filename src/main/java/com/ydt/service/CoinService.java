package com.ydt.service;

import com.ydt.domain.Coin;
import com.ydt.repository.CoinRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Coin.
 */
@Service
@Transactional
public class CoinService {

    private final Logger log = LoggerFactory.getLogger(CoinService.class);

    private final CoinRepository coinRepository;

    public CoinService(CoinRepository coinRepository) {
        this.coinRepository = coinRepository;
    }

    /**
     * Save a coin.
     *
     * @param coin the entity to save
     * @return the persisted entity
     */
    public Coin save(Coin coin) {
        log.debug("Request to save Coin : {}", coin);
        return coinRepository.save(coin);
    }

    /**
     * Get all the coins.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Coin> findAll(Pageable pageable) {
        log.debug("Request to get all Coins");
        return coinRepository.findAll(pageable);
    }

    /**
     * Get one coin by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Coin findOne(Long id) {
        log.debug("Request to get Coin : {}", id);
        return coinRepository.findOne(id);
    }

    /**
     * Delete the coin by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Coin : {}", id);
        coinRepository.delete(id);
    }
}
