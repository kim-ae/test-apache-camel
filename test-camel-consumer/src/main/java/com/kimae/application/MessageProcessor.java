package com.kimae.application;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class MessageProcessor implements Processor  {

    @Override
    public void process(Exchange exchange) throws Exception {
        Object body = exchange.getIn().getBody();
        System.out.println("processando "+body.toString());
        try {
            Thread.sleep(1000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
