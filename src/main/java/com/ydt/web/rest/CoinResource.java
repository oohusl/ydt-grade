package com.ydt.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ydt.domain.Coin;
import com.ydt.service.CoinService;
import com.ydt.web.rest.errors.BadRequestAlertException;
import com.ydt.web.rest.util.HeaderUtil;
import com.ydt.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Coin.
 */
@RestController
@RequestMapping("/api")
public class CoinResource {

    private final Logger log = LoggerFactory.getLogger(CoinResource.class);

    private static final String ENTITY_NAME = "coin";

    private final CoinService coinService;

    public CoinResource(CoinService coinService) {
        this.coinService = coinService;
    }

    /**
     * POST  /coins : Create a new coin.
     *
     * @param coin the coin to create
     * @return the ResponseEntity with status 201 (Created) and with body the new coin, or with status 400 (Bad Request) if the coin has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/coins")
    @Timed
    public ResponseEntity<Coin> createCoin(@RequestBody Coin coin) throws URISyntaxException {
        log.debug("REST request to save Coin : {}", coin);
        if (coin.getId() != null) {
            throw new BadRequestAlertException("A new coin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Coin result = coinService.save(coin);
        return ResponseEntity.created(new URI("/api/coins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /coins : Updates an existing coin.
     *
     * @param coin the coin to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated coin,
     * or with status 400 (Bad Request) if the coin is not valid,
     * or with status 500 (Internal Server Error) if the coin couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/coins")
    @Timed
    public ResponseEntity<Coin> updateCoin(@RequestBody Coin coin) throws URISyntaxException {
        log.debug("REST request to update Coin : {}", coin);
        if (coin.getId() == null) {
            return createCoin(coin);
        }
        Coin result = coinService.save(coin);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, coin.getId().toString()))
            .body(result);
    }

    /**
     * GET  /coins : get all the coins.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of coins in body
     */
    @GetMapping("/coins")
    @Timed
    public ResponseEntity<List<Coin>> getAllCoins(Pageable pageable) {
        log.debug("REST request to get a page of Coins");
        Page<Coin> page = coinService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/coins");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /coins/:id : get the "id" coin.
     *
     * @param id the id of the coin to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the coin, or with status 404 (Not Found)
     */
    @GetMapping("/coins/{id}")
    @Timed
    public ResponseEntity<Coin> getCoin(@PathVariable Long id) {
        log.debug("REST request to get Coin : {}", id);
        Coin coin = coinService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(coin));
    }

    /**
     * DELETE  /coins/:id : delete the "id" coin.
     *
     * @param id the id of the coin to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/coins/{id}")
    @Timed
    public ResponseEntity<Void> deleteCoin(@PathVariable Long id) {
        log.debug("REST request to delete Coin : {}", id);
        coinService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
