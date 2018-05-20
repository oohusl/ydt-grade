package com.ydt.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ydt.domain.Coin;
import com.ydt.domain.Ordr;
import com.ydt.domain.OrdrCoin;
import com.ydt.domain.enumeration.OrderState;
import com.ydt.repository.CoinRepository;
import com.ydt.repository.OrdrCoinRepository;
import com.ydt.repository.OrdrRepository;
import com.ydt.repository.UserRepository;
import com.ydt.security.SecurityUtils;
import com.ydt.service.OrdrService;
import com.ydt.service.UserService;
import com.ydt.service.dto.OrdrCoinDTO;
import com.ydt.service.dto.OrdrDTO;
import com.ydt.service.mapper.CoinMapper;
import com.ydt.service.mapper.OrdrMapper;
import com.ydt.web.rest.errors.BadRequestAlertException;
import com.ydt.web.rest.util.HeaderUtil;
import com.ydt.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Ordr.
 */
@RestController
@RequestMapping("/api/account")
public class AccountOrdrResource {

    private final Logger log = LoggerFactory.getLogger(AccountOrdrResource.class);

    private static final String ENTITY_NAME = "ordr";

    private final OrdrService ordrService;

    @Autowired
    private OrdrRepository ordrRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrdrCoinRepository ordrCoinRepository;

    @Autowired
    private CoinRepository coinRepository;

    public AccountOrdrResource(OrdrService ordrService) {
        this.ordrService = ordrService;
    }

    @PostMapping("/ordrs")
    @Timed
    @ResponseStatus(HttpStatus.OK)
    public void createOrdr(@RequestBody OrdrDTO ordrDTO) throws URISyntaxException {
        log.debug("REST request to save Ordr with coins: {}", ordrDTO);
        Integer totalQuantity = 0;
        BigDecimal totalHandingFee = new BigDecimal(0);
        for(OrdrCoinDTO ordrCoinDTO : ordrDTO.getCoins()){
            totalQuantity = totalQuantity + ordrCoinDTO.getQuantity();
            totalHandingFee = totalHandingFee.add(ordrCoinDTO.getDeclaredValue());
        }
        Ordr ordr = OrdrMapper.INSTANCE.toEntity(ordrDTO);
        ordr.setTotalQuantity(totalQuantity);
        ordr.setUser(userRepository.getCurrentUser());
        ordr.setState(OrderState.NEW);
        ordr.setHandingFee(totalHandingFee);
        ordr.setCreateDate(Instant.now());
        Ordr result = ordrService.save(ordr);

        for(OrdrCoinDTO ordrCoinDTO : ordrDTO.getCoins()){
            OrdrCoin ordrCoin = CoinMapper.INSTANCE.toEntity(ordrCoinDTO);
            ordrCoin.setOrdr(result);
            ordrCoinRepository.save(ordrCoin);

            Coin coin = CoinMapper.INSTANCE.toCoin(ordrCoinDTO);
            coin.setOrdr(result);
            for(int i = 0; i < ordrCoinDTO.getQuantity(); ++i){
                this.coinRepository.save(coin);
            }
        }
    }


    /**
     * GET  /ordrs : get all the ordrs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ordrs in body
     */
    @GetMapping("/ordrs")
    @Timed
    public List<Ordr> getAllOrdrs() {
        log.debug("REST request to get a page of Ordrs");
        List<Ordr> page = ordrRepository.findByUserIsCurrentUser();
        return page;
    }

    /**
     * GET  /ordrs/:id : get the "id" ordr.
     *
     * @param id the id of the ordr to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ordr, or with status 404 (Not Found)
     */
    @GetMapping("/ordrs/{id}")
    @Timed
    public ResponseEntity<Ordr> getOrdr(@PathVariable Long id) {
        log.debug("REST request to get Ordr : {}", id);
        Ordr ordr = ordrService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ordr));
    }

    /**
     * DELETE  /ordrs/:id : delete the "id" ordr.
     *
     * @param id the id of the ordr to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ordrs/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrdr(@PathVariable Long id) {
        log.debug("REST request to delete Ordr : {}", id);
        Ordr ordr = ordrService.findOne(id);
        if(ordr.getUser().getLogin().equals(SecurityUtils.getCurrentUserLogin())){
            ordrService.delete(id);
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /coins/:id : get the "id" coin.
     *
     * @param ordrId the id of the coin to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the coin, or with status 404 (Not Found)
     */
    @GetMapping("/coins/{ordrId}/coins")
    @Timed
    public List<Coin> getCoin(@PathVariable Long ordrId) {
        log.debug("REST request to get Coin : {}", ordrId);
        List<Coin> coins = coinRepository.findByOrdrId(ordrId);
        return coins;
    }
}
