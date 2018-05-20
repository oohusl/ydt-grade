package com.ydt.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ydt.domain.ImageDepot;
import com.ydt.service.ImageDepotService;
import com.ydt.web.rest.errors.BadRequestAlertException;
import com.ydt.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ImageDepot.
 */
@RestController
@RequestMapping("/api")
public class ImageDepotResource {

    private final Logger log = LoggerFactory.getLogger(ImageDepotResource.class);

    private static final String ENTITY_NAME = "imageDepot";

    private final ImageDepotService imageDepotService;

    public ImageDepotResource(ImageDepotService imageDepotService) {
        this.imageDepotService = imageDepotService;
    }

    /**
     * POST  /image-depots : Create a new imageDepot.
     *
     * @param imageDepot the imageDepot to create
     * @return the ResponseEntity with status 201 (Created) and with body the new imageDepot, or with status 400 (Bad Request) if the imageDepot has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/image-depots")
    @Timed
    public ResponseEntity<ImageDepot> createImageDepot(@RequestBody ImageDepot imageDepot) throws URISyntaxException {
        log.debug("REST request to save ImageDepot : {}", imageDepot);
        if (imageDepot.getId() != null) {
            throw new BadRequestAlertException("A new imageDepot cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ImageDepot result = imageDepotService.save(imageDepot);
        return ResponseEntity.created(new URI("/api/image-depots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /image-depots : Updates an existing imageDepot.
     *
     * @param imageDepot the imageDepot to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated imageDepot,
     * or with status 400 (Bad Request) if the imageDepot is not valid,
     * or with status 500 (Internal Server Error) if the imageDepot couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/image-depots")
    @Timed
    public ResponseEntity<ImageDepot> updateImageDepot(@RequestBody ImageDepot imageDepot) throws URISyntaxException {
        log.debug("REST request to update ImageDepot : {}", imageDepot);
        if (imageDepot.getId() == null) {
            return createImageDepot(imageDepot);
        }
        ImageDepot result = imageDepotService.save(imageDepot);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, imageDepot.getId().toString()))
            .body(result);
    }

    /**
     * GET  /image-depots : get all the imageDepots.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of imageDepots in body
     */
    @GetMapping("/image-depots")
    @Timed
    public List<ImageDepot> getAllImageDepots() {
        log.debug("REST request to get all ImageDepots");
        return imageDepotService.findAll();
        }

    /**
     * GET  /image-depots/:id : get the "id" imageDepot.
     *
     * @param id the id of the imageDepot to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the imageDepot, or with status 404 (Not Found)
     */
    @GetMapping("/image-depots/{id}")
    @Timed
    public ResponseEntity<ImageDepot> getImageDepot(@PathVariable Long id) {
        log.debug("REST request to get ImageDepot : {}", id);
        ImageDepot imageDepot = imageDepotService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(imageDepot));
    }

    /**
     * DELETE  /image-depots/:id : delete the "id" imageDepot.
     *
     * @param id the id of the imageDepot to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/image-depots/{id}")
    @Timed
    public ResponseEntity<Void> deleteImageDepot(@PathVariable Long id) {
        log.debug("REST request to delete ImageDepot : {}", id);
        imageDepotService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
