package org.timetracking;

import org.junit.jupiter.api.Test;
import org.timetracking.event.TimeTrackingEvent;
import org.timetracking.event.TimeTrackingEventException;
import org.timetracking.event.TimeTrackingEventType;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TimeTrackingEventCommandLineReaderTest {
    @Test
    void nullShouldAskForThreeArgs() {
        assertThatThrownBy(() -> read((String) null))
            .isInstanceOf(TimeTrackingEventException.class)
            .hasMessage("Three arguments expected");
    }

    @Test
    void twoArgsShouldAskForThreeArgs() {
        assertThatThrownBy(() -> read("",""))
                .isInstanceOf(TimeTrackingEventException.class)
                .hasMessage("Three arguments expected");
    }

    @Test
    void fourArgsShouldAskForThreeArgs() {
        assertThatThrownBy(() -> read("","","",""))
                .isInstanceOf(TimeTrackingEventException.class)
                .hasMessage("Three arguments expected");
    }

    @Test
    void firstArgsShouldBeNumber() {
        assertThatThrownBy(() -> read("A","",""))
                .isInstanceOf(TimeTrackingEventException.class)
                .hasMessage("Employee ID expected in first argument");
    }

    @Test
    void secondArgsShouldBeValidEvent() {
        assertThatThrownBy(() -> read("1","1",""))
                .isInstanceOf(TimeTrackingEventException.class)
                .hasMessage("Expected value in second arguments are: START, STOP");
    }

    @Test
    void thirdArgsShouldBeNumber() {
        assertThatThrownBy(() -> read("1","START","A"))
                .isInstanceOf(TimeTrackingEventException.class)
                .hasMessage("Task ID expected in third argument");
    }

    @Test
    void validArgsShouldReturnValidEvent() {
        TimeTrackingEvent event = read("1","START","13");
        assertThat(event).isNotNull();
        assertThat(event.getEmployeeId()).isEqualTo(1);
        assertThat(event.getType()).isEqualTo(TimeTrackingEventType.START);
        assertThat(event.getTaskId()).isEqualTo(13);
        assertThat(event.getEventDateTime()).isBetween(LocalDateTime.now().minusSeconds(1),LocalDateTime.now().plusSeconds(1));
    }

    private TimeTrackingEvent read(String... args){
        return new TimeTrackingEventCommandLineReader().read(args);
    }
}