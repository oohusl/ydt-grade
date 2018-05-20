package com.ydt.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ydt.domain.FeeRule;
import com.ydt.service.FeeRuleService;
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
 * REST controller for managing FeeRule.
 */
@RestController
@RequestMapping("/api")
public class FeeRuleResource {

    private final Logger log = LoggerFactory.getLogger(FeeRuleResource.class);

    private static final String ENTITY_NAME = "feeRule";

    private final FeeRuleService feeRuleService;

    public FeeRuleResource(FeeRuleService feeRuleService) {
        this.feeRuleService = feeRuleService;
    }

    /**
     * POST  /fee-rules : Create a new feeRule.
     *
     * @param feeRule the feeRule to create
     * @return the ResponseEntity with status 201 (Created) and with body the new feeRule, or with status 400 (Bad Request) if the feeRule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fee-rules")
    @Timed
    public ResponseEntity<FeeRule> createFeeRule(@RequestBody FeeRule feeRule) throws URISyntaxException {
        log.debug("REST request to save FeeRule : {}", feeRule);
        if (feeRule.getId() != null) {
            throw new BadRequestAlertException("A new feeRule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FeeRule result = feeRuleService.save(feeRule);
        return ResponseEntity.created(new URI("/api/fee-rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fee-rules : Updates an existing feeRule.
     *
     * @param feeRule the feeRule to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated feeRule,
     * or with status 400 (Bad Request) if the feeRule is not valid,
     * or with status 500 (Internal Server Error) if the feeRule couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fee-rules")
    @Timed
    public ResponseEntity<FeeRule> updateFeeRule(@RequestBody FeeRule feeRule) throws URISyntaxException {
        log.debug("REST request to update FeeRule : {}", feeRule);
        if (feeRule.getId() == null) {
            return createFeeRule(feeRule);
        }
        FeeRule result = feeRuleService.save(feeRule);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, feeRule.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fee-rules : get all the feeRules.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of feeRules in body
     */
    @GetMapping("/fee-rules")
    @Timed
    public List<FeeRule> getAllFeeRules() {
        log.debug("REST request to get all FeeRules");
        return feeRuleService.findAll();
        }

    /**
     * GET  /fee-rules/:id : get the "id" feeRule.
     *
     * @param id the id of the feeRule to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the feeRule, or with status 404 (Not Found)
     */
    @GetMapping("/fee-rules/{id}")
    @Timed
    public ResponseEntity<FeeRule> getFeeRule(@PathVariable Long id) {
        log.debug("REST request to get FeeRule : {}", id);
        FeeRule feeRule = feeRuleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(feeRule));
    }

    /**
     * DELETE  /fee-rules/:id : delete the "id" feeRule.
     *
     * @param id the id of the feeRule to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fee-rules/{id}")
    @Timed
    public ResponseEntity<Void> deleteFeeRule(@PathVariable Long id) {
        log.debug("REST request to delete FeeRule : {}", id);
        feeRuleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
