package com.dashboard.app.service;

import com.dashboard.app.domain.DailyCalls;
import com.dashboard.app.repository.DailyCallsRepository;
import com.dashboard.app.service.dto.DailyCallsDTO;
import com.dashboard.app.service.mapper.DailyCallsMapper;
import java.io.IOException;
import java.util.Optional;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service Implementation for managing {@link com.dashboard.app.domain.DailyCalls}.
 */
@Service
@Transactional
public class DailyCallsService {

    private final Logger log = LoggerFactory.getLogger(DailyCallsService.class);

    private final DailyCallsRepository dailyCallsRepository;

    private final DailyCallsMapper dailyCallsMapper;

    public DailyCallsService(DailyCallsRepository dailyCallsRepository, DailyCallsMapper dailyCallsMapper) {
        this.dailyCallsRepository = dailyCallsRepository;
        this.dailyCallsMapper = dailyCallsMapper;
    }

    /**
     * Save a dailyCalls.
     *
     * @param dailyCallsDTO the entity to save.
     * @return the persisted entity.
     */
    public DailyCallsDTO save(DailyCallsDTO dailyCallsDTO) {
        log.debug("Request to save DailyCalls : {}", dailyCallsDTO);
        DailyCalls dailyCalls = dailyCallsMapper.toEntity(dailyCallsDTO);
        dailyCalls = dailyCallsRepository.save(dailyCalls);
        return dailyCallsMapper.toDto(dailyCalls);
    }

    /**
     * Update a dailyCalls.
     *
     * @param dailyCallsDTO the entity to save.
     * @return the persisted entity.
     */
    public DailyCallsDTO update(DailyCallsDTO dailyCallsDTO) {
        log.debug("Request to update DailyCalls : {}", dailyCallsDTO);
        DailyCalls dailyCalls = dailyCallsMapper.toEntity(dailyCallsDTO);
        dailyCalls = dailyCallsRepository.save(dailyCalls);
        return dailyCallsMapper.toDto(dailyCalls);
    }

    /**
     * Partially update a dailyCalls.
     *
     * @param dailyCallsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DailyCallsDTO> partialUpdate(DailyCallsDTO dailyCallsDTO) {
        log.debug("Request to partially update DailyCalls : {}", dailyCallsDTO);

        return dailyCallsRepository
            .findById(dailyCallsDTO.getId())
            .map(existingDailyCalls -> {
                dailyCallsMapper.partialUpdate(existingDailyCalls, dailyCallsDTO);

                return existingDailyCalls;
            })
            .map(dailyCallsRepository::save)
            .map(dailyCallsMapper::toDto);
    }

    /**
     * Get all the dailyCalls.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DailyCallsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DailyCalls");
        return dailyCallsRepository.findAll(pageable).map(dailyCallsMapper::toDto);
    }

    /**
     * Get one dailyCalls by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DailyCallsDTO> findOne(Long id) {
        log.debug("Request to get DailyCalls : {}", id);
        return dailyCallsRepository.findById(id).map(dailyCallsMapper::toDto);
    }

    /**
     * Delete the dailyCalls by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DailyCalls : {}", id);
        dailyCallsRepository.deleteById(id);
    }

    public void updateDataFromFile(MultipartFile file) throws IOException {
        // Load the Excel file
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                // Skip the header row
                continue;
            }

            if (row.getCell(0) == null) break;

            DailyCalls dailyCalls = new DailyCalls();

            dailyCalls.setDay(row.getCell(0).getLocalDateTimeCellValue().toLocalDate());

            dailyCallsRepository.save(dailyCalls);
            // Set other properties accordingly

        }
        workbook.close();
    }
}
