package ua.com.paradine.web.rest;

import ua.com.paradine.service.WorkingHoursService;
import ua.com.paradine.web.rest.errors.BadRequestAlertException;
import ua.com.paradine.service.dto.WorkingHoursDTO;

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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link ua.com.paradine.domain.WorkingHours}.
 */
@RestController
@RequestMapping("/api")
public class WorkingHoursResource {

    private final Logger log = LoggerFactory.getLogger(WorkingHoursResource.class);

    private static final String ENTITY_NAME = "workingHours";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkingHoursService workingHoursService;

    public WorkingHoursResource(WorkingHoursService workingHoursService) {
        this.workingHoursService = workingHoursService;
    }

    /**
     * {@code POST  /working-hours} : Create a new workingHours.
     *
     * @param workingHoursDTO the workingHoursDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workingHoursDTO, or with status {@code 400 (Bad Request)} if the workingHours has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/working-hours")
    public ResponseEntity<WorkingHoursDTO> createWorkingHours(@Valid @RequestBody WorkingHoursDTO workingHoursDTO) throws URISyntaxException {
        log.debug("REST request to save WorkingHours : {}", workingHoursDTO);
        if (workingHoursDTO.getId() != null) {
            throw new BadRequestAlertException("A new workingHours cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkingHoursDTO result = workingHoursService.save(workingHoursDTO);
        return ResponseEntity.created(new URI("/api/working-hours/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /working-hours} : Updates an existing workingHours.
     *
     * @param workingHoursDTO the workingHoursDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workingHoursDTO,
     * or with status {@code 400 (Bad Request)} if the workingHoursDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workingHoursDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/working-hours")
    public ResponseEntity<WorkingHoursDTO> updateWorkingHours(@Valid @RequestBody WorkingHoursDTO workingHoursDTO) throws URISyntaxException {
        log.debug("REST request to update WorkingHours : {}", workingHoursDTO);
        if (workingHoursDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WorkingHoursDTO result = workingHoursService.save(workingHoursDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workingHoursDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /working-hours} : get all the workingHours.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workingHours in body.
     */
    @GetMapping("/working-hours")
    public ResponseEntity<List<WorkingHoursDTO>> getAllWorkingHours(Pageable pageable) {
        log.debug("REST request to get a page of WorkingHours");
        Page<WorkingHoursDTO> page = workingHoursService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /working-hours/:id} : get the "id" workingHours.
     *
     * @param id the id of the workingHoursDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workingHoursDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/working-hours/{id}")
    public ResponseEntity<WorkingHoursDTO> getWorkingHours(@PathVariable Long id) {
        log.debug("REST request to get WorkingHours : {}", id);
        Optional<WorkingHoursDTO> workingHoursDTO = workingHoursService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workingHoursDTO);
    }

    /**
     * {@code DELETE  /working-hours/:id} : delete the "id" workingHours.
     *
     * @param id the id of the workingHoursDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/working-hours/{id}")
    public ResponseEntity<Void> deleteWorkingHours(@PathVariable Long id) {
        log.debug("REST request to delete WorkingHours : {}", id);
        workingHoursService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/working-hours?query=:query} : search for the workingHours corresponding
     * to the query.
     *
     * @param query the query of the workingHours search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/working-hours")
    public ResponseEntity<List<WorkingHoursDTO>> searchWorkingHours(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of WorkingHours for query {}", query);
        Page<WorkingHoursDTO> page = workingHoursService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
