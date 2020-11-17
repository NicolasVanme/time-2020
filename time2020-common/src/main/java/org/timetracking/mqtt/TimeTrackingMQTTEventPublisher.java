package org.timetracking.mqtt;

import org.timetracking.event.TimeTrackingEvent;

public interface TimeTrackingMQTTEventPublisher extends AutoCloseable {

    void publish(TimeTrackingEvent event);

}
