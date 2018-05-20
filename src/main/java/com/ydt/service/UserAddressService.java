package com.ydt.service;

import com.ydt.domain.UserAddress;
import com.ydt.repository.UserAddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing UserAddress.
 */
@Service
@Transactional
public class UserAddressService {

    private final Logger log = LoggerFactory.getLogger(UserAddressService.class);

    private final UserAddressRepository userAddressRepository;

    public UserAddressService(UserAddressRepository userAddressRepository) {
        this.userAddressRepository = userAddressRepository;
    }

    /**
     * Save a userAddress.
     *
     * @param userAddress the entity to save
     * @return the persisted entity
     */
    public UserAddress save(UserAddress userAddress) {
        log.debug("Request to save UserAddress : {}", userAddress);
        return userAddressRepository.save(userAddress);
    }

    /**
     * Get all the userAddresses.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<UserAddress> findAll() {
        log.debug("Request to get all UserAddresses");
        return userAddressRepository.findAll();
    }

    /**
     * Get one userAddress by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public UserAddress findOne(Long id) {
        log.debug("Request to get UserAddress : {}", id);
        return userAddressRepository.findOne(id);
    }

    /**
     * Delete the userAddress by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserAddress : {}", id);
        userAddressRepository.delete(id);
    }
}
