package com.dashboard.app.service;

import com.dashboard.app.domain.DailyChats;
import com.dashboard.app.repository.DailyChatsRepository;
import com.dashboard.app.service.dto.DailyChatsDTO;
import com.dashboard.app.service.mapper.DailyChatsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dashboard.app.domain.DailyChats}.
 */
@Service
@Transactional
public class DailyChatsService {

    private final Logger log = LoggerFactory.getLogger(DailyChatsService.class);

    private final DailyChatsRepository dailyChatsRepository;

    private final DailyChatsMapper dailyChatsMapper;

    public DailyChatsService(DailyChatsRepository dailyChatsRepository, DailyChatsMapper dailyChatsMapper) {
        this.dailyChatsRepository = dailyChatsRepository;
        this.dailyChatsMapper = dailyChatsMapper;
    }

    /**
     * Save a dailyChats.
     *
     * @param dailyChatsDTO the entity to save.
     * @return the persisted entity.
     */
    public DailyChatsDTO save(DailyChatsDTO dailyChatsDTO) {
        log.debug("Request to save DailyChats : {}", dailyChatsDTO);
        DailyChats dailyChats = dailyChatsMapper.toEntity(dailyChatsDTO);
        dailyChats = dailyChatsRepository.save(dailyChats);
        return dailyChatsMapper.toDto(dailyChats);
    }

    /**
     * Update a dailyChats.
     *
     * @param dailyChatsDTO the entity to save.
     * @return the persisted entity.
     */
    public DailyChatsDTO update(DailyChatsDTO dailyChatsDTO) {
        log.debug("Request to update DailyChats : {}", dailyChatsDTO);
        DailyChats dailyChats = dailyChatsMapper.toEntity(dailyChatsDTO);
        dailyChats = dailyChatsRepository.save(dailyChats);
        return dailyChatsMapper.toDto(dailyChats);
    }

    /**
     * Partially update a dailyChats.
     *
     * @param dailyChatsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DailyChatsDTO> partialUpdate(DailyChatsDTO dailyChatsDTO) {
        log.debug("Request to partially update DailyChats : {}", dailyChatsDTO);

        return dailyChatsRepository
            .findById(dailyChatsDTO.getId())
            .map(existingDailyChats -> {
                dailyChatsMapper.partialUpdate(existingDailyChats, dailyChatsDTO);

                return existingDailyChats;
            })
            .map(dailyChatsRepository::save)
            .map(dailyChatsMapper::toDto);
    }

    /**
     * Get all the dailyChats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DailyChatsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DailyChats");
        return dailyChatsRepository.findAll(pageable).map(dailyChatsMapper::toDto);
    }

    /**
     * Get one dailyChats by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DailyChatsDTO> findOne(Long id) {
        log.debug("Request to get DailyChats : {}", id);
        return dailyChatsRepository.findById(id).map(dailyChatsMapper::toDto);
    }

    /**
     * Delete the dailyChats by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DailyChats : {}", id);
        dailyChatsRepository.deleteById(id);
    }
}
