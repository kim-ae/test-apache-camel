package com.kimae.camel.config;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kimae.application.MessageProcessor;

@Component
public class AuditorWorkerRouter extends RouteBuilder {

    @Value("${broker.dead.letter.queue}")
    private String brokerDeadLetterQueue;

    @Value("${broker.in.queue}")
    private String brokerInQueue;

    @Value("${broker.out.queue}")
    private String brokerOutQueue;

    private MessageProcessor messageProcessor;

    public AuditorWorkerRouter(MessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
    }

    @Override
    public void configure() throws Exception {
        errorHandler(deadLetterChannel(brokerDeadLetterQueue)
                .useOriginalMessage()
                .retryAttemptedLogLevel(LoggingLevel.INFO));

        from(brokerInQueue)
                .process(messageProcessor)
                .to(brokerOutQueue);
    }
}
