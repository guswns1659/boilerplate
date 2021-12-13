package com.titanic.mvc.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class JackRabbitPublisher {

    private final RabbitTemplate rabbitTemplate;

    public JackRabbitPublisher(@Qualifier("oldRabbitTemplate") RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishMessage(String item) {
        rabbitTemplate.convertAndSend("items", item);
    }
}
