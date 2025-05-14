package com.example.Spring._boot_Camel;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class ProdAndConsumer {
    public static void main(String[] args) throws Exception {

        CamelContext context = new DefaultCamelContext();

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start")
                        .to("seda:end");
            }
        });
        context.start();
        ProducerTemplate producerTemplate = context.createProducerTemplate();
        producerTemplate.sendBody("direct:start", "Hello Apache");

        ConsumerTemplate consumerTemplate = context.createConsumerTemplate();
        String massage = consumerTemplate.receiveBody("seds", String.class);
        System.out.println(massage);
    }
}
