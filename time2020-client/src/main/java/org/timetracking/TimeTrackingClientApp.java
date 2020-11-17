package org.timetracking;

import org.timetracking.event.TimeTrackingEvent;
import org.timetracking.mqtt.TimeTrackingMQTTClientFactory;
import org.timetracking.mqtt.TimeTrackingMQTTEventPublisher;

public class TimeTrackingClientApp {

    public static void main(String[] args) {
        try {
            TimeTrackingEvent timeTrackingEvent = new TimeTrackingEventCommandLineReader().read(args);

            System.out.println("Creating MQTT event publisher...");

            try (TimeTrackingMQTTEventPublisher mqttEventPublisher = TimeTrackingMQTTClientFactory.createMQTTEventSender()) {
                System.out.println("Sending '" + timeTrackingEvent.getType() + "' Time Tracking event...");

                mqttEventPublisher.publish(timeTrackingEvent);

                System.out.println("MQTT Time Tracking event successfully sent !");
            }
        } catch (Exception e) {
            System.err.println("Cannot read command line argument to create Time Tracking event");
            e.printStackTrace();
        }
    }
}
