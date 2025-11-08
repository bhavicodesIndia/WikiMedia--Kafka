package com.bhaviCodes.springboot;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.MessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

public class wikimediaChangeHandler implements EventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(wikimediaChangeHandler.class);
    private KafkaTemplate<String, String> kafkaTemplate;
    private String topic = "wikimedia";

    public wikimediaChangeHandler(KafkaTemplate<String, String> kafkaTemplate, String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Override
    public void onOpen() {
        LOGGER.info("✅ Connected to Wikimedia stream");
    }

    @Override
    public void onClosed() {
        LOGGER.warn("❌ Stream closed, will reconnect...");
    }

    @Override
    public void onError(Throwable t) {
        LOGGER.error("⚠️ Stream error: ", t);
    }


    @Override
    public void onMessage(String s, MessageEvent messageEvent) throws Exception {
        LOGGER.info(String.format("onMessage: %s", messageEvent.getData().toString()));
        kafkaTemplate.send(topic, messageEvent.getData());
    }

    @Override
    public void onComment(String s) throws Exception {

    }

}
