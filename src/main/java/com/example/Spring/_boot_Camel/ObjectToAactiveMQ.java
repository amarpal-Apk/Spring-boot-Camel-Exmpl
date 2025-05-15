package com.example.Spring._boot_Camel;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

import javax.jms.ConnectionFactory;
import java.util.Date;

public class ObjectToAactiveMQ {

    public static void main(String[] args) throws Exception {

        CamelContext context = new DefaultCamelContext();
        String brokerURL = "tcp://localhost:61616";  // Example ActiveMQ broker URL
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);

        context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge((ConnectionFactory) connectionFactory));

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start")
                        .to("jms:queue:my-queue");
            }
        });
        context.start();

        ProducerTemplate producerTemplate = context.createProducerTemplate();
        Student std= new Student(1,"Amar");
        producerTemplate.sendBody("direct:start", std);

//        Thread.sleep(60000);
//        context.start();
    }
}
