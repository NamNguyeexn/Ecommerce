package com.prod.kafka.listeners;

import com.common.utils.ConvertJson;
import com.prod.facades.DTO.OrderDTO;
import com.prod.facades.IOrderFacade;
import com.prod.kafka.consumers.MessageConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class OrderListener {
    @Autowired
    private IOrderFacade orderFacade;
    @Autowired
    private ConvertJson convertJson;
    @MessageConsumer(
            topicPartitions = {
                    @TopicPartition(
                            topic = "order-topic",
                            partitions = "0"
                    )
            }
    )
    public void listen(ConsumerRecord<String, String> record, Acknowledgment ack) {
        try {
            String key = record.key();
            String value = record.value();
            if (Objects.equals(key, "create-order")) {
                log.info("Received message: {} - {}", key, value);
                OrderDTO orderDTO = convertJson.convertFromJson(value, OrderDTO.class);
                orderFacade.createOrder(orderDTO);
            }
            ack.acknowledge();
        } catch (Exception e) {
            log.error(e.getMessage());
            ack.acknowledge();
        }
    }

}
