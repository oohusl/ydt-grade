package com.ydt.repository;

import com.ydt.domain.FeeRule;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FeeRule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeeRuleRepository extends JpaRepository<FeeRule, Long> {

}
