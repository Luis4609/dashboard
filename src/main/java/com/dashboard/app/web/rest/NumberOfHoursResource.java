package com.dashboard.app.web.rest;

import com.dashboard.app.repository.NumberOfHoursRepository;
import com.dashboard.app.service.NumberOfHoursQueryService;
import com.dashboard.app.service.NumberOfHoursService;
import com.dashboard.app.service.criteria.NumberOfHoursCriteria;
import com.dashboard.app.service.dto.NumberOfHoursDTO;
import com.dashboard.app.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.dashboard.app.domain.NumberOfHours}.
 */
@RestController
@RequestMapping("/api")
public class NumberOfHoursResource {

    private final Logger log = LoggerFactory.getLogger(NumberOfHoursResource.class);

    private static final String ENTITY_NAME = "numberOfHours";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NumberOfHoursService numberOfHoursService;

    private final NumberOfHoursRepository numberOfHoursRepository;

    private final NumberOfHoursQueryService numberOfHoursQueryService;

    public NumberOfHoursResource(
        NumberOfHoursService numberOfHoursService,
        NumberOfHoursRepository numberOfHoursRepository,
        NumberOfHoursQueryService numberOfHoursQueryService
    ) {
        this.numberOfHoursService = numberOfHoursService;
        this.numberOfHoursRepository = numberOfHoursRepository;
        this.numberOfHoursQueryService = numberOfHoursQueryService;
    }

    /**
     * {@code POST  /number-of-hours} : Create a new numberOfHours.
     *
     * @param numberOfHoursDTO the numberOfHoursDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new numberOfHoursDTO, or with status {@code 400 (Bad Request)} if the numberOfHours has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/number-of-hours")
    public ResponseEntity<NumberOfHoursDTO> createNumberOfHours(@Valid @RequestBody NumberOfHoursDTO numberOfHoursDTO)
        throws URISyntaxException {
        log.debug("REST request to save NumberOfHours : {}", numberOfHoursDTO);
        if (numberOfHoursDTO.getId() != null) {
            throw new BadRequestAlertException("A new numberOfHours cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NumberOfHoursDTO result = numberOfHoursService.save(numberOfHoursDTO);
        return ResponseEntity
            .created(new URI("/api/number-of-hours/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /number-of-hours/:id} : Updates an existing numberOfHours.
     *
     * @param id the id of the numberOfHoursDTO to save.
     * @param numberOfHoursDTO the numberOfHoursDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated numberOfHoursDTO,
     * or with status {@code 400 (Bad Request)} if the numberOfHoursDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the numberOfHoursDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/number-of-hours/{id}")
    public ResponseEntity<NumberOfHoursDTO> updateNumberOfHours(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NumberOfHoursDTO numberOfHoursDTO
    ) throws URISyntaxException {
        log.debug("REST request to update NumberOfHours : {}, {}", id, numberOfHoursDTO);
        if (numberOfHoursDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, numberOfHoursDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!numberOfHoursRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NumberOfHoursDTO result = numberOfHoursService.update(numberOfHoursDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, numberOfHoursDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /number-of-hours/:id} : Partial updates given fields of an existing numberOfHours, field will ignore if it is null
     *
     * @param id the id of the numberOfHoursDTO to save.
     * @param numberOfHoursDTO the numberOfHoursDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated numberOfHoursDTO,
     * or with status {@code 400 (Bad Request)} if the numberOfHoursDTO is not valid,
     * or with status {@code 404 (Not Found)} if the numberOfHoursDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the numberOfHoursDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/number-of-hours/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NumberOfHoursDTO> partialUpdateNumberOfHours(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NumberOfHoursDTO numberOfHoursDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update NumberOfHours partially : {}, {}", id, numberOfHoursDTO);
        if (numberOfHoursDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, numberOfHoursDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!numberOfHoursRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NumberOfHoursDTO> result = numberOfHoursService.partialUpdate(numberOfHoursDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, numberOfHoursDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /number-of-hours} : get all the numberOfHours.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of numberOfHours in body.
     */
    @GetMapping("/number-of-hours")
    public ResponseEntity<List<NumberOfHoursDTO>> getAllNumberOfHours(
        NumberOfHoursCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get NumberOfHours by criteria: {}", criteria);

        Page<NumberOfHoursDTO> page = numberOfHoursQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /number-of-hours/count} : count all the numberOfHours.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/number-of-hours/count")
    public ResponseEntity<Long> countNumberOfHours(NumberOfHoursCriteria criteria) {
        log.debug("REST request to count NumberOfHours by criteria: {}", criteria);
        return ResponseEntity.ok().body(numberOfHoursQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /number-of-hours/:id} : get the "id" numberOfHours.
     *
     * @param id the id of the numberOfHoursDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the numberOfHoursDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/number-of-hours/{id}")
    public ResponseEntity<NumberOfHoursDTO> getNumberOfHours(@PathVariable Long id) {
        log.debug("REST request to get NumberOfHours : {}", id);
        Optional<NumberOfHoursDTO> numberOfHoursDTO = numberOfHoursService.findOne(id);
        return ResponseUtil.wrapOrNotFound(numberOfHoursDTO);
    }

    /**
     * {@code DELETE  /number-of-hours/:id} : delete the "id" numberOfHours.
     *
     * @param id the id of the numberOfHoursDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/number-of-hours/{id}")
    public ResponseEntity<Void> deleteNumberOfHours(@PathVariable Long id) {
        log.debug("REST request to delete NumberOfHours : {}", id);
        numberOfHoursService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
