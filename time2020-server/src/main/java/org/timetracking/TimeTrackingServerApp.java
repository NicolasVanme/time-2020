package org.timetracking;

import org.timetracking.console.TimeTrackingConsole;
import org.timetracking.mqtt.TimeTrackingMQTTClientFactory;
import org.timetracking.mqtt.TimeTrackingMQTTEventReceiver;
import org.timetracking.persistence.InitialLoadService;
import org.timetracking.persistence.PersistenceService;
import org.timetracking.persistence.TimeTrackingEventPersistence;

public class TimeTrackingServerApp {

    public static void main(String[] args) {
        try (PersistenceService persistenceService = new PersistenceService();
             TimeTrackingMQTTEventReceiver timeTrackingMQTTClient = TimeTrackingMQTTClientFactory.createMQTTEventReceiver()) {

            // Load items in DB for test -> <property name="hbm2ddl.auto">create</property>
            new InitialLoadService(persistenceService).load();

            timeTrackingMQTTClient.addListener(new TimeTrackingEventPersistence(persistenceService));

            new TimeTrackingConsole(persistenceService).firstMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
