package com.dashboard.app.service;

import static java.util.stream.Collectors.*;

import com.dashboard.app.domain.DailyCalls;
import com.dashboard.app.repository.DailyCallsRepository;
import com.dashboard.app.service.dto.DailyCallsDTO;
import com.dashboard.app.service.dto.DailyCallsMetrics;
import com.dashboard.app.service.mapper.DailyCallsMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
            if (row.getRowNum() == 0) continue;

            if (row.getCell(0) == null || row.getCell(0).toString().isEmpty()) break;

            DailyCalls dailyCalls;

            Optional<DailyCalls> dailyCalls1 = dailyCallsRepository.findByDay(row.getCell(0).getLocalDateTimeCellValue().toLocalDate());
            dailyCalls = dailyCalls1.orElseGet(DailyCalls::new);

            dailyCalls.setDay(row.getCell(0).getLocalDateTimeCellValue().toLocalDate());
            dailyCalls.setTotalDailyReceivedCalls((int) row.getCell(1).getNumericCellValue());
            dailyCalls.setTotalDailyAttendedCalls((int) row.getCell(2).getNumericCellValue());
            dailyCalls.setTotalDailyMissedCalls((int) row.getCell(3).getNumericCellValue());
            dailyCalls.setTotalDailyReceivedCallsExternalAgent((int) row.getCell(4).getNumericCellValue());
            dailyCalls.setTotalDailyAttendedCallsExternalAgent((int) row.getCell(5).getNumericCellValue());
            dailyCalls.setTotalDailyReceivedCallsInternalAgent((int) row.getCell(6).getNumericCellValue());
            dailyCalls.setTotalDailyAttendedCallsInternalAgent((int) row.getCell(7).getNumericCellValue());
            dailyCalls.setTotalDailyCallsTimeInMin((int) row.getCell(8).getNumericCellValue());
            dailyCalls.setTotalDailyCallsTimeExternalAgentInMin((int) row.getCell(9).getNumericCellValue());
            dailyCalls.setTotalDailyCallsTimeInternalAgentInMin((int) row.getCell(10).getNumericCellValue());
            dailyCalls.setAvgDailyOperationTimeExternalAgentInMin((float) row.getCell(11).getNumericCellValue());
            dailyCalls.setAvgDailyOperationTimeInternalAgentInMin((float) row.getCell(12).getNumericCellValue());

            dailyCallsRepository.save(dailyCalls);
        }
        workbook.close();
    }

    /**
     * TODO
     * get monthly summary by Date Range
     * @return
     */
    public List<DailyCallsDTO> getMonthlyCalls() {
        final String MONTHLY_REPORT_QUERY =
            "SELECT dc.id, DATE_FORMAT(dc.day, '%Y-%m') AS day, SUM(dc.total_daily_received_calls), SUM(dc.total_daily_attended_calls), SUM(dc.total_daily_missed_calls), SUM(dc.total_daily_received_calls_external_agent),\n" +
            "SUM(dc.total_daily_attended_calls_external_agent), SUM(dc.total_daily_received_calls_internal_agent), SUM(dc.total_daily_attended_calls_internal_agent), SUM(dc.total_daily_calls_time_in_min),\n" +
            "SUM(dc.total_daily_calls_time_external_agent_in_min), SUM(dc.total_daily_calls_time_internal_agent_in_min),\n" +
            "AVG(dc.avg_daily_operation_time_external_agent_in_min), AVG(dc.avg_daily_operation_time_internal_agent_in_min)\n" +
            "FROM dashboard.daily_calls dc\n" +
            "WHERE dc.day between '2023-11-01' AND '2023-11-30'\n" +
            "GROUP BY month(dc.day), year(dc.day)";

        //        Query query = entityManager.createNativeQuery(MONTHLY_REPORT_QUERY, DailyCalls.class);
        //
        //        return query.getResultList();

        return dailyCallsRepository.findAll().stream().map(dailyCallsMapper::toDto).collect(Collectors.toList());
    }

    /**
     * Gets metrics.
     *
     * @return the metrics
     */
    public DailyCallsMetrics getMetrics() {
        Object[] result = dailyCallsRepository.getMainCallsMetrics();

        DailyCallsMetrics dailyCallsMetrics = new DailyCallsMetrics();
        dailyCallsMetrics.setTotalReceivedCall(Integer.parseInt((String) result[0]));
        dailyCallsMetrics.setTotalAttendedCalls((Integer) result[1]);
        dailyCallsMetrics.setTotalLostCalls((Integer) result[2]);

        return dailyCallsMetrics;
    }
}
