package com.ydt.repository;

import com.ydt.domain.UserAddress;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the UserAddress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {

    @Query("select user_address from UserAddress user_address where user_address.user.login = ?#{principal.username}")
    List<UserAddress> findByUserIsCurrentUser();

}
