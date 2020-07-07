package ua.com.paradine.web.rest;

import ua.com.paradine.service.IntendedVisitService;
import ua.com.paradine.web.rest.errors.BadRequestAlertException;
import ua.com.paradine.service.dto.IntendedVisitDTO;
import ua.com.paradine.service.dto.IntendedVisitCriteria;
import ua.com.paradine.service.IntendedVisitQueryService;

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
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link ua.com.paradine.domain.IntendedVisit}.
 */
@RestController
@RequestMapping("/api")
public class IntendedVisitResource {

    private final Logger log = LoggerFactory.getLogger(IntendedVisitResource.class);

    private static final String ENTITY_NAME = "intendedVisit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IntendedVisitService intendedVisitService;

    private final IntendedVisitQueryService intendedVisitQueryService;

    public IntendedVisitResource(IntendedVisitService intendedVisitService, IntendedVisitQueryService intendedVisitQueryService) {
        this.intendedVisitService = intendedVisitService;
        this.intendedVisitQueryService = intendedVisitQueryService;
    }

    /**
     * {@code POST  /intended-visits} : Create a new intendedVisit.
     *
     * @param intendedVisitDTO the intendedVisitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new intendedVisitDTO, or with status {@code 400 (Bad Request)} if the intendedVisit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/intended-visits")
    public ResponseEntity<IntendedVisitDTO> createIntendedVisit(@Valid @RequestBody IntendedVisitDTO intendedVisitDTO) throws URISyntaxException {
        log.debug("REST request to save IntendedVisit : {}", intendedVisitDTO);
        if (intendedVisitDTO.getId() != null) {
            throw new BadRequestAlertException("A new intendedVisit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IntendedVisitDTO result = intendedVisitService.save(intendedVisitDTO);
        return ResponseEntity.created(new URI("/api/intended-visits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /intended-visits} : Updates an existing intendedVisit.
     *
     * @param intendedVisitDTO the intendedVisitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated intendedVisitDTO,
     * or with status {@code 400 (Bad Request)} if the intendedVisitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the intendedVisitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/intended-visits")
    public ResponseEntity<IntendedVisitDTO> updateIntendedVisit(@Valid @RequestBody IntendedVisitDTO intendedVisitDTO) throws URISyntaxException {
        log.debug("REST request to update IntendedVisit : {}", intendedVisitDTO);
        if (intendedVisitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IntendedVisitDTO result = intendedVisitService.save(intendedVisitDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, intendedVisitDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /intended-visits} : get all the intendedVisits.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of intendedVisits in body.
     */
    @GetMapping("/intended-visits")
    public ResponseEntity<List<IntendedVisitDTO>> getAllIntendedVisits(IntendedVisitCriteria criteria, Pageable pageable) {
        log.debug("REST request to get IntendedVisits by criteria: {}", criteria);
        Page<IntendedVisitDTO> page = intendedVisitQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /intended-visits/count} : count all the intendedVisits.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/intended-visits/count")
    public ResponseEntity<Long> countIntendedVisits(IntendedVisitCriteria criteria) {
        log.debug("REST request to count IntendedVisits by criteria: {}", criteria);
        return ResponseEntity.ok().body(intendedVisitQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /intended-visits/:id} : get the "id" intendedVisit.
     *
     * @param id the id of the intendedVisitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the intendedVisitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/intended-visits/{id}")
    public ResponseEntity<IntendedVisitDTO> getIntendedVisit(@PathVariable Long id) {
        log.debug("REST request to get IntendedVisit : {}", id);
        Optional<IntendedVisitDTO> intendedVisitDTO = intendedVisitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(intendedVisitDTO);
    }

    /**
     * {@code DELETE  /intended-visits/:id} : delete the "id" intendedVisit.
     *
     * @param id the id of the intendedVisitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/intended-visits/{id}")
    public ResponseEntity<Void> deleteIntendedVisit(@PathVariable Long id) {
        log.debug("REST request to delete IntendedVisit : {}", id);
        intendedVisitService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/intended-visits?query=:query} : search for the intendedVisit corresponding
     * to the query.
     *
     * @param query the query of the intendedVisit search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/intended-visits")
    public ResponseEntity<List<IntendedVisitDTO>> searchIntendedVisits(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of IntendedVisits for query {}", query);
        Page<IntendedVisitDTO> page = intendedVisitService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
