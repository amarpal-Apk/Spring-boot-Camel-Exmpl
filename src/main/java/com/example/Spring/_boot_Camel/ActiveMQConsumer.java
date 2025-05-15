package com.example.Spring._boot_Camel;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

import javax.jms.ConnectionFactory;

public class ActiveMQConsumer {
    public static void main(String[] args) throws Exception {

        CamelContext context = new DefaultCamelContext();
        String brokerURL = "tcp://localhost:61616";  // Example ActiveMQ broker URL
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);

        context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge((ConnectionFactory) connectionFactory));

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("jms:queue:my-queue")
                        .to("seda:end");
            }
        });
        context.start();

        ConsumerTemplate consumerTemplate=context.createConsumerTemplate();

       String msg= consumerTemplate.receiveBody("seda:end",String.class);

        System.out.println(msg);

    }

}
