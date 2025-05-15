package com.example.Spring._boot_Camel;

import javax.jms.ConnectionFactory;

//import jakarta.jms.Connection;
import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;


public class FileToActivemq {
    public static void main(String[] args) throws Exception {

        CamelContext context = new DefaultCamelContext();

//        ConnectionFactory connectionFactory = (ConnectionFactory) new ActiveMQConnectionFactory("tcp://localhost:61616");
        String brokerURL = "tcp://localhost:61616";  // Example ActiveMQ broker URL
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);

        context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge((ConnectionFactory) connectionFactory));

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("file:/E:/SpringBootCamp/Spring-boot-Camel/input-box?noop=true")
                        .log("Processing file: ${file:name}")
                        .to("jms:queue:my-queue");
            }
        });
        while (true){
            context.start();
            System.out.println("Route is running...");
//            Thread.sleep(Long.MAX_VALUE);
        }

    }
}
