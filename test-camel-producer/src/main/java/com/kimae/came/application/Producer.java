package com.kimae.came.application;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kimae.camel.config.CamelConfig;


@Component
public class Producer {
    @Autowired
    private CamelConfig config;

    public void produceMessage(Integer id) {
        Connection connection = null;
        try {
            connection = config.getConnection();
            connection.start();
            Session session = config.getSession(connection);
            MessageProducer producer = config.messageProducer(session);
            producer.send(config.createMsg("ID: "+id.toString(), session));
            producer.close();
            session.close();
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
