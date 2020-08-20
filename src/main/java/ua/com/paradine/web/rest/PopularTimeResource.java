package ua.com.paradine.web.rest;

import ua.com.paradine.service.PopularTimeService;
import ua.com.paradine.web.rest.errors.BadRequestAlertException;
import ua.com.paradine.service.dto.PopularTimeDTO;
import ua.com.paradine.service.dto.PopularTimeCriteria;
import ua.com.paradine.service.PopularTimeQueryService;

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
 * REST controller for managing {@link ua.com.paradine.domain.PopularTime}.
 */
@RestController
@RequestMapping("/api")
public class PopularTimeResource {

    private final Logger log = LoggerFactory.getLogger(PopularTimeResource.class);

    private static final String ENTITY_NAME = "popularTime";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PopularTimeService popularTimeService;

    private final PopularTimeQueryService popularTimeQueryService;

    public PopularTimeResource(PopularTimeService popularTimeService, PopularTimeQueryService popularTimeQueryService) {
        this.popularTimeService = popularTimeService;
        this.popularTimeQueryService = popularTimeQueryService;
    }

    /**
     * {@code POST  /popular-times} : Create a new popularTime.
     *
     * @param popularTimeDTO the popularTimeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new popularTimeDTO, or with status {@code 400 (Bad Request)} if the popularTime has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/popular-times")
    public ResponseEntity<PopularTimeDTO> createPopularTime(@Valid @RequestBody PopularTimeDTO popularTimeDTO) throws URISyntaxException {
        log.debug("REST request to save PopularTime : {}", popularTimeDTO);
        if (popularTimeDTO.getId() != null) {
            throw new BadRequestAlertException("A new popularTime cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PopularTimeDTO result = popularTimeService.save(popularTimeDTO);
        return ResponseEntity.created(new URI("/api/popular-times/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /popular-times} : Updates an existing popularTime.
     *
     * @param popularTimeDTO the popularTimeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated popularTimeDTO,
     * or with status {@code 400 (Bad Request)} if the popularTimeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the popularTimeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/popular-times")
    public ResponseEntity<PopularTimeDTO> updatePopularTime(@Valid @RequestBody PopularTimeDTO popularTimeDTO) throws URISyntaxException {
        log.debug("REST request to update PopularTime : {}", popularTimeDTO);
        if (popularTimeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PopularTimeDTO result = popularTimeService.save(popularTimeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, popularTimeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /popular-times} : get all the popularTimes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of popularTimes in body.
     */
    @GetMapping("/popular-times")
    public ResponseEntity<List<PopularTimeDTO>> getAllPopularTimes(PopularTimeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PopularTimes by criteria: {}", criteria);
        Page<PopularTimeDTO> page = popularTimeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /popular-times/count} : count all the popularTimes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/popular-times/count")
    public ResponseEntity<Long> countPopularTimes(PopularTimeCriteria criteria) {
        log.debug("REST request to count PopularTimes by criteria: {}", criteria);
        return ResponseEntity.ok().body(popularTimeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /popular-times/:id} : get the "id" popularTime.
     *
     * @param id the id of the popularTimeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the popularTimeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/popular-times/{id}")
    public ResponseEntity<PopularTimeDTO> getPopularTime(@PathVariable Long id) {
        log.debug("REST request to get PopularTime : {}", id);
        Optional<PopularTimeDTO> popularTimeDTO = popularTimeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(popularTimeDTO);
    }

    /**
     * {@code DELETE  /popular-times/:id} : delete the "id" popularTime.
     *
     * @param id the id of the popularTimeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/popular-times/{id}")
    public ResponseEntity<Void> deletePopularTime(@PathVariable Long id) {
        log.debug("REST request to delete PopularTime : {}", id);
        popularTimeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/popular-times?query=:query} : search for the popularTime corresponding
     * to the query.
     *
     * @param query the query of the popularTime search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/popular-times")
    public ResponseEntity<List<PopularTimeDTO>> searchPopularTimes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PopularTimes for query {}", query);
        Page<PopularTimeDTO> page = popularTimeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
