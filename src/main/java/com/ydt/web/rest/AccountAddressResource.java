package com.ydt.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ydt.domain.User;
import com.ydt.domain.UserAddress;
import com.ydt.repository.UserAddressRepository;
import com.ydt.repository.UserRepository;
import com.ydt.security.SecurityUtils;
import com.ydt.service.UserAddressService;
import com.ydt.service.UserService;
import com.ydt.web.rest.errors.BadRequestAlertException;
import com.ydt.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserAddress.
 */
@RestController
@RequestMapping("/api/account")
public class AccountAddressResource {

    private final Logger log = LoggerFactory.getLogger(UserAddressResource.class);

    private static final String ENTITY_NAME = "userAddress";

    private final UserAddressService userAddressService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAddressRepository userAddressRepository;

    public AccountAddressResource(UserAddressService userAddressService) {
        this.userAddressService = userAddressService;
    }

    /**
     * POST  /user-addresses : Create a new userAddress.
     *
     * @param userAddress the userAddress to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userAddress, or with status 400 (Bad Request) if the userAddress has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-addresses")
    @Timed
    public ResponseEntity<UserAddress> createUserAddress(@RequestBody UserAddress userAddress) throws URISyntaxException {
        log.debug("REST request to save UserAddress : {}", userAddress);
        if (userAddress.getId() != null) {
            throw new BadRequestAlertException("A new userAddress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        User user = userRepository.getCurrentUser();
        userAddress.setUser(user);
        UserAddress result = userAddressService.save(userAddress);
        return ResponseEntity.created(new URI("/api/user-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-addresses : Updates an existing userAddress.
     *
     * @param userAddress the userAddress to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userAddress,
     * or with status 400 (Bad Request) if the userAddress is not valid,
     * or with status 500 (Internal Server Error) if the userAddress couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-addresses")
    @Timed
    public ResponseEntity<UserAddress> updateUserAddress(@RequestBody UserAddress userAddress) throws URISyntaxException {
        log.debug("REST request to update UserAddress : {}", userAddress);
        if (userAddress.getId() == null) {
            return createUserAddress(userAddress);
        }
        UserAddress ua = userAddressService.findOne(userAddress.getId());
        if(!ua.getUser().getLogin().equals(SecurityUtils.getCurrentUserLogin())){
            throw new BadRequestAlertException("", "", "");
        }

        ua.setReceiver(userAddress.getReceiver());
        ua.setReceiverAddr(userAddress.getReceiverAddr());
        ua.setReceiverCity(userAddress.getReceiverCity());
        ua.setReceiverTel(userAddress.getReceiverTel());

        UserAddress result = userAddressService.save(ua);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userAddress.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-addresses : get all the userAddresses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userAddresses in body
     */
    @GetMapping("/user-addresses")
    @Timed
    public List<UserAddress> getAllUserAddresses() {
        log.debug("REST request to get all UserAddresses");
        return userAddressRepository.findByUserIsCurrentUser();
    }


    /**
     * DELETE  /user-addresses/:id : delete the "id" userAddress.
     *
     * @param id the id of the userAddress to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-addresses/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserAddress(@PathVariable Long id) {
        log.debug("REST request to delete UserAddress : {}", id);
        UserAddress userAddress = userAddressRepository.findOne(id);
        if(userAddress != null && userAddress.getUser().getLogin().equals(SecurityUtils.getCurrentUserLogin())){
            userAddressRepository.delete(userAddress);
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

