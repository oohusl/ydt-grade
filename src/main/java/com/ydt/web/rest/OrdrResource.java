package com.ydt.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ydt.domain.Ordr;
import com.ydt.service.OrdrService;
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
 * REST controller for managing Ordr.
 */
@RestController
@RequestMapping("/api")
public class OrdrResource {

    private final Logger log = LoggerFactory.getLogger(OrdrResource.class);

    private static final String ENTITY_NAME = "ordr";

    private final OrdrService ordrService;

    public OrdrResource(OrdrService ordrService) {
        this.ordrService = ordrService;
    }

    /**
     * POST  /ordrs : Create a new ordr.
     *
     * @param ordr the ordr to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ordr, or with status 400 (Bad Request) if the ordr has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ordrs")
    @Timed
    public ResponseEntity<Ordr> createOrdr(@RequestBody Ordr ordr) throws URISyntaxException {
        log.debug("REST request to save Ordr : {}", ordr);
        if (ordr.getId() != null) {
            throw new BadRequestAlertException("A new ordr cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ordr result = ordrService.save(ordr);
        return ResponseEntity.created(new URI("/api/ordrs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ordrs : Updates an existing ordr.
     *
     * @param ordr the ordr to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ordr,
     * or with status 400 (Bad Request) if the ordr is not valid,
     * or with status 500 (Internal Server Error) if the ordr couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ordrs")
    @Timed
    public ResponseEntity<Ordr> updateOrdr(@RequestBody Ordr ordr) throws URISyntaxException {
        log.debug("REST request to update Ordr : {}", ordr);
        if (ordr.getId() == null) {
            return createOrdr(ordr);
        }
        Ordr result = ordrService.save(ordr);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ordr.getId().toString()))
            .body(result);
    }

    @PutMapping("/ordrs-state")
    @Timed
    public ResponseEntity<Ordr> updateOrdrState(@RequestBody Ordr ordr) throws URISyntaxException {
        log.debug("REST request to update Ordr state: {}", ordr);

        Ordr result = ordrService.findOne(ordr.getId());
        if(result != null){
            result.setState(ordr.getState());
            ordrService.save(result);
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ordr.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ordrs : get all the ordrs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ordrs in body
     */
    @GetMapping("/ordrs")
    @Timed
    public ResponseEntity<List<Ordr>> getAllOrdrs(Pageable pageable) {
        log.debug("REST request to get a page of Ordrs");
        Page<Ordr> page = ordrService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ordrs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ordrs/:id : get the "id" ordr.
     *
     * @param id the id of the ordr to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ordr, or with status 404 (Not Found)
     */
    @GetMapping("/ordrs/{id}")
    @Timed
    public ResponseEntity<Ordr> getOrdr(@PathVariable Long id) {
        log.debug("REST request to get Ordr : {}", id);
        Ordr ordr = ordrService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ordr));
    }

    /**
     * DELETE  /ordrs/:id : delete the "id" ordr.
     *
     * @param id the id of the ordr to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ordrs/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrdr(@PathVariable Long id) {
        log.debug("REST request to delete Ordr : {}", id);
        ordrService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
