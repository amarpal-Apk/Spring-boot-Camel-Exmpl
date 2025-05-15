package com.example.Spring._boot_Camel;

import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class ProdAndConsumer {
    public static void main(String[] args) throws Exception {

        CamelContext context = new DefaultCamelContext();

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start")
                        .process(new Processor() {
                            @Override
                            public void process(Exchange exchange) throws Exception {
                                String msg=exchange.getIn().getBody(String.class);
                                msg+=" :- By Amar Pal";
                                System.out.println("This is processor");
                                exchange.getOut().setBody(msg);

                            }
                        })
                        .to("seda:end");
            }
        });
        context.start();
        ProducerTemplate producerTemplate = context.createProducerTemplate();
        producerTemplate.sendBody("direct:start", "Hello Apache");

        ConsumerTemplate consumerTemplate = context.createConsumerTemplate();
        String massage = consumerTemplate.receiveBody("seda:end", String.class);
        System.out.println(massage);
    }
}
