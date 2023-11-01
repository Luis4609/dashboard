package com.dashboard.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dashboard.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NumberOfHoursDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NumberOfHoursDTO.class);
        NumberOfHoursDTO numberOfHoursDTO1 = new NumberOfHoursDTO();
        numberOfHoursDTO1.setId(1L);
        NumberOfHoursDTO numberOfHoursDTO2 = new NumberOfHoursDTO();
        assertThat(numberOfHoursDTO1).isNotEqualTo(numberOfHoursDTO2);
        numberOfHoursDTO2.setId(numberOfHoursDTO1.getId());
        assertThat(numberOfHoursDTO1).isEqualTo(numberOfHoursDTO2);
        numberOfHoursDTO2.setId(2L);
        assertThat(numberOfHoursDTO1).isNotEqualTo(numberOfHoursDTO2);
        numberOfHoursDTO1.setId(null);
        assertThat(numberOfHoursDTO1).isNotEqualTo(numberOfHoursDTO2);
    }
}
