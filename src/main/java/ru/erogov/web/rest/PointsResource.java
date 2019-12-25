package ru.erogov.web.rest;

import ru.erogov.domain.Points;
import ru.erogov.repository.PointsRepository;
import ru.erogov.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link ru.erogov.domain.Points}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PointsResource {

    private final Logger log = LoggerFactory.getLogger(PointsResource.class);

    private static final String ENTITY_NAME = "points";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PointsRepository pointsRepository;

    public PointsResource(PointsRepository pointsRepository) {
        this.pointsRepository = pointsRepository;
    }

    /**
     * {@code POST  /points} : Create a new points.
     *
     * @param points the points to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new points, or with status {@code 400 (Bad Request)} if the points has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/points")
    public ResponseEntity<Points> createPoints(@RequestBody Points points) throws URISyntaxException {
        log.debug("REST request to save Points : {}", points);
        if (points.getId() != null) {
            throw new BadRequestAlertException("A new points cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Points result = pointsRepository.save(points);
        return ResponseEntity.created(new URI("/api/points/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /points} : Updates an existing points.
     *
     * @param points the points to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated points,
     * or with status {@code 400 (Bad Request)} if the points is not valid,
     * or with status {@code 500 (Internal Server Error)} if the points couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/points")
    public ResponseEntity<Points> updatePoints(@RequestBody Points points) throws URISyntaxException {
        log.debug("REST request to update Points : {}", points);
        if (points.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Points result = pointsRepository.save(points);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, points.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /points} : get all the points.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of points in body.
     */
    @GetMapping("/points")
    public ResponseEntity<List<Points>> getAllPoints(Pageable pageable) {
        log.debug("REST request to get a page of Points");
        Page<Points> page = pointsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /points/:id} : get the "id" points.
     *
     * @param id the id of the points to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the points, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/points/{id}")
    public ResponseEntity<Points> getPoints(@PathVariable Long id) {
        log.debug("REST request to get Points : {}", id);
        Optional<Points> points = pointsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(points);
    }

    /**
     * {@code DELETE  /points/:id} : delete the "id" points.
     *
     * @param id the id of the points to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/points/{id}")
    public ResponseEntity<Void> deletePoints(@PathVariable Long id) {
        log.debug("REST request to delete Points : {}", id);
        pointsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
