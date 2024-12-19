package com.prod.kafka.producers;

import com.common.utils.ConvertJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderProducer {
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    private ConvertJson convertJson;

    public void send(String key, Object obj) {
        try {
            String value = convertJson.convertToJson(obj);
            switch (key){
                case "create-order" -> {
                    kafkaTemplate.send("order-topic", key, value);
                }
                default -> {
                    log.error("Topic {} khong hop le", key);
                }
            }
        } catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
