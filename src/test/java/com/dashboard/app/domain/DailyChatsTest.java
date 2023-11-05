package com.dashboard.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dashboard.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DailyChatsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DailyChats.class);
        DailyChats dailyChats1 = new DailyChats();
        dailyChats1.setId(1L);
        DailyChats dailyChats2 = new DailyChats();
        dailyChats2.setId(dailyChats1.getId());
        assertThat(dailyChats1).isEqualTo(dailyChats2);
        dailyChats2.setId(2L);
        assertThat(dailyChats1).isNotEqualTo(dailyChats2);
        dailyChats1.setId(null);
        assertThat(dailyChats1).isNotEqualTo(dailyChats2);
    }
}
