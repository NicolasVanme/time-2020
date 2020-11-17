package org.timetracking.mqtt;

import org.timetracking.event.TimeTrackingEventListener;

public interface TimeTrackingMQTTEventReceiver extends AutoCloseable {

    void addListener(TimeTrackingEventListener listener);

}
