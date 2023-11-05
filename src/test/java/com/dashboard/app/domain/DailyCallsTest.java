package com.dashboard.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dashboard.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DailyCallsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DailyCalls.class);
        DailyCalls dailyCalls1 = new DailyCalls();
        dailyCalls1.setId(1L);
        DailyCalls dailyCalls2 = new DailyCalls();
        dailyCalls2.setId(dailyCalls1.getId());
        assertThat(dailyCalls1).isEqualTo(dailyCalls2);
        dailyCalls2.setId(2L);
        assertThat(dailyCalls1).isNotEqualTo(dailyCalls2);
        dailyCalls1.setId(null);
        assertThat(dailyCalls1).isNotEqualTo(dailyCalls2);
    }
}
