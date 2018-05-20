package com.ydt.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ydt.domain.OrdrCoin;
import com.ydt.service.OrdrCoinService;
import com.ydt.web.rest.errors.BadRequestAlertException;
import com.ydt.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing OrdrCoin.
 */
@RestController
@RequestMapping("/api")
public class OrdrCoinResource {

    private final Logger log = LoggerFactory.getLogger(OrdrCoinResource.class);

    private static final String ENTITY_NAME = "ordrCoin";

    private final OrdrCoinService ordrCoinService;

    public OrdrCoinResource(OrdrCoinService ordrCoinService) {
        this.ordrCoinService = ordrCoinService;
    }

    /**
     * POST  /ordr-coins : Create a new ordrCoin.
     *
     * @param ordrCoin the ordrCoin to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ordrCoin, or with status 400 (Bad Request) if the ordrCoin has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ordr-coins")
    @Timed
    public ResponseEntity<OrdrCoin> createOrdrCoin(@RequestBody OrdrCoin ordrCoin) throws URISyntaxException {
        log.debug("REST request to save OrdrCoin : {}", ordrCoin);
        if (ordrCoin.getId() != null) {
            throw new BadRequestAlertException("A new ordrCoin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrdrCoin result = ordrCoinService.save(ordrCoin);
        return ResponseEntity.created(new URI("/api/ordr-coins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ordr-coins : Updates an existing ordrCoin.
     *
     * @param ordrCoin the ordrCoin to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ordrCoin,
     * or with status 400 (Bad Request) if the ordrCoin is not valid,
     * or with status 500 (Internal Server Error) if the ordrCoin couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ordr-coins")
    @Timed
    public ResponseEntity<OrdrCoin> updateOrdrCoin(@RequestBody OrdrCoin ordrCoin) throws URISyntaxException {
        log.debug("REST request to update OrdrCoin : {}", ordrCoin);
        if (ordrCoin.getId() == null) {
            return createOrdrCoin(ordrCoin);
        }
        OrdrCoin result = ordrCoinService.save(ordrCoin);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ordrCoin.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ordr-coins : get all the ordrCoins.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ordrCoins in body
     */
    @GetMapping("/ordr-coins")
    @Timed
    public List<OrdrCoin> getAllOrdrCoins() {
        log.debug("REST request to get all OrdrCoins");
        return ordrCoinService.findAll();
        }

    /**
     * GET  /ordr-coins/:id : get the "id" ordrCoin.
     *
     * @param id the id of the ordrCoin to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ordrCoin, or with status 404 (Not Found)
     */
    @GetMapping("/ordr-coins/{id}")
    @Timed
    public ResponseEntity<OrdrCoin> getOrdrCoin(@PathVariable Long id) {
        log.debug("REST request to get OrdrCoin : {}", id);
        OrdrCoin ordrCoin = ordrCoinService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ordrCoin));
    }

    /**
     * DELETE  /ordr-coins/:id : delete the "id" ordrCoin.
     *
     * @param id the id of the ordrCoin to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ordr-coins/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrdrCoin(@PathVariable Long id) {
        log.debug("REST request to delete OrdrCoin : {}", id);
        ordrCoinService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
