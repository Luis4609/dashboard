package com.dashboard.app.web.rest;

import com.dashboard.app.repository.DailyCallsRepository;
import com.dashboard.app.service.DailyCallsQueryService;
import com.dashboard.app.service.DailyCallsService;
import com.dashboard.app.service.criteria.DailyCallsCriteria;
import com.dashboard.app.service.dto.DailyCallsDTO;
import com.dashboard.app.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.dashboard.app.domain.DailyCalls}.
 */
@RestController
@RequestMapping("/api")
public class DailyCallsResource {

    private final Logger log = LoggerFactory.getLogger(DailyCallsResource.class);

    private static final String ENTITY_NAME = "dailyCalls";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DailyCallsService dailyCallsService;

    private final DailyCallsRepository dailyCallsRepository;

    private final DailyCallsQueryService dailyCallsQueryService;

    public DailyCallsResource(
        DailyCallsService dailyCallsService,
        DailyCallsRepository dailyCallsRepository,
        DailyCallsQueryService dailyCallsQueryService
    ) {
        this.dailyCallsService = dailyCallsService;
        this.dailyCallsRepository = dailyCallsRepository;
        this.dailyCallsQueryService = dailyCallsQueryService;
    }

    /**
     * {@code POST  /daily-calls} : Create a new dailyCalls.
     *
     * @param dailyCallsDTO the dailyCallsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dailyCallsDTO, or with status {@code 400 (Bad Request)} if the dailyCalls has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/daily-calls")
    public ResponseEntity<DailyCallsDTO> createDailyCalls(@Valid @RequestBody DailyCallsDTO dailyCallsDTO) throws URISyntaxException {
        log.debug("REST request to save DailyCalls : {}", dailyCallsDTO);
        if (dailyCallsDTO.getId() != null) {
            throw new BadRequestAlertException("A new dailyCalls cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DailyCallsDTO result = dailyCallsService.save(dailyCallsDTO);
        return ResponseEntity
            .created(new URI("/api/daily-calls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /daily-calls/:id} : Updates an existing dailyCalls.
     *
     * @param id the id of the dailyCallsDTO to save.
     * @param dailyCallsDTO the dailyCallsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dailyCallsDTO,
     * or with status {@code 400 (Bad Request)} if the dailyCallsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dailyCallsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/daily-calls/{id}")
    public ResponseEntity<DailyCallsDTO> updateDailyCalls(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DailyCallsDTO dailyCallsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DailyCalls : {}, {}", id, dailyCallsDTO);
        if (dailyCallsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dailyCallsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dailyCallsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DailyCallsDTO result = dailyCallsService.update(dailyCallsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dailyCallsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /daily-calls/:id} : Partial updates given fields of an existing dailyCalls, field will ignore if it is null
     *
     * @param id the id of the dailyCallsDTO to save.
     * @param dailyCallsDTO the dailyCallsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dailyCallsDTO,
     * or with status {@code 400 (Bad Request)} if the dailyCallsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dailyCallsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dailyCallsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/daily-calls/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DailyCallsDTO> partialUpdateDailyCalls(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DailyCallsDTO dailyCallsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DailyCalls partially : {}, {}", id, dailyCallsDTO);
        if (dailyCallsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dailyCallsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dailyCallsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DailyCallsDTO> result = dailyCallsService.partialUpdate(dailyCallsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dailyCallsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /daily-calls} : get all the dailyCalls.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dailyCalls in body.
     */
    @GetMapping("/daily-calls")
    public ResponseEntity<List<DailyCallsDTO>> getAllDailyCalls(
        DailyCallsCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get DailyCalls by criteria: {}", criteria);

        Page<DailyCallsDTO> page = dailyCallsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /daily-calls/count} : count all the dailyCalls.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/daily-calls/count")
    public ResponseEntity<Long> countDailyCalls(DailyCallsCriteria criteria) {
        log.debug("REST request to count DailyCalls by criteria: {}", criteria);
        return ResponseEntity.ok().body(dailyCallsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /daily-calls/:id} : get the "id" dailyCalls.
     *
     * @param id the id of the dailyCallsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dailyCallsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/daily-calls/{id}")
    public ResponseEntity<DailyCallsDTO> getDailyCalls(@PathVariable Long id) {
        log.debug("REST request to get DailyCalls : {}", id);
        Optional<DailyCallsDTO> dailyCallsDTO = dailyCallsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dailyCallsDTO);
    }

    /**
     * {@code DELETE  /daily-calls/:id} : delete the "id" dailyCalls.
     *
     * @param id the id of the dailyCallsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/daily-calls/{id}")
    public ResponseEntity<Void> deleteDailyCalls(@PathVariable Long id) {
        log.debug("REST request to delete DailyCalls : {}", id);
        dailyCallsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @PostMapping("/daily-calls/upload")
    public ResponseEntity<String> uploadExcelFile(@RequestParam MultipartFile file) {
        try {
            dailyCallsService.updateDataFromFile(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, file.getName()))
            .build();
    }

    @GetMapping("/monthly-calls")
    public ResponseEntity<List<DailyCallsDTO>> generateMonthlyCallsReport() {
        return ResponseEntity.ok().body(dailyCallsService.getMonthlyCalls());
    }
}
