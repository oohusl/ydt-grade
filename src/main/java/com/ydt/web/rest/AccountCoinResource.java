package com.ydt.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ydt.domain.Coin;
import com.ydt.repository.CoinRepository;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Coin.
 */
@RestController
@RequestMapping("/api/account")
public class AccountCoinResource {

    private final Logger log = LoggerFactory.getLogger(AccountCoinResource.class);

    private static final String ENTITY_NAME = "coin";

    @Autowired
    private CoinRepository coinRepository;

    public AccountCoinResource() {
    }


    /**
     * GET  /coins/:id : get the "id" coin.
     *
     * @param certNo the id of the coin to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the coin, or with status 404 (Not Found)
     */
    @GetMapping("/coins/{certNo}")
    @Timed
    public ResponseEntity<Coin> getCoin(@PathVariable String certNo) {
        log.debug("REST request to get Coin : {}", certNo);
        Optional<Coin> coin = coinRepository.findOneByCertNo(certNo);
        return ResponseUtil.wrapOrNotFound(coin);
    }
}
