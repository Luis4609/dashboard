package com.dashboard.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dashboard.app.IntegrationTest;
import com.dashboard.app.domain.NumberOfHours;
import com.dashboard.app.repository.NumberOfHoursRepository;
import com.dashboard.app.service.dto.NumberOfHoursDTO;
import com.dashboard.app.service.mapper.NumberOfHoursMapper;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link NumberOfHoursResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NumberOfHoursResourceIT {

    private static final String DEFAULT_MONTH = "AAAAAAAAAA";
    private static final String UPDATED_MONTH = "BBBBBBBBBB";

    private static final Double DEFAULT_EXTERNAL_AGENT_DAILY_HOURS_AVG = 1D;
    private static final Double UPDATED_EXTERNAL_AGENT_DAILY_HOURS_AVG = 2D;
    private static final Double SMALLER_EXTERNAL_AGENT_DAILY_HOURS_AVG = 1D - 1D;

    private static final Double DEFAULT_DAILY_HOUR_AVG = 1D;
    private static final Double UPDATED_DAILY_HOUR_AVG = 2D;
    private static final Double SMALLER_DAILY_HOUR_AVG = 1D - 1D;

    private static final Integer DEFAULT_AVG_HOURS_TO_ANSWER_CALLS = 1;
    private static final Integer UPDATED_AVG_HOURS_TO_ANSWER_CALLS = 2;
    private static final Integer SMALLER_AVG_HOURS_TO_ANSWER_CALLS = 1 - 1;

    private static final Double DEFAULT_TOTAL_HOURS_TO_ANSWER_CALLS = 1D;
    private static final Double UPDATED_TOTAL_HOURS_TO_ANSWER_CALLS = 2D;
    private static final Double SMALLER_TOTAL_HOURS_TO_ANSWER_CALLS = 1D - 1D;

    private static final Integer DEFAULT_TOTAL_RECEIVED_CALLS = 1;
    private static final Integer UPDATED_TOTAL_RECEIVED_CALLS = 2;
    private static final Integer SMALLER_TOTAL_RECEIVED_CALLS = 1 - 1;

    private static final Integer DEFAULT_TOTAL_ATTENDED_CALLS = 1;
    private static final Integer UPDATED_TOTAL_ATTENDED_CALLS = 2;
    private static final Integer SMALLER_TOTAL_ATTENDED_CALLS = 1 - 1;

    private static final Double DEFAULT_ATTENDED_CALLS_PERCENTAGE = 1D;
    private static final Double UPDATED_ATTENDED_CALLS_PERCENTAGE = 2D;
    private static final Double SMALLER_ATTENDED_CALLS_PERCENTAGE = 1D - 1D;

    private static final Double DEFAULT_AVG_DAILY_ATTENDED_CALLS = 1D;
    private static final Double UPDATED_AVG_DAILY_ATTENDED_CALLS = 2D;
    private static final Double SMALLER_AVG_DAILY_ATTENDED_CALLS = 1D - 1D;

    private static final Double DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL = 1D;
    private static final Double UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL = 2D;
    private static final Double SMALLER_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL = 1D - 1D;

    private static final Double DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL_BY_DAY = 1D;
    private static final Double UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL_BY_DAY = 2D;
    private static final Double SMALLER_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL_BY_DAY = 1D - 1D;

    private static final Double DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_INTERNAL = 1D;
    private static final Double UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_INTERNAL = 2D;
    private static final Double SMALLER_AVG_DAILY_ATTENDED_CALLS_BY_INTERNAL = 1D - 1D;

    private static final Integer DEFAULT_TOTAL_RECEIVED_CHATS = 1;
    private static final Integer UPDATED_TOTAL_RECEIVED_CHATS = 2;
    private static final Integer SMALLER_TOTAL_RECEIVED_CHATS = 1 - 1;

    private static final Integer DEFAULT_TOTAL_ATTENDED_CHATS = 1;
    private static final Integer UPDATED_TOTAL_ATTENDED_CHATS = 2;
    private static final Integer SMALLER_TOTAL_ATTENDED_CHATS = 1 - 1;

    private static final Integer DEFAULT_TOTAL_RECEIVED_MAILS = 1;
    private static final Integer UPDATED_TOTAL_RECEIVED_MAILS = 2;
    private static final Integer SMALLER_TOTAL_RECEIVED_MAILS = 1 - 1;

    private static final Integer DEFAULT_TOTAL_ATTENDED_MAILS = 1;
    private static final Integer UPDATED_TOTAL_ATTENDED_MAILS = 2;
    private static final Integer SMALLER_TOTAL_ATTENDED_MAILS = 1 - 1;

    private static final String ENTITY_API_URL = "/api/number-of-hours";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NumberOfHoursRepository numberOfHoursRepository;

    @Autowired
    private NumberOfHoursMapper numberOfHoursMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNumberOfHoursMockMvc;

    private NumberOfHours numberOfHours;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NumberOfHours createEntity(EntityManager em) {
        NumberOfHours numberOfHours = new NumberOfHours()
            .month(DEFAULT_MONTH)
            .externalAgentDailyHoursAvg(DEFAULT_EXTERNAL_AGENT_DAILY_HOURS_AVG)
            .dailyHourAvg(DEFAULT_DAILY_HOUR_AVG)
            .avgHoursToAnswerCalls(DEFAULT_AVG_HOURS_TO_ANSWER_CALLS)
            .totalHoursToAnswerCalls(DEFAULT_TOTAL_HOURS_TO_ANSWER_CALLS)
            .totalReceivedCalls(DEFAULT_TOTAL_RECEIVED_CALLS)
            .totalAttendedCalls(DEFAULT_TOTAL_ATTENDED_CALLS)
            .attendedCallsPercentage(DEFAULT_ATTENDED_CALLS_PERCENTAGE)
            .avgDailyAttendedCalls(DEFAULT_AVG_DAILY_ATTENDED_CALLS)
            .avgDailyAttendedCallsByExternal(DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL)
            .avgDailyAttendedCallsByExternalByDay(DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL_BY_DAY)
            .avgDailyAttendedCallsByInternal(DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_INTERNAL)
            .totalReceivedChats(DEFAULT_TOTAL_RECEIVED_CHATS)
            .totalAttendedChats(DEFAULT_TOTAL_ATTENDED_CHATS)
            .totalReceivedMails(DEFAULT_TOTAL_RECEIVED_MAILS)
            .totalAttendedMails(DEFAULT_TOTAL_ATTENDED_MAILS);
        return numberOfHours;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NumberOfHours createUpdatedEntity(EntityManager em) {
        NumberOfHours numberOfHours = new NumberOfHours()
            .month(UPDATED_MONTH)
            .externalAgentDailyHoursAvg(UPDATED_EXTERNAL_AGENT_DAILY_HOURS_AVG)
            .dailyHourAvg(UPDATED_DAILY_HOUR_AVG)
            .avgHoursToAnswerCalls(UPDATED_AVG_HOURS_TO_ANSWER_CALLS)
            .totalHoursToAnswerCalls(UPDATED_TOTAL_HOURS_TO_ANSWER_CALLS)
            .totalReceivedCalls(UPDATED_TOTAL_RECEIVED_CALLS)
            .totalAttendedCalls(UPDATED_TOTAL_ATTENDED_CALLS)
            .attendedCallsPercentage(UPDATED_ATTENDED_CALLS_PERCENTAGE)
            .avgDailyAttendedCalls(UPDATED_AVG_DAILY_ATTENDED_CALLS)
            .avgDailyAttendedCallsByExternal(UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL)
            .avgDailyAttendedCallsByExternalByDay(UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL_BY_DAY)
            .avgDailyAttendedCallsByInternal(UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_INTERNAL)
            .totalReceivedChats(UPDATED_TOTAL_RECEIVED_CHATS)
            .totalAttendedChats(UPDATED_TOTAL_ATTENDED_CHATS)
            .totalReceivedMails(UPDATED_TOTAL_RECEIVED_MAILS)
            .totalAttendedMails(UPDATED_TOTAL_ATTENDED_MAILS);
        return numberOfHours;
    }

    @BeforeEach
    public void initTest() {
        numberOfHours = createEntity(em);
    }

    @Test
    @Transactional
    void createNumberOfHours() throws Exception {
        int databaseSizeBeforeCreate = numberOfHoursRepository.findAll().size();
        // Create the NumberOfHours
        NumberOfHoursDTO numberOfHoursDTO = numberOfHoursMapper.toDto(numberOfHours);
        restNumberOfHoursMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(numberOfHoursDTO))
            )
            .andExpect(status().isCreated());

        // Validate the NumberOfHours in the database
        List<NumberOfHours> numberOfHoursList = numberOfHoursRepository.findAll();
        assertThat(numberOfHoursList).hasSize(databaseSizeBeforeCreate + 1);
        NumberOfHours testNumberOfHours = numberOfHoursList.get(numberOfHoursList.size() - 1);
        assertThat(testNumberOfHours.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testNumberOfHours.getExternalAgentDailyHoursAvg()).isEqualTo(DEFAULT_EXTERNAL_AGENT_DAILY_HOURS_AVG);
        assertThat(testNumberOfHours.getDailyHourAvg()).isEqualTo(DEFAULT_DAILY_HOUR_AVG);
        assertThat(testNumberOfHours.getAvgHoursToAnswerCalls()).isEqualTo(DEFAULT_AVG_HOURS_TO_ANSWER_CALLS);
        assertThat(testNumberOfHours.getTotalHoursToAnswerCalls()).isEqualTo(DEFAULT_TOTAL_HOURS_TO_ANSWER_CALLS);
        assertThat(testNumberOfHours.getTotalReceivedCalls()).isEqualTo(DEFAULT_TOTAL_RECEIVED_CALLS);
        assertThat(testNumberOfHours.getTotalAttendedCalls()).isEqualTo(DEFAULT_TOTAL_ATTENDED_CALLS);
        assertThat(testNumberOfHours.getAttendedCallsPercentage()).isEqualTo(DEFAULT_ATTENDED_CALLS_PERCENTAGE);
        assertThat(testNumberOfHours.getAvgDailyAttendedCalls()).isEqualTo(DEFAULT_AVG_DAILY_ATTENDED_CALLS);
        assertThat(testNumberOfHours.getAvgDailyAttendedCallsByExternal()).isEqualTo(DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL);
        assertThat(testNumberOfHours.getAvgDailyAttendedCallsByExternalByDay())
            .isEqualTo(DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL_BY_DAY);
        assertThat(testNumberOfHours.getAvgDailyAttendedCallsByInternal()).isEqualTo(DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_INTERNAL);
        assertThat(testNumberOfHours.getTotalReceivedChats()).isEqualTo(DEFAULT_TOTAL_RECEIVED_CHATS);
        assertThat(testNumberOfHours.getTotalAttendedChats()).isEqualTo(DEFAULT_TOTAL_ATTENDED_CHATS);
        assertThat(testNumberOfHours.getTotalReceivedMails()).isEqualTo(DEFAULT_TOTAL_RECEIVED_MAILS);
        assertThat(testNumberOfHours.getTotalAttendedMails()).isEqualTo(DEFAULT_TOTAL_ATTENDED_MAILS);
    }

    @Test
    @Transactional
    void createNumberOfHoursWithExistingId() throws Exception {
        // Create the NumberOfHours with an existing ID
        numberOfHours.setId(1L);
        NumberOfHoursDTO numberOfHoursDTO = numberOfHoursMapper.toDto(numberOfHours);

        int databaseSizeBeforeCreate = numberOfHoursRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNumberOfHoursMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(numberOfHoursDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NumberOfHours in the database
        List<NumberOfHours> numberOfHoursList = numberOfHoursRepository.findAll();
        assertThat(numberOfHoursList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMonthIsRequired() throws Exception {
        int databaseSizeBeforeTest = numberOfHoursRepository.findAll().size();
        // set the field null
        numberOfHours.setMonth(null);

        // Create the NumberOfHours, which fails.
        NumberOfHoursDTO numberOfHoursDTO = numberOfHoursMapper.toDto(numberOfHours);

        restNumberOfHoursMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(numberOfHoursDTO))
            )
            .andExpect(status().isBadRequest());

        List<NumberOfHours> numberOfHoursList = numberOfHoursRepository.findAll();
        assertThat(numberOfHoursList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkExternalAgentDailyHoursAvgIsRequired() throws Exception {
        int databaseSizeBeforeTest = numberOfHoursRepository.findAll().size();
        // set the field null
        numberOfHours.setExternalAgentDailyHoursAvg(null);

        // Create the NumberOfHours, which fails.
        NumberOfHoursDTO numberOfHoursDTO = numberOfHoursMapper.toDto(numberOfHours);

        restNumberOfHoursMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(numberOfHoursDTO))
            )
            .andExpect(status().isBadRequest());

        List<NumberOfHours> numberOfHoursList = numberOfHoursRepository.findAll();
        assertThat(numberOfHoursList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNumberOfHours() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList
        restNumberOfHoursMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(numberOfHours.getId().intValue())))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH)))
            .andExpect(jsonPath("$.[*].externalAgentDailyHoursAvg").value(hasItem(DEFAULT_EXTERNAL_AGENT_DAILY_HOURS_AVG.doubleValue())))
            .andExpect(jsonPath("$.[*].dailyHourAvg").value(hasItem(DEFAULT_DAILY_HOUR_AVG.doubleValue())))
            .andExpect(jsonPath("$.[*].avgHoursToAnswerCalls").value(hasItem(DEFAULT_AVG_HOURS_TO_ANSWER_CALLS)))
            .andExpect(jsonPath("$.[*].totalHoursToAnswerCalls").value(hasItem(DEFAULT_TOTAL_HOURS_TO_ANSWER_CALLS.doubleValue())))
            .andExpect(jsonPath("$.[*].totalReceivedCalls").value(hasItem(DEFAULT_TOTAL_RECEIVED_CALLS)))
            .andExpect(jsonPath("$.[*].totalAttendedCalls").value(hasItem(DEFAULT_TOTAL_ATTENDED_CALLS)))
            .andExpect(jsonPath("$.[*].attendedCallsPercentage").value(hasItem(DEFAULT_ATTENDED_CALLS_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].avgDailyAttendedCalls").value(hasItem(DEFAULT_AVG_DAILY_ATTENDED_CALLS.doubleValue())))
            .andExpect(
                jsonPath("$.[*].avgDailyAttendedCallsByExternal").value(hasItem(DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL.doubleValue()))
            )
            .andExpect(
                jsonPath("$.[*].avgDailyAttendedCallsByExternalByDay")
                    .value(hasItem(DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL_BY_DAY.doubleValue()))
            )
            .andExpect(
                jsonPath("$.[*].avgDailyAttendedCallsByInternal").value(hasItem(DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_INTERNAL.doubleValue()))
            )
            .andExpect(jsonPath("$.[*].totalReceivedChats").value(hasItem(DEFAULT_TOTAL_RECEIVED_CHATS)))
            .andExpect(jsonPath("$.[*].totalAttendedChats").value(hasItem(DEFAULT_TOTAL_ATTENDED_CHATS)))
            .andExpect(jsonPath("$.[*].totalReceivedMails").value(hasItem(DEFAULT_TOTAL_RECEIVED_MAILS)))
            .andExpect(jsonPath("$.[*].totalAttendedMails").value(hasItem(DEFAULT_TOTAL_ATTENDED_MAILS)));
    }

    @Test
    @Transactional
    void getNumberOfHours() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get the numberOfHours
        restNumberOfHoursMockMvc
            .perform(get(ENTITY_API_URL_ID, numberOfHours.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(numberOfHours.getId().intValue()))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH))
            .andExpect(jsonPath("$.externalAgentDailyHoursAvg").value(DEFAULT_EXTERNAL_AGENT_DAILY_HOURS_AVG.doubleValue()))
            .andExpect(jsonPath("$.dailyHourAvg").value(DEFAULT_DAILY_HOUR_AVG.doubleValue()))
            .andExpect(jsonPath("$.avgHoursToAnswerCalls").value(DEFAULT_AVG_HOURS_TO_ANSWER_CALLS))
            .andExpect(jsonPath("$.totalHoursToAnswerCalls").value(DEFAULT_TOTAL_HOURS_TO_ANSWER_CALLS.doubleValue()))
            .andExpect(jsonPath("$.totalReceivedCalls").value(DEFAULT_TOTAL_RECEIVED_CALLS))
            .andExpect(jsonPath("$.totalAttendedCalls").value(DEFAULT_TOTAL_ATTENDED_CALLS))
            .andExpect(jsonPath("$.attendedCallsPercentage").value(DEFAULT_ATTENDED_CALLS_PERCENTAGE.doubleValue()))
            .andExpect(jsonPath("$.avgDailyAttendedCalls").value(DEFAULT_AVG_DAILY_ATTENDED_CALLS.doubleValue()))
            .andExpect(jsonPath("$.avgDailyAttendedCallsByExternal").value(DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL.doubleValue()))
            .andExpect(
                jsonPath("$.avgDailyAttendedCallsByExternalByDay").value(DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL_BY_DAY.doubleValue())
            )
            .andExpect(jsonPath("$.avgDailyAttendedCallsByInternal").value(DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_INTERNAL.doubleValue()))
            .andExpect(jsonPath("$.totalReceivedChats").value(DEFAULT_TOTAL_RECEIVED_CHATS))
            .andExpect(jsonPath("$.totalAttendedChats").value(DEFAULT_TOTAL_ATTENDED_CHATS))
            .andExpect(jsonPath("$.totalReceivedMails").value(DEFAULT_TOTAL_RECEIVED_MAILS))
            .andExpect(jsonPath("$.totalAttendedMails").value(DEFAULT_TOTAL_ATTENDED_MAILS));
    }

    @Test
    @Transactional
    void getNumberOfHoursByIdFiltering() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        Long id = numberOfHours.getId();

        defaultNumberOfHoursShouldBeFound("id.equals=" + id);
        defaultNumberOfHoursShouldNotBeFound("id.notEquals=" + id);

        defaultNumberOfHoursShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNumberOfHoursShouldNotBeFound("id.greaterThan=" + id);

        defaultNumberOfHoursShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNumberOfHoursShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByMonthIsEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where month equals to DEFAULT_MONTH
        defaultNumberOfHoursShouldBeFound("month.equals=" + DEFAULT_MONTH);

        // Get all the numberOfHoursList where month equals to UPDATED_MONTH
        defaultNumberOfHoursShouldNotBeFound("month.equals=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByMonthIsInShouldWork() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where month in DEFAULT_MONTH or UPDATED_MONTH
        defaultNumberOfHoursShouldBeFound("month.in=" + DEFAULT_MONTH + "," + UPDATED_MONTH);

        // Get all the numberOfHoursList where month equals to UPDATED_MONTH
        defaultNumberOfHoursShouldNotBeFound("month.in=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByMonthIsNullOrNotNull() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where month is not null
        defaultNumberOfHoursShouldBeFound("month.specified=true");

        // Get all the numberOfHoursList where month is null
        defaultNumberOfHoursShouldNotBeFound("month.specified=false");
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByMonthContainsSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where month contains DEFAULT_MONTH
        defaultNumberOfHoursShouldBeFound("month.contains=" + DEFAULT_MONTH);

        // Get all the numberOfHoursList where month contains UPDATED_MONTH
        defaultNumberOfHoursShouldNotBeFound("month.contains=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByMonthNotContainsSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where month does not contain DEFAULT_MONTH
        defaultNumberOfHoursShouldNotBeFound("month.doesNotContain=" + DEFAULT_MONTH);

        // Get all the numberOfHoursList where month does not contain UPDATED_MONTH
        defaultNumberOfHoursShouldBeFound("month.doesNotContain=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByExternalAgentDailyHoursAvgIsEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where externalAgentDailyHoursAvg equals to DEFAULT_EXTERNAL_AGENT_DAILY_HOURS_AVG
        defaultNumberOfHoursShouldBeFound("externalAgentDailyHoursAvg.equals=" + DEFAULT_EXTERNAL_AGENT_DAILY_HOURS_AVG);

        // Get all the numberOfHoursList where externalAgentDailyHoursAvg equals to UPDATED_EXTERNAL_AGENT_DAILY_HOURS_AVG
        defaultNumberOfHoursShouldNotBeFound("externalAgentDailyHoursAvg.equals=" + UPDATED_EXTERNAL_AGENT_DAILY_HOURS_AVG);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByExternalAgentDailyHoursAvgIsInShouldWork() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where externalAgentDailyHoursAvg in DEFAULT_EXTERNAL_AGENT_DAILY_HOURS_AVG or UPDATED_EXTERNAL_AGENT_DAILY_HOURS_AVG
        defaultNumberOfHoursShouldBeFound(
            "externalAgentDailyHoursAvg.in=" + DEFAULT_EXTERNAL_AGENT_DAILY_HOURS_AVG + "," + UPDATED_EXTERNAL_AGENT_DAILY_HOURS_AVG
        );

        // Get all the numberOfHoursList where externalAgentDailyHoursAvg equals to UPDATED_EXTERNAL_AGENT_DAILY_HOURS_AVG
        defaultNumberOfHoursShouldNotBeFound("externalAgentDailyHoursAvg.in=" + UPDATED_EXTERNAL_AGENT_DAILY_HOURS_AVG);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByExternalAgentDailyHoursAvgIsNullOrNotNull() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where externalAgentDailyHoursAvg is not null
        defaultNumberOfHoursShouldBeFound("externalAgentDailyHoursAvg.specified=true");

        // Get all the numberOfHoursList where externalAgentDailyHoursAvg is null
        defaultNumberOfHoursShouldNotBeFound("externalAgentDailyHoursAvg.specified=false");
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByExternalAgentDailyHoursAvgIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where externalAgentDailyHoursAvg is greater than or equal to DEFAULT_EXTERNAL_AGENT_DAILY_HOURS_AVG
        defaultNumberOfHoursShouldBeFound("externalAgentDailyHoursAvg.greaterThanOrEqual=" + DEFAULT_EXTERNAL_AGENT_DAILY_HOURS_AVG);

        // Get all the numberOfHoursList where externalAgentDailyHoursAvg is greater than or equal to UPDATED_EXTERNAL_AGENT_DAILY_HOURS_AVG
        defaultNumberOfHoursShouldNotBeFound("externalAgentDailyHoursAvg.greaterThanOrEqual=" + UPDATED_EXTERNAL_AGENT_DAILY_HOURS_AVG);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByExternalAgentDailyHoursAvgIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where externalAgentDailyHoursAvg is less than or equal to DEFAULT_EXTERNAL_AGENT_DAILY_HOURS_AVG
        defaultNumberOfHoursShouldBeFound("externalAgentDailyHoursAvg.lessThanOrEqual=" + DEFAULT_EXTERNAL_AGENT_DAILY_HOURS_AVG);

        // Get all the numberOfHoursList where externalAgentDailyHoursAvg is less than or equal to SMALLER_EXTERNAL_AGENT_DAILY_HOURS_AVG
        defaultNumberOfHoursShouldNotBeFound("externalAgentDailyHoursAvg.lessThanOrEqual=" + SMALLER_EXTERNAL_AGENT_DAILY_HOURS_AVG);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByExternalAgentDailyHoursAvgIsLessThanSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where externalAgentDailyHoursAvg is less than DEFAULT_EXTERNAL_AGENT_DAILY_HOURS_AVG
        defaultNumberOfHoursShouldNotBeFound("externalAgentDailyHoursAvg.lessThan=" + DEFAULT_EXTERNAL_AGENT_DAILY_HOURS_AVG);

        // Get all the numberOfHoursList where externalAgentDailyHoursAvg is less than UPDATED_EXTERNAL_AGENT_DAILY_HOURS_AVG
        defaultNumberOfHoursShouldBeFound("externalAgentDailyHoursAvg.lessThan=" + UPDATED_EXTERNAL_AGENT_DAILY_HOURS_AVG);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByExternalAgentDailyHoursAvgIsGreaterThanSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where externalAgentDailyHoursAvg is greater than DEFAULT_EXTERNAL_AGENT_DAILY_HOURS_AVG
        defaultNumberOfHoursShouldNotBeFound("externalAgentDailyHoursAvg.greaterThan=" + DEFAULT_EXTERNAL_AGENT_DAILY_HOURS_AVG);

        // Get all the numberOfHoursList where externalAgentDailyHoursAvg is greater than SMALLER_EXTERNAL_AGENT_DAILY_HOURS_AVG
        defaultNumberOfHoursShouldBeFound("externalAgentDailyHoursAvg.greaterThan=" + SMALLER_EXTERNAL_AGENT_DAILY_HOURS_AVG);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByDailyHourAvgIsEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where dailyHourAvg equals to DEFAULT_DAILY_HOUR_AVG
        defaultNumberOfHoursShouldBeFound("dailyHourAvg.equals=" + DEFAULT_DAILY_HOUR_AVG);

        // Get all the numberOfHoursList where dailyHourAvg equals to UPDATED_DAILY_HOUR_AVG
        defaultNumberOfHoursShouldNotBeFound("dailyHourAvg.equals=" + UPDATED_DAILY_HOUR_AVG);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByDailyHourAvgIsInShouldWork() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where dailyHourAvg in DEFAULT_DAILY_HOUR_AVG or UPDATED_DAILY_HOUR_AVG
        defaultNumberOfHoursShouldBeFound("dailyHourAvg.in=" + DEFAULT_DAILY_HOUR_AVG + "," + UPDATED_DAILY_HOUR_AVG);

        // Get all the numberOfHoursList where dailyHourAvg equals to UPDATED_DAILY_HOUR_AVG
        defaultNumberOfHoursShouldNotBeFound("dailyHourAvg.in=" + UPDATED_DAILY_HOUR_AVG);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByDailyHourAvgIsNullOrNotNull() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where dailyHourAvg is not null
        defaultNumberOfHoursShouldBeFound("dailyHourAvg.specified=true");

        // Get all the numberOfHoursList where dailyHourAvg is null
        defaultNumberOfHoursShouldNotBeFound("dailyHourAvg.specified=false");
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByDailyHourAvgIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where dailyHourAvg is greater than or equal to DEFAULT_DAILY_HOUR_AVG
        defaultNumberOfHoursShouldBeFound("dailyHourAvg.greaterThanOrEqual=" + DEFAULT_DAILY_HOUR_AVG);

        // Get all the numberOfHoursList where dailyHourAvg is greater than or equal to UPDATED_DAILY_HOUR_AVG
        defaultNumberOfHoursShouldNotBeFound("dailyHourAvg.greaterThanOrEqual=" + UPDATED_DAILY_HOUR_AVG);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByDailyHourAvgIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where dailyHourAvg is less than or equal to DEFAULT_DAILY_HOUR_AVG
        defaultNumberOfHoursShouldBeFound("dailyHourAvg.lessThanOrEqual=" + DEFAULT_DAILY_HOUR_AVG);

        // Get all the numberOfHoursList where dailyHourAvg is less than or equal to SMALLER_DAILY_HOUR_AVG
        defaultNumberOfHoursShouldNotBeFound("dailyHourAvg.lessThanOrEqual=" + SMALLER_DAILY_HOUR_AVG);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByDailyHourAvgIsLessThanSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where dailyHourAvg is less than DEFAULT_DAILY_HOUR_AVG
        defaultNumberOfHoursShouldNotBeFound("dailyHourAvg.lessThan=" + DEFAULT_DAILY_HOUR_AVG);

        // Get all the numberOfHoursList where dailyHourAvg is less than UPDATED_DAILY_HOUR_AVG
        defaultNumberOfHoursShouldBeFound("dailyHourAvg.lessThan=" + UPDATED_DAILY_HOUR_AVG);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByDailyHourAvgIsGreaterThanSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where dailyHourAvg is greater than DEFAULT_DAILY_HOUR_AVG
        defaultNumberOfHoursShouldNotBeFound("dailyHourAvg.greaterThan=" + DEFAULT_DAILY_HOUR_AVG);

        // Get all the numberOfHoursList where dailyHourAvg is greater than SMALLER_DAILY_HOUR_AVG
        defaultNumberOfHoursShouldBeFound("dailyHourAvg.greaterThan=" + SMALLER_DAILY_HOUR_AVG);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAvgHoursToAnswerCallsIsEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where avgHoursToAnswerCalls equals to DEFAULT_AVG_HOURS_TO_ANSWER_CALLS
        defaultNumberOfHoursShouldBeFound("avgHoursToAnswerCalls.equals=" + DEFAULT_AVG_HOURS_TO_ANSWER_CALLS);

        // Get all the numberOfHoursList where avgHoursToAnswerCalls equals to UPDATED_AVG_HOURS_TO_ANSWER_CALLS
        defaultNumberOfHoursShouldNotBeFound("avgHoursToAnswerCalls.equals=" + UPDATED_AVG_HOURS_TO_ANSWER_CALLS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAvgHoursToAnswerCallsIsInShouldWork() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where avgHoursToAnswerCalls in DEFAULT_AVG_HOURS_TO_ANSWER_CALLS or UPDATED_AVG_HOURS_TO_ANSWER_CALLS
        defaultNumberOfHoursShouldBeFound(
            "avgHoursToAnswerCalls.in=" + DEFAULT_AVG_HOURS_TO_ANSWER_CALLS + "," + UPDATED_AVG_HOURS_TO_ANSWER_CALLS
        );

        // Get all the numberOfHoursList where avgHoursToAnswerCalls equals to UPDATED_AVG_HOURS_TO_ANSWER_CALLS
        defaultNumberOfHoursShouldNotBeFound("avgHoursToAnswerCalls.in=" + UPDATED_AVG_HOURS_TO_ANSWER_CALLS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAvgHoursToAnswerCallsIsNullOrNotNull() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where avgHoursToAnswerCalls is not null
        defaultNumberOfHoursShouldBeFound("avgHoursToAnswerCalls.specified=true");

        // Get all the numberOfHoursList where avgHoursToAnswerCalls is null
        defaultNumberOfHoursShouldNotBeFound("avgHoursToAnswerCalls.specified=false");
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAvgHoursToAnswerCallsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where avgHoursToAnswerCalls is greater than or equal to DEFAULT_AVG_HOURS_TO_ANSWER_CALLS
        defaultNumberOfHoursShouldBeFound("avgHoursToAnswerCalls.greaterThanOrEqual=" + DEFAULT_AVG_HOURS_TO_ANSWER_CALLS);

        // Get all the numberOfHoursList where avgHoursToAnswerCalls is greater than or equal to UPDATED_AVG_HOURS_TO_ANSWER_CALLS
        defaultNumberOfHoursShouldNotBeFound("avgHoursToAnswerCalls.greaterThanOrEqual=" + UPDATED_AVG_HOURS_TO_ANSWER_CALLS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAvgHoursToAnswerCallsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where avgHoursToAnswerCalls is less than or equal to DEFAULT_AVG_HOURS_TO_ANSWER_CALLS
        defaultNumberOfHoursShouldBeFound("avgHoursToAnswerCalls.lessThanOrEqual=" + DEFAULT_AVG_HOURS_TO_ANSWER_CALLS);

        // Get all the numberOfHoursList where avgHoursToAnswerCalls is less than or equal to SMALLER_AVG_HOURS_TO_ANSWER_CALLS
        defaultNumberOfHoursShouldNotBeFound("avgHoursToAnswerCalls.lessThanOrEqual=" + SMALLER_AVG_HOURS_TO_ANSWER_CALLS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAvgHoursToAnswerCallsIsLessThanSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where avgHoursToAnswerCalls is less than DEFAULT_AVG_HOURS_TO_ANSWER_CALLS
        defaultNumberOfHoursShouldNotBeFound("avgHoursToAnswerCalls.lessThan=" + DEFAULT_AVG_HOURS_TO_ANSWER_CALLS);

        // Get all the numberOfHoursList where avgHoursToAnswerCalls is less than UPDATED_AVG_HOURS_TO_ANSWER_CALLS
        defaultNumberOfHoursShouldBeFound("avgHoursToAnswerCalls.lessThan=" + UPDATED_AVG_HOURS_TO_ANSWER_CALLS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAvgHoursToAnswerCallsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where avgHoursToAnswerCalls is greater than DEFAULT_AVG_HOURS_TO_ANSWER_CALLS
        defaultNumberOfHoursShouldNotBeFound("avgHoursToAnswerCalls.greaterThan=" + DEFAULT_AVG_HOURS_TO_ANSWER_CALLS);

        // Get all the numberOfHoursList where avgHoursToAnswerCalls is greater than SMALLER_AVG_HOURS_TO_ANSWER_CALLS
        defaultNumberOfHoursShouldBeFound("avgHoursToAnswerCalls.greaterThan=" + SMALLER_AVG_HOURS_TO_ANSWER_CALLS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalHoursToAnswerCallsIsEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalHoursToAnswerCalls equals to DEFAULT_TOTAL_HOURS_TO_ANSWER_CALLS
        defaultNumberOfHoursShouldBeFound("totalHoursToAnswerCalls.equals=" + DEFAULT_TOTAL_HOURS_TO_ANSWER_CALLS);

        // Get all the numberOfHoursList where totalHoursToAnswerCalls equals to UPDATED_TOTAL_HOURS_TO_ANSWER_CALLS
        defaultNumberOfHoursShouldNotBeFound("totalHoursToAnswerCalls.equals=" + UPDATED_TOTAL_HOURS_TO_ANSWER_CALLS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalHoursToAnswerCallsIsInShouldWork() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalHoursToAnswerCalls in DEFAULT_TOTAL_HOURS_TO_ANSWER_CALLS or UPDATED_TOTAL_HOURS_TO_ANSWER_CALLS
        defaultNumberOfHoursShouldBeFound(
            "totalHoursToAnswerCalls.in=" + DEFAULT_TOTAL_HOURS_TO_ANSWER_CALLS + "," + UPDATED_TOTAL_HOURS_TO_ANSWER_CALLS
        );

        // Get all the numberOfHoursList where totalHoursToAnswerCalls equals to UPDATED_TOTAL_HOURS_TO_ANSWER_CALLS
        defaultNumberOfHoursShouldNotBeFound("totalHoursToAnswerCalls.in=" + UPDATED_TOTAL_HOURS_TO_ANSWER_CALLS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalHoursToAnswerCallsIsNullOrNotNull() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalHoursToAnswerCalls is not null
        defaultNumberOfHoursShouldBeFound("totalHoursToAnswerCalls.specified=true");

        // Get all the numberOfHoursList where totalHoursToAnswerCalls is null
        defaultNumberOfHoursShouldNotBeFound("totalHoursToAnswerCalls.specified=false");
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalHoursToAnswerCallsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalHoursToAnswerCalls is greater than or equal to DEFAULT_TOTAL_HOURS_TO_ANSWER_CALLS
        defaultNumberOfHoursShouldBeFound("totalHoursToAnswerCalls.greaterThanOrEqual=" + DEFAULT_TOTAL_HOURS_TO_ANSWER_CALLS);

        // Get all the numberOfHoursList where totalHoursToAnswerCalls is greater than or equal to UPDATED_TOTAL_HOURS_TO_ANSWER_CALLS
        defaultNumberOfHoursShouldNotBeFound("totalHoursToAnswerCalls.greaterThanOrEqual=" + UPDATED_TOTAL_HOURS_TO_ANSWER_CALLS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalHoursToAnswerCallsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalHoursToAnswerCalls is less than or equal to DEFAULT_TOTAL_HOURS_TO_ANSWER_CALLS
        defaultNumberOfHoursShouldBeFound("totalHoursToAnswerCalls.lessThanOrEqual=" + DEFAULT_TOTAL_HOURS_TO_ANSWER_CALLS);

        // Get all the numberOfHoursList where totalHoursToAnswerCalls is less than or equal to SMALLER_TOTAL_HOURS_TO_ANSWER_CALLS
        defaultNumberOfHoursShouldNotBeFound("totalHoursToAnswerCalls.lessThanOrEqual=" + SMALLER_TOTAL_HOURS_TO_ANSWER_CALLS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalHoursToAnswerCallsIsLessThanSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalHoursToAnswerCalls is less than DEFAULT_TOTAL_HOURS_TO_ANSWER_CALLS
        defaultNumberOfHoursShouldNotBeFound("totalHoursToAnswerCalls.lessThan=" + DEFAULT_TOTAL_HOURS_TO_ANSWER_CALLS);

        // Get all the numberOfHoursList where totalHoursToAnswerCalls is less than UPDATED_TOTAL_HOURS_TO_ANSWER_CALLS
        defaultNumberOfHoursShouldBeFound("totalHoursToAnswerCalls.lessThan=" + UPDATED_TOTAL_HOURS_TO_ANSWER_CALLS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalHoursToAnswerCallsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalHoursToAnswerCalls is greater than DEFAULT_TOTAL_HOURS_TO_ANSWER_CALLS
        defaultNumberOfHoursShouldNotBeFound("totalHoursToAnswerCalls.greaterThan=" + DEFAULT_TOTAL_HOURS_TO_ANSWER_CALLS);

        // Get all the numberOfHoursList where totalHoursToAnswerCalls is greater than SMALLER_TOTAL_HOURS_TO_ANSWER_CALLS
        defaultNumberOfHoursShouldBeFound("totalHoursToAnswerCalls.greaterThan=" + SMALLER_TOTAL_HOURS_TO_ANSWER_CALLS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalReceivedCallsIsEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalReceivedCalls equals to DEFAULT_TOTAL_RECEIVED_CALLS
        defaultNumberOfHoursShouldBeFound("totalReceivedCalls.equals=" + DEFAULT_TOTAL_RECEIVED_CALLS);

        // Get all the numberOfHoursList where totalReceivedCalls equals to UPDATED_TOTAL_RECEIVED_CALLS
        defaultNumberOfHoursShouldNotBeFound("totalReceivedCalls.equals=" + UPDATED_TOTAL_RECEIVED_CALLS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalReceivedCallsIsInShouldWork() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalReceivedCalls in DEFAULT_TOTAL_RECEIVED_CALLS or UPDATED_TOTAL_RECEIVED_CALLS
        defaultNumberOfHoursShouldBeFound("totalReceivedCalls.in=" + DEFAULT_TOTAL_RECEIVED_CALLS + "," + UPDATED_TOTAL_RECEIVED_CALLS);

        // Get all the numberOfHoursList where totalReceivedCalls equals to UPDATED_TOTAL_RECEIVED_CALLS
        defaultNumberOfHoursShouldNotBeFound("totalReceivedCalls.in=" + UPDATED_TOTAL_RECEIVED_CALLS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalReceivedCallsIsNullOrNotNull() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalReceivedCalls is not null
        defaultNumberOfHoursShouldBeFound("totalReceivedCalls.specified=true");

        // Get all the numberOfHoursList where totalReceivedCalls is null
        defaultNumberOfHoursShouldNotBeFound("totalReceivedCalls.specified=false");
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalReceivedCallsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalReceivedCalls is greater than or equal to DEFAULT_TOTAL_RECEIVED_CALLS
        defaultNumberOfHoursShouldBeFound("totalReceivedCalls.greaterThanOrEqual=" + DEFAULT_TOTAL_RECEIVED_CALLS);

        // Get all the numberOfHoursList where totalReceivedCalls is greater than or equal to UPDATED_TOTAL_RECEIVED_CALLS
        defaultNumberOfHoursShouldNotBeFound("totalReceivedCalls.greaterThanOrEqual=" + UPDATED_TOTAL_RECEIVED_CALLS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalReceivedCallsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalReceivedCalls is less than or equal to DEFAULT_TOTAL_RECEIVED_CALLS
        defaultNumberOfHoursShouldBeFound("totalReceivedCalls.lessThanOrEqual=" + DEFAULT_TOTAL_RECEIVED_CALLS);

        // Get all the numberOfHoursList where totalReceivedCalls is less than or equal to SMALLER_TOTAL_RECEIVED_CALLS
        defaultNumberOfHoursShouldNotBeFound("totalReceivedCalls.lessThanOrEqual=" + SMALLER_TOTAL_RECEIVED_CALLS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalReceivedCallsIsLessThanSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalReceivedCalls is less than DEFAULT_TOTAL_RECEIVED_CALLS
        defaultNumberOfHoursShouldNotBeFound("totalReceivedCalls.lessThan=" + DEFAULT_TOTAL_RECEIVED_CALLS);

        // Get all the numberOfHoursList where totalReceivedCalls is less than UPDATED_TOTAL_RECEIVED_CALLS
        defaultNumberOfHoursShouldBeFound("totalReceivedCalls.lessThan=" + UPDATED_TOTAL_RECEIVED_CALLS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalReceivedCallsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalReceivedCalls is greater than DEFAULT_TOTAL_RECEIVED_CALLS
        defaultNumberOfHoursShouldNotBeFound("totalReceivedCalls.greaterThan=" + DEFAULT_TOTAL_RECEIVED_CALLS);

        // Get all the numberOfHoursList where totalReceivedCalls is greater than SMALLER_TOTAL_RECEIVED_CALLS
        defaultNumberOfHoursShouldBeFound("totalReceivedCalls.greaterThan=" + SMALLER_TOTAL_RECEIVED_CALLS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalAttendedCallsIsEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalAttendedCalls equals to DEFAULT_TOTAL_ATTENDED_CALLS
        defaultNumberOfHoursShouldBeFound("totalAttendedCalls.equals=" + DEFAULT_TOTAL_ATTENDED_CALLS);

        // Get all the numberOfHoursList where totalAttendedCalls equals to UPDATED_TOTAL_ATTENDED_CALLS
        defaultNumberOfHoursShouldNotBeFound("totalAttendedCalls.equals=" + UPDATED_TOTAL_ATTENDED_CALLS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalAttendedCallsIsInShouldWork() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalAttendedCalls in DEFAULT_TOTAL_ATTENDED_CALLS or UPDATED_TOTAL_ATTENDED_CALLS
        defaultNumberOfHoursShouldBeFound("totalAttendedCalls.in=" + DEFAULT_TOTAL_ATTENDED_CALLS + "," + UPDATED_TOTAL_ATTENDED_CALLS);

        // Get all the numberOfHoursList where totalAttendedCalls equals to UPDATED_TOTAL_ATTENDED_CALLS
        defaultNumberOfHoursShouldNotBeFound("totalAttendedCalls.in=" + UPDATED_TOTAL_ATTENDED_CALLS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalAttendedCallsIsNullOrNotNull() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalAttendedCalls is not null
        defaultNumberOfHoursShouldBeFound("totalAttendedCalls.specified=true");

        // Get all the numberOfHoursList where totalAttendedCalls is null
        defaultNumberOfHoursShouldNotBeFound("totalAttendedCalls.specified=false");
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalAttendedCallsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalAttendedCalls is greater than or equal to DEFAULT_TOTAL_ATTENDED_CALLS
        defaultNumberOfHoursShouldBeFound("totalAttendedCalls.greaterThanOrEqual=" + DEFAULT_TOTAL_ATTENDED_CALLS);

        // Get all the numberOfHoursList where totalAttendedCalls is greater than or equal to UPDATED_TOTAL_ATTENDED_CALLS
        defaultNumberOfHoursShouldNotBeFound("totalAttendedCalls.greaterThanOrEqual=" + UPDATED_TOTAL_ATTENDED_CALLS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalAttendedCallsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalAttendedCalls is less than or equal to DEFAULT_TOTAL_ATTENDED_CALLS
        defaultNumberOfHoursShouldBeFound("totalAttendedCalls.lessThanOrEqual=" + DEFAULT_TOTAL_ATTENDED_CALLS);

        // Get all the numberOfHoursList where totalAttendedCalls is less than or equal to SMALLER_TOTAL_ATTENDED_CALLS
        defaultNumberOfHoursShouldNotBeFound("totalAttendedCalls.lessThanOrEqual=" + SMALLER_TOTAL_ATTENDED_CALLS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalAttendedCallsIsLessThanSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalAttendedCalls is less than DEFAULT_TOTAL_ATTENDED_CALLS
        defaultNumberOfHoursShouldNotBeFound("totalAttendedCalls.lessThan=" + DEFAULT_TOTAL_ATTENDED_CALLS);

        // Get all the numberOfHoursList where totalAttendedCalls is less than UPDATED_TOTAL_ATTENDED_CALLS
        defaultNumberOfHoursShouldBeFound("totalAttendedCalls.lessThan=" + UPDATED_TOTAL_ATTENDED_CALLS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalAttendedCallsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalAttendedCalls is greater than DEFAULT_TOTAL_ATTENDED_CALLS
        defaultNumberOfHoursShouldNotBeFound("totalAttendedCalls.greaterThan=" + DEFAULT_TOTAL_ATTENDED_CALLS);

        // Get all the numberOfHoursList where totalAttendedCalls is greater than SMALLER_TOTAL_ATTENDED_CALLS
        defaultNumberOfHoursShouldBeFound("totalAttendedCalls.greaterThan=" + SMALLER_TOTAL_ATTENDED_CALLS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAttendedCallsPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where attendedCallsPercentage equals to DEFAULT_ATTENDED_CALLS_PERCENTAGE
        defaultNumberOfHoursShouldBeFound("attendedCallsPercentage.equals=" + DEFAULT_ATTENDED_CALLS_PERCENTAGE);

        // Get all the numberOfHoursList where attendedCallsPercentage equals to UPDATED_ATTENDED_CALLS_PERCENTAGE
        defaultNumberOfHoursShouldNotBeFound("attendedCallsPercentage.equals=" + UPDATED_ATTENDED_CALLS_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAttendedCallsPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where attendedCallsPercentage in DEFAULT_ATTENDED_CALLS_PERCENTAGE or UPDATED_ATTENDED_CALLS_PERCENTAGE
        defaultNumberOfHoursShouldBeFound(
            "attendedCallsPercentage.in=" + DEFAULT_ATTENDED_CALLS_PERCENTAGE + "," + UPDATED_ATTENDED_CALLS_PERCENTAGE
        );

        // Get all the numberOfHoursList where attendedCallsPercentage equals to UPDATED_ATTENDED_CALLS_PERCENTAGE
        defaultNumberOfHoursShouldNotBeFound("attendedCallsPercentage.in=" + UPDATED_ATTENDED_CALLS_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAttendedCallsPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where attendedCallsPercentage is not null
        defaultNumberOfHoursShouldBeFound("attendedCallsPercentage.specified=true");

        // Get all the numberOfHoursList where attendedCallsPercentage is null
        defaultNumberOfHoursShouldNotBeFound("attendedCallsPercentage.specified=false");
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAttendedCallsPercentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where attendedCallsPercentage is greater than or equal to DEFAULT_ATTENDED_CALLS_PERCENTAGE
        defaultNumberOfHoursShouldBeFound("attendedCallsPercentage.greaterThanOrEqual=" + DEFAULT_ATTENDED_CALLS_PERCENTAGE);

        // Get all the numberOfHoursList where attendedCallsPercentage is greater than or equal to UPDATED_ATTENDED_CALLS_PERCENTAGE
        defaultNumberOfHoursShouldNotBeFound("attendedCallsPercentage.greaterThanOrEqual=" + UPDATED_ATTENDED_CALLS_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAttendedCallsPercentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where attendedCallsPercentage is less than or equal to DEFAULT_ATTENDED_CALLS_PERCENTAGE
        defaultNumberOfHoursShouldBeFound("attendedCallsPercentage.lessThanOrEqual=" + DEFAULT_ATTENDED_CALLS_PERCENTAGE);

        // Get all the numberOfHoursList where attendedCallsPercentage is less than or equal to SMALLER_ATTENDED_CALLS_PERCENTAGE
        defaultNumberOfHoursShouldNotBeFound("attendedCallsPercentage.lessThanOrEqual=" + SMALLER_ATTENDED_CALLS_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAttendedCallsPercentageIsLessThanSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where attendedCallsPercentage is less than DEFAULT_ATTENDED_CALLS_PERCENTAGE
        defaultNumberOfHoursShouldNotBeFound("attendedCallsPercentage.lessThan=" + DEFAULT_ATTENDED_CALLS_PERCENTAGE);

        // Get all the numberOfHoursList where attendedCallsPercentage is less than UPDATED_ATTENDED_CALLS_PERCENTAGE
        defaultNumberOfHoursShouldBeFound("attendedCallsPercentage.lessThan=" + UPDATED_ATTENDED_CALLS_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAttendedCallsPercentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where attendedCallsPercentage is greater than DEFAULT_ATTENDED_CALLS_PERCENTAGE
        defaultNumberOfHoursShouldNotBeFound("attendedCallsPercentage.greaterThan=" + DEFAULT_ATTENDED_CALLS_PERCENTAGE);

        // Get all the numberOfHoursList where attendedCallsPercentage is greater than SMALLER_ATTENDED_CALLS_PERCENTAGE
        defaultNumberOfHoursShouldBeFound("attendedCallsPercentage.greaterThan=" + SMALLER_ATTENDED_CALLS_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAvgDailyAttendedCallsIsEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where avgDailyAttendedCalls equals to DEFAULT_AVG_DAILY_ATTENDED_CALLS
        defaultNumberOfHoursShouldBeFound("avgDailyAttendedCalls.equals=" + DEFAULT_AVG_DAILY_ATTENDED_CALLS);

        // Get all the numberOfHoursList where avgDailyAttendedCalls equals to UPDATED_AVG_DAILY_ATTENDED_CALLS
        defaultNumberOfHoursShouldNotBeFound("avgDailyAttendedCalls.equals=" + UPDATED_AVG_DAILY_ATTENDED_CALLS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAvgDailyAttendedCallsIsInShouldWork() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where avgDailyAttendedCalls in DEFAULT_AVG_DAILY_ATTENDED_CALLS or UPDATED_AVG_DAILY_ATTENDED_CALLS
        defaultNumberOfHoursShouldBeFound(
            "avgDailyAttendedCalls.in=" + DEFAULT_AVG_DAILY_ATTENDED_CALLS + "," + UPDATED_AVG_DAILY_ATTENDED_CALLS
        );

        // Get all the numberOfHoursList where avgDailyAttendedCalls equals to UPDATED_AVG_DAILY_ATTENDED_CALLS
        defaultNumberOfHoursShouldNotBeFound("avgDailyAttendedCalls.in=" + UPDATED_AVG_DAILY_ATTENDED_CALLS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAvgDailyAttendedCallsIsNullOrNotNull() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where avgDailyAttendedCalls is not null
        defaultNumberOfHoursShouldBeFound("avgDailyAttendedCalls.specified=true");

        // Get all the numberOfHoursList where avgDailyAttendedCalls is null
        defaultNumberOfHoursShouldNotBeFound("avgDailyAttendedCalls.specified=false");
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAvgDailyAttendedCallsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where avgDailyAttendedCalls is greater than or equal to DEFAULT_AVG_DAILY_ATTENDED_CALLS
        defaultNumberOfHoursShouldBeFound("avgDailyAttendedCalls.greaterThanOrEqual=" + DEFAULT_AVG_DAILY_ATTENDED_CALLS);

        // Get all the numberOfHoursList where avgDailyAttendedCalls is greater than or equal to UPDATED_AVG_DAILY_ATTENDED_CALLS
        defaultNumberOfHoursShouldNotBeFound("avgDailyAttendedCalls.greaterThanOrEqual=" + UPDATED_AVG_DAILY_ATTENDED_CALLS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAvgDailyAttendedCallsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where avgDailyAttendedCalls is less than or equal to DEFAULT_AVG_DAILY_ATTENDED_CALLS
        defaultNumberOfHoursShouldBeFound("avgDailyAttendedCalls.lessThanOrEqual=" + DEFAULT_AVG_DAILY_ATTENDED_CALLS);

        // Get all the numberOfHoursList where avgDailyAttendedCalls is less than or equal to SMALLER_AVG_DAILY_ATTENDED_CALLS
        defaultNumberOfHoursShouldNotBeFound("avgDailyAttendedCalls.lessThanOrEqual=" + SMALLER_AVG_DAILY_ATTENDED_CALLS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAvgDailyAttendedCallsIsLessThanSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where avgDailyAttendedCalls is less than DEFAULT_AVG_DAILY_ATTENDED_CALLS
        defaultNumberOfHoursShouldNotBeFound("avgDailyAttendedCalls.lessThan=" + DEFAULT_AVG_DAILY_ATTENDED_CALLS);

        // Get all the numberOfHoursList where avgDailyAttendedCalls is less than UPDATED_AVG_DAILY_ATTENDED_CALLS
        defaultNumberOfHoursShouldBeFound("avgDailyAttendedCalls.lessThan=" + UPDATED_AVG_DAILY_ATTENDED_CALLS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAvgDailyAttendedCallsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where avgDailyAttendedCalls is greater than DEFAULT_AVG_DAILY_ATTENDED_CALLS
        defaultNumberOfHoursShouldNotBeFound("avgDailyAttendedCalls.greaterThan=" + DEFAULT_AVG_DAILY_ATTENDED_CALLS);

        // Get all the numberOfHoursList where avgDailyAttendedCalls is greater than SMALLER_AVG_DAILY_ATTENDED_CALLS
        defaultNumberOfHoursShouldBeFound("avgDailyAttendedCalls.greaterThan=" + SMALLER_AVG_DAILY_ATTENDED_CALLS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAvgDailyAttendedCallsByExternalIsEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where avgDailyAttendedCallsByExternal equals to DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL
        defaultNumberOfHoursShouldBeFound("avgDailyAttendedCallsByExternal.equals=" + DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL);

        // Get all the numberOfHoursList where avgDailyAttendedCallsByExternal equals to UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL
        defaultNumberOfHoursShouldNotBeFound("avgDailyAttendedCallsByExternal.equals=" + UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAvgDailyAttendedCallsByExternalIsInShouldWork() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where avgDailyAttendedCallsByExternal in DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL or UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL
        defaultNumberOfHoursShouldBeFound(
            "avgDailyAttendedCallsByExternal.in=" +
            DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL +
            "," +
            UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL
        );

        // Get all the numberOfHoursList where avgDailyAttendedCallsByExternal equals to UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL
        defaultNumberOfHoursShouldNotBeFound("avgDailyAttendedCallsByExternal.in=" + UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAvgDailyAttendedCallsByExternalIsNullOrNotNull() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where avgDailyAttendedCallsByExternal is not null
        defaultNumberOfHoursShouldBeFound("avgDailyAttendedCallsByExternal.specified=true");

        // Get all the numberOfHoursList where avgDailyAttendedCallsByExternal is null
        defaultNumberOfHoursShouldNotBeFound("avgDailyAttendedCallsByExternal.specified=false");
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAvgDailyAttendedCallsByExternalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where avgDailyAttendedCallsByExternal is greater than or equal to DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL
        defaultNumberOfHoursShouldBeFound(
            "avgDailyAttendedCallsByExternal.greaterThanOrEqual=" + DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL
        );

        // Get all the numberOfHoursList where avgDailyAttendedCallsByExternal is greater than or equal to UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL
        defaultNumberOfHoursShouldNotBeFound(
            "avgDailyAttendedCallsByExternal.greaterThanOrEqual=" + UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL
        );
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAvgDailyAttendedCallsByExternalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where avgDailyAttendedCallsByExternal is less than or equal to DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL
        defaultNumberOfHoursShouldBeFound(
            "avgDailyAttendedCallsByExternal.lessThanOrEqual=" + DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL
        );

        // Get all the numberOfHoursList where avgDailyAttendedCallsByExternal is less than or equal to SMALLER_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL
        defaultNumberOfHoursShouldNotBeFound(
            "avgDailyAttendedCallsByExternal.lessThanOrEqual=" + SMALLER_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL
        );
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAvgDailyAttendedCallsByExternalIsLessThanSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where avgDailyAttendedCallsByExternal is less than DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL
        defaultNumberOfHoursShouldNotBeFound("avgDailyAttendedCallsByExternal.lessThan=" + DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL);

        // Get all the numberOfHoursList where avgDailyAttendedCallsByExternal is less than UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL
        defaultNumberOfHoursShouldBeFound("avgDailyAttendedCallsByExternal.lessThan=" + UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAvgDailyAttendedCallsByExternalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where avgDailyAttendedCallsByExternal is greater than DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL
        defaultNumberOfHoursShouldNotBeFound("avgDailyAttendedCallsByExternal.greaterThan=" + DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL);

        // Get all the numberOfHoursList where avgDailyAttendedCallsByExternal is greater than SMALLER_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL
        defaultNumberOfHoursShouldBeFound("avgDailyAttendedCallsByExternal.greaterThan=" + SMALLER_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAvgDailyAttendedCallsByExternalByDayIsEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where avgDailyAttendedCallsByExternalByDay equals to DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL_BY_DAY
        defaultNumberOfHoursShouldBeFound(
            "avgDailyAttendedCallsByExternalByDay.equals=" + DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL_BY_DAY
        );

        // Get all the numberOfHoursList where avgDailyAttendedCallsByExternalByDay equals to UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL_BY_DAY
        defaultNumberOfHoursShouldNotBeFound(
            "avgDailyAttendedCallsByExternalByDay.equals=" + UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL_BY_DAY
        );
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAvgDailyAttendedCallsByExternalByDayIsInShouldWork() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where avgDailyAttendedCallsByExternalByDay in DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL_BY_DAY or UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL_BY_DAY
        defaultNumberOfHoursShouldBeFound(
            "avgDailyAttendedCallsByExternalByDay.in=" +
            DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL_BY_DAY +
            "," +
            UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL_BY_DAY
        );

        // Get all the numberOfHoursList where avgDailyAttendedCallsByExternalByDay equals to UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL_BY_DAY
        defaultNumberOfHoursShouldNotBeFound(
            "avgDailyAttendedCallsByExternalByDay.in=" + UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL_BY_DAY
        );
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAvgDailyAttendedCallsByExternalByDayIsNullOrNotNull() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where avgDailyAttendedCallsByExternalByDay is not null
        defaultNumberOfHoursShouldBeFound("avgDailyAttendedCallsByExternalByDay.specified=true");

        // Get all the numberOfHoursList where avgDailyAttendedCallsByExternalByDay is null
        defaultNumberOfHoursShouldNotBeFound("avgDailyAttendedCallsByExternalByDay.specified=false");
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAvgDailyAttendedCallsByExternalByDayIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where avgDailyAttendedCallsByExternalByDay is greater than or equal to DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL_BY_DAY
        defaultNumberOfHoursShouldBeFound(
            "avgDailyAttendedCallsByExternalByDay.greaterThanOrEqual=" + DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL_BY_DAY
        );

        // Get all the numberOfHoursList where avgDailyAttendedCallsByExternalByDay is greater than or equal to UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL_BY_DAY
        defaultNumberOfHoursShouldNotBeFound(
            "avgDailyAttendedCallsByExternalByDay.greaterThanOrEqual=" + UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL_BY_DAY
        );
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAvgDailyAttendedCallsByExternalByDayIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where avgDailyAttendedCallsByExternalByDay is less than or equal to DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL_BY_DAY
        defaultNumberOfHoursShouldBeFound(
            "avgDailyAttendedCallsByExternalByDay.lessThanOrEqual=" + DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL_BY_DAY
        );

        // Get all the numberOfHoursList where avgDailyAttendedCallsByExternalByDay is less than or equal to SMALLER_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL_BY_DAY
        defaultNumberOfHoursShouldNotBeFound(
            "avgDailyAttendedCallsByExternalByDay.lessThanOrEqual=" + SMALLER_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL_BY_DAY
        );
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAvgDailyAttendedCallsByExternalByDayIsLessThanSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where avgDailyAttendedCallsByExternalByDay is less than DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL_BY_DAY
        defaultNumberOfHoursShouldNotBeFound(
            "avgDailyAttendedCallsByExternalByDay.lessThan=" + DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL_BY_DAY
        );

        // Get all the numberOfHoursList where avgDailyAttendedCallsByExternalByDay is less than UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL_BY_DAY
        defaultNumberOfHoursShouldBeFound(
            "avgDailyAttendedCallsByExternalByDay.lessThan=" + UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL_BY_DAY
        );
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAvgDailyAttendedCallsByExternalByDayIsGreaterThanSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where avgDailyAttendedCallsByExternalByDay is greater than DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL_BY_DAY
        defaultNumberOfHoursShouldNotBeFound(
            "avgDailyAttendedCallsByExternalByDay.greaterThan=" + DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL_BY_DAY
        );

        // Get all the numberOfHoursList where avgDailyAttendedCallsByExternalByDay is greater than SMALLER_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL_BY_DAY
        defaultNumberOfHoursShouldBeFound(
            "avgDailyAttendedCallsByExternalByDay.greaterThan=" + SMALLER_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL_BY_DAY
        );
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAvgDailyAttendedCallsByInternalIsEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where avgDailyAttendedCallsByInternal equals to DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_INTERNAL
        defaultNumberOfHoursShouldBeFound("avgDailyAttendedCallsByInternal.equals=" + DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_INTERNAL);

        // Get all the numberOfHoursList where avgDailyAttendedCallsByInternal equals to UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_INTERNAL
        defaultNumberOfHoursShouldNotBeFound("avgDailyAttendedCallsByInternal.equals=" + UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_INTERNAL);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAvgDailyAttendedCallsByInternalIsInShouldWork() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where avgDailyAttendedCallsByInternal in DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_INTERNAL or UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_INTERNAL
        defaultNumberOfHoursShouldBeFound(
            "avgDailyAttendedCallsByInternal.in=" +
            DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_INTERNAL +
            "," +
            UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_INTERNAL
        );

        // Get all the numberOfHoursList where avgDailyAttendedCallsByInternal equals to UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_INTERNAL
        defaultNumberOfHoursShouldNotBeFound("avgDailyAttendedCallsByInternal.in=" + UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_INTERNAL);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAvgDailyAttendedCallsByInternalIsNullOrNotNull() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where avgDailyAttendedCallsByInternal is not null
        defaultNumberOfHoursShouldBeFound("avgDailyAttendedCallsByInternal.specified=true");

        // Get all the numberOfHoursList where avgDailyAttendedCallsByInternal is null
        defaultNumberOfHoursShouldNotBeFound("avgDailyAttendedCallsByInternal.specified=false");
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAvgDailyAttendedCallsByInternalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where avgDailyAttendedCallsByInternal is greater than or equal to DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_INTERNAL
        defaultNumberOfHoursShouldBeFound(
            "avgDailyAttendedCallsByInternal.greaterThanOrEqual=" + DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_INTERNAL
        );

        // Get all the numberOfHoursList where avgDailyAttendedCallsByInternal is greater than or equal to UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_INTERNAL
        defaultNumberOfHoursShouldNotBeFound(
            "avgDailyAttendedCallsByInternal.greaterThanOrEqual=" + UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_INTERNAL
        );
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAvgDailyAttendedCallsByInternalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where avgDailyAttendedCallsByInternal is less than or equal to DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_INTERNAL
        defaultNumberOfHoursShouldBeFound(
            "avgDailyAttendedCallsByInternal.lessThanOrEqual=" + DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_INTERNAL
        );

        // Get all the numberOfHoursList where avgDailyAttendedCallsByInternal is less than or equal to SMALLER_AVG_DAILY_ATTENDED_CALLS_BY_INTERNAL
        defaultNumberOfHoursShouldNotBeFound(
            "avgDailyAttendedCallsByInternal.lessThanOrEqual=" + SMALLER_AVG_DAILY_ATTENDED_CALLS_BY_INTERNAL
        );
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAvgDailyAttendedCallsByInternalIsLessThanSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where avgDailyAttendedCallsByInternal is less than DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_INTERNAL
        defaultNumberOfHoursShouldNotBeFound("avgDailyAttendedCallsByInternal.lessThan=" + DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_INTERNAL);

        // Get all the numberOfHoursList where avgDailyAttendedCallsByInternal is less than UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_INTERNAL
        defaultNumberOfHoursShouldBeFound("avgDailyAttendedCallsByInternal.lessThan=" + UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_INTERNAL);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByAvgDailyAttendedCallsByInternalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where avgDailyAttendedCallsByInternal is greater than DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_INTERNAL
        defaultNumberOfHoursShouldNotBeFound("avgDailyAttendedCallsByInternal.greaterThan=" + DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_INTERNAL);

        // Get all the numberOfHoursList where avgDailyAttendedCallsByInternal is greater than SMALLER_AVG_DAILY_ATTENDED_CALLS_BY_INTERNAL
        defaultNumberOfHoursShouldBeFound("avgDailyAttendedCallsByInternal.greaterThan=" + SMALLER_AVG_DAILY_ATTENDED_CALLS_BY_INTERNAL);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalReceivedChatsIsEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalReceivedChats equals to DEFAULT_TOTAL_RECEIVED_CHATS
        defaultNumberOfHoursShouldBeFound("totalReceivedChats.equals=" + DEFAULT_TOTAL_RECEIVED_CHATS);

        // Get all the numberOfHoursList where totalReceivedChats equals to UPDATED_TOTAL_RECEIVED_CHATS
        defaultNumberOfHoursShouldNotBeFound("totalReceivedChats.equals=" + UPDATED_TOTAL_RECEIVED_CHATS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalReceivedChatsIsInShouldWork() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalReceivedChats in DEFAULT_TOTAL_RECEIVED_CHATS or UPDATED_TOTAL_RECEIVED_CHATS
        defaultNumberOfHoursShouldBeFound("totalReceivedChats.in=" + DEFAULT_TOTAL_RECEIVED_CHATS + "," + UPDATED_TOTAL_RECEIVED_CHATS);

        // Get all the numberOfHoursList where totalReceivedChats equals to UPDATED_TOTAL_RECEIVED_CHATS
        defaultNumberOfHoursShouldNotBeFound("totalReceivedChats.in=" + UPDATED_TOTAL_RECEIVED_CHATS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalReceivedChatsIsNullOrNotNull() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalReceivedChats is not null
        defaultNumberOfHoursShouldBeFound("totalReceivedChats.specified=true");

        // Get all the numberOfHoursList where totalReceivedChats is null
        defaultNumberOfHoursShouldNotBeFound("totalReceivedChats.specified=false");
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalReceivedChatsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalReceivedChats is greater than or equal to DEFAULT_TOTAL_RECEIVED_CHATS
        defaultNumberOfHoursShouldBeFound("totalReceivedChats.greaterThanOrEqual=" + DEFAULT_TOTAL_RECEIVED_CHATS);

        // Get all the numberOfHoursList where totalReceivedChats is greater than or equal to UPDATED_TOTAL_RECEIVED_CHATS
        defaultNumberOfHoursShouldNotBeFound("totalReceivedChats.greaterThanOrEqual=" + UPDATED_TOTAL_RECEIVED_CHATS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalReceivedChatsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalReceivedChats is less than or equal to DEFAULT_TOTAL_RECEIVED_CHATS
        defaultNumberOfHoursShouldBeFound("totalReceivedChats.lessThanOrEqual=" + DEFAULT_TOTAL_RECEIVED_CHATS);

        // Get all the numberOfHoursList where totalReceivedChats is less than or equal to SMALLER_TOTAL_RECEIVED_CHATS
        defaultNumberOfHoursShouldNotBeFound("totalReceivedChats.lessThanOrEqual=" + SMALLER_TOTAL_RECEIVED_CHATS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalReceivedChatsIsLessThanSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalReceivedChats is less than DEFAULT_TOTAL_RECEIVED_CHATS
        defaultNumberOfHoursShouldNotBeFound("totalReceivedChats.lessThan=" + DEFAULT_TOTAL_RECEIVED_CHATS);

        // Get all the numberOfHoursList where totalReceivedChats is less than UPDATED_TOTAL_RECEIVED_CHATS
        defaultNumberOfHoursShouldBeFound("totalReceivedChats.lessThan=" + UPDATED_TOTAL_RECEIVED_CHATS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalReceivedChatsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalReceivedChats is greater than DEFAULT_TOTAL_RECEIVED_CHATS
        defaultNumberOfHoursShouldNotBeFound("totalReceivedChats.greaterThan=" + DEFAULT_TOTAL_RECEIVED_CHATS);

        // Get all the numberOfHoursList where totalReceivedChats is greater than SMALLER_TOTAL_RECEIVED_CHATS
        defaultNumberOfHoursShouldBeFound("totalReceivedChats.greaterThan=" + SMALLER_TOTAL_RECEIVED_CHATS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalAttendedChatsIsEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalAttendedChats equals to DEFAULT_TOTAL_ATTENDED_CHATS
        defaultNumberOfHoursShouldBeFound("totalAttendedChats.equals=" + DEFAULT_TOTAL_ATTENDED_CHATS);

        // Get all the numberOfHoursList where totalAttendedChats equals to UPDATED_TOTAL_ATTENDED_CHATS
        defaultNumberOfHoursShouldNotBeFound("totalAttendedChats.equals=" + UPDATED_TOTAL_ATTENDED_CHATS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalAttendedChatsIsInShouldWork() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalAttendedChats in DEFAULT_TOTAL_ATTENDED_CHATS or UPDATED_TOTAL_ATTENDED_CHATS
        defaultNumberOfHoursShouldBeFound("totalAttendedChats.in=" + DEFAULT_TOTAL_ATTENDED_CHATS + "," + UPDATED_TOTAL_ATTENDED_CHATS);

        // Get all the numberOfHoursList where totalAttendedChats equals to UPDATED_TOTAL_ATTENDED_CHATS
        defaultNumberOfHoursShouldNotBeFound("totalAttendedChats.in=" + UPDATED_TOTAL_ATTENDED_CHATS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalAttendedChatsIsNullOrNotNull() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalAttendedChats is not null
        defaultNumberOfHoursShouldBeFound("totalAttendedChats.specified=true");

        // Get all the numberOfHoursList where totalAttendedChats is null
        defaultNumberOfHoursShouldNotBeFound("totalAttendedChats.specified=false");
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalAttendedChatsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalAttendedChats is greater than or equal to DEFAULT_TOTAL_ATTENDED_CHATS
        defaultNumberOfHoursShouldBeFound("totalAttendedChats.greaterThanOrEqual=" + DEFAULT_TOTAL_ATTENDED_CHATS);

        // Get all the numberOfHoursList where totalAttendedChats is greater than or equal to UPDATED_TOTAL_ATTENDED_CHATS
        defaultNumberOfHoursShouldNotBeFound("totalAttendedChats.greaterThanOrEqual=" + UPDATED_TOTAL_ATTENDED_CHATS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalAttendedChatsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalAttendedChats is less than or equal to DEFAULT_TOTAL_ATTENDED_CHATS
        defaultNumberOfHoursShouldBeFound("totalAttendedChats.lessThanOrEqual=" + DEFAULT_TOTAL_ATTENDED_CHATS);

        // Get all the numberOfHoursList where totalAttendedChats is less than or equal to SMALLER_TOTAL_ATTENDED_CHATS
        defaultNumberOfHoursShouldNotBeFound("totalAttendedChats.lessThanOrEqual=" + SMALLER_TOTAL_ATTENDED_CHATS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalAttendedChatsIsLessThanSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalAttendedChats is less than DEFAULT_TOTAL_ATTENDED_CHATS
        defaultNumberOfHoursShouldNotBeFound("totalAttendedChats.lessThan=" + DEFAULT_TOTAL_ATTENDED_CHATS);

        // Get all the numberOfHoursList where totalAttendedChats is less than UPDATED_TOTAL_ATTENDED_CHATS
        defaultNumberOfHoursShouldBeFound("totalAttendedChats.lessThan=" + UPDATED_TOTAL_ATTENDED_CHATS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalAttendedChatsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalAttendedChats is greater than DEFAULT_TOTAL_ATTENDED_CHATS
        defaultNumberOfHoursShouldNotBeFound("totalAttendedChats.greaterThan=" + DEFAULT_TOTAL_ATTENDED_CHATS);

        // Get all the numberOfHoursList where totalAttendedChats is greater than SMALLER_TOTAL_ATTENDED_CHATS
        defaultNumberOfHoursShouldBeFound("totalAttendedChats.greaterThan=" + SMALLER_TOTAL_ATTENDED_CHATS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalReceivedMailsIsEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalReceivedMails equals to DEFAULT_TOTAL_RECEIVED_MAILS
        defaultNumberOfHoursShouldBeFound("totalReceivedMails.equals=" + DEFAULT_TOTAL_RECEIVED_MAILS);

        // Get all the numberOfHoursList where totalReceivedMails equals to UPDATED_TOTAL_RECEIVED_MAILS
        defaultNumberOfHoursShouldNotBeFound("totalReceivedMails.equals=" + UPDATED_TOTAL_RECEIVED_MAILS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalReceivedMailsIsInShouldWork() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalReceivedMails in DEFAULT_TOTAL_RECEIVED_MAILS or UPDATED_TOTAL_RECEIVED_MAILS
        defaultNumberOfHoursShouldBeFound("totalReceivedMails.in=" + DEFAULT_TOTAL_RECEIVED_MAILS + "," + UPDATED_TOTAL_RECEIVED_MAILS);

        // Get all the numberOfHoursList where totalReceivedMails equals to UPDATED_TOTAL_RECEIVED_MAILS
        defaultNumberOfHoursShouldNotBeFound("totalReceivedMails.in=" + UPDATED_TOTAL_RECEIVED_MAILS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalReceivedMailsIsNullOrNotNull() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalReceivedMails is not null
        defaultNumberOfHoursShouldBeFound("totalReceivedMails.specified=true");

        // Get all the numberOfHoursList where totalReceivedMails is null
        defaultNumberOfHoursShouldNotBeFound("totalReceivedMails.specified=false");
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalReceivedMailsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalReceivedMails is greater than or equal to DEFAULT_TOTAL_RECEIVED_MAILS
        defaultNumberOfHoursShouldBeFound("totalReceivedMails.greaterThanOrEqual=" + DEFAULT_TOTAL_RECEIVED_MAILS);

        // Get all the numberOfHoursList where totalReceivedMails is greater than or equal to UPDATED_TOTAL_RECEIVED_MAILS
        defaultNumberOfHoursShouldNotBeFound("totalReceivedMails.greaterThanOrEqual=" + UPDATED_TOTAL_RECEIVED_MAILS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalReceivedMailsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalReceivedMails is less than or equal to DEFAULT_TOTAL_RECEIVED_MAILS
        defaultNumberOfHoursShouldBeFound("totalReceivedMails.lessThanOrEqual=" + DEFAULT_TOTAL_RECEIVED_MAILS);

        // Get all the numberOfHoursList where totalReceivedMails is less than or equal to SMALLER_TOTAL_RECEIVED_MAILS
        defaultNumberOfHoursShouldNotBeFound("totalReceivedMails.lessThanOrEqual=" + SMALLER_TOTAL_RECEIVED_MAILS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalReceivedMailsIsLessThanSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalReceivedMails is less than DEFAULT_TOTAL_RECEIVED_MAILS
        defaultNumberOfHoursShouldNotBeFound("totalReceivedMails.lessThan=" + DEFAULT_TOTAL_RECEIVED_MAILS);

        // Get all the numberOfHoursList where totalReceivedMails is less than UPDATED_TOTAL_RECEIVED_MAILS
        defaultNumberOfHoursShouldBeFound("totalReceivedMails.lessThan=" + UPDATED_TOTAL_RECEIVED_MAILS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalReceivedMailsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalReceivedMails is greater than DEFAULT_TOTAL_RECEIVED_MAILS
        defaultNumberOfHoursShouldNotBeFound("totalReceivedMails.greaterThan=" + DEFAULT_TOTAL_RECEIVED_MAILS);

        // Get all the numberOfHoursList where totalReceivedMails is greater than SMALLER_TOTAL_RECEIVED_MAILS
        defaultNumberOfHoursShouldBeFound("totalReceivedMails.greaterThan=" + SMALLER_TOTAL_RECEIVED_MAILS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalAttendedMailsIsEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalAttendedMails equals to DEFAULT_TOTAL_ATTENDED_MAILS
        defaultNumberOfHoursShouldBeFound("totalAttendedMails.equals=" + DEFAULT_TOTAL_ATTENDED_MAILS);

        // Get all the numberOfHoursList where totalAttendedMails equals to UPDATED_TOTAL_ATTENDED_MAILS
        defaultNumberOfHoursShouldNotBeFound("totalAttendedMails.equals=" + UPDATED_TOTAL_ATTENDED_MAILS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalAttendedMailsIsInShouldWork() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalAttendedMails in DEFAULT_TOTAL_ATTENDED_MAILS or UPDATED_TOTAL_ATTENDED_MAILS
        defaultNumberOfHoursShouldBeFound("totalAttendedMails.in=" + DEFAULT_TOTAL_ATTENDED_MAILS + "," + UPDATED_TOTAL_ATTENDED_MAILS);

        // Get all the numberOfHoursList where totalAttendedMails equals to UPDATED_TOTAL_ATTENDED_MAILS
        defaultNumberOfHoursShouldNotBeFound("totalAttendedMails.in=" + UPDATED_TOTAL_ATTENDED_MAILS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalAttendedMailsIsNullOrNotNull() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalAttendedMails is not null
        defaultNumberOfHoursShouldBeFound("totalAttendedMails.specified=true");

        // Get all the numberOfHoursList where totalAttendedMails is null
        defaultNumberOfHoursShouldNotBeFound("totalAttendedMails.specified=false");
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalAttendedMailsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalAttendedMails is greater than or equal to DEFAULT_TOTAL_ATTENDED_MAILS
        defaultNumberOfHoursShouldBeFound("totalAttendedMails.greaterThanOrEqual=" + DEFAULT_TOTAL_ATTENDED_MAILS);

        // Get all the numberOfHoursList where totalAttendedMails is greater than or equal to UPDATED_TOTAL_ATTENDED_MAILS
        defaultNumberOfHoursShouldNotBeFound("totalAttendedMails.greaterThanOrEqual=" + UPDATED_TOTAL_ATTENDED_MAILS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalAttendedMailsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalAttendedMails is less than or equal to DEFAULT_TOTAL_ATTENDED_MAILS
        defaultNumberOfHoursShouldBeFound("totalAttendedMails.lessThanOrEqual=" + DEFAULT_TOTAL_ATTENDED_MAILS);

        // Get all the numberOfHoursList where totalAttendedMails is less than or equal to SMALLER_TOTAL_ATTENDED_MAILS
        defaultNumberOfHoursShouldNotBeFound("totalAttendedMails.lessThanOrEqual=" + SMALLER_TOTAL_ATTENDED_MAILS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalAttendedMailsIsLessThanSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalAttendedMails is less than DEFAULT_TOTAL_ATTENDED_MAILS
        defaultNumberOfHoursShouldNotBeFound("totalAttendedMails.lessThan=" + DEFAULT_TOTAL_ATTENDED_MAILS);

        // Get all the numberOfHoursList where totalAttendedMails is less than UPDATED_TOTAL_ATTENDED_MAILS
        defaultNumberOfHoursShouldBeFound("totalAttendedMails.lessThan=" + UPDATED_TOTAL_ATTENDED_MAILS);
    }

    @Test
    @Transactional
    void getAllNumberOfHoursByTotalAttendedMailsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        // Get all the numberOfHoursList where totalAttendedMails is greater than DEFAULT_TOTAL_ATTENDED_MAILS
        defaultNumberOfHoursShouldNotBeFound("totalAttendedMails.greaterThan=" + DEFAULT_TOTAL_ATTENDED_MAILS);

        // Get all the numberOfHoursList where totalAttendedMails is greater than SMALLER_TOTAL_ATTENDED_MAILS
        defaultNumberOfHoursShouldBeFound("totalAttendedMails.greaterThan=" + SMALLER_TOTAL_ATTENDED_MAILS);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNumberOfHoursShouldBeFound(String filter) throws Exception {
        restNumberOfHoursMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(numberOfHours.getId().intValue())))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH)))
            .andExpect(jsonPath("$.[*].externalAgentDailyHoursAvg").value(hasItem(DEFAULT_EXTERNAL_AGENT_DAILY_HOURS_AVG.doubleValue())))
            .andExpect(jsonPath("$.[*].dailyHourAvg").value(hasItem(DEFAULT_DAILY_HOUR_AVG.doubleValue())))
            .andExpect(jsonPath("$.[*].avgHoursToAnswerCalls").value(hasItem(DEFAULT_AVG_HOURS_TO_ANSWER_CALLS)))
            .andExpect(jsonPath("$.[*].totalHoursToAnswerCalls").value(hasItem(DEFAULT_TOTAL_HOURS_TO_ANSWER_CALLS.doubleValue())))
            .andExpect(jsonPath("$.[*].totalReceivedCalls").value(hasItem(DEFAULT_TOTAL_RECEIVED_CALLS)))
            .andExpect(jsonPath("$.[*].totalAttendedCalls").value(hasItem(DEFAULT_TOTAL_ATTENDED_CALLS)))
            .andExpect(jsonPath("$.[*].attendedCallsPercentage").value(hasItem(DEFAULT_ATTENDED_CALLS_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].avgDailyAttendedCalls").value(hasItem(DEFAULT_AVG_DAILY_ATTENDED_CALLS.doubleValue())))
            .andExpect(
                jsonPath("$.[*].avgDailyAttendedCallsByExternal").value(hasItem(DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL.doubleValue()))
            )
            .andExpect(
                jsonPath("$.[*].avgDailyAttendedCallsByExternalByDay")
                    .value(hasItem(DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL_BY_DAY.doubleValue()))
            )
            .andExpect(
                jsonPath("$.[*].avgDailyAttendedCallsByInternal").value(hasItem(DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_INTERNAL.doubleValue()))
            )
            .andExpect(jsonPath("$.[*].totalReceivedChats").value(hasItem(DEFAULT_TOTAL_RECEIVED_CHATS)))
            .andExpect(jsonPath("$.[*].totalAttendedChats").value(hasItem(DEFAULT_TOTAL_ATTENDED_CHATS)))
            .andExpect(jsonPath("$.[*].totalReceivedMails").value(hasItem(DEFAULT_TOTAL_RECEIVED_MAILS)))
            .andExpect(jsonPath("$.[*].totalAttendedMails").value(hasItem(DEFAULT_TOTAL_ATTENDED_MAILS)));

        // Check, that the count call also returns 1
        restNumberOfHoursMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNumberOfHoursShouldNotBeFound(String filter) throws Exception {
        restNumberOfHoursMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNumberOfHoursMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNumberOfHours() throws Exception {
        // Get the numberOfHours
        restNumberOfHoursMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNumberOfHours() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        int databaseSizeBeforeUpdate = numberOfHoursRepository.findAll().size();

        // Update the numberOfHours
        NumberOfHours updatedNumberOfHours = numberOfHoursRepository.findById(numberOfHours.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedNumberOfHours are not directly saved in db
        em.detach(updatedNumberOfHours);
        updatedNumberOfHours
            .month(UPDATED_MONTH)
            .externalAgentDailyHoursAvg(UPDATED_EXTERNAL_AGENT_DAILY_HOURS_AVG)
            .dailyHourAvg(UPDATED_DAILY_HOUR_AVG)
            .avgHoursToAnswerCalls(UPDATED_AVG_HOURS_TO_ANSWER_CALLS)
            .totalHoursToAnswerCalls(UPDATED_TOTAL_HOURS_TO_ANSWER_CALLS)
            .totalReceivedCalls(UPDATED_TOTAL_RECEIVED_CALLS)
            .totalAttendedCalls(UPDATED_TOTAL_ATTENDED_CALLS)
            .attendedCallsPercentage(UPDATED_ATTENDED_CALLS_PERCENTAGE)
            .avgDailyAttendedCalls(UPDATED_AVG_DAILY_ATTENDED_CALLS)
            .avgDailyAttendedCallsByExternal(UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL)
            .avgDailyAttendedCallsByExternalByDay(UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL_BY_DAY)
            .avgDailyAttendedCallsByInternal(UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_INTERNAL)
            .totalReceivedChats(UPDATED_TOTAL_RECEIVED_CHATS)
            .totalAttendedChats(UPDATED_TOTAL_ATTENDED_CHATS)
            .totalReceivedMails(UPDATED_TOTAL_RECEIVED_MAILS)
            .totalAttendedMails(UPDATED_TOTAL_ATTENDED_MAILS);
        NumberOfHoursDTO numberOfHoursDTO = numberOfHoursMapper.toDto(updatedNumberOfHours);

        restNumberOfHoursMockMvc
            .perform(
                put(ENTITY_API_URL_ID, numberOfHoursDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(numberOfHoursDTO))
            )
            .andExpect(status().isOk());

        // Validate the NumberOfHours in the database
        List<NumberOfHours> numberOfHoursList = numberOfHoursRepository.findAll();
        assertThat(numberOfHoursList).hasSize(databaseSizeBeforeUpdate);
        NumberOfHours testNumberOfHours = numberOfHoursList.get(numberOfHoursList.size() - 1);
        assertThat(testNumberOfHours.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testNumberOfHours.getExternalAgentDailyHoursAvg()).isEqualTo(UPDATED_EXTERNAL_AGENT_DAILY_HOURS_AVG);
        assertThat(testNumberOfHours.getDailyHourAvg()).isEqualTo(UPDATED_DAILY_HOUR_AVG);
        assertThat(testNumberOfHours.getAvgHoursToAnswerCalls()).isEqualTo(UPDATED_AVG_HOURS_TO_ANSWER_CALLS);
        assertThat(testNumberOfHours.getTotalHoursToAnswerCalls()).isEqualTo(UPDATED_TOTAL_HOURS_TO_ANSWER_CALLS);
        assertThat(testNumberOfHours.getTotalReceivedCalls()).isEqualTo(UPDATED_TOTAL_RECEIVED_CALLS);
        assertThat(testNumberOfHours.getTotalAttendedCalls()).isEqualTo(UPDATED_TOTAL_ATTENDED_CALLS);
        assertThat(testNumberOfHours.getAttendedCallsPercentage()).isEqualTo(UPDATED_ATTENDED_CALLS_PERCENTAGE);
        assertThat(testNumberOfHours.getAvgDailyAttendedCalls()).isEqualTo(UPDATED_AVG_DAILY_ATTENDED_CALLS);
        assertThat(testNumberOfHours.getAvgDailyAttendedCallsByExternal()).isEqualTo(UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL);
        assertThat(testNumberOfHours.getAvgDailyAttendedCallsByExternalByDay())
            .isEqualTo(UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL_BY_DAY);
        assertThat(testNumberOfHours.getAvgDailyAttendedCallsByInternal()).isEqualTo(UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_INTERNAL);
        assertThat(testNumberOfHours.getTotalReceivedChats()).isEqualTo(UPDATED_TOTAL_RECEIVED_CHATS);
        assertThat(testNumberOfHours.getTotalAttendedChats()).isEqualTo(UPDATED_TOTAL_ATTENDED_CHATS);
        assertThat(testNumberOfHours.getTotalReceivedMails()).isEqualTo(UPDATED_TOTAL_RECEIVED_MAILS);
        assertThat(testNumberOfHours.getTotalAttendedMails()).isEqualTo(UPDATED_TOTAL_ATTENDED_MAILS);
    }

    @Test
    @Transactional
    void putNonExistingNumberOfHours() throws Exception {
        int databaseSizeBeforeUpdate = numberOfHoursRepository.findAll().size();
        numberOfHours.setId(count.incrementAndGet());

        // Create the NumberOfHours
        NumberOfHoursDTO numberOfHoursDTO = numberOfHoursMapper.toDto(numberOfHours);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNumberOfHoursMockMvc
            .perform(
                put(ENTITY_API_URL_ID, numberOfHoursDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(numberOfHoursDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NumberOfHours in the database
        List<NumberOfHours> numberOfHoursList = numberOfHoursRepository.findAll();
        assertThat(numberOfHoursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNumberOfHours() throws Exception {
        int databaseSizeBeforeUpdate = numberOfHoursRepository.findAll().size();
        numberOfHours.setId(count.incrementAndGet());

        // Create the NumberOfHours
        NumberOfHoursDTO numberOfHoursDTO = numberOfHoursMapper.toDto(numberOfHours);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNumberOfHoursMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(numberOfHoursDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NumberOfHours in the database
        List<NumberOfHours> numberOfHoursList = numberOfHoursRepository.findAll();
        assertThat(numberOfHoursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNumberOfHours() throws Exception {
        int databaseSizeBeforeUpdate = numberOfHoursRepository.findAll().size();
        numberOfHours.setId(count.incrementAndGet());

        // Create the NumberOfHours
        NumberOfHoursDTO numberOfHoursDTO = numberOfHoursMapper.toDto(numberOfHours);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNumberOfHoursMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(numberOfHoursDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NumberOfHours in the database
        List<NumberOfHours> numberOfHoursList = numberOfHoursRepository.findAll();
        assertThat(numberOfHoursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNumberOfHoursWithPatch() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        int databaseSizeBeforeUpdate = numberOfHoursRepository.findAll().size();

        // Update the numberOfHours using partial update
        NumberOfHours partialUpdatedNumberOfHours = new NumberOfHours();
        partialUpdatedNumberOfHours.setId(numberOfHours.getId());

        partialUpdatedNumberOfHours
            .externalAgentDailyHoursAvg(UPDATED_EXTERNAL_AGENT_DAILY_HOURS_AVG)
            .dailyHourAvg(UPDATED_DAILY_HOUR_AVG)
            .avgHoursToAnswerCalls(UPDATED_AVG_HOURS_TO_ANSWER_CALLS)
            .attendedCallsPercentage(UPDATED_ATTENDED_CALLS_PERCENTAGE)
            .avgDailyAttendedCallsByExternal(UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL)
            .totalReceivedChats(UPDATED_TOTAL_RECEIVED_CHATS)
            .totalAttendedMails(UPDATED_TOTAL_ATTENDED_MAILS);

        restNumberOfHoursMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNumberOfHours.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNumberOfHours))
            )
            .andExpect(status().isOk());

        // Validate the NumberOfHours in the database
        List<NumberOfHours> numberOfHoursList = numberOfHoursRepository.findAll();
        assertThat(numberOfHoursList).hasSize(databaseSizeBeforeUpdate);
        NumberOfHours testNumberOfHours = numberOfHoursList.get(numberOfHoursList.size() - 1);
        assertThat(testNumberOfHours.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testNumberOfHours.getExternalAgentDailyHoursAvg()).isEqualTo(UPDATED_EXTERNAL_AGENT_DAILY_HOURS_AVG);
        assertThat(testNumberOfHours.getDailyHourAvg()).isEqualTo(UPDATED_DAILY_HOUR_AVG);
        assertThat(testNumberOfHours.getAvgHoursToAnswerCalls()).isEqualTo(UPDATED_AVG_HOURS_TO_ANSWER_CALLS);
        assertThat(testNumberOfHours.getTotalHoursToAnswerCalls()).isEqualTo(DEFAULT_TOTAL_HOURS_TO_ANSWER_CALLS);
        assertThat(testNumberOfHours.getTotalReceivedCalls()).isEqualTo(DEFAULT_TOTAL_RECEIVED_CALLS);
        assertThat(testNumberOfHours.getTotalAttendedCalls()).isEqualTo(DEFAULT_TOTAL_ATTENDED_CALLS);
        assertThat(testNumberOfHours.getAttendedCallsPercentage()).isEqualTo(UPDATED_ATTENDED_CALLS_PERCENTAGE);
        assertThat(testNumberOfHours.getAvgDailyAttendedCalls()).isEqualTo(DEFAULT_AVG_DAILY_ATTENDED_CALLS);
        assertThat(testNumberOfHours.getAvgDailyAttendedCallsByExternal()).isEqualTo(UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL);
        assertThat(testNumberOfHours.getAvgDailyAttendedCallsByExternalByDay())
            .isEqualTo(DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL_BY_DAY);
        assertThat(testNumberOfHours.getAvgDailyAttendedCallsByInternal()).isEqualTo(DEFAULT_AVG_DAILY_ATTENDED_CALLS_BY_INTERNAL);
        assertThat(testNumberOfHours.getTotalReceivedChats()).isEqualTo(UPDATED_TOTAL_RECEIVED_CHATS);
        assertThat(testNumberOfHours.getTotalAttendedChats()).isEqualTo(DEFAULT_TOTAL_ATTENDED_CHATS);
        assertThat(testNumberOfHours.getTotalReceivedMails()).isEqualTo(DEFAULT_TOTAL_RECEIVED_MAILS);
        assertThat(testNumberOfHours.getTotalAttendedMails()).isEqualTo(UPDATED_TOTAL_ATTENDED_MAILS);
    }

    @Test
    @Transactional
    void fullUpdateNumberOfHoursWithPatch() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        int databaseSizeBeforeUpdate = numberOfHoursRepository.findAll().size();

        // Update the numberOfHours using partial update
        NumberOfHours partialUpdatedNumberOfHours = new NumberOfHours();
        partialUpdatedNumberOfHours.setId(numberOfHours.getId());

        partialUpdatedNumberOfHours
            .month(UPDATED_MONTH)
            .externalAgentDailyHoursAvg(UPDATED_EXTERNAL_AGENT_DAILY_HOURS_AVG)
            .dailyHourAvg(UPDATED_DAILY_HOUR_AVG)
            .avgHoursToAnswerCalls(UPDATED_AVG_HOURS_TO_ANSWER_CALLS)
            .totalHoursToAnswerCalls(UPDATED_TOTAL_HOURS_TO_ANSWER_CALLS)
            .totalReceivedCalls(UPDATED_TOTAL_RECEIVED_CALLS)
            .totalAttendedCalls(UPDATED_TOTAL_ATTENDED_CALLS)
            .attendedCallsPercentage(UPDATED_ATTENDED_CALLS_PERCENTAGE)
            .avgDailyAttendedCalls(UPDATED_AVG_DAILY_ATTENDED_CALLS)
            .avgDailyAttendedCallsByExternal(UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL)
            .avgDailyAttendedCallsByExternalByDay(UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL_BY_DAY)
            .avgDailyAttendedCallsByInternal(UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_INTERNAL)
            .totalReceivedChats(UPDATED_TOTAL_RECEIVED_CHATS)
            .totalAttendedChats(UPDATED_TOTAL_ATTENDED_CHATS)
            .totalReceivedMails(UPDATED_TOTAL_RECEIVED_MAILS)
            .totalAttendedMails(UPDATED_TOTAL_ATTENDED_MAILS);

        restNumberOfHoursMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNumberOfHours.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNumberOfHours))
            )
            .andExpect(status().isOk());

        // Validate the NumberOfHours in the database
        List<NumberOfHours> numberOfHoursList = numberOfHoursRepository.findAll();
        assertThat(numberOfHoursList).hasSize(databaseSizeBeforeUpdate);
        NumberOfHours testNumberOfHours = numberOfHoursList.get(numberOfHoursList.size() - 1);
        assertThat(testNumberOfHours.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testNumberOfHours.getExternalAgentDailyHoursAvg()).isEqualTo(UPDATED_EXTERNAL_AGENT_DAILY_HOURS_AVG);
        assertThat(testNumberOfHours.getDailyHourAvg()).isEqualTo(UPDATED_DAILY_HOUR_AVG);
        assertThat(testNumberOfHours.getAvgHoursToAnswerCalls()).isEqualTo(UPDATED_AVG_HOURS_TO_ANSWER_CALLS);
        assertThat(testNumberOfHours.getTotalHoursToAnswerCalls()).isEqualTo(UPDATED_TOTAL_HOURS_TO_ANSWER_CALLS);
        assertThat(testNumberOfHours.getTotalReceivedCalls()).isEqualTo(UPDATED_TOTAL_RECEIVED_CALLS);
        assertThat(testNumberOfHours.getTotalAttendedCalls()).isEqualTo(UPDATED_TOTAL_ATTENDED_CALLS);
        assertThat(testNumberOfHours.getAttendedCallsPercentage()).isEqualTo(UPDATED_ATTENDED_CALLS_PERCENTAGE);
        assertThat(testNumberOfHours.getAvgDailyAttendedCalls()).isEqualTo(UPDATED_AVG_DAILY_ATTENDED_CALLS);
        assertThat(testNumberOfHours.getAvgDailyAttendedCallsByExternal()).isEqualTo(UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL);
        assertThat(testNumberOfHours.getAvgDailyAttendedCallsByExternalByDay())
            .isEqualTo(UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_EXTERNAL_BY_DAY);
        assertThat(testNumberOfHours.getAvgDailyAttendedCallsByInternal()).isEqualTo(UPDATED_AVG_DAILY_ATTENDED_CALLS_BY_INTERNAL);
        assertThat(testNumberOfHours.getTotalReceivedChats()).isEqualTo(UPDATED_TOTAL_RECEIVED_CHATS);
        assertThat(testNumberOfHours.getTotalAttendedChats()).isEqualTo(UPDATED_TOTAL_ATTENDED_CHATS);
        assertThat(testNumberOfHours.getTotalReceivedMails()).isEqualTo(UPDATED_TOTAL_RECEIVED_MAILS);
        assertThat(testNumberOfHours.getTotalAttendedMails()).isEqualTo(UPDATED_TOTAL_ATTENDED_MAILS);
    }

    @Test
    @Transactional
    void patchNonExistingNumberOfHours() throws Exception {
        int databaseSizeBeforeUpdate = numberOfHoursRepository.findAll().size();
        numberOfHours.setId(count.incrementAndGet());

        // Create the NumberOfHours
        NumberOfHoursDTO numberOfHoursDTO = numberOfHoursMapper.toDto(numberOfHours);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNumberOfHoursMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, numberOfHoursDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(numberOfHoursDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NumberOfHours in the database
        List<NumberOfHours> numberOfHoursList = numberOfHoursRepository.findAll();
        assertThat(numberOfHoursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNumberOfHours() throws Exception {
        int databaseSizeBeforeUpdate = numberOfHoursRepository.findAll().size();
        numberOfHours.setId(count.incrementAndGet());

        // Create the NumberOfHours
        NumberOfHoursDTO numberOfHoursDTO = numberOfHoursMapper.toDto(numberOfHours);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNumberOfHoursMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(numberOfHoursDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NumberOfHours in the database
        List<NumberOfHours> numberOfHoursList = numberOfHoursRepository.findAll();
        assertThat(numberOfHoursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNumberOfHours() throws Exception {
        int databaseSizeBeforeUpdate = numberOfHoursRepository.findAll().size();
        numberOfHours.setId(count.incrementAndGet());

        // Create the NumberOfHours
        NumberOfHoursDTO numberOfHoursDTO = numberOfHoursMapper.toDto(numberOfHours);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNumberOfHoursMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(numberOfHoursDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NumberOfHours in the database
        List<NumberOfHours> numberOfHoursList = numberOfHoursRepository.findAll();
        assertThat(numberOfHoursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNumberOfHours() throws Exception {
        // Initialize the database
        numberOfHoursRepository.saveAndFlush(numberOfHours);

        int databaseSizeBeforeDelete = numberOfHoursRepository.findAll().size();

        // Delete the numberOfHours
        restNumberOfHoursMockMvc
            .perform(delete(ENTITY_API_URL_ID, numberOfHours.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NumberOfHours> numberOfHoursList = numberOfHoursRepository.findAll();
        assertThat(numberOfHoursList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
