package org.timetracking.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.timetracking.event.TimeTrackingEvent;
import org.timetracking.event.TimeTrackingEventConverter;
import org.timetracking.event.TimeTrackingEventException;
import org.timetracking.event.TimeTrackingEventListener;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

class TimeTrackingMQTTClient implements TimeTrackingMQTTEventPublisher, TimeTrackingMQTTEventReceiver {

    // Open online MQTT broker from Eclipse for testing (should be retrieved from config file)
    private static final String DEFAULT_MQTT_URI = "tcp://mqtt.eclipse.org:1883";
    private static final String TIME_TRACKING_TOPIC = "time-tracking-topic";
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    protected final String clientId;
    protected final IMqttClient mqttClient;
    protected final MqttClientPersistence persistence;
    protected final List<TimeTrackingEventListener> listeners;
    protected final TimeTrackingEventConverter converter;

    protected TimeTrackingMQTTClient() {
        this(DEFAULT_MQTT_URI);
    }

    protected TimeTrackingMQTTClient(String mqttBrokerUri) {
        this(UUID.randomUUID().toString(), mqttBrokerUri);
    }

    protected TimeTrackingMQTTClient(String clientId, String mqttBrokerUri) {
        Objects.requireNonNull(clientId, "Client ID is mandatory");
        Objects.requireNonNull(mqttBrokerUri, "Hostname is mandatory");

        this.clientId = clientId;

        try {
            persistence = new MemoryPersistence();
            mqttClient = new MqttClient(mqttBrokerUri, clientId, persistence);
            connect();
        } catch (MqttException e) {
            throw new TimeTrackingEventException("Cannot connect to MQTT broker : " + mqttBrokerUri, e);
        }

        listeners = new ArrayList<>();
        converter = new TimeTrackingEventConverter();
    }

    @Override
    public void close() {
        if (mqttClient.isConnected()) {
            try {
                mqttClient.disconnect();
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }

        try {
            mqttClient.close();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void publish(TimeTrackingEvent event) {
        Objects.requireNonNull(event, "Event is mandatory");

        try {
            String eventAsString = converter.convertToString(event);
            mqttClient.publish(TIME_TRACKING_TOPIC, new MqttMessage(eventAsString.getBytes(CHARSET)));
        } catch (MqttException e) {
            throw new TimeTrackingEventException("Cannot send message to MQTT broker", e);
        }
    }


    public void addListener(TimeTrackingEventListener listener) {
        Objects.requireNonNull(listener, "Listener is mandatory");

        boolean shouldSubscribeOnFirstListenerRegistered = listeners.isEmpty();
        if (!listeners.contains(listener)) {
            listeners.add(listener);

            if (shouldSubscribeOnFirstListenerRegistered) {
                try {
                    mqttClient.subscribe(TIME_TRACKING_TOPIC, this::sendEventToListeners);
                } catch (MqttException e) {
                    throw new TimeTrackingEventException("Cannot subscribe to MQTT broker", e);
                }
            }
        }
    }

    private void connect() throws MqttException {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        mqttClient.connect(options);
    }

    private void sendEventToListeners(String topic, MqttMessage message) {
        String serializedMessage = new String(message.getPayload(), CHARSET);
        System.out.println("\nReceived MQTT message : " + serializedMessage);

        TimeTrackingEvent event = converter.convertFromString(serializedMessage);
        for (TimeTrackingEventListener listener : listeners) {
            listener.onEvent(event);
        }
    }
}
