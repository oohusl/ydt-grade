package com.ydt.repository;

import com.ydt.domain.ImageDepot;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ImageDepot entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImageDepotRepository extends JpaRepository<ImageDepot, Long> {

}
