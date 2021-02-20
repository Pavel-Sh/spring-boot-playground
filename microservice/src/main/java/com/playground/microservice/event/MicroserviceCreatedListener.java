package com.playground.microservice.event;

import com.playground.microservice.web.MicroserviceController;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MicroserviceCreatedListener {

    public static Logger logger = LoggerFactory.getLogger(MicroserviceCreatedListener.class);

    @KafkaListener(topics = MicroserviceController.MICROSERVICE_CREATED_TOPIC, groupId = "test",concurrency = "20")
    public void listen(ConsumerRecord<?, ?> cr) throws Exception {
        logger.info(cr.toString());
    }
}
