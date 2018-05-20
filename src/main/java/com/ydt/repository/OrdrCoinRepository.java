package com.ydt.repository;

import com.ydt.domain.OrdrCoin;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the OrdrCoin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrdrCoinRepository extends JpaRepository<OrdrCoin, Long> {

}
