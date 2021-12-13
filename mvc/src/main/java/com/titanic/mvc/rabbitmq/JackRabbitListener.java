package com.titanic.mvc.rabbitmq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JackRabbitListener {

    @RabbitListener(queues = "items", containerFactory = "oldListenerContainerFactory")
    public void receiveMessage(@Payload Message message) {
        log.info("message : {}", message);
    }
}
