package com.dashboard.app.service;

import com.dashboard.app.domain.*; // for static metamodels
import com.dashboard.app.domain.NumberOfHours;
import com.dashboard.app.repository.NumberOfHoursRepository;
import com.dashboard.app.service.criteria.NumberOfHoursCriteria;
import com.dashboard.app.service.dto.NumberOfHoursDTO;
import com.dashboard.app.service.mapper.NumberOfHoursMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link NumberOfHours} entities in the database.
 * The main input is a {@link NumberOfHoursCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NumberOfHoursDTO} or a {@link Page} of {@link NumberOfHoursDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NumberOfHoursQueryService extends QueryService<NumberOfHours> {

    private final Logger log = LoggerFactory.getLogger(NumberOfHoursQueryService.class);

    private final NumberOfHoursRepository numberOfHoursRepository;

    private final NumberOfHoursMapper numberOfHoursMapper;

    public NumberOfHoursQueryService(NumberOfHoursRepository numberOfHoursRepository, NumberOfHoursMapper numberOfHoursMapper) {
        this.numberOfHoursRepository = numberOfHoursRepository;
        this.numberOfHoursMapper = numberOfHoursMapper;
    }

    /**
     * Return a {@link List} of {@link NumberOfHoursDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NumberOfHoursDTO> findByCriteria(NumberOfHoursCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NumberOfHours> specification = createSpecification(criteria);
        return numberOfHoursMapper.toDto(numberOfHoursRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NumberOfHoursDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NumberOfHoursDTO> findByCriteria(NumberOfHoursCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NumberOfHours> specification = createSpecification(criteria);
        return numberOfHoursRepository.findAll(specification, page).map(numberOfHoursMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NumberOfHoursCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NumberOfHours> specification = createSpecification(criteria);
        return numberOfHoursRepository.count(specification);
    }

    /**
     * Function to convert {@link NumberOfHoursCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NumberOfHours> createSpecification(NumberOfHoursCriteria criteria) {
        Specification<NumberOfHours> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NumberOfHours_.id));
            }
            if (criteria.getMonth() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMonth(), NumberOfHours_.month));
            }
            if (criteria.getExternalAgentDailyHoursAvg() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getExternalAgentDailyHoursAvg(), NumberOfHours_.externalAgentDailyHoursAvg)
                    );
            }
            if (criteria.getDailyHourAvg() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDailyHourAvg(), NumberOfHours_.dailyHourAvg));
            }
            if (criteria.getAvgHoursToAnswerCalls() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getAvgHoursToAnswerCalls(), NumberOfHours_.avgHoursToAnswerCalls));
            }
            if (criteria.getTotalHoursToAnswerCalls() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getTotalHoursToAnswerCalls(), NumberOfHours_.totalHoursToAnswerCalls)
                    );
            }
            if (criteria.getTotalReceivedCalls() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTotalReceivedCalls(), NumberOfHours_.totalReceivedCalls));
            }
            if (criteria.getTotalAttendedCalls() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTotalAttendedCalls(), NumberOfHours_.totalAttendedCalls));
            }
            if (criteria.getAttendedCallsPercentage() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getAttendedCallsPercentage(), NumberOfHours_.attendedCallsPercentage)
                    );
            }
            if (criteria.getAvgDailyAttendedCalls() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getAvgDailyAttendedCalls(), NumberOfHours_.avgDailyAttendedCalls));
            }
            if (criteria.getAvgDailyAttendedCallsByExternal() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getAvgDailyAttendedCallsByExternal(),
                            NumberOfHours_.avgDailyAttendedCallsByExternal
                        )
                    );
            }
            if (criteria.getAvgDailyAttendedCallsByExternalByDay() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getAvgDailyAttendedCallsByExternalByDay(),
                            NumberOfHours_.avgDailyAttendedCallsByExternalByDay
                        )
                    );
            }
            if (criteria.getAvgDailyAttendedCallsByInternal() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getAvgDailyAttendedCallsByInternal(),
                            NumberOfHours_.avgDailyAttendedCallsByInternal
                        )
                    );
            }
            if (criteria.getTotalReceivedChats() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTotalReceivedChats(), NumberOfHours_.totalReceivedChats));
            }
            if (criteria.getTotalAttendedChats() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTotalAttendedChats(), NumberOfHours_.totalAttendedChats));
            }
            if (criteria.getTotalReceivedMails() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTotalReceivedMails(), NumberOfHours_.totalReceivedMails));
            }
            if (criteria.getTotalAttendedMails() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTotalAttendedMails(), NumberOfHours_.totalAttendedMails));
            }
        }
        return specification;
    }
}
