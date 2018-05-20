package com.ydt.repository;

import com.ydt.domain.Ordr;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Ordr entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrdrRepository extends JpaRepository<Ordr, Long> {

    @Query("select ordr from Ordr ordr where ordr.user.login = ?#{principal.username}")
    List<Ordr> findByUserIsCurrentUser();

}
