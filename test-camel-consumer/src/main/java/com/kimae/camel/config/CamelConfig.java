package com.kimae.camel.config;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CamelConfig {
    
    public final Boolean NON_TRANSACTED = false;

    @Value("${broker.in.produce.queue}")
    private String brokerInProduceQueue;

    @Value("${broker.user}")
    private String brokerUser;

    @Value("${broker.password}")
    private String brokerPassword;

    @Value("${broker.url}")
    private String brokerUrl;

    @Bean
    CamelContextConfiguration contextConfiguration() {
        return new CamelContextConfiguration() {
            @Override
            public void beforeApplicationStart(CamelContext context) {
                context.addComponent("auditor-worker", JmsComponent.jmsComponentAutoAcknowledge(jmsConnectionFactory()));
            }

            @Override
            public void afterApplicationStart(CamelContext camelContext) { }
        };
    }

    @Bean
    ConnectionFactory jmsConnectionFactory() {
        PooledConnectionFactory pool = new PooledConnectionFactory();
        pool.setConnectionFactory(new ActiveMQConnectionFactory(brokerUser, brokerPassword, brokerUrl));
        return pool;
    }

    private ActiveMQConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory(brokerUrl, brokerPassword, brokerUrl);
    }

    public Connection getConnection() throws JMSException {
        return connectionFactory().createConnection();
    }

    public Session getSession(Connection connection) throws JMSException {
        return connection.createSession(NON_TRANSACTED, Session.AUTO_ACKNOWLEDGE);
    }

    private Destination getQueue(Session session) throws JMSException {
        return session.createQueue(brokerInProduceQueue);
    }

    public TextMessage createMsg(String message, Session session) throws JMSException {
        return session.createTextMessage(message);
    }

    public MessageProducer messageProducer(Session session) throws JMSException {
        return session.createProducer(getQueue(session));
    }
}
