package com.example.Spring._boot_Camel;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.SimpleRegistry;

import javax.jms.ConnectionFactory;
import java.util.Map;

public class CallMethodUsingClassComponent {

    public static void main(String[] args) throws Exception {

        MyServive myServive=new MyServive();
        SimpleRegistry simpleRegistry=new SimpleRegistry();
        simpleRegistry.put("myService", (Map<Class<?>, Object>) myServive);
        CamelContext context = new DefaultCamelContext(simpleRegistry);

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
//                from("direct:start")
//                        .to("class:com.example.Spring._boot_Camel.MyServive?method=doSomething");
                from("direct:start")
                        .to("bean:myService?method=doSomething");
            }
        });

        context.start();

        ProducerTemplate producerTemplate=context.createProducerTemplate();
        producerTemplate.sendBody("direct:start", "hello");



    }
}
