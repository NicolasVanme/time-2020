package org.timetracking.mqtt;

public class TimeTrackingMQTTClientFactory {

    public static TimeTrackingMQTTEventPublisher createMQTTEventSender() {
        return new TimeTrackingMQTTClient();
    }

    public static TimeTrackingMQTTEventReceiver createMQTTEventReceiver() {
        return new TimeTrackingMQTTClient();
    }

}
