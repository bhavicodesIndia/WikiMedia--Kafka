package com.bhaviCodes.wikimediaConsumer;

import com.bhaviCodes.wikimediaConsumer.Entity.wikiMediaData;
import com.bhaviCodes.wikimediaConsumer.repository.wikiMediaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaDatabaseConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaDatabaseConsumer.class);

    private final wikiMediaRepository wikiMediaRepository;

    public KafkaDatabaseConsumer(wikiMediaRepository wikiMediaRepository) {
        this.wikiMediaRepository = wikiMediaRepository;
    }

    @KafkaListener(topics = "wikimedia" , groupId = "myGroup")
    public void consumer(String eventMessage)
    {
        LOGGER.info(String.format(
                "Consumer  message= [ %s  ]",eventMessage));
        wikiMediaData wikiData = new wikiMediaData();
        wikiData.setWikiMediaData(eventMessage);
        wikiMediaRepository.save(wikiData);
    }
}

