package com.ydt.service;

import com.ydt.domain.FeeRule;
import com.ydt.repository.FeeRuleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing FeeRule.
 */
@Service
@Transactional
public class FeeRuleService {

    private final Logger log = LoggerFactory.getLogger(FeeRuleService.class);

    private final FeeRuleRepository feeRuleRepository;

    public FeeRuleService(FeeRuleRepository feeRuleRepository) {
        this.feeRuleRepository = feeRuleRepository;
    }

    /**
     * Save a feeRule.
     *
     * @param feeRule the entity to save
     * @return the persisted entity
     */
    public FeeRule save(FeeRule feeRule) {
        log.debug("Request to save FeeRule : {}", feeRule);
        return feeRuleRepository.save(feeRule);
    }

    /**
     * Get all the feeRules.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<FeeRule> findAll() {
        log.debug("Request to get all FeeRules");
        return feeRuleRepository.findAll();
    }

    /**
     * Get one feeRule by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public FeeRule findOne(Long id) {
        log.debug("Request to get FeeRule : {}", id);
        return feeRuleRepository.findOne(id);
    }

    /**
     * Delete the feeRule by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete FeeRule : {}", id);
        feeRuleRepository.delete(id);
    }
}
