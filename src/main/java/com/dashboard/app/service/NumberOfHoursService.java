package com.dashboard.app.service;

import com.dashboard.app.domain.NumberOfHours;
import com.dashboard.app.repository.NumberOfHoursRepository;
import com.dashboard.app.service.dto.NumberOfHoursDTO;
import com.dashboard.app.service.mapper.NumberOfHoursMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dashboard.app.domain.NumberOfHours}.
 */
@Service
@Transactional
public class NumberOfHoursService {

    private final Logger log = LoggerFactory.getLogger(NumberOfHoursService.class);

    private final NumberOfHoursRepository numberOfHoursRepository;

    private final NumberOfHoursMapper numberOfHoursMapper;

    public NumberOfHoursService(NumberOfHoursRepository numberOfHoursRepository, NumberOfHoursMapper numberOfHoursMapper) {
        this.numberOfHoursRepository = numberOfHoursRepository;
        this.numberOfHoursMapper = numberOfHoursMapper;
    }

    /**
     * Save a numberOfHours.
     *
     * @param numberOfHoursDTO the entity to save.
     * @return the persisted entity.
     */
    public NumberOfHoursDTO save(NumberOfHoursDTO numberOfHoursDTO) {
        log.debug("Request to save NumberOfHours : {}", numberOfHoursDTO);
        NumberOfHours numberOfHours = numberOfHoursMapper.toEntity(numberOfHoursDTO);
        numberOfHours = numberOfHoursRepository.save(numberOfHours);
        return numberOfHoursMapper.toDto(numberOfHours);
    }

    /**
     * Update a numberOfHours.
     *
     * @param numberOfHoursDTO the entity to save.
     * @return the persisted entity.
     */
    public NumberOfHoursDTO update(NumberOfHoursDTO numberOfHoursDTO) {
        log.debug("Request to update NumberOfHours : {}", numberOfHoursDTO);
        NumberOfHours numberOfHours = numberOfHoursMapper.toEntity(numberOfHoursDTO);
        numberOfHours = numberOfHoursRepository.save(numberOfHours);
        return numberOfHoursMapper.toDto(numberOfHours);
    }

    /**
     * Partially update a numberOfHours.
     *
     * @param numberOfHoursDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NumberOfHoursDTO> partialUpdate(NumberOfHoursDTO numberOfHoursDTO) {
        log.debug("Request to partially update NumberOfHours : {}", numberOfHoursDTO);

        return numberOfHoursRepository
            .findById(numberOfHoursDTO.getId())
            .map(existingNumberOfHours -> {
                numberOfHoursMapper.partialUpdate(existingNumberOfHours, numberOfHoursDTO);

                return existingNumberOfHours;
            })
            .map(numberOfHoursRepository::save)
            .map(numberOfHoursMapper::toDto);
    }

    /**
     * Get all the numberOfHours.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NumberOfHoursDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NumberOfHours");
        return numberOfHoursRepository.findAll(pageable).map(numberOfHoursMapper::toDto);
    }

    /**
     * Get one numberOfHours by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NumberOfHoursDTO> findOne(Long id) {
        log.debug("Request to get NumberOfHours : {}", id);
        return numberOfHoursRepository.findById(id).map(numberOfHoursMapper::toDto);
    }

    /**
     * Delete the numberOfHours by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete NumberOfHours : {}", id);
        numberOfHoursRepository.deleteById(id);
    }
}
