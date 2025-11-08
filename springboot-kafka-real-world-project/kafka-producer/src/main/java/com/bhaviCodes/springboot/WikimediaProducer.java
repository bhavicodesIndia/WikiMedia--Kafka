package com.bhaviCodes.springboot;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import okhttp3.OkHttpClient;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.net.URI;


@Service
public class WikimediaProducer {

    private static final Logger logger = LoggerFactory.getLogger(WikimediaProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    public WikimediaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        logger.info("Wikimedia Producer initialized");
        this.kafkaTemplate = kafkaTemplate;
    }


    public void sendMessage(String topic) {
        logger.info("Starting Wikimedia stream for topic: {}", topic);

        String url = "https://stream.wikimedia.org/v2/stream/recentchange";
        EventHandler handler = new wikimediaChangeHandler(kafkaTemplate, topic);

        // ✅ Create OkHttpClient with required headers
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request requestWithUserAgent = original.newBuilder()
                            .header("User-Agent", "SpringBootKafkaProducer/1.0 (contact: bhavicodes@domain.com)")
                            .build();
                    return chain.proceed(requestWithUserAgent);
                })
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(0, TimeUnit.MINUTES) // no timeout for SSE
                .build();

        EventSource.Builder builder = new EventSource.Builder(handler, URI.create(url))
                .client(httpClient)
                .reconnectTime(Duration.ofSeconds(5));

        try (EventSource eventSource = builder.build()) {
            eventSource.start();
            Thread.currentThread().join();
        } catch (Exception e) {
            logger.error("Error in Wikimedia stream connection", e);
            Thread.currentThread().interrupt();
        }
    }
//
//
//    public void sendMessage(String topic) {
//        logger.info("Starting Wikimedia stream for topic: {}", topic);
//
//        String url = "https://stream.wikimedia.org/v2/stream/recentchange";
//        EventHandler handler = new wikimediaChangeHandler(kafkaTemplate, topic);
//
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(10, TimeUnit.SECONDS)
//                .readTimeout(0, TimeUnit.MINUTES) // never time out read
//                .build();
//
//        EventSource.Builder builder = new EventSource.Builder(handler, URI.create(url))
//                .client(okHttpClient)
//                .reconnectTime(Duration.ofSeconds(5)); // fixed retry delay
//
//        try (EventSource eventSource = builder.build()) {
//            eventSource.start();
//
//            // keep application thread alive (runs until you terminate manually)
//            Thread.currentThread().join();
//        } catch (Exception e) {
//            logger.error("Error in Wikimedia stream connection", e);
//            Thread.currentThread().interrupt();
//        }
//    }

//    public void sendMessage(String topic) {
//        logger.info("Sending Wikimedia stream data to topic: {}", topic);
//
//        try {
//            String url = "https://stream.wikimedia.org/v2/stream/recentchange";
//            EventHandler eventHandler = new wikimediaChangeHandler(kafkaTemplate, topic);
//
//            EventSource.Builder builder = new EventSource.Builder(eventHandler, URI.create(url))
//                    // Wait 5 seconds before reconnecting instead of exponential retries
//                    .reconnectTime(Duration.ofSeconds(5));
//
//            try (EventSource eventSource = builder.build()) {
//                eventSource.start();
//
//                // Keep running asynchronously for a controlled time
//                Thread.sleep(Duration.ofMinutes(10).toMillis());
//            }
//
//        } catch (Exception e) {
//            logger.error("Error while consuming Wikimedia stream", e);
//        }
//    }
}
