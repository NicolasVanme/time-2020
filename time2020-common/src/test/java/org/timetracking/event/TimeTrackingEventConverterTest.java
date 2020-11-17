package org.timetracking.event;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TimeTrackingEventConverterTest {

    private final TimeTrackingEventConverter converter = new TimeTrackingEventConverter();

    @Test
    void shouldConvertToStringCorrectly() {
        TimeTrackingEvent timeTrackingEvent = mock(TimeTrackingEvent.class);
        when(timeTrackingEvent.getEmployeeId()).thenReturn(100);
        when(timeTrackingEvent.getTaskId()).thenReturn(200);
        when(timeTrackingEvent.getType()).thenReturn(TimeTrackingEventType.START);
        when(timeTrackingEvent.getEventDateTime()).thenReturn(toLocalDateTime(123456789));

        String eventAsString = converter.convertToString(timeTrackingEvent);
        assertThat(eventAsString).isEqualTo("100::200::START::123456789");
    }

    @Test
    void shouldConvertFromStringCorrectly() {
        TimeTrackingEvent timeTrackingEvent = converter.convertFromString("100::200::START::123456789");

        assertThat(timeTrackingEvent.getEmployeeId()).isEqualTo(100);
        assertThat(timeTrackingEvent.getTaskId()).isEqualTo(200);
        assertThat(timeTrackingEvent.getType()).isEqualTo(TimeTrackingEventType.START);
        assertThat(timeTrackingEvent.getEventDateTime()).isEqualTo(toLocalDateTime(123456789));
    }

    @Test
    void shouldThrowsExceptionWhenStringIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> converter.convertFromString(""));
    }

    @Test
    void shouldThrowsExceptionWhenStringIsNotComplete() {
        assertThrows(IllegalArgumentException.class, () -> converter.convertFromString("100::200::123456789"));
    }

    private LocalDateTime toLocalDateTime(long seconds) {
        return LocalDateTime.ofEpochSecond(seconds, 0, ZoneOffset.UTC);
    }

}