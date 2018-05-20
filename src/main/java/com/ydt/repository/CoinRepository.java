package com.ydt.repository;

import com.ydt.domain.Coin;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data JPA repository for the Coin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CoinRepository extends JpaRepository<Coin, Long> {
    Optional<Coin> findOneByCertNo(String certNo);

    List<Coin> findByOrdrId(Long ordrId);
}
