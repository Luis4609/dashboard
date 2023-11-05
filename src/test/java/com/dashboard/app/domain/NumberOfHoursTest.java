package com.dashboard.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dashboard.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NumberOfHoursTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NumberOfHours.class);
        NumberOfHours numberOfHours1 = new NumberOfHours();
        numberOfHours1.setId(1L);
        NumberOfHours numberOfHours2 = new NumberOfHours();
        numberOfHours2.setId(numberOfHours1.getId());
        assertThat(numberOfHours1).isEqualTo(numberOfHours2);
        numberOfHours2.setId(2L);
        assertThat(numberOfHours1).isNotEqualTo(numberOfHours2);
        numberOfHours1.setId(null);
        assertThat(numberOfHours1).isNotEqualTo(numberOfHours2);
    }
}
