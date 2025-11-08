package com.bhaviCodes.wikimediaConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaDatabaseConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaDatabaseConsumer.class);

    @KafkaListener(topics = "wikimedia" , groupId = "myGroup")
    public void consumer(String eventMessage)
    {
        LOGGER.info(String.format(
                "Consumer  message= [ %s ]",eventMessage));
    }
}

